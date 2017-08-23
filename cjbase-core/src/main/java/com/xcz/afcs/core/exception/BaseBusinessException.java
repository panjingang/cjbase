package com.xcz.afcs.core.exception;


import com.xcz.afcs.core.enums.BaseErrorCode;

public class BaseBusinessException extends RuntimeException {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    protected int retCode;
    
    protected String retMsg;
    
    public BaseBusinessException(int retCode, String retMsg) {
        super(retMsg);
        this.retCode = retCode;
        this.retMsg = retMsg;
    }
    
    public BaseBusinessException(String message, int retCode, String retMsg) {
        super(message);
        this.retCode = retCode;
        this.retMsg = retMsg;
    }
    
    public BaseBusinessException(String message, Throwable cause, int retCode, String retMsg) {
        super(message, cause);
        this.retCode = retCode;
        this.retMsg = retMsg;
    }
    
    public BaseBusinessException(BaseErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMsg());
    }
    
    public BaseBusinessException(String message, BaseErrorCode errorCode) {
        this(message, errorCode.getCode(), errorCode.getMsg());
    }
    
    public BaseBusinessException(String message, Throwable cause, BaseErrorCode errorCode) {
        this(message, cause, errorCode.getCode(), errorCode.getMsg());
    }
    
    public BaseBusinessException(BaseErrorCode errorCode, String extraInfo) {
        this(errorCode.getCode(), buildErrorInfoWithExtraInfo(errorCode, extraInfo));
    }
    
    public BaseBusinessException(String message, BaseErrorCode errorCode, String extraInfo) {
        this(message, errorCode.getCode(), buildErrorInfoWithExtraInfo(errorCode, extraInfo));
    }
    
    public BaseBusinessException(String message, Throwable cause, BaseErrorCode errorCode, String extraInfo) {
        this(message, cause, errorCode.getCode(), buildErrorInfoWithExtraInfo(errorCode, extraInfo));
    }
    
    public static String buildErrorInfoWithExtraInfo(BaseErrorCode errorCode, String extraInfo) {
        return new StringBuilder().append(errorCode.getMsg()).append('[').append(extraInfo).append(']')
                .toString();
    }
    
    public int getRetCode() {
        return retCode;
    }
    
    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }
    
    public String getRetMsg() {
        return retMsg;
    }
    
    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
    
}
