package kiun.com.bvroutine.handlers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kiun.com.bvroutine.base.BVBaseActivity;
import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.data.BaseBean;
import kiun.com.bvroutine.data.KeyValue;
import kiun.com.bvroutine.interfaces.callers.SetCaller;

public class ActivityHandler<B extends Parcelable> extends BaseHandler<B>{

    private Class<? extends Activity> activityClz;
    private List<KeyValue<Class<? extends Activity>,SetCaller<Intent>>> keyValues = new LinkedList<>();

    public ActivityHandler(int handlerBr, Class<? extends Activity> clz){
        super(handlerBr);
        activityClz = clz;
    }

    public void addResult(Class<? extends Activity> clz, SetCaller<Intent> caller){
        keyValues.add(new KeyValue<>(clz, caller));
    }

    @Override
    public void onClick(Context context, int tag, B data) {

        Intent intent = new Intent(context, activityClz);
        intent.putExtra("EXTRA", data);
        context.startActivity(intent);
    }

    public void resultClick(Context context, int index, String key, B data){
        if (context instanceof BVBaseActivity && index >= 0 && index < keyValues.size()){
            Intent intent = new Intent(context, keyValues.get(index).key());
            if (data != null){
                intent.putExtra(key, data);
            }
            ((BVBaseActivity) context).startForResult(intent, keyValues.get(index).value());
        }
    }
}