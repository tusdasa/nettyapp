package ren.epub.nettyapp.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextGetBeanHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextGetBeanHelper.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        if(applicationContext!=null){
            return applicationContext;
        }else {
            return null;
        }
    }

    public static Object getBean(String className) throws BeansException,IllegalArgumentException {
        if(className==null || className.length() == 0) {
            throw new IllegalArgumentException("className is empty");
        }

        String beanName;
        if(className.length() > 1) {
            beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
        } else {
            beanName = className.toLowerCase();
        }
        return applicationContext != null ? applicationContext.getBean(beanName) : null;
    }

    public static  <T> T getBean(Class<T> beanClass) throws BeansException,IllegalArgumentException {
        if(beanClass==null) {
            throw new IllegalArgumentException("beanClass is empty");
        }
        return applicationContext != null ? applicationContext.getBean(beanClass) : null;
    }
}

