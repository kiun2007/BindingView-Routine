package kiun.com.bvroutine.utils;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.interfaces.callers.CompareCaller;

public class ListUtil {

    public static<T> List<T>[] filters(List<T> list, boolean repeat, CompareCaller<T>... callers) {
        List<T> allItems = list;
        List<T>[] selectedItems = new List[callers.length];
        for (int i = 0; i < selectedItems.length; i++) {
            selectedItems[i] = new LinkedList<>();
        }

        for (T item : allItems) {
            for (int i = 0; i < callers.length; i++) {
                if (callers[i].call(item)){
                    selectedItems[i].add(item);
                    if (!repeat){
                        break;
                    }
                }
            }
        }
        return selectedItems;
    }

    public static<T> List<T> filter(List<T> list, CompareCaller<T> caller) {
        return filters(list, false,caller)[0];
    }

    public static<T> List<T> filter(List<T> list, boolean repeat, CompareCaller<T> caller) {
        return filters(list, repeat,caller)[0];
    }
}
