package com.drsports.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.drsports.uikit.MyImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyImageView myImageView = findViewById(R.id.image);
        try {
            myImageView.setImage(getAssets().open("timg.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
