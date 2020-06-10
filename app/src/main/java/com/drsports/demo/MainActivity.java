package com.drsports.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.drsports.uikit.MyImageView;
import com.jakewharton.rxbinding4.view.RxView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import kotlin.Unit;

public class MainActivity extends AppCompatActivity {

    private Button rxJavaBnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rxJavaBnt=findViewById(R.id.rx_java);
        RxView.clicks(rxJavaBnt)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Unit>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Unit unit) {
                        Log.d("TAG", "发送了网络请求" );
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });






    }

    public void onOpenImage(View view) {
        startActivity(new Intent(this,ImageActivity.class));
    }
    public void onOpenGl(View view) {
        startActivity(new Intent(this,OpenGlActivity.class));
    }
}
