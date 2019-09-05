package com.example.myapplication.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.myapplication.utils.LogUtils;

/**
 * Created by MBENBEN on 2016/3/17.
 */
public class PlayButton extends View implements OnPlayPauseToggleListener {

    Drawable.Callback callback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            postInvalidate();
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {

        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {

        }
    };
    private PlayDrawable mPlayPauseDrawable;
    private int mWidth;
    private int mHeight;
    private float mButtonRadius;
    private boolean mFirstDraw = true;
    private boolean isPlaying;
    private AnimatorSet mAnimatorSet;

    public PlayButton(Context context) {
        super(context);
        init();
    }

    public PlayButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPlayPauseDrawable = new PlayDrawable(getContext());
        mPlayPauseDrawable.setCallback(callback);
        mPlayPauseDrawable.setToggleListener(this);
//        mPlayPauseDrawable.setPauseColor();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        mButtonRadius = mWidth;
        mPlayPauseDrawable.resize((1.5f * mButtonRadius / 5.0f), (4.5f * mButtonRadius / 5.0f),
                (mButtonRadius / 5.0f));

        mPlayPauseDrawable.setBounds(0, 0, mWidth, mHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPlayPauseDrawable.draw(canvas);
        if (mFirstDraw) {
            onToggled();
            mFirstDraw = false;
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean click) {
        isPlaying = click;
        if (click) {
            start();
        } else {
            stop();
        }
    }

    public void start() {
        mPlayPauseDrawable.setPlaying(true);
        postInvalidate();
    }

    public void stop() {
        mPlayPauseDrawable.setPlaying(false);
        postInvalidate();
    }

    @Override
    public void onToggled() {
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }
        LogUtils.e("onToggled" + isPlaying());
        mAnimatorSet = new AnimatorSet();
        final Animator pausePlayAnim = mPlayPauseDrawable.getPausePlayAnimator();
        mAnimatorSet.setInterpolator(new DecelerateInterpolator());
        mAnimatorSet.setDuration(200);
        mAnimatorSet.playTogether(pausePlayAnim);
        mAnimatorSet.start();
    }

    public class PlayDrawable extends Drawable {

        private final Path mLeftPauseBar = new Path();
        private final Path mRightPauseBar = new Path();
        private final Paint mPaint = new Paint();
        private final RectF mBounds = new RectF();
        private float mPauseBarWidth;
        private float mPauseBarHeight;
        private float mPauseBarDistance;
        private OnPlayPauseToggleListener onPlayPauseToggleListener;
        private float mWidth;
        private float mHeight;
        private float mProgress;
        private boolean mIsPlay;

        private final Property<PlayDrawable, Float> PROGRESS =
                new Property<PlayDrawable, Float>(Float.class, "progress") {
                    @Override
                    public Float get(PlayDrawable d) {
                        return d.getProgress();
                    }

                    @Override
                    public void set(PlayDrawable d, Float value) {
                        d.setProgress(value);
                    }
                };

        public PlayDrawable(Context context) {
            final Resources res = context.getResources();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.GRAY);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            mBounds.set(bounds);
            mWidth = mBounds.width();
            mHeight = mBounds.height();
        }

        public void setToggleListener(OnPlayPauseToggleListener onPlayPauseToggleListener) {
            this.onPlayPauseToggleListener = onPlayPauseToggleListener;
        }

        @Override
        public void draw(Canvas canvas) {
            mLeftPauseBar.rewind();
            mRightPauseBar.rewind();

            // The current distance between the two pause bars.
            final float barDist = lerp(mPauseBarDistance, 0, mProgress);
            // The current width of each pause bar.
            final float barWidth = lerp(mPauseBarWidth, mPauseBarHeight / 1.7f, mProgress);
            // The current position of the left pause bar's top left coordinate.
            final float firstBarTopLeft = lerp(0, barWidth, mProgress);
            // The current position of the right pause bar's top right coordinate.
            final float secondBarTopRight = lerp(2 * barWidth + barDist, barWidth + barDist, mProgress);

            // Draw the left pause bar. The left pause bar transforms into the
            // top half of the play button triangle by animating the position of the
            // rectangle's top left coordinate and expanding its bottom width.
            mLeftPauseBar.moveTo(0, 0);
            mLeftPauseBar.lineTo(firstBarTopLeft, -mPauseBarHeight);
            mLeftPauseBar.lineTo(barWidth, -mPauseBarHeight);
            mLeftPauseBar.lineTo(barWidth, 0);
            mLeftPauseBar.close();

            // Draw the right pause bar. The right pause bar transforms into the
            // bottom half of the play button triangle by animating the position of the
            // rectangle's top right coordinate and expanding its bottom width.
            mRightPauseBar.moveTo(barWidth + barDist, 0);
            mRightPauseBar.lineTo(barWidth + barDist, -mPauseBarHeight);
            mRightPauseBar.lineTo(secondBarTopRight, -mPauseBarHeight);
            mRightPauseBar.lineTo(2 * barWidth + barDist, 0);
            mRightPauseBar.close();

            canvas.save();

            // Translate the play button a tiny bit to the right so it looks more centered.
            canvas.translate(lerp(0, mPauseBarHeight / 8f, mProgress), 0);

            // (1) Pause --> Play: rotate 0 to 90 degrees clockwise.
            // (2) Play --> Pause: rotate 90 to 180 degrees clockwise.
            final float rotationProgress = isPlaying ? 1 - mProgress : mProgress;
            final float startingRotation = isPlaying ? 90 : 0;
            canvas.rotate(lerp(startingRotation, startingRotation + 90, rotationProgress), mWidth / 2f,
                    mHeight / 2f);

            // Position the pause/play button in the center of the drawable's bounds.
            canvas.translate(mWidth / 2f - ((2 * barWidth + barDist) / 2f),
                    mHeight / 2f + (mPauseBarHeight / 2f));

            // Draw the two bars that form the animated pause/play button.
            canvas.drawPath(mLeftPauseBar, mPaint);
            canvas.drawPath(mRightPauseBar, mPaint);

            canvas.restore();
        }

        public Animator getPausePlayAnimator() {
            final Animator anim = ObjectAnimator.ofFloat(this, PROGRESS, isPlaying ? 1 : 0, mIsPlay ? 0 : 1);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mIsPlay = !mIsPlay;
                }
            });
            return anim;
        }

        private float getProgress() {
            return mProgress;
        }

        private void setProgress(float progress) {
            mProgress = progress;
            invalidateSelf();
        }

        @Override
        public void setAlpha(int alpha) {
            mPaint.setAlpha(alpha);
            invalidateSelf();
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            mPaint.setColorFilter(cf);
            invalidateSelf();
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        public void resize(float mPauseBarWidth, float mPauseBarHeight, float mPauseBarDistance) {
            this.mPauseBarWidth = mPauseBarWidth;
            this.mPauseBarHeight = mPauseBarHeight;
            this.mPauseBarDistance = mPauseBarDistance;
            invalidateSelf();
        }

        /**
         * Linear interpolate between a and b with parameter t.
         */
        private float lerp(float a, float b, float t) {
            return a + (b - a) * t;
        }

        public void setPlaying(boolean playing) {
            if (isPlaying() == playing && onPlayPauseToggleListener != null) {
                onPlayPauseToggleListener.onToggled();
            }
        }
    }
}
