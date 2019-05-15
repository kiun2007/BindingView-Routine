package kiun.com.bvroutine.base;

import android.content.Context;

public class BaseHandler<T>{

    protected int handlerBR = 0;

    public BaseHandler(){
    }

    public BaseHandler(int handlerBR){
        this.handlerBR = handlerBR;
    }

    public void onClick(Context context, int tag, T data){
    }

    public int getBR() {
        return handlerBR;
    }
}