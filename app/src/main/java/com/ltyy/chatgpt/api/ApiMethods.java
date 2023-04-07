package com.ltyy.chatgpt.api;

import android.content.Context;

import com.google.gson.Gson;
import com.ltyy.chatgpt.app.AppConstants;
import com.ltyy.chatgpt.app.AppSPContact;
import com.ltyy.chatgpt.bean.PromptRes;
import com.ltyy.chatgpt.param.PromptParams;
import com.ltyy.chatgpt.utils.JsonUtils;
import com.ltyy.chatgpt.utils.LogUtils;
import com.ltyy.chatgpt.utils.NetworkUtils;
import com.ltyy.chatgpt.utils.SharedPreferencesUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class ApiMethods {

    private static final String HTTP_INPUT_TYPE = "application/json";
    private static final String TAG = ApiMethods.class.getSimpleName();
    private static volatile ApiMethods singleton = null;
    private ApiService appApiService;

    public ApiMethods(ApiService apiService){
        this.appApiService = apiService;
    }

    public static ApiMethods instance(Context context) {
        if (singleton == null) {
            synchronized (ApiMethods.class) {
                if (singleton == null) {
                    singleton = new Builder(createAppApiService(context)).build();
                }
            }
        }
        return singleton;
    }

    public static ApiMethods getInstance(){
        return singleton;
    }

    public static class Builder {
        private ApiService appApiService;

        public Builder(ApiService appApiService) {
            if (appApiService == null) {
                throw new IllegalArgumentException("AppApiService must not be null.");
            }
            this.appApiService = appApiService;
        }

        public ApiMethods build() {
            return new ApiMethods(appApiService);
        }
    }

    private static ApiService createAppApiService(final Context context) {
        String apiKey = SharedPreferencesUtils.getInstance().getString(AppSPContact.SP_PARAM_API_KEY);
        String apiHost = SharedPreferencesUtils.getInstance().getString(AppSPContact.SP_PARAM_API_HOST, "https://api.openai.com/");
        Interceptor requestInterceptor = chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .header("Authorization", "Bearer " + apiKey)
                    .build();
            LogUtils.d(TAG, "apiKey:" + apiKey);
            LogUtils.d(TAG, "AppApiService url:" + request.url().toString());
            return chain.proceed(request);
        };
        //云端响应头拦截器，用来配置缓存策略
        Interceptor rewriteCacheControlInterceptor = chain -> {
            Request request = chain.request();
            if (!NetworkUtils.isNetworkAvailable(context)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isNetworkAvailable(context)) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        };
        File baseDir = context.getCacheDir();
        final File cacheDir = new File(baseDir, "HttpResponseCache");
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient httpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addNetworkInterceptor(rewriteCacheControlInterceptor)
                .addInterceptor(rewriteCacheControlInterceptor)
                .connectTimeout(AppConstants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(AppConstants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .cache(new Cache(cacheDir, AppConstants.HTTP_RESPONSE_DISK_CACHE_MAX_SIZE))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder)
                .addConverterFactory(LenientGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(apiHost)
                .build();
        return retrofit.create(ApiService.class);
    }

    public RequestBody createRequestBody(String json) {
        LogUtils.d(TAG, json);
        return RequestBody.create(MediaType.parse(HTTP_INPUT_TYPE), json);
    }

    public void getPrompt(Observer<PromptRes> observer, PromptParams param) {
        appApiService.getPrompt(createRequestBody(JsonUtils.get().toJson(param))).
                compose(new RedirectResponseTransformer<>()).subscribe(observer);
    }

}
