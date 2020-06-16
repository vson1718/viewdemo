package com.drsports.hookplugin;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author vson
 * @date 2020/6/15
 * 项目描述:
 */
public class HookUtil {

    private static final String TARGET_INTENT = "target_intent";


    public static void hookAMS() {

        Object singleton;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Class<?> clazz = Class.forName("android.app.ActivityTaskManager");
                Field singletonField = clazz.getDeclaredField("IActivityTaskManagerSingleton");
                singletonField.setAccessible(true);
                singleton = singletonField.get(null);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Class<?> clazz = Class.forName("android.app.ActivityManager");
                Field singletonField = clazz.getDeclaredField("IActivityManagerSingleton");
                singletonField.setAccessible(true);
                singleton = singletonField.get(null);
            } else {
                Class<?> clazz = Class.forName("android.app.ActivityManagerNative");
                Field singletonField = clazz.getDeclaredField("gDefault");
                singletonField.setAccessible(true);
                singleton = singletonField.get(null);
            }
            Class<?> singletonClazz = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClazz.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            final Object mInstance = mInstanceField.get(singleton);

            Class<?> iActivityManagerClass = Class.forName("android.app.IActivityManager");
            Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{iActivityManagerClass}, new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            // IActivityManager 的方法执行的时候，都会先跑这儿
                            if ("startActivity".equals(method.getName())) {
                                int index = 0;
                                for (int i = 0; i < args.length; i++) {
                                    if (args[i] instanceof Intent) {
                                        index = i;
                                        break;
                                    }
                                }
                                Intent intent = (Intent) args[index];
                                Intent intentProxy = new Intent();
                                intentProxy.setClassName("com.drsports.hookplugin", "com.drsports.hookplugin.ProxyActivity");
                                intentProxy.putExtra(TARGET_INTENT, intent);
                            }
                            return method.invoke(mInstance, args);
                        }
                    });
            mInstanceField.set(singleton, proxyInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void hookHandler() {

        try {
            Class<?> clazz = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = clazz.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object activityThread = sCurrentActivityThreadField.get(null);
            Field mHField = clazz.getDeclaredField("mH");
            mHField.setAccessible(true);
            Object mH = mHField.get(activityThread);

            Class<?> handlerClass = Class.forName("android.os.Handler");
            Field mCallbackFiled = handlerClass.getDeclaredField("mCallback");
            mCallbackFiled.setAccessible(true);
            mCallbackFiled.set(mH, new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message msg) {
                    // 将Intent换回来
                    switch (msg.what) {
                        case 100:
                            try {
                                Field intentField = msg.obj.getClass().getDeclaredField("intent");
                                intentField.setAccessible(true);
                                // 拿到插件的Intent
                                Intent proxyIntent = (Intent) intentField.get(msg.obj);
                                //替换回来
                                Intent intent = (Intent) proxyIntent.getSerializableExtra(TARGET_INTENT);
                                if (intent != null) {
                                    intentField.set(msg.obj, intent);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        case 159:
                            try {
                                Class<?> ClientTransactionClazz = Class.forName("android.app.servertransaction.ClientTransaction");
                                Field mActivityCallbacksFiled = ClientTransactionClazz.getDeclaredField("mActivityCallbacks");
                                mActivityCallbacksFiled.setAccessible(true);
                                List mActivityCallbacks = (List) mActivityCallbacksFiled.get(msg.obj);
                                for (int i = 0; i < mActivityCallbacks.size(); i++) {
                                    if (mActivityCallbacks.get(i).getClass().getName()
                                            .equals("android.app.servertransaction.LaunchActivityItem")) {
                                        Object mLaunchActivityItem = mActivityCallbacks.get(i);
                                        Field mIntentFiled = mLaunchActivityItem.getClass().getDeclaredField("mIntent");
                                        mIntentFiled.setAccessible(true);
                                        Intent proxyIntent = (Intent) mIntentFiled.get(mLaunchActivityItem);
                                        Intent intent = (Intent) proxyIntent.getSerializableExtra(TARGET_INTENT);
                                        if (intent != null) {
                                            mIntentFiled.set(mLaunchActivityItem, intent);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            break;

                    }
                    return false;
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
