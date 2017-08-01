package lm.macro.spring.app;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@SuppressWarnings("unchecked")
public class LmAppContext implements ApplicationContextAware {
    private static ApplicationContext ctx;

    public static ApplicationContext getCtx() {
        return ctx;
    }

    public static <T> T getBean(String name) {
        if (ctx == null)
            return null;

        return (T) ctx.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        if (ctx == null)
            return null;

        return (T) ctx.getBean(clazz);
    }

    public static void publishEvent(ApplicationEvent event) {
        if (ctx == null)
            return;

        ctx.publishEvent(event);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return ctx.getBean(name, clazz);
    }

    @SuppressWarnings("unchecked")
    public static void autowireBean(Object... list) {
        for (Object o : list) {
            autowireBeanObject(o);
        }
    }

    @SuppressWarnings("unchecked")
    public static void autowireBean(Object o) {
        if (o instanceof Collections) {
            autowireBeanList((List) o);
        } else {
            autowireBeanObject(o);
        }
    }

    private static void autowireBeanList(List<Object> list) {
        for (Object o : list) {
            autowireBeanObject(o);
        }
    }

    private static void autowireBeanObject(Object o) {
        if (ctx == null)
            return;

        AutowireCapableBeanFactory autowireCapableBeanFactory = ctx.getAutowireCapableBeanFactory();
        autowireCapableBeanFactory.autowireBean(o);

        if (o instanceof InitializingBean) {
            try {
                ((InitializingBean) o).afterPropertiesSet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LmAppContext.ctx = applicationContext;
    }
}
