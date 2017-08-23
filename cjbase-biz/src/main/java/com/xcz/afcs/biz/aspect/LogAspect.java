package com.xcz.afcs.biz.aspect;

import com.xcz.afcs.api.param.BaseParameter;
import com.xcz.afcs.util.PrintUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class LogAspect {

    private static final String CLASS_NAME = LogAspect.class.getName();

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    private String from = "";
    
    private String local = "";
    
    private String inMark;
    
    private String outMark;
    
    public LogAspect() {
        LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + CLASS_NAME + " loaded");
    }
    
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        if (pjp.getArgs() != null) {
            Object param = pjp.getArgs()[0];
            if (param instanceof BaseParameter) {
                LOG.info(getInMark() + PrintUtil.printObjectData(param));
            }
        }else{
            LOG.info(getInMark() + "{}");
        }
        Object result = null;
        try {
            result = pjp.proceed();
        } finally {
            LOG.info(getOutMark() + PrintUtil.printObjectData(result));
            // MDC的clear在这里限制了这个切面的通用性，本来这个切面可以用在任何边界（不仅是外部边界）
            MDC.clear();
        }
        return result;
    }
    
    public String getFrom() {
        return from;
    }
    
    public void setFrom(String from) {
        if (null == from) {
            from = "";
        }
        this.from = from;
        inMark = null;
    }
    
    public String getLocal() {
        return local;
    }
    
    public void setLocal(String local) {
        if (null == local) {
            local = "";
        }
        this.local = local;
        inMark = null;
    }
    
    public String getInMark() {
        if (null == inMark) {
            inMark = '[' + from + "->" + local + ']';
        }
        return inMark;
    }
    
    public void setInMark(String inMark) {
        this.inMark = inMark;
    }
    
    public String getOutMark() {
        if (null == outMark) {
            outMark = '[' + from + "<-" + local + ']';
        }
        return outMark;
    }
    
    public void setOutMark(String outMark) {
        this.outMark = outMark;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LoggerAspect [from=").append(from).append(", local=").append(local).append(", inMark=")
                .append(inMark).append(", outMark=").append(outMark).append("]");
        return builder.toString();
    }
    
}
