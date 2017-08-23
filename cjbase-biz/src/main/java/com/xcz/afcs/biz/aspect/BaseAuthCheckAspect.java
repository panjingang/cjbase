package com.xcz.afcs.biz.aspect;

import com.xcz.afcs.biz.constants.Fields;
import com.xcz.afcs.core.annotation.NoLogin;
import com.xcz.afcs.core.context.DataContext;
import com.xcz.afcs.core.enums.BaseErrorCode;
import com.xcz.afcs.core.exception.BaseBusinessException;
import com.xcz.afcs.core.model.SessionIdentity;
import com.xcz.afcs.util.NetworkUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public abstract class BaseAuthCheckAspect {

    private static final String CLASS_NAME = BaseAuthCheckAspect.class.getName();

    private static final Logger LOG = LoggerFactory.getLogger(BaseAuthCheckAspect.class);

    public BaseAuthCheckAspect() {
        LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + CLASS_NAME + " loaded");
    }

    public Object checkAuth(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String accessToken = getAccessToken(request);
        String clientIp    = getClientIP(request);
        String accessUrl   = getRequestUrl(request);
        Object result      = null;
        try {
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            //获取Method
            Method method = methodSignature.getMethod();
            SessionIdentity identity = getSessionIdentity(accessToken);
            if (method.isAnnotationPresent(NoLogin.class)) {

            }
            else {
                if (identity == null) {
                    // 如果会话身份对象不存在则表示未登录或已超时
                    throw new BaseBusinessException(BaseErrorCode.NOT_LOGINED);
                }
            }
            if (identity != null) {
                DataContext.put(Fields.CONTEXT_ACCESS_TOKEN, accessToken);
                DataContext.put(DataContext.SESSION_IDENTITY, identity);
            }
            DataContext.put(Fields.CONTEXT_CLIENT_IP, clientIp);
            result = pjp.proceed();
        } finally {
            clearBinding();
        }
        return result;
    }

    public abstract SessionIdentity getSessionIdentity(String accessToken);


    public void clearBinding() {
        DataContext.remove(Fields.CONTEXT_ACCESS_TOKEN);
        DataContext.remove(Fields.CONTEXT_CLIENT_IP);
        DataContext.remove(DataContext.SESSION_IDENTITY);
    }


    protected String getAccessToken(HttpServletRequest request){
        String authorization = request.getHeader(Fields.AUTHORIZATION);
        if (StringUtils.isNotBlank(authorization)) {
            return authorization.substring(Fields.BEARER.length());
        }else{
            authorization = request.getParameter(Fields.ACCESS_TOKEN);
            if (StringUtils.isNotBlank(authorization)) {
                return authorization;
            }
            return null;
        }
    }

    protected String getClientIP(HttpServletRequest request){
        String remoteAddr = request.getHeader("RemoteAddr");
        if (StringUtils.isNotBlank(remoteAddr)) {
            return remoteAddr;
        }
        String ip = NetworkUtil.getIpAddr(request);
        if (StringUtils.isBlank(ip)) {
            return "127.0.0.1";
        }
        return ip;
    }

    protected String getRequestUrl(HttpServletRequest request){
        return request.getRequestURI();
    }


}
