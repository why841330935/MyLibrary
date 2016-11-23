package com.why.library.platform;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.why.library.WBaseApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * @author ymy
 * @// TODO: 2016/9/6 请求client 
 */
public class RetrofitUtils {
//    private static Context mContext;
    public static Retrofit retrofit = null;
//    public static String mTokenId;
    public static String MEDIA_TYPE_JSON = "application/json";
    public static Retrofit getInstance() {
//        mContext = context;
//        mTokenId = tokenId;
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(addHeaderInterceptor());

//            //设置缓存
//            File cacheFile = new File(mContext.getExternalCacheDir(), "RetrofitCache");
//            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
//            builder.cache(cache).addInterceptor(addCacheInterceptor());

            //设置超时
//            builder.connectTimeout(9, TimeUnit.SECONDS);
//            builder.readTimeout(9, TimeUnit.SECONDS);
//            builder.writeTimeout(9, TimeUnit.SECONDS);

            //错误重连
//            builder.retryOnConnectionFailure(false);

            OkHttpClient client = builder.connectTimeout(9,TimeUnit.SECONDS)
                    .readTimeout(9,TimeUnit.SECONDS)
                    .writeTimeout(9,TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseApi.API_BASE_DEVELOP_URL)
                    .addConverterFactory(StringConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }


    /**
     * 设置公共参数
     */
    private static Interceptor addQueryParameterInterceptor() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
                        .addQueryParameter("platform", "android")
                        .addQueryParameter("version", "1.0.0")
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }

    /**
     * 设置头
     */
    private static Interceptor addHeaderInterceptor() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        // Provide your custom header here
//                        .header("User-Agent", FAppUtil.getUserAgent())
//                        .header("X-Request-ID","android" + FAppUtil.getStringRandom(10))
//                        .header("X-Device-Type","ADR")
//                        .header("X-Sign", MD5Util.md5(FAppUtil.getTimeStamp() + "FFAndroid"))
//                        .header("X-TimeStamp",FAppUtil.getTimeStamp())
//                        .header("X-AuthToken",mTokenId)
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        return headerInterceptor;
    }

    /**
     * 设置缓存
     */
    private static Interceptor addCacheInterceptor() {
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!isNetworkAvailable()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (isNetworkAvailable()) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 7;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("nyn")
                            .build();
                }
                return response;
            }
        };
        return cacheInterceptor;
    }


    /**
     * 判断网络
     */
    public static boolean isNetworkAvailable() {
        Context context = WBaseApplication.getInstance().getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Gson getGson(){

        Gson gson = new GsonBuilder().excludeFieldsWithModifiers().create();
        return gson;
    }

}
