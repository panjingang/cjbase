package com.xcz.afcs.core.util;

import com.xcz.afcs.core.common.Fields;
import com.xcz.afcs.core.enums.BaseErrorCode;
import com.xcz.afcs.util.ValueUtil;

import java.util.HashMap;
import java.util.Map;

public class VOUtil {
    
    public static Map<String, Object> genSuccessResult() {
        Map<String, Object> result = new HashMap<String, Object>();
        return setSuccessResult(result);
    }
    
    public static Map<String, Object> genSuccessResult(String errorInfo) {
        return genErrorResult(BaseErrorCode.SUCCESS.getCode(), errorInfo);
    }
    
    public static Map<String, Object> setSuccessResult(Map<String, Object> result) {
        result.put(Fields.RET_CODE, BaseErrorCode.SUCCESS.getCode());
        return result;
    }
    
    public static Map<String, Object> setSuccessResult(Map<String, Object> result, String errorInfo) {
        return setErrorResult(result, BaseErrorCode.SUCCESS.getCode(), errorInfo);
    }
    
    public static Map<String, Object> genCommonErrorResult() {
        return genErrorResult(BaseErrorCode.COMMON_ERROR);
    }
    
    public static Map<String, Object> genCommonErrorResult(String errorInfo) {
        return genErrorResult(BaseErrorCode.COMMON_ERROR.getCode(), errorInfo);
    }
    
    public static Map<String, Object> genErrorResult(BaseErrorCode errorCode) {
        return genErrorResult(errorCode.getCode(), errorCode.getMsg());
    }
    
    public static Map<String, Object> genErrorResult(int errorNo, String errorInfo) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Fields.RET_CODE, errorNo);
        result.put(Fields.RET_MSG, errorInfo);
        return result;
    }
    
    public static Map<String, Object> genErrorResultWithExtraInfo(BaseErrorCode errorCode, String extraInfo) {
        return genErrorResult(errorCode.getCode(), buildErrorInfoWithExtraInfo(errorCode, extraInfo));
    }
    
    public static Map<String, Object> setCommonErrorResult(Map<String, Object> result) {
        return setErrorResult(result, BaseErrorCode.COMMON_ERROR);
    }
    
    public static Map<String, Object> setErrorResult(Map<String, Object> result, BaseErrorCode errorCode) {
        return setErrorResult(result, errorCode.getCode(), errorCode.getMsg());
    }
    
	public static Map<String, Object> setErrorResult(Map<String, Object> result, int errorNo, String errorInfo) {
		result.put(Fields.RET_CODE, errorNo);
		result.put(Fields.RET_MSG, errorInfo);
		return result;
	}
    
    public static Map<String, Object> setErrorResultWithExtraInfo(Map<String, Object> result, BaseErrorCode errorCode,
            String extraInfo) {
        return setErrorResult(result, errorCode.getCode(), buildErrorInfoWithExtraInfo(errorCode, extraInfo));
    }
    
    public static String buildErrorInfoWithExtraInfo(BaseErrorCode errorCode, String extraInfo) {
        return new StringBuilder().append(errorCode.getMsg()).append('[').append(extraInfo).append(']')
                .toString();
    }
    
    public static boolean isSuccess(Map<String, Object> result) {
        return null != result
                && BaseErrorCode.SUCCESS.getCode() == ValueUtil.getInt(result, Fields.RET_CODE,
                        BaseErrorCode.SUCCESS.getCode());
    }
    
    public static boolean isErrorOf(Map<String, Object> result, BaseErrorCode errorCode) {
        if (null != result) {
            Object error_no = result.get(Fields.RET_CODE);
            if (null != error_no) {
                return String.valueOf(errorCode.getCode()).equals(String.valueOf(error_no));
            }
        }
        return false;
    }
    
    public static boolean isErrorOf(Map<String, Object> result, int errorNo) {
        if (null != result) {
            Object error_no = result.get(Fields.RET_CODE);
            if (null != error_no) {
                return String.valueOf(errorNo).equals(String.valueOf(error_no));
            }
        }
        return false;
    }
    
}
