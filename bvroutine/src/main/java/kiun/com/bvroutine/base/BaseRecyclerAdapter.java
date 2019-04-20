package kiun.com.bvroutine.base;

import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.interfaces.view.LoadAdapter;

public abstract class BaseRecyclerAdapter<T, L extends ListViewPresenter> extends RecyclerView.Adapter<BindingHolder> implements LoadAdapter<T> {

    protected int mLayoutId;
    protected int mDataBr;
    protected BaseHandler mHandler;
    protected List<T> listData = new LinkedList();
    protected L listViewPresenter;

    public BaseRecyclerAdapter(L presenter, int layoutId, int dataBr, BaseHandler handler){
        mLayoutId = layoutId;
        mDataBr = dataBr;
        mHandler = handler;
        listViewPresenter = presenter;
    }

    @Override
    public void add(List<T> list) {
        if(list != null){
            listData.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public void clear() {
        listData.clear();
    }

    @Override
    public List<T> getAll() {
        return listData;
    }

    @Override
    public void notifySet() {
        notifyDataSetChanged();
    }
}
