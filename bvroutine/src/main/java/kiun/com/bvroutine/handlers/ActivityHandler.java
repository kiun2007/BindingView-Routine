package kiun.com.bvroutine.handlers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.data.BaseBean;

public class ActivityHandler<B extends BaseBean> extends BaseHandler<B>{

    private Class<? extends Activity> activityClz;

    public ActivityHandler(int handlerBr, Class<? extends Activity> clz){
        super(handlerBr);
        activityClz = clz;
    }

    @Override
    public void onClick(Context context, int tag, B data) {
        Intent intent = new Intent(context, activityClz);
        intent.putExtra("EXTRA", data);
        context.startActivity(intent);
    }
}