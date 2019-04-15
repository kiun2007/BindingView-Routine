package kiun.com.bvroutine.interfaces.view;

import java.util.List;

public interface LoadAdapter<T> {

    /**
     * 添加数据到尾部.
     * @param list 新增的数据.
     */
    void add(List<T> list);

    /**
     * 清除所有数据.
     */
    void clear();
}
