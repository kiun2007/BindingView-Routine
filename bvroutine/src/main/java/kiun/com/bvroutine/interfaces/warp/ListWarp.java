package kiun.com.bvroutine.interfaces.warp;

import java.util.List;

public interface ListWarp<T> {

    List<T> getList();

    /**
     * 获取总页码数.
     * @return 总共有多少页.
     */
    int getPages();

    /**
     * 获取一共有多少条数据.
     * @return 总共有多少条数据.
     */
    int getTotal();
}