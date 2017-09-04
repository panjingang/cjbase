package com.xcz.afcs.core.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

import java.lang.annotation.Annotation;
import java.util.Map;

public class RuntimeContext {
    
    private static final Logger LOG = LoggerFactory.getLogger(RuntimeContext.class);
    
    private static RuntimeContext runtimeContext = new RuntimeContext();
    
    private RuntimeContext() {
    }
    
    public static RuntimeContext getInstance() {
        return runtimeContext;
    }
    
    public static class SpringContext implements ApplicationContextAware {
        
        private static SpringContext springContext = new SpringContext();
        
        private SpringContext() {
        }
        
        public static SpringContext getInstance() {
            return springContext;
        }
        
        private static ApplicationContext applicationContext;
        
        public static ApplicationContext getSpringContext() {
            if (null == applicationContext) {
                throw new RuntimeException(
                        "Spring ApplicationContext is null. Please config this Class as a spring bean. e.g. <bean class='com.xcz.afcs.context.RuntimeContext$SpringContext' factory-method='getInstance' />");
            }
            return applicationContext;
        }
        
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            SpringContext.applicationContext = applicationContext;
        }
        
    }

    public static boolean isInSpringContext(boolean silent) {
        try {
            getSpringContext();
            return true;
        } catch (Exception e) {
            if (!silent) {
                LOG.error("get spring context failed", e);
            }
            return false;
        }
    }
    
    public static ApplicationContext getSpringContext() {
        return SpringContext.getSpringContext();
    }
    
    public static Object getBean(String name) {
        return getBean(name, true);
    }
    
    public static <T> T getBean(String name, Class<T> requiredType) {
        return getBean(name, requiredType, true);
    }
    
    public static <T> T getBean(Class<T> requiredType) {
        return getBean(requiredType, true);
    }
    
    public static <T> Map<String, T> getBeansOfType(Class<T> requiredType) {
        return getBeansOfType(requiredType, true);
    }
    
    public static <T> Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return getBeansWithAnnotation(annotationType, true);
    }
    
    public static Object getBean(String name, boolean searchInAncestor) {
        Object bean = getSpringContext().getBean(name);
        if (null == bean && searchInAncestor) {
            ApplicationContext parentContext = null;
            while (null == bean && null != (parentContext = getSpringContext().getParent())) {
                bean = parentContext.getBean(name);
            }
        }
        return bean;
    }
    
    public static <T> T getBean(String name, Class<T> requiredType, boolean searchInAncestor) {
        T bean = getSpringContext().getBean(name, requiredType);
        if (null == bean && searchInAncestor) {
            ApplicationContext parentContext = null;
            while (null == bean && null != (parentContext = getSpringContext().getParent())) {
                bean = parentContext.getBean(name, requiredType);
            }
        }
        return bean;
    }
    
    public static <T> T getBean(Class<T> requiredType, boolean searchInAncestor) {
        T bean = getSpringContext().getBean(requiredType);
        if (null == bean && searchInAncestor) {
            ApplicationContext parentContext = null;
            while (null == bean && null != (parentContext = getSpringContext().getParent())) {
                bean = parentContext.getBean(requiredType);
            }
        }
        return bean;
    }
    
    public static <T> Map<String, T> getBeansOfType(Class<T> requiredType, boolean searchInAncestor) {
        Map<String, T> beans = getSpringContext().getBeansOfType(requiredType);
        if (beans.isEmpty() && searchInAncestor) {
            ApplicationContext parentContext = null;
            while (beans.isEmpty() && null != (parentContext = getSpringContext().getParent())) {
                beans = parentContext.getBeansOfType(requiredType);
            }
        }
        return beans;
    }
    
    public static <T> Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType,
            boolean searchInAncestor) {
        Map<String, Object> beans = getSpringContext().getBeansWithAnnotation(annotationType);
        if (beans.isEmpty() && searchInAncestor) {
            ApplicationContext parentContext = null;
            while (beans.isEmpty() && null != (parentContext = getSpringContext().getParent())) {
                beans = parentContext.getBeansWithAnnotation(annotationType);
            }
        }
        return beans;
    }
    
    public static void publishEvent(ApplicationEvent event) {
        getSpringContext().publishEvent(event);
    }

}
