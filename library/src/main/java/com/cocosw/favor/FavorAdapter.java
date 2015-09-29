package com.cocosw.favor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 25/09/15.
 */
public class FavorAdapter {

    private final SharedPreferences sp;

    private final Map<Class<?>, Map<Method, RestMethodInfo>> serviceMethodInfoCache =
            new LinkedHashMap<>();


    public FavorAdapter(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public FavorAdapter(SharedPreferences sp) {
        this.sp = sp;
    }

    static RestMethodInfo getMethodInfo(Map<Method, RestMethodInfo> cache, Method method, SharedPreferences sp) {
        synchronized (cache) {
            RestMethodInfo methodInfo = cache.get(method);
            if (methodInfo == null) {
                methodInfo = new RestMethodInfo(method, sp);

                cache.put(method, methodInfo);
            }
            return methodInfo;
        }
    }

    static <T> void validateServiceClass(Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("Only interface endpoint definitions are supported.");
        }
        // Prevent API interfaces from extending other interfaces. This not only avoids a bug in
        // Android (http://b.android.com/58753) but it forces composition of API declarations which is
        // the recommended pattern.
        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("Interface definitions must not extend other interfaces.");
        }
    }

    /**
     * Create an implementation of the API defined by the specified {@code service} interface.
     */
    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> service) {
        validateServiceClass(service);
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new RestHandler(getMethodInfoCache(service)));
    }

    Map<Method, RestMethodInfo> getMethodInfoCache(Class<?> service) {
        synchronized (serviceMethodInfoCache) {
            Map<Method, RestMethodInfo> methodInfoCache = serviceMethodInfoCache.get(service);
            if (methodInfoCache == null) {
                methodInfoCache = new LinkedHashMap<>();
                serviceMethodInfoCache.put(service, methodInfoCache);
            }
            return methodInfoCache;
        }
    }

    private class RestHandler implements InvocationHandler {
        private final Map<Method, RestMethodInfo> methodDetailsCache;

        RestHandler(Map<Method, RestMethodInfo> methodDetailsCache) {
            this.methodDetailsCache = methodDetailsCache;
        }

        @SuppressWarnings("unchecked") //
        @Override
        public Object invoke(Object proxy, Method method, final Object[] args)
                throws Throwable {
            // If the method is a method from Object then defer to normal invocation.
            if (method.getDeclaringClass() == Object.class) {
                return method.invoke(this, args);
            }

            // Load or create the details cache for the current method.
            final RestMethodInfo methodInfo = getMethodInfo(methodDetailsCache, method, sp);
            methodInfo.init();

            if (methodInfo.responseType == RestMethodInfo.ResponseType.VOID) {
                //Setter
                //执行 sp相关操作；
                // methodInfo.
                return methodInfo.set(args);


            } else {
                //Getter
                switch (methodInfo.responseType) {
                    case OBSERVABLE:
                        return methodInfo.rxPref;
                    default:
                        return methodInfo.get();
                }
            }
        }

    }


}
