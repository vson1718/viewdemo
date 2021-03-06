package com.drsports.opengllib.widget;

import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.SurfaceView;

import androidx.camera.core.Preview;
import androidx.lifecycle.LifecycleOwner;

import com.drsports.opengllib.filter.ScreenFilter;
import com.drsports.opengllib.utils.CameraHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author vson
 * @date 2020/6/10
 * Company:上海动博士企业发展有限公司
 * E-mail :vson1718@163.com
 * 项目描述:
 */
public class CameraRender implements GLSurfaceView.Renderer,
        Preview.OnPreviewOutputUpdateListener
        , SurfaceTexture.OnFrameAvailableListener {


    private CameraView cameraView;
    private CameraHelper cameraHelper;

    private SurfaceTexture mCameraTexture;
    private int[] textures;
    private ScreenFilter screenFilter;
    float[] mtx = new float[16];


    public CameraRender(CameraView cameraView) {
        this.cameraView = cameraView;
        LifecycleOwner lifecycleOwner = (LifecycleOwner) cameraView.getContext();
        cameraHelper = new CameraHelper(lifecycleOwner, this);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //创建OpenGL 纹理 ,把摄像头的数据与这个纹理关联
        //当做能在opengl用的一个图片的ID
        textures = new int[1];
        mCameraTexture.attachToGLContext(textures[0]);
        // 当摄像头数据有更新回调 onFrameAvailable
        mCameraTexture.setOnFrameAvailableListener(this);
        screenFilter = new ScreenFilter(cameraView.getContext());
        Log.d("TAG","onSurfaceCreated=====");
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        screenFilter.setSize(width, height);
        Log.d("TAG","onSurfaceChanged=====");

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 更新纹理textures[0]
        mCameraTexture.updateTexImage();
        mCameraTexture.getTransformMatrix(mtx);

        screenFilter.setTransformMatrix(mtx);
        screenFilter.onDraw(textures[0]);
        Log.d("TAG","onDrawFrame=====");

    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        //  请求执行一次 onDrawFrame
        cameraView.requestRender();
        Log.d("TAG","onFrameAvailable=====");
    }

    @Override
    public void onUpdated(Preview.PreviewOutput output) {
        mCameraTexture = output.getSurfaceTexture();
        Log.d("TAG","onUpdated=====");
    }

    public void onSurfaceDestroyed() {

    }

}
