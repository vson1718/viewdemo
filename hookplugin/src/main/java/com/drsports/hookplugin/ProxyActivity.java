package com.drsports.hookplugin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

/**
 * @author vson
 * @date 2020/6/15
 * 项目描述:
 */
public class ProxyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Vson", "onCreate: 我是代理的Activity");
    }
}
