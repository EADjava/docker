package com.fintech.modules.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author xujunqi
 * @date: 2017/11/10 14:04
 * @description: 获取spring上下文对象信息
 *               静态类方法:
 *                  1)返回spring上下文对象: applicationContext
 *                  2)通过spring上下文对象获取指定对象的实例信息, 包括3个重载的方法实现
 */
@Component
public class ApplicationContextProvider
        implements ApplicationContextAware {

    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextProvider.applicationContext = applicationContext;
    }

    /**
     * @author xujunqi
     * @date: 2017/11/16 19:28
     * @description: 获取applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @author: xujunqi
     * @date: 2017/11/16 19:28
     * @description: 通过name获取 Bean.
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * @author: xujunqi
     * @date: 2017/11/16 19:28
     * @description: 通过class获取Bean.
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * @author: xujunqi
     * @date: 2017/11/16 19:28
     * @description: 通过name,以及Clazz返回指定的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
