package kiun.com.bvroutine.presenters.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;
import kiun.com.bvroutine.interfaces.callers.GetTNoParamCall;
import kiun.com.bvroutine.interfaces.callers.SetCaller;

public class GetAsyncLoader<T> extends AsyncTaskLoader<T> {

    T mApps;
    @Override
    public void deliverResult(T apps) {
        if (isReset()) {
            if (apps != null) {
                onReleaseResources(apps);
            }
        }
        T oldApps = mApps;
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
    public void onCanceled(T apps) {
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

    protected void onReleaseResources(T apps) {
    }

    GetTNoParamCall<T> asyncCaller;
    SetCaller<T> callbackCaller;
    public GetAsyncLoader(@NonNull Context context, GetTNoParamCall<T> getCall, SetCaller<T> callback) {
        super(context);
        asyncCaller = getCall;
        callbackCaller = callback;
    }

    @Override
    public T loadInBackground() {
        try{
            return asyncCaller.call();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public SetCaller<T> getCallback() {
        return callbackCaller;
    }
}