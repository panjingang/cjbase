package com.xcz.afcs.biz.aspect;

import com.xcz.afcs.api.result.ListResult;
import com.xcz.afcs.api.result.ObjectResult;
import com.xcz.afcs.api.result.PageResult;
import com.xcz.afcs.api.util.ResultValueUtil;
import com.xcz.afcs.core.enums.BaseErrorCode;
import com.xcz.afcs.core.exception.BaseBusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class ExceptionHandleAspect {

	private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandleAspect.class);

	public ExceptionHandleAspect() {
		LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + getClass().getName() + " loaded");
	}

	public Object handleException(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Method method = methodSignature.getMethod();
		Class<?> returnType = method.getReturnType();
		Object result = null;
		try {
			result = pjp.proceed();
		}catch (BaseBusinessException e) {
			return this.handleMethodReturnType(returnType, e.getRetCode(), e.getMessage());
		}
		catch (Exception e) {
			LOG.error("执行失败", e);
			return this.handleMethodReturnType(returnType, BaseErrorCode.COMMON_ERROR.getCode(), BaseErrorCode.COMMON_ERROR.getMsg());
		}
		return result;
	}


	/**
	 *
	 * @description 不同类型的返回结果不同的处理
	 * @param returnType
	 * @param errorCode
	 * @param message
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object handleMethodReturnType(Class<?> returnType, Integer errorCode, String message) {
		if (PageResult.class.isAssignableFrom(returnType)) {
			return ResultValueUtil.genErrorPageResult(returnType, errorCode, message);
		} else if (ListResult.class.isAssignableFrom(returnType)) {
			return ResultValueUtil.genErrorListResult(returnType, errorCode, message);
		} else if (ObjectResult.class.isAssignableFrom(returnType)) {
			return ResultValueUtil.genErrorObjectResult(returnType, errorCode, message);
		}
		else {
			return ResultValueUtil.genBaseResult(errorCode, message);
		}
	}
}
