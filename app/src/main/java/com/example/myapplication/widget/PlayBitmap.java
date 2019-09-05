/*
* Copyright (C) 2015 Mert Şimşek
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.example.myapplication.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class PlayBitmap extends View {
    /**
     * Button paint for play/pause control button
     */
    private static Paint mPaintOuter;
    /**
     * Paint to draw cover photo to canvas
     */
    private static Paint mPaintCover;
    /**
     * Handler will post runnable object every @ROTATE_DELAY seconds.
     */
    private static int ROTATE_DELAY = 20;
    /**
     * mRotateDegrees count increase 1 by 1 default.
     * I used that parameter as velocity.
     * 旋转的速度
     */
    private static float VELOCITY = 0.5f;
    /**
     * Bitmap for shader.
     */
    private Bitmap mBitmapCover;
    /**
     * Shader for make drawable circle
     * 图片渲染
     */
    private BitmapShader mShader;
    /**
     * Image Height and Width values.
     */
    private int mHeight;
    private int mWidth;
    /**
     * Center values for cover image.
     */
    private float mCenterX;
    private float mCenterY;
    /**
     * Cover image is rotating. That is why we hold that value.
     * 选择的角度
     */
    private float mRotateDegrees;
    /**
     * Handler for posting runnable object
     */
    private Handler mHandlerRotate;
    /**
     * isPlaying
     */
    private boolean isRotating;
    /**
     * Runnable for turning image (default velocity is 10)
     */
    private final Runnable mRunnableRotate = new Runnable() {
        @Override
        public void run() {
            if (isRotating) {
                updateCoverRotate();
                mHandlerRotate.postDelayed(mRunnableRotate, ROTATE_DELAY);
            }
        }
    };
    /**
     * Default color code for cover
     */
    private int mDefaultCoverColor = Color.GRAY;
    /**
     * 外圆的半径
     */
    private float mOuterRedius;
    /**
     * 内圆的半径
     */
    private float mInnerRedius;
    /**
     * 缩放的比例
     */
    private float mCoverScale;

    /**
     * Constructor
     */
    public PlayBitmap(Context context) {
        super(context);
        init(context, null);
    }

    /**
     * Constructor
     */
    public PlayBitmap(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * Constructor
     */
    public PlayBitmap(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * Constructor
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PlayBitmap(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * Initializes resource values, create objects which we need them later.
     * Object creation must not called onDraw() method, otherwise it won't be
     * smooth.
     */
    private void init(Context context, AttributeSet attrs) {

        setWillNotDraw(false);

        mRotateDegrees = 0.0f;

        //Handler and Runnable object for turn cover image by updating rotation degrees
        mHandlerRotate = new Handler();

        //Play/Pause button circle paint
        mPaintOuter = new Paint();
        mPaintOuter.setAntiAlias(true);//设置抗锯齿
        mPaintOuter.setStyle(Paint.Style.FILL);//设置填充
        mPaintOuter.setColor(Color.DKGRAY);//设置颜色
    }

    /**
     * Calculate mWidth, mHeight, mCenterX, mCenterY values and
     * scale resource default_album. Create shader. This is not called multiple times.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        int minSide = Math.min(mWidth, mHeight);
        mWidth = minSide;
        mHeight = minSide;

        //设置宽高
        this.setMeasuredDimension(mWidth, mHeight);

        mCenterX = mWidth / 2f;
        mCenterY = mHeight / 2f;

        //set RectF left, top, right, bottom coordiantes
//        rectF.set(10.0f, 10.0f, mWidth - 10.0f, mHeight - 10.0f);

        mOuterRedius = mWidth / 2;
        mInnerRedius = mOuterRedius - 10;

        createShader();

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * This is where magic happens as you know.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mShader == null) return;
        canvas.drawCircle(mCenterX, mCenterY, mOuterRedius, mPaintOuter);
        //Draw cover image
        canvas.rotate(mRotateDegrees, mCenterX, mCenterY);
        canvas.drawCircle(mCenterX, mCenterY, mInnerRedius, mPaintCover);

        //Rotate back to make play/pause button stable(No turn)
        canvas.rotate(-mRotateDegrees, mCenterX, mCenterY);
    }

    public void setBitmapCover(Bitmap bitmap) {
        mBitmapCover = bitmap;
        createShader();
        postInvalidate();
    }

    /**
     * We need to convert drawable (which we get from attributes) to default_album
     * to prepare if for BitmapShader
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * Create shader and set shader to mPaintCover
     */
    private void createShader() {
        if (mWidth == 0) return;

        //if mBitmapCover is null then create default colored cover
        if (mBitmapCover == null) {
            mBitmapCover = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
            mBitmapCover.eraseColor(mDefaultCoverColor);
        }

        mCoverScale = (float) mWidth / (float) mBitmapCover.getWidth();
        mBitmapCover = Bitmap.createScaledBitmap(mBitmapCover, (int) (mBitmapCover.getWidth() * mCoverScale),
                (int) (mBitmapCover.getHeight() * mCoverScale), true);

        mShader = new BitmapShader(mBitmapCover, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaintCover = new Paint();
        mPaintCover.setAntiAlias(true);
        mPaintCover.setShader(mShader);
    }

    /**
     * Update rotate degree of cover and invalide onDraw();
     */
    public void updateCoverRotate() {
        mRotateDegrees += VELOCITY;
        mRotateDegrees = mRotateDegrees % 360;
        postInvalidate();
    }

    /**
     * Checks is rotating
     */
    public boolean isRotating() {
        return isRotating;
    }

    /**
     * Start turning image
     */
    public void start() {
        isRotating = true;
        mHandlerRotate.removeCallbacksAndMessages(null);
        mHandlerRotate.postDelayed(mRunnableRotate, ROTATE_DELAY);
        postInvalidate();
    }

    public void setRota(boolean rota) {
        if (rota)
            start();
        else
            stop();
    }

    /**
     * Stop turning image
     */
    public void stop() {
        isRotating = false;
        postInvalidate();
    }

    /**
     * Set velocity.When updateCoverRotate() method called,
     * increase degree by velocity value.
     */
    public void setVelocity(int velocity) {
        if (velocity > 0) VELOCITY = velocity;
    }

    /**
     * set cover image resource
     */
    public void setCoverDrawable(int coverDrawable) {
        Drawable drawable = getContext().getResources().getDrawable(coverDrawable);
        mBitmapCover = drawableToBitmap(drawable);
        createShader();
        postInvalidate();
    }

    /**
     * sets cover image
     *
     * @param drawable
     */
    public void setCoverDrawable(Drawable drawable) {
        mBitmapCover = drawableToBitmap(drawable);
        createShader();
        postInvalidate();
    }
}
