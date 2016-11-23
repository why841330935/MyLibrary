package com.why.library.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class WGlideCircleWithBorderOverlayTransform extends BitmapTransformation
{
    private static int borderRadius;
    private static int borderOverlayRadius;
    private static int borderColor;
    private static int borderOverlayColor;
    private static BitmapShader mBitmapShader;
    private static Matrix mShaderMatrix;

    public WGlideCircleWithBorderOverlayTransform(Context context, int borderRadius, int borderColor, int borderOverlayRadius, int borderOverlayColor) {
        super(context);
        this.borderRadius = borderRadius;
        this.borderColor = borderColor;
        this.borderOverlayRadius = borderOverlayRadius;
        this.borderOverlayColor = borderOverlayColor;
        this.mShaderMatrix = new Matrix();
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform, Math.min(outWidth, outHeight));
    }

    private static Bitmap circleCrop(BitmapPool pool, Bitmap source, int scaleFactor) {
        if (source == null) return null;

        int size = Math.min(source.getWidth(), source.getHeight());
        float scale = (float) size / (float) scaleFactor;
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        // TODO this could be acquired from the pool too
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
//        if (squared != source) {
//            source.recycle();
//        }

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        mBitmapShader = new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(mBitmapShader);
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        Paint paintOverlayBg = new Paint();
//        int borderOverlayColor = ColorUtils.setAlphaComponent(borderOverlayCorlor, 0xFF);
        paintOverlayBg.setColor(borderOverlayColor);
        paintOverlayBg.setAntiAlias(true);
        canvas.drawCircle(r, r, r, paintOverlayBg);

        // Prepare the background
        Paint paintBg = new Paint();
//        int borderColor = ColorUtils.setAlphaComponent(borderCorlor, 0xFF);
        paintBg.setColor(borderColor);
        paintBg.setAntiAlias(true);

        // Draw the background circle
        canvas.drawCircle(r, r, r - borderOverlayRadius * scale, paintBg);

        // Draw the image smaller than the background so a little border will be seen
        float bitmapScale = 1 - (borderRadius + borderOverlayRadius) * scale / r;
        mShaderMatrix.set(null);
        mShaderMatrix.setScale(bitmapScale, bitmapScale);
        mShaderMatrix.postTranslate((int) ((borderRadius + borderOverlayRadius) * scale + 0.5f),
                (int) ((borderRadius + borderOverlayRadius) * scale + 0.5));
        mBitmapShader.setLocalMatrix(mShaderMatrix);
        canvas.drawCircle(r, r, r - (borderRadius + borderOverlayRadius) * scale, paint);

        squared.recycle();

        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }}