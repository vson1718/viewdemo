package com.drsports.opengllib.utils;

import android.graphics.Camera;
import android.os.HandlerThread;
import android.util.Size;

import androidx.camera.core.CameraX;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.camera.core.UseCase;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author vson
 * @date 2020/6/10
 * Company:上海动博士企业发展有限公司
 * E-mail :vson1718@163.com
 * 项目描述:
 */
public class CameraHelper {


    private HandlerThread handlerThread;

    private CameraX.LensFacing currentFacing = CameraX.LensFacing.BACK;

    private Preview.OnPreviewOutputUpdateListener listener;

    public CameraHelper(LifecycleOwner lifecycleOwner, Preview.OnPreviewOutputUpdateListener listener) {
        this.listener = listener;
        handlerThread = new HandlerThread("Analyze-thread");
        handlerThread.start();
        CameraX.bindToLifecycle(lifecycleOwner, getPreView());
    }

    private Preview getPreView() {
        // 分辨率并不是最终的分辨率，CameraX会自动根据设备的支持情况，结合你的参数，设置一个最为接近的分辨率
        PreviewConfig previewConfig = new PreviewConfig.Builder()
                .setTargetResolution(new Size(640, 480))
                .setLensFacing(currentFacing)
                .build();
        Preview preview = new Preview(previewConfig);
        preview.setOnPreviewOutputUpdateListener(listener);
        return preview;
    }


}
