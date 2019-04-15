package kiun.com.bvroutine.views.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;
import kiun.com.bvroutine.interfaces.view.LoadAdapter;

public class RecyclerSimpleAdapter extends RecyclerView.Adapter<RecyclerSimpleAdapter.BindingHolder> implements LoadAdapter {

    private int mLayoutId;
    private int mDataBr;
    private BaseHandler mHandler;
    private List listData = new LinkedList();
    ListViewPresenter listViewPresenter;

    public RecyclerSimpleAdapter(ListViewPresenter presenter, int layoutId, int dataBr, BaseHandler handler){
        mLayoutId = layoutId;
        mDataBr = dataBr;
        mHandler = handler;
        listViewPresenter = presenter;
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

    @Override
    public void add(List list) {
        if(list != null){
            listData.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public void clear() {
        listData.clear();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;
        public BindingHolder(View itemView) {
            super(itemView);
        }
        public ViewDataBinding getBinding() {
            return binding;
        }
        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }

}
