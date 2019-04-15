package kiun.com.bvroutine.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by kiun_2007 on 2018/7/23.
 */

public class HTTPUtil {

    static class Code{
        int code = 0;
        public Code(int code){
            this.code = code;
        }
        public int setCode(int code){
            return this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public static void configBase(String base){
    }

    public static MultipartBody.Part createMultipartBody(byte[] file){
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData("file", MCString.randUUID() + ".zip", requestFile);
    }

    private static JSONObject uploadFile(String uploadUrl, JSONObject header, Object file) {

        boolean isFile = file instanceof File;
        MultipartBody.Part body = null;

        RequestBody requestFile = isFile ? RequestBody.create(MediaType.parse("multipart/form-data"), (File)file) : RequestBody.create(MediaType.parse("multipart/form-data"), (byte[]) file);
        body = MultipartBody.Part.createFormData("file", isFile ? ((File)file).getName() : MCString.randUUID(), requestFile);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addPart(body);

        OkHttpClient client = new OkHttpClient();

        Request.Builder requestBuilder = new Request.Builder().url(uploadUrl);

        for (Iterator<String> it = header.keys(); it.hasNext(); ) {
            String key = it.next();
            requestBuilder.addHeader(key, header.optString(key));
        }

        Request request = requestBuilder.post(builder.build()).build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if(response.code() == 200){
                return getJSONData(response.body().string());
            }else{
                return JSONUtil.createStandardError(response.code(), response.message());
            }
        } catch (IOException e) {
            return JSONUtil.createStandardError(1001, e.getMessage());
        }
    }

    public static JSONObject uploadFile(String uploadUrl, JSONObject header, byte[] file) {
        return uploadFile(uploadUrl, header, (Object) file);
    }

    public static JSONObject uploadFile(String uploadUrl, JSONObject header, File file) {
        return uploadFile(uploadUrl, header, (Object) file);
    }

    private static JSONObject getJSONData(String json){
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject postURLJSON(String url, JSONObject data, JSONObject header){

        Code code = new Code(1001);
        String responseValue = postURL(url, data.toString(), header, code);
        if(code.getCode() != 200){
            return JSONUtil.createStandardError(code.getCode(), responseValue);
        }
        return getJSONData(responseValue);
    }

    public static String postURL(String url, String data, JSONObject header, Code code){

        if(code == null){
            code = new Code(0);
        }
        try {
            URL getterUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) getterUrl.openConnection();
            connection.setRequestMethod("POST");
            if (header != null){
                for (Iterator<String> it = header.keys(); it.hasNext(); ) {
                    String key = it.next();
                    connection.setRequestProperty(key, header.optString(key));
                }
            }
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(25000);

            connection.setDoInput(true);
            connection.setDoOutput(true);

            if(data != null){
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(data);
                out.flush();
                out.close();
            }

            InputStream in = null;
            if((code.setCode(connection.getResponseCode())) == 200){
                in = connection.getInputStream();
            }else{
                in = connection.getErrorStream();
            }

            //下面对获取到的输入流进行读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line = null;
            while((line = reader.readLine())!=null){
                response.append(line);
            }
            return response.toString();
        } catch (MalformedURLException e) {
            code.setCode(1002);
            return e.getMessage();
        } catch (IOException e) {
            code.setCode(1001);
            return e.getMessage();
        }
    }

    public static String postURL(String url, String data){
        return postURL(url, data, null, null);
    }
}