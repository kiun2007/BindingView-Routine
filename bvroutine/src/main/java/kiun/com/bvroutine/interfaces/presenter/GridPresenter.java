package kiun.com.bvroutine.interfaces.presenter;

import java.util.List;

public interface GridPresenter<T>{

    void add(T item);

    void remove(T item);

    void clear();

    void notifySet();

    void addList(List<T> items);

    List<T> allList();
}
