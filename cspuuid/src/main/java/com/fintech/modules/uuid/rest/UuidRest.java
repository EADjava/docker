package com.fintech.modules.uuid.rest;

import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fintech.modules.uuid.dto.UuidReqDTO;
import com.fintech.modules.uuid.dto.UuidRespDTO;
import com.fintech.modules.uuid.enums.UuidRetCodeEnum;
import com.fintech.modules.uuid.service.UuidService;

/**
 * @author xujunqi
 * @date: 2018/1/20 14:22
 * @description: uuid Rest接口调用类
 */
@RestController
@RequestMapping("/api/uuid")
public class UuidRest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UuidService uuidService;

    @ResponseBody
    @RequestMapping(path = "/generate", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ApiOperation(nickname = "generate", value = "获取流水号，默认返回字符串", notes = "bizKey为必填参数，默认为'JY'",response=UuidRespDTO.class)
    public UuidRespDTO generateUuid(@Valid @RequestBody UuidReqDTO uuidReqDTO, BindingResult result) {
        logger.info("开始处理UUID申请请求: {}", JSONObject.toJSONString(uuidReqDTO));
        UuidRespDTO uuidRespDTO = new UuidRespDTO(uuidReqDTO.getBizKey());
        String uuid = null;
        try {
            if (result.hasErrors()) {
                StringBuffer errorSb = new StringBuffer("req param CHECK_FAIL: ");
                for (ObjectError error : result.getAllErrors()) {
                    errorSb.append(error.getDefaultMessage()).append("; ");
                }
                uuidRespDTO.fail(UuidRetCodeEnum.CSHX_RETCODE_VALID.getCode(),errorSb.toString());
                logger.warn("UUID请求不符合校验: {}", uuidRespDTO);
                return uuidRespDTO;
            }
            String appendBizKey = uuidReqDTO.getAppendBizKey();
            if("N".equals(appendBizKey)){
                //返回纯数字格式的UUID
                uuid = uuidService.generateBizUuid(uuidReqDTO.getBizKey(),false);
            }else{
                //返回 bizKey +数字格式的UUID
                uuid = uuidService.generateBizUuid(uuidReqDTO.getBizKey(),true);
            }
        } catch (Exception e) {
            uuidRespDTO.fail(UuidRetCodeEnum.CSHX_RETCODE_FAIL);
            logger.error("处理UUID申请异常明细: ", e);
        }
        uuidRespDTO = new UuidRespDTO(uuidReqDTO.getBizKey(), uuid);

        logger.info("处理UUID申请处理完成: {}", uuidRespDTO);
        return uuidRespDTO;
    }

    /**
     * 定义非法请求时的统一异常反馈信息
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public  UuidRespDTO handleInvalidRequestError(Exception ex) {
        logger.error("generateUuid handleInvalidRequestError : ", ex);
        String errorMsg = ex.getMessage();
        if(errorMsg == null) errorMsg="null";
        errorMsg.replaceAll("[\\t\\n\\r]", "").replaceAll("[\\\\]", ".").replaceAll("\"", "");

        UuidRespDTO uuidRespDTO = new UuidRespDTO();
        uuidRespDTO.fail(UuidRetCodeEnum.CSHX_RETCODE_FAIL.getCode(),errorMsg);
        return uuidRespDTO;
    }
}
