package com.drsports.demo;

import android.app.Application;

import com.drsports.hookplugin.HookPlugin;

/**
 * @author vson
 * @date 2020/6/15
 * 项目描述:
 */
public class ClientApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        HookPlugin.init(this);
    }

}
