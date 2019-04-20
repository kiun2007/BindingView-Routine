package kiun.com.bvroutine.views.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.base.BaseRecyclerAdapter;
import kiun.com.bvroutine.base.BindingHolder;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;

public class RecyclerSimpleAdapter extends BaseRecyclerAdapter<Object, ListViewPresenter>{

    public RecyclerSimpleAdapter(ListViewPresenter presenter, int layoutId, int dataBr, BaseHandler handler){
        super(presenter, layoutId, dataBr, handler);
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutId, parent, false);

        if(mHandler != null && mHandler.getBR() > 0){
            binding.setVariable(mHandler.getBR(), mHandler);
        }

        BindingHolder holder = new BindingHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {
        holder.getBinding().setVariable(mDataBr, listData.get(position));
        holder.getBinding().executePendingBindings();

        if(listViewPresenter != null && position >= listData.size() - 2){
            listViewPresenter.loadMore();
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
