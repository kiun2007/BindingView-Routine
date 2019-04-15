package kiun.com.bvroutine.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JSONUtil {

    private static class CaseHashMap extends HashMap{

        @Override
        public boolean containsKey(Object key) {
            if (key instanceof String){
                key = ((String) key).toLowerCase();
            }
            return super.containsKey(key);
        }

        @Override
        public Object get(Object key) {
            if (key instanceof String){
                key = ((String) key).toLowerCase();
            }
            return super.get(key);
        }

        @Override
        public Object put(Object key, Object value) {
            if (key instanceof String){
                key = ((String) key).toLowerCase();
            }
            return super.put(key, value);
        }
    }

    /**
     * 将JSON数组重新组装,方便通过主键查找想要的数据.
     * @param array JSON数组.
     * @param mapKey 检索主键.
     * @return 重新组装的JSON Map.
     */
    public static Map<String, JSONObject> assembleJSONMap(JSONArray array, String mapKey){

        Map<String, JSONObject> map = new CaseHashMap();
        if(array.length() == 0){
            return null;
        }

        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.optJSONObject(i);
            String keyValue;
            if(jsonObject != null && (keyValue = jsonObject.optString(mapKey)) != null){
                map.put(keyValue, jsonObject);
            }
        }
        return map;
    }

    public static<T extends JSONObject>T clone(T des, JSONObject src){
        if (src == null){
            return null;
        }

        for (Iterator<String> it = src.keys(); it.hasNext(); ) {
            String key = it.next();
            try {
                des.put(key, src.opt(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return des;
    }

    public static void fillJSON(JSONObject src, JSONObject des){
        List<String> keys = new LinkedList<>();
        for (Iterator<String> it = src.keys(); it.hasNext(); ) {
            String key = it.next();
            keys.add(key);
        }
        for (String key : keys){
            src.remove(key);
        }

        addJSON(src, des);
    }

    public static JSONObject addJSON(JSONObject src, JSONObject adds){

        if(adds == null){
            return src;
        }
        if (src == null){
            return adds;
        }

        for (Iterator<String> it = adds.keys(); it.hasNext(); ) {
            String key = it.next();
            try {
                src.put(key, adds.opt(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return src;
    }

    public static void addMap(JSONObject src, Map addMap){
        if(src == null || addMap == null){
            return;
        }
        for (Object urlParamName : addMap.keySet()){
            if(!src.has(urlParamName.toString())){
                try {
                    src.put(urlParamName.toString(), addMap.get(urlParamName));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static JSONObject copyJSON(JSONObject src, JSONObject desc, String[] igs){

        JSONObject values = new JSONObject();
        for (Iterator<String> it = src.keys(); it.hasNext(); ) {
            String key = it.next();
            if(MCString.isWith(key, igs)){
                continue;
            }
            try {
                values.put(key, src.opt(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (Iterator<String> it = desc.keys(); it.hasNext(); ) {
            String key = it.next();
            try {
                values.put(key, desc.opt(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return values;
    }

    public static JSONObject toJSONByBody(String body){
        if (body.indexOf("=") > 0){
            Map<String, String> maps = new HashMap<>();
            String[] paramItems = body.split("&");
            for (String paramItem : paramItems) {
                String[] keyValue = paramItem.split("=");
                if (keyValue != null && keyValue.length == 2){
                    maps.put(keyValue[0], keyValue[1]);
                }
            }
            return new JSONObject(maps);
        }
        return new JSONObject();
    }

    public static JSONObject toJSONByFile(File file){
        Map<String, Object> mapJSON = new HashMap<>();
        mapJSON.put("fileTitle", file.getName());
        int lastIndex = file.getName().lastIndexOf(".");
        mapJSON.put("fileExt", lastIndex < 0 ? "" : file.getName().substring(lastIndex+1));
        mapJSON.put("fileName", file.getName());
        mapJSON.put("filePath", file.getAbsolutePath());
        mapJSON.put("fileSize", file.length());
        mapJSON.put("createDate", MCString.formatDate("yyyy-MM-dd", new Date(file.lastModified())));
        mapJSON.put("id", MCString.randUUID());
        mapJSON.put("about_type", "");
        mapJSON.put("about_id", "");
        return new JSONObject(mapJSON);
    }

    public static String getWhereJSON(JSONObject data){

        String where = null;
        Map<String, Object> addList = new HashMap<>();

        for (Iterator<String> it = data.keys(); it.hasNext(); ) {
            String key = it.next(), newKey = null;
            boolean isWhere = key.indexOf("<WHE>") == 0;
            if (isWhere) newKey = key.substring(5);

            if (isWhere){
                if (where == null) where = "";
                where += String.format("%s = '%s',", newKey, data.optString(key));

                addList.put(newKey, data.opt(key));
            }
        }

        for (String key : addList.keySet()){
            data.remove("<WHE>" + key);
            try {
                data.put(key, addList.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (where != null) where = where.substring(0, where.length() - 1);
        return where;
    }

    public static JSONObject createStandardNull(String nullString){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("success", true);
            jsonObject.put("code", JSONObject.NULL);
            jsonObject.put("message", JSONObject.NULL);
            jsonObject.put("throwable", JSONObject.NULL);
            JSONTokener tokener = new JSONTokener(nullString);
            jsonObject.put("data", tokener.nextValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject createStandardError(int code, String message){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("success", false);
            jsonObject.put("code", code);
            jsonObject.put("message", message);
            jsonObject.put("throwable", JSONObject.NULL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 将字符串转化为JSON对象或JSON数组.
     * @param data JSON格式的字符串.
     * @return 返回的JSON对象.
     */
    public static Object getJSONData(String data) {
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject json = null;
        try {
            Object nextValue = jsonParser.nextValue();
            if (nextValue instanceof JSONObject) {
                json = (JSONObject) nextValue;
            }
            return json;
        } catch (JSONException e) {
        }
        return null;
    }
}
