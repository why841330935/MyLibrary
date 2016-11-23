package com.why.library.imageload;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.why.library.R;
import com.why.library.WBaseApplication;
import com.why.library.tool.WLogTool;
import com.why.library.tool.WScreenUtil;

/**
 * Created by Liujc on 2016/4/25.
 * Email liujiangchuan@hotmail.com
 */
public class WImageLoader
{
    private WImageLoader()
    {
    }

    private static class GlideControlHolder
    {
        private static WImageLoader instance = new WImageLoader();
    }

    public static WImageLoader getInstance()
    {
        return GlideControlHolder.instance;
    }

    /**
     * glide加载图 默认展位图&error图
     * @param context
     * @param url
     * @param imageView
     */
    public void loadImage(Context context, String url, ImageView imageView)
    {
        Glide.with(context).load(url).placeholder(R.color.f_transparent_1a).error(R.color.f_transparent_1a).crossFade().into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", Url=" + url + ", ImageView=" + imageView.toString());
    }

    /**
     * 加载图片
     * @param context 所在上下文
     * @param url url
     * @param imageView 資源id
     * @param defaultImg 展位图&error图
     */
    public void loadImage(Context context,String url,ImageView imageView,int defaultImg){
        Glide.with(context).load(url).placeholder(defaultImg)
                .error(defaultImg).crossFade().into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", Url=" + url + ", ImageView=" +
                imageView.toString());
    }

    public void loadImage(String url, ImageView imageView)
    {
        loadImage(WBaseApplication.getInstance(), url, imageView);
    }

    public void loadUriImage(Context context, Uri uri, ImageView imageView)
    {
        Glide.with(context).load(uri).into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", Uri=" + uri.toString() +
                ", ImageView=" + imageView.toString());
    }

    public void loadUriImage(Uri uri, ImageView imageView)
    {
        loadUriImage(WBaseApplication.getInstance(), uri, imageView);
    }

    public void loadResImage(Context context, int resId, ImageView imageView)
    {
        Glide.with(context).load(resId).placeholder(R.color.f_transparent_1a)
                .error(R.color.f_transparent_1a).crossFade().into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", resId=" + resId + ", ImageView=" +
                imageView.toString());
    }

    public void loadResImage(int resId, ImageView imageView)
    {
        loadResImage(WBaseApplication.getInstance(), resId, imageView);
    }

    public void loadLocalImage(Context context, String path, ImageView imageView)
    {
        Glide.with(context).load("file://" + path).placeholder(R.color.f_transparent_1a)
                .error(R.color.f_transparent_1a).crossFade().into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", Path=" + path + ", ImageView=" +
                imageView.toString());
    }

    public void loadLocalImage(String path, ImageView imageView)
    {
        loadLocalImage(WBaseApplication.getInstance(), path, imageView);
    }

    /**
     * gilde默认加载圆形图片
     * @param context
     * @param url
     * @param imageView
     */
    public void loadCircleImage(Context context, String url, ImageView imageView)
    {
        Glide.with(context).load(url).placeholder(R.color.transparent).error(R.color.transparent).crossFade()
                .transform(new WGlideCircleTransform(context)).into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", Url=" + url + ", ImageView=" +
                imageView.toString());
    }

    /**
     * gilde加载圆形图片
     * @param context
     * @param url
     * @param imageView
     * @param defaultImg 展位图&error图
     */
    public void loadCircleImage(Context context, String url, ImageView imageView,int defaultImg)
    {
        Glide.with(context).load(url).placeholder(defaultImg)
                .error(defaultImg).crossFade()
                .transform(new WGlideCircleTransform(context)).into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", Url=" + url + ", ImageView=" +
                imageView.toString());
    }

    public void loadCircleImage(String url, ImageView imageView)
    {
        loadCircleImage(WBaseApplication.getInstance(), url, imageView);
    }

    public void loadCircleResImage(Context context, int resId, ImageView imageView)
    {
        Glide.with(context).load(resId).placeholder(R.color.f_transparent_1a)
                .error(R.color.f_transparent_1a).crossFade()
                .transform(new WGlideCircleTransform(context)).into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", resId=" + resId + ", ImageView=" +
                imageView.toString());
    }

    public void loadCircleResImage(int resId, ImageView imageView)
    {
        loadCircleResImage(WBaseApplication.getInstance(), resId, imageView);
    }

    public void loadCircleLocalImage(Context context, String path, ImageView imageView)
    {
        Glide.with(context).load("file://" + path).placeholder(R.color.f_transparent_1a)
                .error(R.color.f_transparent_1a).crossFade()
                .transform(new WGlideCircleTransform(context)).into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", Path=" + path + ", ImageView=" +
                imageView.toString());
    }

    public void loadCircleLocalImage(String path, ImageView imageView)
    {
        loadCircleLocalImage(WBaseApplication.getInstance(), path, imageView);
    }

    /**
     * glide加载gif图片
     * @param context
     * @param resId 资源id
     * @param imageView
     */
    public void loadResGif(Context context, int resId, ImageView imageView)
    {
        Glide.with(context).load(resId).asGif().placeholder(R.color.f_transparent_1a)
                .error(R.color.f_transparent_1a).into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", resId=" + resId + ", ImageView=" +
                imageView.toString());
    }

    public void loadResGif(int resId, ImageView imageView)
    {
        loadResGif(WBaseApplication.getInstance(), resId, imageView);
    }

    public void setOnScrollStateChanged(int scollState)
    {
        switch (scollState)
        {
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                Glide.with(WBaseApplication.getInstance()).pauseRequests();
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                Glide.with(WBaseApplication.getInstance()).resumeRequests();
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                Glide.with(WBaseApplication.getInstance()).resumeRequests();
        }
    }

    /**
     * @param context
     * @param url
     * @param imageView
     * @param rx        unit dp
     * @param ry        unit dp
     */
    public void loadRoundRectImage(Context context, String url, ImageView imageView, int rx, int ry)
    {
        Glide.with(context).load(url).placeholder(R.color.f_transparent_1a)
                .error(R.color.f_transparent_1a).crossFade()
                .transform(new WGlideRoundRectTransform(context, rx, ry)).into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", Url=" + url + ", ImageView=" +
                imageView.toString());
    }

    /**
     * @param url
     * @param imageView
     * @param rx        unit dp
     * @param ry        unit dp
     */
    public void loadRoundRectImage(String url, ImageView imageView, int rx, int ry)
    {
        loadRoundRectImage(WBaseApplication.getInstance(), url, imageView, rx, ry);
    }

    /**
     * @param context
     * @param resId
     * @param imageView
     * @param rx        unit dp
     * @param ry        unit dp
     */
    public void loadRoundRectImageImage(Context context, int resId, ImageView imageView, int rx,
                                        int ry)
    {
        Glide.with(context).load(resId).placeholder(R.color.f_transparent_1a)
                .error(R.color.f_transparent_1a).crossFade()
                .transform(new WGlideRoundRectTransform(context, rx, ry)).into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", resId=" + resId + ", ImageView=" +
                imageView.toString());
    }

    /**
     * @param resId
     * @param imageView
     * @param rx        unit dp
     * @param ry        unit dp
     */
    public void loadRoundRectImageImage(int resId, ImageView imageView, int rx, int ry)
    {
        loadRoundRectImageImage(WBaseApplication.getInstance(), resId, imageView, rx, ry);
    }

    /**
     * @param context
     * @param path
     * @param imageView
     * @param rx        unit dp
     * @param ry        unit dp
     */
    public void loadRoundRectImageImage(Context context, String path, ImageView imageView, int rx,
                                        int ry)
    {
        Glide.with(context).load("file://" + path).placeholder(R.color.f_transparent_1a)
                .error(R.color.f_transparent_1a).crossFade()
                .transform(new WGlideRoundRectTransform(context, rx, ry)).into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", Path=" + path + ", ImageView=" +
                imageView.toString());
    }

    /**
     * @param path
     * @param imageView
     * @param rx        unit dp
     * @param ry        unit dp
     */
    public void loadRoundRectImageImage(String path, ImageView imageView, int rx, int ry)
    {
        loadRoundRectImageImage(WBaseApplication.getInstance(), path, imageView, rx, ry);
    }

    public void loadRoundCornersImage(Context context, String url, Drawable holderDrawable, ImageView imageView, int radius, WGlideRoundCornersTransform.CornerType cornerType){
        Glide.with(context).load(url).asBitmap().placeholder(holderDrawable)
                .error(holderDrawable)
                .transform(new WGlideRoundCornersTransform(context, radius, 0,
                        cornerType)).into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", Url=" + url + ", ImageView=" +
                imageView.toString());
    }

    public void loadRoundCornersImage(String url, Drawable holderDrawable, ImageView imageView, int radius, WGlideRoundCornersTransform.CornerType cornerType){
        loadRoundCornersImage(WBaseApplication.getInstance(), url, holderDrawable, imageView, radius, cornerType);
    }

    public void loadRoundCornersImage(Context context, int resId, ImageView imageView, int radius, WGlideRoundCornersTransform.CornerType cornerType){
        Glide.with(context).load(resId).placeholder(R.color.f_transparent_1a)
                .error(R.color.f_transparent_1a).crossFade()
                .bitmapTransform(new WGlideRoundCornersTransform(context, WScreenUtil.setAutoAdjustScreen(radius), 0,
                        cornerType)).into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", resId=" + resId + ", ImageView=" +
                imageView.toString());
    }

    public void loadRoundCornersImage(int resId, ImageView imageView, int radius, WGlideRoundCornersTransform.CornerType cornerType){
        loadRoundCornersImage(WBaseApplication.getInstance(), resId, imageView, radius, cornerType);
    }

    /**
     * 加载圆形带边框image
     * @param borderColorRes 内层边框颜色
     * @param overlayColorRes 外层边框颜色
     * */
    public void loadCircleWithBorderOverlayImage(Context context, int placeholderRes, int resId, ImageView imageView, int borderColorRes, int overlayColorRes){
        final int borderWidth = 7;
        final int overlayWidth = 4;
        Glide.with(context).load(resId).placeholder(placeholderRes)
                .error(R.color.f_transparent_1a).crossFade()
                .bitmapTransform(new WGlideCircleWithBorderOverlayTransform(context, WScreenUtil.setAutoAdjustScreen(borderWidth), context.getResources().getColor(borderColorRes), WScreenUtil.setAutoAdjustScreen(overlayWidth), context.getResources().getColor(overlayColorRes))).into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", resId=" + resId + ", ImageView=" +
                imageView.toString());
    }

    /**
     * 加载圆形带边框image
     * @param borderColorRes 内层边框颜色
     * @param overlayColorRes 外层边框颜色
     * */
    public void loadCircleWithBorderOverlayImage(Context context,int placeholderRes, String url, ImageView imageView, int borderColorRes, int overlayColorRes){
        final int borderWidth = 7;
        final int overlayWidth = 4;
        Glide.with(context).load(url).placeholder(placeholderRes)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(false)
                .bitmapTransform(new WGlideCircleWithBorderOverlayTransform(context, WScreenUtil.setAutoAdjustScreen(borderWidth), context.getResources().getColor(borderColorRes), WScreenUtil.setAutoAdjustScreen(overlayWidth), context.getResources().getColor(overlayColorRes))).into(imageView);
        WLogTool.i("Context=" + context.getClass().getName() + ", url=" + url + ", ImageView=" +
                imageView.toString());
    }
}
