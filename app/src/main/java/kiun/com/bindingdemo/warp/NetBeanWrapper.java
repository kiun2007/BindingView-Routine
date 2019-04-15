package kiun.com.bindingdemo.warp;

import kiun.com.bvroutine.interfaces.warp.DataWarp;

/**
 * Created by ${lcarry} on 2017/6/1.
 */

public class NetBeanWrapper<T> extends NetBaseWrapper implements DataWarp<T> {

    // 转化数据对象
    private T data;

    public NetBeanWrapper() {
        super();
    }

    public NetBeanWrapper(boolean flag, String msg) {
        super(flag, msg);
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isDataOK() {
        return data != null;
    }

    @Override
    public String toString() {
        return "NetBeanWrapper{" +
                "data=" + data +
                '}';
    }
}
