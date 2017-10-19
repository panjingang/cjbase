package com.xcz.afcs.biz.aspect;

import com.alibaba.fastjson.JSON;
import com.xcz.afcs.api.param.BaseParameter;
import com.xcz.afcs.core.jackson.PrintObjectMapper;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public abstract class LogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    protected static final PrintObjectMapper objectMapper = new PrintObjectMapper();

    @Setter
    private String from = "client";

    @Setter
    private String local = "server";

    private String inMark;

    private String outMark;

    public LogAspect() {
        LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + getClass().getName() + " loaded");
    }
    
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            if (pjp.getArgs() != null) {
                Object param = pjp.getArgs()[0];
                if (param instanceof BaseParameter) {
                    LOG.info(getInMark() + " " + objectMapper.writeValueAsString(param));
                }
            } else {
                LOG.info(getInMark() + " {}");
            }
            result = pjp.proceed();
            return result;
        }
        finally{
            LOG.info(getOutMark()+ " "+objectMapper.writeValueAsString(result)+" 处理时间:"+(System.currentTimeMillis()-startTime)+" 毫秒");
        }
    }


    public String getInMark() {
        if (null == inMark) {
            inMark = '[' + from + "->" + local + ']';
        }
        return inMark;
    }

    public String getOutMark() {
        if (null == outMark) {
            outMark = '[' + from + "<-" + local + ']';
        }
        return outMark;
    }

}
