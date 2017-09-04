package com.xcz.afcs.biz.aspect;

import com.alibaba.fastjson.JSON;
import com.xcz.afcs.api.param.BaseParameter;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    private ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    @Setter
    private String from = "client";

    @Setter
    private String local = "server";

    private String inMark;

    private String outMark;

    public LogAspect() {
        LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + getClass().getName() + " loaded");
    }
    
    public void beforeLog(ProceedingJoinPoint pjp) {
        startTime.set(System.currentTimeMillis());
        if (pjp.getArgs() != null) {
            Object param = pjp.getArgs()[0];
            if (param instanceof BaseParameter) {
                LOG.info(getInMark()+ " "+JSON.toJSONString(param));
            }
        }else{
            LOG.info(getInMark() + " {}");
        }
    }

    public void afterLog(Object result) {
        LOG.info(getOutMark()+ " "+JSON.toJSONString(result)+" 处理时间:"+(System.currentTimeMillis()-startTime.get())+" 毫秒");
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
