package kiun.com.bindingdemo;

import android.text.TextUtils;
import android.view.View;

import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VueWebViewClient extends InterceptorWebViewClient {

    static Map<String,byte[]> javascriptPools = new HashMap<>();
    static Map<String,Map<String, String>> headerPools = new HashMap<>();

    View loadingView;
    public VueWebViewClient(WebView webView, View loadingView, String url) {
        super(webView, url);
        this.loadingView = loadingView;
    }

    protected void fillResource(Map<String, String> header, String resType){
        header.put("Connection", "keep-alive");
        header.put("Content-Type", "Content-Type,X-Requested-With,token,persId,source");
        header.put("Access-Control-Allow-Methods", "*");
        header.put("Date", new Date().toGMTString());
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest request) {

        String contentType = null;
        Map<String, String> responseHeaders = new HashMap<>();

        if (request.getRequestHeaders().containsKey("Content-type")){
            contentType = request.getRequestHeaders().get("Content-type");
        }

        if(TextUtils.isEmpty(contentType) || contentType.indexOf("application/json") < 0){

            //js css 等资源.
            String accept = request.getRequestHeaders().get("Accept");
            if (!TextUtils.isEmpty(accept)){

                String url = request.getUrl().toString();
                WebResourceResponse response = null;
                byte[] data;
                bodyString = null;

                if (accept.indexOf("text/css") > -1){
                    response = new WebResourceResponse("text/css", "UTF-8", null);
                }else{
                    if (url.lastIndexOf(".js") > 0){
                        response = new WebResourceResponse("application/x-javascript", "UTF-8", null);
                    }else if (url.lastIndexOf(".html") > 0){
                        response = new WebResourceResponse("text/html", "UTF-8", null);
                    }
                }

                if (javascriptPools.containsKey(url)){
                    data = javascriptPools.get(url);
                    responseHeaders = headerPools.get(url);
                }else{
                    data = requestNetWork(request, responseHeaders);
                    javascriptPools.put(url,data);
                    headerPools.put(url, responseHeaders);
                }

                if (response != null){
                    response.setData(data == null ? null : new ByteArrayInputStream(data));
                    response.setResponseHeaders(responseHeaders);
                }
                return response;
            }
            return null;
        }
        return super.shouldInterceptRequest(webView, request);
    }

    @Override
    public void onPageFinished(WebView webView, String s) {

    }
}
