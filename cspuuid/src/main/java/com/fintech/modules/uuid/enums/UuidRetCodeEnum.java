package com.fintech.modules.uuid.enums;

/**
 * @author xujunqi
 * @date: 2018/1/25 9:58
 * @description: 返回值枚举类
 */
public enum UuidRetCodeEnum {
    CSHX_RETCODE_SUCC("0000", "SUCCESS"),
    CSHX_RETCODE_VALID("1000", "CHECK_FAIL"),
    CSHX_RETCODE_FAIL("2000", "FAIL");

    UuidRetCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static UuidRetCodeEnum getRetCodeEnumByCode(String retCode) {
        for (UuidRetCodeEnum retCodeEnum : UuidRetCodeEnum.values()) {
            if (retCodeEnum.getCode().equals(retCode)) {
                return retCodeEnum;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("[");
        sb.append("retCode='").append(code).append('\'');
        sb.append(", retMsg='").append(msg).append('\'');
        sb.append(']');
        return sb.toString();
    }
}
