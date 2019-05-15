package kiun.com.bvroutine.data;

import android.databinding.ObservableField;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.interfaces.IgnoreParam;
import kiun.com.bvroutine.interfaces.QueryParam;

public class QueryBean<T> {

    private static String baseGuid = null;

    public static void setBaseGuid(String guid){
        baseGuid = guid;
    }

    public static String getBaseGuid(){
        return baseGuid;
    }

    private T extra;

    public QueryBean(){
        presId = baseGuid;
    }

    private String presId = null;

    public String getPresId() {
        return presId;
    }

    public void setPresId(String presId) {
        this.presId = presId;
    }

    public QueryBean(T extra){
        this();
        this.extra = extra;
    }

    private void putToJSON(JSONObject json, String name, Object value) throws JSONException {
        if (value != null) {
            if (value instanceof ObservableField) {
                json.put(name, ((ObservableField) value).get());
            } else {
                json.put(name, value);
            }
        }
    }

    private void putValueToJSON(JSONObject json, Object object, String base) throws JSONException, IllegalAccessException, InvocationTargetException {

        List<Field> allFields = new LinkedList<>();
        Class clz = object.getClass();
        while(!clz.getSimpleName().equals(base)){
            for (Field field : clz.getDeclaredFields()){
                if (Modifier.isStatic(field.getModifiers())){
                    continue;
                }
                if (!field.isAnnotationPresent(IgnoreParam.class)){
                    allFields.add(field);
                }
            }
            clz = clz.getSuperclass();
        }

        for (Field field : allFields) {
            field.setAccessible(true);
            QueryParam queryParam = field.getAnnotation(QueryParam.class);
            putToJSON(json, queryParam == null ? field.getName() : queryParam.value(), field.get(object));
        }

        for (Method method : object.getClass().getMethods()){
            QueryParam queryParam = method.getAnnotation(QueryParam.class);
            if (queryParam != null) {
                putToJSON(json, queryParam.value(), method.invoke(object));
            }
        }
    }

    public JSONObject getQuery(){
        JSONObject json = new JSONObject();
        try {
            if (extra != null) {
                putValueToJSON(json, extra, Object.class.getSimpleName());
            }
            putValueToJSON(json, this, QueryBean.class.getSimpleName());
            if (presId != null){
                json.put("presId", presId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return json;
    }

    public T getExtra() {
        return extra;
    }

    public void setExtra(T extra) {
        this.extra = extra;
    }
}
