package ru.dima.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BeanPostProcessor {

    public static <T> T proccess(T bean) {

        Class<?> c = bean.getClass();
        String name = bean.getClass().getName();

        if (c.getName().startsWith("com.sun.proxy")) {
            return (T) Proxy.newProxyInstance(
                    c.getClassLoader(),
                    c.getInterfaces(),
                    (proxy, method, args) -> invoke(proxy, method, args, bean, name)
            );
        } else {
            c = getOriginName(c);

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Config.class);
            enhancer.setCallback((InvocationHandler) (obj, method, args) -> invoke(obj, method, args, bean, name));
            Class<?>[] types = Config.class.getConstructors()[0].getParameterTypes();
            if (c.getConstructors().length == 0) {
                return (T) enhancer.create();
            }
            Constructor constructor = c.getConstructors()[0];
            return (T) enhancer.create(constructor.getParameterTypes(), new Object[constructor.getParameterTypes().length]);
        }
    }

    public static Object invoke(Object o, Method method, Object[] args, Object bean, String beanName) throws Throwable {
        if (!method.canAccess(bean)) {
            method.setAccessible(true);
        }
        if (method.getReturnType().isPrimitive()) {
            return method.invoke(o, args);
        }

        Object invoke = null;
        long start = System.nanoTime();

        try {
            return method.invoke(o, args);
        } catch (Throwable t) {
            throw t;
        } finally {
            System.out.printf("%d мкс\n", (System.nanoTime() - start) / 1000);
        }
    }


    public static Class<?> getOriginName(Class<?> c) {
        if (c.getName().contains("$") || c.getName().contains("@")) {
            try {
                c = Class.forName(c.getName().replaceAll("\\$.*", "").replaceAll("@.*", ""));
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }
        return c;
    }

}
