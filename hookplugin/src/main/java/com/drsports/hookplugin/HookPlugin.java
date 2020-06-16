package com.drsports.hookplugin;

import android.content.Context;

/**
 * @author vson
 * @date 2020/6/15
 * 项目描述:
 */
public class HookPlugin {

    public static void init(Context context) {
        LoadUtil.loadClass(context);
        HookUtil.hookAMS();
//        HookUtil.hookHandler();
    }
}
