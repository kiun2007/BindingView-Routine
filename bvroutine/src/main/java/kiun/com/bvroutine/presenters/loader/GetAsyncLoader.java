package kiun.com.bvroutine.presenters.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;
import kiun.com.bvroutine.interfaces.callers.GetTNoParamCall;
import kiun.com.bvroutine.interfaces.callers.SetCaller;

public class GetAsyncLoader extends AsyncTaskLoader<Object> {

    Object mApps;
    @Override
    public void deliverResult(Object apps) {
        if (isReset()) {
            if (apps != null) {
                onReleaseResources(apps);
            }
        }
        Object oldApps = mApps;
        mApps = apps;

        if (isStarted()) {
            super.deliverResult(apps);
        }

        if (oldApps != null) {
            onReleaseResources(oldApps);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mApps != null) {
            deliverResult(mApps);
        }
        if (takeContentChanged() || mApps == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(Object apps) {
        super.onCanceled(apps);
        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(apps);
    }


    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (mApps != null) {
            onReleaseResources(mApps);
            mApps = null;
        }
    }

    protected void onReleaseResources(Object apps) {
    }

    GetTNoParamCall<Object> asyncCaller;
    SetCaller<Object> callbackCaller;
    SetCaller<Exception> errCallbackCaller;
    public GetAsyncLoader(@NonNull Context context, GetTNoParamCall<Object> getCall, SetCaller<Object> callback, SetCaller<Exception> errCaller) {
        super(context);
        asyncCaller = getCall;
        callbackCaller = callback;
        errCallbackCaller = errCaller;
    }

    @Override
    public Object loadInBackground() {
        try{
            return asyncCaller.call();
        }catch (Exception ex){
            return ex;
        }
    }

    public SetCaller<Object> getCallback() {
        return callbackCaller;
    }

    public SetCaller<Exception> getErrorCallBack(){
        return errCallbackCaller;
    }
}