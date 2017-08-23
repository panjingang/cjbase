package com.xcz.afcs.biz.aspect;

import com.xcz.afcs.api.param.BaseParameter;
import com.xcz.afcs.validate.ValidateUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateAspect {
    
    private static final String CLASS_NAME = ValidateAspect.class.getName();

    private static final Logger LOG = LoggerFactory.getLogger(ValidateAspect.class);
    
    public ValidateAspect() {
        LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + CLASS_NAME + " loaded");
    }
    
    public Object validateParam(ProceedingJoinPoint pjp) throws Throwable {
        for (Object arg : pjp.getArgs()) {
            if (arg instanceof BaseParameter) {
            	ValidateUtil.validOrThrowException(arg);
            } else {
                // ignore;
            }
        }
        
        return pjp.proceed();
    }
    
}
