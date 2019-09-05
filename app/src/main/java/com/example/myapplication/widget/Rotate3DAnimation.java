package com.example.myapplication.widget;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by MBENBEN on 2016/3/23.
 */
public class Rotate3DAnimation extends Animation {
    private final float fromDegarees;
    private final float toDegrees;
    private final float centerX;
    private final float centerY;
    private final float depthZ;
    private final boolean revers;
    private Camera camera;

    public Rotate3DAnimation(float fromDegarees, float toDegrees
            , float centerX, float centerY, float depthZ, boolean revers){
        this.fromDegarees = fromDegarees;
        this.toDegrees = toDegrees;
        this.centerX = centerX;
        this.centerY = centerY;
        this.depthZ = depthZ;
        this.revers = revers;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        camera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        final float fromDegarees = this.fromDegarees;
        float degrees = fromDegarees + ((toDegrees - fromDegarees) * interpolatedTime);
        float centerX = this.centerX;
        float centerY = this.centerY;
        Camera camera = this.camera;
        Matrix matrix = t.getMatrix();
        camera.save();
        if(revers){
            camera.translate(0f,0f,depthZ * interpolatedTime);
        }else{
            camera.translate(0f,0f,depthZ * (1f - interpolatedTime));
        }
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
