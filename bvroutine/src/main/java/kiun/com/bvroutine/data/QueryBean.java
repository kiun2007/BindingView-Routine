package kiun.com.bvroutine.data;

import android.databinding.ObservableField;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class QueryBean {

    public JSONObject getQuery(){

        List<Field> allFields = new LinkedList<>();
        Class clz = getClass();
        while(!clz.getSimpleName().equals(QueryBean.class.getSimpleName())){
            for (Field field : clz.getDeclaredFields()){
                allFields.add(field);
            }
            clz = clz.getSuperclass();
        }

        JSONObject jsonObject = new JSONObject();

        for (Field field : allFields) {
            Object value;
            try {
                field.setAccessible(true);
                value = field.get(this);
                if(value != null){
                    if (value instanceof ObservableField){
                        jsonObject.put(field.getName(), ((ObservableField) value).get());
                    }else{
                        jsonObject.put(field.getName(), value);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
}
