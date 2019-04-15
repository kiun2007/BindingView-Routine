/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kiun.com.bindingdemo.warp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    private String TAG=GsonResponseBodyConverter.class.getSimpleName();

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        try {
            //TODO：此处将接口返回的json字符串进行反序列化为对象，按需改造
            /** 接口返回结构如下
             {
             "code": "",
             "msg": "",
             "total": "",
             "rows": []
             }
             ** 需改造的结构如下
             {
             "code": "",
             "msg": "",
             "data":
             {
             "list":[]
             }
             }
             */
            JsonObject jsonValue = (JsonObject) new JsonParser().parse(value.string());

            JsonObject jsonRtValue = new JsonObject();
//            String strCode = jsonValue.get("code").getAsString();
//            String strMsg = jsonValue.get("msg").getAsString();
////            String strTotal = jsonValue.get("total").getAsString();//total暂时无用
//            jsonRtValue.addProperty("code",strCode);
//            jsonRtValue.addProperty("msg",strMsg);
            jsonRtValue.addProperty(Constants.CODE, Constants.SUCCESS);
            jsonRtValue.addProperty(Constants.MSG, Constants.BLANK);


            if (jsonValue.get(Constants.CODE) != null) {

                //存在code，为第二种接口标准
//                JsonReader jsonReader = gson.newJsonReader(value.charStream());
//                return adapter.read(jsonReader);

                if (jsonValue.get(Constants.DATA) == null || jsonValue.get(Constants.DATA).toString().equals("null")) {
                    JsonReader jsonReader = gson.newJsonReader(new StringReader(jsonValue.toString()));
                    return adapter.read(jsonReader);
                } else {

                    JSONTokener jsonTokener = new JSONTokener(jsonValue.get(Constants.DATA).toString());
                    Object object = null;
                    try {
                        object = jsonTokener.nextValue();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObject jsData;
                    if (object instanceof JSONArray) {
                        jsData = new JsonObject();
                        jsData.add(Constants.LIST, jsonValue.get(Constants.DATA));
                    } else {
                        jsData = jsonValue.get(Constants.DATA).getAsJsonObject();

//                    JsonObject jsObj =
//                    jsonRtValue.add(Constants.DATA, jsObj);
                    }
                    jsonRtValue.add(Constants.DATA, jsData);
                    /**
                     * 字段为success的时候没有这个字段
                     */
                     jsonRtValue.add(Constants.SUCCESSSTR, jsonValue.get(Constants.SUCCESSSTR));

                    JsonReader jsonReader = gson.newJsonReader(new StringReader(jsonRtValue.toString()));
                    return adapter.read(jsonReader);
                }
            } else {
                if (jsonValue.get(Constants.ROW) == null) {
                    //没有row字段
                    if (jsonValue.get(Constants.DATA_CAPITAL) != null) {
                        if (jsonValue.get(Constants.DATA_CAPITAL).isJsonArray()) {
                            //DATA 是json数组
                            JsonArray jsRows = jsonValue.get(Constants.DATA_CAPITAL).getAsJsonArray();
                            JsonObject jsonData = new JsonObject();
                            jsonData.add(Constants.LIST, jsRows);
                            jsonRtValue.add(Constants.DATA, jsonData);
                        } else if (jsonValue.get(Constants.DATA_CAPITAL).isJsonObject()) {
                            //DATA 是json对象
                            JsonObject jsObj = jsonValue.get(Constants.DATA_CAPITAL).getAsJsonObject();
                            jsonRtValue.add(Constants.DATA, jsObj);
                        } else {
                            //未知
                        }
                    } else {
                        if (jsonValue.get(Constants.DATA) != null) {
                            //data 是json对象
                            try {
//                                String jsStr = jsonValue.get(Constants.DATA).toString();
//                                JsonObject jsObj = new JsonObject();
//                                jsObj.addProperty("id", jsStr);

                                JsonObject jsObj = jsonValue.get(Constants.DATA).getAsJsonObject();
                                jsonRtValue.add(Constants.DATA, jsObj);
//                                jsonRtValue.add(Constants.DATA, jsObj);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                } else {
                    if (jsonValue.get(Constants.ROW).isJsonArray()) {
                        //TODO:根据total确定data数据是json对象或者json数组，条件不准
                        //TODO:尝试判断rows为json对象或者json数组
                        //表示json数组
                        JsonArray jsRows = jsonValue.get(Constants.ROW).getAsJsonArray();
                        JsonObject jsonData = new JsonObject();
//                jsonData.addProperty("total",strTotal);//total暂时无用
                        jsonData.add(Constants.LIST, jsRows);
                        jsonRtValue.add(Constants.DATA, jsonData);
                    } else if (jsonValue.get(Constants.ROW).isJsonObject()) {
                        //表示json对象
                        JsonObject jsObj = jsonValue.get(Constants.ROW).getAsJsonObject();
                        jsonRtValue.add(Constants.DATA, jsObj);
                    }
                }

                JsonReader jsonReader = gson.newJsonReader(new StringReader(jsonRtValue.toString()));
                return adapter.read(jsonReader);
            }

        } finally {
            value.close();
        }
    }
}
