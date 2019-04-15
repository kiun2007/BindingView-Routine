package kiun.com.bindingdemo.warp;


import java.io.IOException;
import java.util.List;

import kiun.com.bindingdemo.MainApplication;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static kiun.com.bindingdemo.warp.NetConstants.API_BASE_URL;
import static kiun.com.bindingdemo.warp.NetConstants.API_BASE_URL_SEC;

public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        String token = "5EB5847113044333A4B31D2307CD1EEB";
        String persId = token;

        Request request = chain.request()
                .newBuilder()
                .addHeader("version", NetConstants.APP_VERSION_V2)
                .addHeader("client", NetConstants.APP_CLIENT)
                .addHeader("source", SystemUtil.getDeviceBrand() + "," + SystemUtil.getSystemModel())
                .addHeader("uid", SystemUtil.getIMEI(MainApplication.app))
                .addHeader("token", token)//header添加token
                .addHeader("persId", persId)//header添加新token
                .build();

        Response response = null;
        //从request中获取headers，通过给定的键url_name
        List<String> headerValues = request.headers(NetConstants.URL_NAME);
        if (headerValues != null && headerValues.size() > 0) {
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            //builder.removeHeader(HttpConfig.HEADER_KEY);

            //匹配获得新的BaseUrl
            String headerValue = headerValues.get(0);
            HttpUrl newBaseUrl = null;
            if (NetConstants.URL_NAME_DEFAULT.equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(API_BASE_URL);
            } else if (NetConstants.URL_NAME_EMERGENCY.equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(API_BASE_URL_SEC);
            } else {
                newBaseUrl = request.url();
            }

            //从request中获取原有的HttpUrl实例oldHttpUrl
            HttpUrl oldHttpUrl = request.url();

            //重建新的HttpUrl，修改需要修改的url部分
            HttpUrl newFullUrl = oldHttpUrl
                    .newBuilder()
                    .scheme(newBaseUrl.scheme())
                    .host(newBaseUrl.host())
                    .port(newBaseUrl.port())
                    .build();

            Request requestNew = request.newBuilder().url(newFullUrl).build();

            response = chain.proceed(requestNew);
            MediaType mediaType = response.body().contentType();
            byte[] date = response.body().bytes();
            if (GZIPUtils.isGzip(response.headers())) {
                date = GZIPUtils.uncompress(date);
            }

            return response.newBuilder().body(ResponseBody.create(mediaType, date)).build();
        } else {
            return chain.proceed(request);
        }
    }
}
