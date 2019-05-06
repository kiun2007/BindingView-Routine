package kiun.com.bvroutine.views.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.base.BaseRecyclerAdapter;
import kiun.com.bvroutine.base.BindingHolder;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;

public class RecyclerSimpleAdapter extends BaseRecyclerAdapter<Object, ListViewPresenter>{

    public RecyclerSimpleAdapter(ListViewPresenter presenter, int layoutId, int errorLayout, int dataBr, ListHandler handler){
        super(presenter, layoutId, errorLayout, dataBr, handler);
    }

    @Override
    public List showList() {
        return listData;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType < 0){
            return super.onCreateViewHolder(parent, viewType);
        }else{
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutId, parent, false);

            if(mHandler != null && mHandler.getBR() > 0){
                binding.setVariable(mHandler.getBR(), mHandler);
            }

            BindingHolder holder = new BindingHolder(binding.getRoot());
            holder.setBinding(binding);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {

        if (holder != null){

            int viewType;
            if ((viewType = getItemViewType(position)) < 0){
                super.onBindViewHolder(holder, position);
            }else{
                holder.getBinding().setVariable(mDataBr, listData.get(position));
                holder.getBinding().executePendingBindings();

                if(listViewPresenter != null && position >= listData.size() - 2){
                    listViewPresenter.loadMore();
                }
            }
        }
    }
}
