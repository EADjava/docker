package com.fintech.modules.base.util;

import java.util.Locale;

/**
 * @author xujunqi
 * @date: 2018/01/20 14:15:00
 * @description: 微服务平台自定义异常类
 */
public class CspException extends Exception {

    private Locale local;

    /**
     * @fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public CspException() {
        super();
    }

    /**
     * @param message
     */
    public CspException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public CspException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public CspException(String message, Throwable cause) {
        super(message, cause);
    }
}