package kiun.com.bindingdemo.warp;

import java.util.ArrayList;
import java.util.List;

public class TypeIndexGetter {

    private List<KeyValueGetDef> allPuts = new ArrayList<>();

    public <T>TypeIndexGetter addGetter(KeyValueGetDef<T> getter){
        allPuts.add(getter);
        return this;
    }

    public <T>T execute(String key, int index, Class<T> tClass, T defValue){
        KeyValueGetDef<T> valuePut = allPuts.get(index);
        if (valuePut != null){
            if (tClass == Double.class){
                return valuePut.get(key, defValue);
            }

        }
        return null;
    }
}