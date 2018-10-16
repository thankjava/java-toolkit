package com.thankjava.toolkit.bean.aop.util;

import com.thankjava.toolkit.bean.aop.anno.After;
import com.thankjava.toolkit.bean.aop.anno.Before;
import com.thankjava.toolkit.bean.aop.entity.AopCache;
import com.thankjava.toolkit.bean.aop.entity.AopConfig;
import com.thankjava.toolkit.core.reflect.ReflectHelper;

import java.lang.reflect.Method;

public class ScannerAopConfig {

    /**
     * 将被代理的类的方法进行扫描，检测是否有aop的配置
     * <p>Function: scanner</p>
     * <p>Description: </p>
     *
     * @param implementObject
     * @author acexy@thankjava.com
     * @date 2016年8月17日 下午5:51:04
     * @version 1.0
     */
    public static void scanner(Object implementObject) {

        if (AopCache.isScannedClass(implementObject.getClass())) {
            return;
        }

        Method[] methods = ReflectHelper.getAllMethod(implementObject);

        if (methods != null) {

            AopConfig config = null;
            Before before = null;
            After after = null;
            Class<?>[] argsType;

            for (Method method : methods) {

                config = new AopConfig();
                argsType = method.getParameterTypes();

                for (Class<?> clazz : argsType) {
                    config.setArgs(clazz.getName());
                }

                before = method.getAnnotation(Before.class);
                after = method.getAnnotation(After.class);

                config.setMethodName(method.getName());
                config.setClassPath(implementObject.getClass().getName());

                if (before == null && after == null) {
                    config.setUsedAop(false);
                } else {
                    config.setProxyInstance(implementObject);
                    config.setUsedAop(true);
                    if (before != null) {
                        config.setBefore(before);
                        try {
                            config.setBeforeInstance(before.cutClass().newInstance());
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    if (after != null) {
                        config.setAfter(after);
                        try {
                            if (before != null && before.cutClass() != null && before.cutClass() == after.cutClass()) {
                                config.setAfterInstance(before.cutClass().newInstance());
                            } else {
                                config.setAfterInstance(after.cutClass().newInstance());

                            }
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }

                AopCache.put(config);
            }
        }
    }

}