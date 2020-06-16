package com.drsports.hookplugin;

import android.content.Context;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * @author vson
 * @date 2020/6/15
 * 项目描述:
 */
public class LoadUtil {

    private static final String apkPath = "/sdcard/appplugin-debug.apk";

//    /**
//     * 加载插件的dexElements and 合并插件dexElements到本地的dexElements
//     *
//     * @param context Context
//     */
//    public static void loadClass(Context context) {
//        try {
//            //private final DexPathList pathList;
//            Class<?> baseDexClassLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
//            Field pathListField = baseDexClassLoaderClass.getDeclaredField("pathList");
//            pathListField.setAccessible(true);
//            //private Element[] dexElements;
//            Class<?> DexPathListClass = Class.forName("dalvik.system.DexPathList");
//            Field dexElementsField = DexPathListClass.getDeclaredField("dexElements");
//            dexElementsField.setAccessible(true);
//
//            //创建插件的 DexClassLoader 类加载器，然后通过反射获取插件的 dexElements 值
//            DexClassLoader dexClassLoader = new DexClassLoader(apkPath, context.getCacheDir().getAbsolutePath(), null, context.getClassLoader());
//            Object pluginPathList = pathListField.get(dexClassLoader);
//            Object[] pluginDexElements = (Object[]) dexElementsField.get(pluginPathList);
//
//            //获取宿主的DexElements
//            PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
//            Object hostPathList = pathListField.get(pathClassLoader);
//            Object[] hostDexElements = (Object[]) dexElementsField.get(hostPathList);
//
//            //创建合并数组
//            Object[] mDexElements = (Object[]) Array.newInstance(pluginDexElements.getClass().getComponentType(), pluginDexElements.length + hostDexElements.length);
//
//            System.arraycopy(hostDexElements, 0, mDexElements, 0, hostDexElements.length);
//            System.arraycopy(pluginDexElements, 0, mDexElements, hostDexElements.length, pluginDexElements.length);
//            // 将创建的 dexElements 放到宿主的 dexElements中
//            // 宿主的dexElements = 新的dexElements
//            dexElementsField.set(hostDexElements, mDexElements);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }




    public static void loadClass(Context context) {

        try {
            // private final DexPathList pathList;
            Class<?> baseDexClassLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField = baseDexClassLoaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);

            // private Element[] dexElements;
            Class<?> dexPathListClass = Class.forName("dalvik.system.DexPathList");
            Field dexElementsField = dexPathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);

            /**
             * 插件
             */
            // 创建插件的 DexClassLoader 类加载器，然后通过反射获取插件的 dexElements 值
            // 插件的
            DexClassLoader dexClassLoader = new DexClassLoader(apkPath, context.getCacheDir().getAbsolutePath(),
                    null, context.getClassLoader());

            Object pluginPathList = pathListField.get(dexClassLoader);
            // 拿到了插件的 dexElements
            Object[] pluginDexElements = (Object[]) dexElementsField.get(pluginPathList);

            /**
             * 宿主
             */
            PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();

            Object hostPathList = pathListField.get(pathClassLoader);
            // 拿到了宿主的 dexElements
            Object[] hostDexElements = (Object[]) dexElementsField.get(hostPathList);


            /**
             * 创建数组
             */
            // Element[]
            Object[] dexElements = (Object[]) Array.newInstance(pluginDexElements.getClass().getComponentType(),
                    hostDexElements.length + pluginDexElements.length);

            System.arraycopy(hostDexElements, 0, dexElements, 0, hostDexElements.length);
            System.arraycopy(pluginDexElements, 0, dexElements, hostDexElements.length, pluginDexElements.length);

            // 将创建的 dexElements 放到宿主的 dexElements中
            // 宿主的dexElements = 新的dexElements
            dexElementsField.set(hostPathList, dexElements);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
