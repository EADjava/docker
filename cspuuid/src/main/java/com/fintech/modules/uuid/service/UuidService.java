package com.fintech.modules.uuid.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xujunqi
 * @date: 2018/1/20 15:19
 * @description: uuid 生成服务
 */
@Service
public class UuidService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    // uuid redis 命名空间前缀
    private final static String UUID_REDIS_KEY_PREFIX = "CSPUUID:BIZ:";
    // 默认业务前缀, 也是rediskey的最后一位
    private final static String DEFAULT_BIZ_PREFIX_KEY = "JY";
    // uuid redis 默认KEY名称
    private final static String DEFAULT_KEY = UUID_REDIS_KEY_PREFIX + DEFAULT_BIZ_PREFIX_KEY;
    // 默认增量
    private final static char PAD_CHAR = '0';
    // 最大补齐长度
    private final static int MAX_PAD_SIZE_8 = 8;
    // 默认增量
    private final static int DEFAULT_DELTA = 1;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public long generate() {
        return generate(DEFAULT_KEY);
    }

    public Long generate(String key) {
        if (StringUtils.isBlank(key)) {
            key = DEFAULT_BIZ_PREFIX_KEY;
        }
        return generate(key, DEFAULT_DELTA);
    }

    public Long generate(String key, int delta) {
        Assert.notNull(key, "key为空");
        Assert.notNull(delta, "delta为空");
        logger.debug("请求生成UUID-Long key={} delta={}", key, delta);

        Long uuid = null;
        try {
            uuid = redisTemplate.opsForValue().increment(key, delta);
            logger.debug("UUID-Long生成成功 key={} delta={} uuid={}", key, delta, uuid);
        } catch (Exception e) {
            logger.error("请求生成UUID-Long异常", e);
        }
        return uuid;
    }

    /**
     * 生产UUID时是否追加bizKey 
     * @param key
     * @param appendKey
     * @return
     */
    public String generateBizUuid(String key,boolean appendKey) {
        String redisKey = key;
        int padSize = MAX_PAD_SIZE_8;

        if (StringUtils.isBlank(key)) {
            key = "";
            redisKey = DEFAULT_BIZ_PREFIX_KEY;
            padSize = MAX_PAD_SIZE_8;
        }
        if(appendKey){
            return this.generateBizUuid(key, redisKey, DEFAULT_DELTA, padSize);
        }else{
            return this.generateBizUuid("", redisKey, DEFAULT_DELTA, padSize);
        }
        
    }

    public String generateBizUuid(String key, String redisKey, int delta, int padSize) {
        Assert.notNull(redisKey, "redisKey为空");
        Assert.notNull(delta, "delta为空");
        logger.debug("请求生成UUID-Long key={} delta={}", key, delta);

        String bizUuid = null;
        try {
            DateFormat df = new SimpleDateFormat("yyMMdd");
            String dateStr = df.format(new Date());

            String bizRedisKey = new StringBuffer(UUID_REDIS_KEY_PREFIX).append(redisKey).append(":").append("ID").toString();
            String bizDateRedisKey = new StringBuffer(UUID_REDIS_KEY_PREFIX).append(redisKey).append(":").append("DATE").toString();

            ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
            Object redisDateObject = valueOps.get(bizDateRedisKey);
            if (redisDateObject != null) {
                String redisDateStr = (String) redisDateObject;
                if (!dateStr.equals(redisDateStr)) {
                    logger.debug("生成UUID-Biz, 日切换[{}]开始", dateStr);
                    try {
                        redisTemplate.multi();
                        valueOps.set(bizDateRedisKey, dateStr);
                        valueOps.set(bizRedisKey, 0);
                        redisTemplate.exec();
                        logger.debug("生成UUID-Biz, 日成功切换为: {}", dateStr);
                    } catch (Exception e) {
                        logger.error("生成UUID-Biz, 日切换异常", e);
                        redisTemplate.discard();
                    }
                }
            } else {
                valueOps.set(bizDateRedisKey, dateStr);
            }
            Long nextUuid = valueOps.increment(bizRedisKey, delta);
            bizUuid = new StringBuilder(key)
                    .append(dateStr)
                    .append(StringUtils.leftPad(nextUuid + "", padSize, PAD_CHAR))
                    .toString();
            logger.debug("UUID-Biz生成成功 key={} delta={} bizUuid={}", key, delta, bizUuid);
        } catch (Exception e) {
            logger.error("请求生成UUID-Biz异常", e);
        }

        return bizUuid;
    }
}
