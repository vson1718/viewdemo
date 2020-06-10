package com.drsports.opengllib.widget;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

/**
 * @author vson
 * @date 2020/6/10
 * Company:上海动博士企业发展有限公司
 * E-mail :vson1718@163.com
 * 项目描述:
 */
public class CameraView extends GLSurfaceView {

    private CameraRender render;


    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //使用OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);
        //設置渲染回調接口
        render = new CameraRender(this);
        /**
         * 启动GLThread
         */
        setRenderer(render);

        /**
         * 刷新方式：
         *     RENDERMODE_WHEN_DIRTY 手动刷新，調用requestRender();
         *     RENDERMODE_CONTINUOUSLY 自動刷新，大概16ms自動回調一次onDraw方法
         */
        //注意必须在setRenderer 后面。
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
        render.onSurfaceDestroyed();
    }
}
