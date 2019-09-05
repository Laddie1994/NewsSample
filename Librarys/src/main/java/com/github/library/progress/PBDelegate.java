package com.github.library.progress;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

interface PBDelegate {
  void draw(Canvas canvas, Paint paint);

  void start();

  void stop();

  void progressiveStop(CircularProgressDrawable.OnEndListener listener);
}
