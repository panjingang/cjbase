package com.xcz.afcs.biz.model;

import java.lang.reflect.Method;

public class MethodInvocation {
    
    private Method method;
    
    private Object target;
    
    public MethodInvocation() {
    }
    
    public MethodInvocation(Method method, Object target) {
        this.method = method;
        this.target = target;
    }
    
    public Object invoke(Object... args) {
        try {
            return method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException("invoke method error", e);
        }
    }
    
    public Method getMethod() {
        return method;
    }
    
    public void setMethod(Method method) {
        this.method = method;
    }
    
    public Object getTarget() {
        return target;
    }
    
    public void setTarget(Object target) {
        this.target = target;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MethodInvocation [method=").append(method).append(", target=").append(target).append("]");
        return builder.toString();
    }
    
}
