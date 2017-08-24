package com.xcz.afcs.core.enums;

import com.xcz.afcs.core.base.ErrorCodeEnumMessage;

public enum BaseErrorCode implements ErrorCodeEnumMessage {
    
    /*
     * 执行成功：1
     */
    SUCCESS(1, "执行成功"),
    
    /*
     * 通用错误：1001~ 1999
     */
    COMMON_ERROR(1001, "系统繁忙，请稍后在试"),
    CONCURRENT_MODIFY(1002, "并发修改失败"),
    ID_NO_ERROR(1003, "身份证号码错误"),
    PARAM_ERROR(1004, "参数错误"),
    INVOKE_REMOTE_ERROR(1005, "远程调用失败"),
    NOT_LOGINED(1006, "用户未登录"),
    NO_PERMISSION(1007, "您的权限不足"),
    REMOTE_CONNECT_ERROR(1009, "无法连接远程服务"),
    READ_TIMEOUT_ERROR(1010, "远程读取数据超时"),
    ;
    
    private Integer code;
    
    private String msg;

    private String toString;
    
    BaseErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
