package kiun.com.bindingdemo.warp;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import kiun.com.bindingdemo.BuildConfig;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static kiun.com.bindingdemo.warp.NetConstants.API_BASE_URL;

/**
 * Created by ${lcarry} on 2017/6/1.
 */
public class ServiceGenerator {

    private static final String TAG = "ServiceGenerator";
    private static OkHttpClient okHttpClient;
    private static final String CHECK_PARAMS = "checkParams";
    public static Context CONTEXT;


    static {
        // 初始化http请求配置
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // 连接超时时间
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        // 连接写入超时时间
        httpClient.writeTimeout(20, TimeUnit.SECONDS);
        // 链接读取超时时间
        httpClient.readTimeout(20, TimeUnit.SECONDS);
        // http缓存大小
        httpClient.cache(new Cache(Environment.getDataDirectory(), 8 * 10 * 1000 * 1000));

        httpClient.addInterceptor(new CacheInterceptor());
        okHttpClient = httpClient.build();
    }

    public static void initServiceGenerator(Context context) {
        if (context != null) {
            CONTEXT = context;
        }
    }

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(MyGsonConverterFactory.create());

    private static Retrofit retrofit = builder.client(okHttpClient).build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
