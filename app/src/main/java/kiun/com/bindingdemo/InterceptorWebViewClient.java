package kiun.com.bindingdemo;

import android.util.Log;

import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sky on 2019/3/24.
 */

public class InterceptorWebViewClient extends WebViewClient{

    public String bodyString = null;
    private OkHttpClient client = new OkHttpClient();
    private WebView mWebView;
    private String h5Url;

    public InterceptorWebViewClient(WebView webView, String url){
        mWebView = webView;
        h5Url = url;
    }

    //覆盖shouldOverrideUrlLoading 方法
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    protected void fillStandardHeaders(Map<String, String> responseHeaders){

        responseHeaders.put("Transfer-Encoding", "chunked");
        responseHeaders.put("Server", "nginx/1.14.2");
        responseHeaders.put("Access-Control-Allow-Origin", "*");
        responseHeaders.put("Access-Control-Allow-Credentials", "true");
        responseHeaders.put("Access-Control-Allow-Methods", "*");
        responseHeaders.put("Connection", "keep-alive");
        responseHeaders.put("Access-Control-Max-Age", "3600");
        responseHeaders.put("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,token,persId,source");
        responseHeaders.put("Date", new Date().toGMTString());
        responseHeaders.put("Content-Type", "application/json;charset=utf-8");
    }

    protected void fillOptionsHeaders(Map<String, String> responseHeaders){
        responseHeaders.put("Access-Control-Allow-Credentials", "true");
        responseHeaders.put("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,token,persId,source");
        responseHeaders.put("Access-Control-Allow-Methods", "*");
        responseHeaders.put("Access-Control-Allow-Origin", "*");
        responseHeaders.put("Access-Control-Max-Age", "3600");
        responseHeaders.put("Allow", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        responseHeaders.put("Date", new Date().toGMTString());
        responseHeaders.put("Keep-Alive", "timeout=58");
        responseHeaders.put("Server", "nginx/1.14.2");
        responseHeaders.put("Transfer-Encoding", "chunked");
    }

    private long countTime = 0;
    protected byte[] requestNetWork(WebResourceRequest webRequest, Map<String,String> headers){

        String requestBody = bodyString;
        long startTime = System.currentTimeMillis();

        Request.Builder builder = new Request.Builder().url(webRequest.getUrl().toString());
        for (String key : webRequest.getRequestHeaders().keySet()){
            builder.addHeader(key, webRequest.getRequestHeaders().get(key));
        }

        Request request;
        if(webRequest.getMethod().equals("POST")){
            MediaType json = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(json, requestBody == null ? "" : requestBody);
            request = builder.post(body).build();
        }else if (webRequest.getMethod().equals("GET")){
            request = builder.get().build();
        }else{
            return null;
        }

        try {
            Response response = client.newCall(request).execute();
            String dataString = response.body().string();
            long time = System.currentTimeMillis() - startTime;

            Log.i("requestNetWork", String.valueOf(time) + ", count = " + (countTime += time));

            for (String name : response.headers().names()){
                headers.put(name, response.header(name));
            }
            return dataString.getBytes("utf-8");
        } catch (IOException e) {
        }
        return null;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {

        WebResourceResponse resourceResponse = super.shouldInterceptRequest(webView, webResourceRequest);
        Map<String, String> responseHeaders = new HashMap<>();

        //拦截ajax配置方法.
        if(webResourceRequest.getMethod().equals("OPTIONS")){

            resourceResponse = new WebResourceResponse();
            fillOptionsHeaders(responseHeaders);
            resourceResponse.setResponseHeaders(responseHeaders);
            resourceResponse.setStatusCodeAndReasonPhrase(200, "OK");
            return resourceResponse;
        }


        byte[] data = requestNetWork(webResourceRequest, responseHeaders);

        bodyString = null;

        resourceResponse = new WebResourceResponse("application/json", "UTF-8", data == null ? null : new ByteArrayInputStream(data));
        resourceResponse.setMimeType("application/json");
        if(data == null){
            resourceResponse.setStatusCodeAndReasonPhrase(404,"OK");
        }else{
            resourceResponse.setStatusCodeAndReasonPhrase(200,"OK");
        }
        resourceResponse.setResponseHeaders(responseHeaders);
        return resourceResponse;
    }

    public void setBody(String body) {
        bodyString = body;
    }
}
