package kiun.com.bindingdemo.warp;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import kiun.com.bvroutine.interfaces.warp.ListWarp;

/**
 * Created by ${lcarry} on 2017/6/1.
 */

public class NetListBeanWrapper<T> extends NetBaseWrapper implements ListWarp<T> {

    // 转化数据对象
    private DataListWrapper<T> data;

    public NetListBeanWrapper() {
        super();
    }

    public NetListBeanWrapper(boolean flag, String msg) {
        super(flag, msg);
    }

    public DataListWrapper<T> getData() {
        return data;
    }


    public List<T> getList() {
        return data == null ? null : data.getList();
    }

    @Override
    public int getPages() {
        return data == null ? 0 : data.getPages();
    }

    public void setData(DataListWrapper<T> data) {
        this.data = data;
    }


    public static class DataListWrapper<S> {

        public DataListWrapper() {
        }

        public DataListWrapper(List<S> data) {
            list = data;
        }

        private int pages;

        private List<S> list;

        public List<S> getList() {
            return list;
        }

        public void setList(List<S> list) {
            this.list = list;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }
    }

    public boolean isDataListOK() {
        return (data != null) && (data.getList() != null);
    }

    private Class<T> clazz;
    public Class<?> getEntityClass() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
        return clazz;
    }
}
