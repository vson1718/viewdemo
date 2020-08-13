#extension GL_OES_EGL_image_external : require
//摄像头数据比较特殊的一个地方
precision mediump float;// 数据精度
varying vec2 aCoord;

uniform samplerExternalOES  vTexture;// samplerExternalOES: 图片， 采样器

void main(){
    //  texture2D: vTexture采样器，采样  aCoord 这个像素点的RGBA值
    //    vec4 rgba = texture2D(vTexture,aCoord);  //rgba
    //    gl_FragColor =vec4(rgba.r,rgba.g,rgba.b,rgba.a);

    float y = aCoord.y;
    float x= aCoord.x;


    if (y < 1.0/3.0) {
        y = y + 1.0/3.0;
    } else if (y > 2.0/3.0){
        y = y - 1.0/3.0;
    }


    if (x < 1.0/3.0){
        x += 1.0/3.0;
    } else if (x > 2.0/3.0){
        x -= 1.0/3.0;
    }


    //    if (y < 0.5){
    //        y += 0.25;
    //    } else {
    //        y -= 0.25;
    //    }
    //    if (x < 0.5){
    //        x += 0.25;
    //    } else {
    //        x -= 0.25;
    //    }
    vec4 rgba  = texture2D(vTexture, vec2(x, y));
    gl_FragColor =vec4(1.-rgba.r, 1.-rgba.g, 1.-rgba.b, rgba.a);


}