package com.xcz.afcs.api.util;

import com.xcz.afcs.api.result.BaseResult;
import com.xcz.afcs.api.result.ListResult;
import com.xcz.afcs.api.result.ObjectResult;
import com.xcz.afcs.api.result.PageResult;
import com.xcz.afcs.api.view.BaseView;
import com.xcz.afcs.core.base.ErrorCodeEnumMessage;
import com.xcz.afcs.core.enums.BaseErrorCode;

import java.util.Collection;
import java.util.List;

public class ResultValueUtil {

    public static boolean isError(BaseResult result){
        return !isSuccess(result);
    }

    public static boolean isSuccess(BaseResult result){
        return BaseErrorCode.SUCCESS.getCode().equals(result.getRetCode());
    }

    public static BaseResult genSuccessBaseResult() {
        return genBaseResult(BaseErrorCode.SUCCESS.getCode(), BaseErrorCode.SUCCESS.getMsg());
    }

    public static BaseResult genBaseResult(ErrorCodeEnumMessage errorCode) {
        return genBaseResult(errorCode.getCode(), errorCode.getMsg());
    }

    public static BaseResult genBaseResult(Integer retCode, String retMsg) {
        BaseResult result = new BaseResult();
        result.setRetCode(retCode);
        result.setRetMsg(retMsg);
        return result;
    }


    public static <T> ObjectResult<T> genSuccessObjectResult(T data){
        ObjectResult<T> result = new ObjectResult<T>();
        result.setData(data);
        result.setRetCode(BaseErrorCode.SUCCESS.getCode());
        result.setRetMsg(BaseErrorCode.SUCCESS.getMsg());
        return result;
    }

    public static <T> ObjectResult<T> genErrorObjectResult(Class<T> viewCls, ErrorCodeEnumMessage errorCode) {
        return genErrorObjectResult(viewCls, errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> ObjectResult<T> genErrorObjectResult(Class<T> viewCls, Integer retCode, String retMsg) {
        ObjectResult<T> result = new ObjectResult<T>();
        result.setRetCode(retCode);
        result.setRetMsg(retMsg);
        return result;
    }


    public static <T> ListResult<T> genSuccessListResult(Collection<T> data){
        ListResult<T> result = new ListResult<T>();
        result.setData(data);
        result.setRetCode(BaseErrorCode.SUCCESS.getCode());
        result.setRetMsg(BaseErrorCode.SUCCESS.getMsg());
        return result;
    }

    public static <T> ListResult<T> genErrorListResult(Class<T> viewCls, ErrorCodeEnumMessage errorCode) {
        return genErrorListResult(viewCls, errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> ListResult<T> genErrorListResult(Class<T> viewCls, Integer retCode, String retMsg) {
        ListResult<T> result = new ListResult<T>();
        result.setRetCode(retCode);
        result.setRetMsg(retMsg);
        return result;
    }

    public static <T> PageResult<T> genSuccessPageResult(Collection<T> data, Integer total, Integer pageSize, Integer pageNum) {
        PageResult<T> result = new PageResult<T>();
        result.setRetCode(BaseErrorCode.SUCCESS.getCode());
        result.setRetMsg(BaseErrorCode.SUCCESS.getMsg());
        result.setData(data);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setTotal(total);
        return result;
    }

    public static <T> PageResult<T> genErrorPageResult(Class<T> viewCls, ErrorCodeEnumMessage errorCode) {
        return genErrorPageResult(viewCls, errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> PageResult<T> genErrorPageResult(Class<T> viewCls, Integer retCode, String retMsg){
        PageResult<T> result = new PageResult<T>();
        result.setRetCode(retCode);
        result.setRetMsg(retMsg);
        return result;
    }

}
