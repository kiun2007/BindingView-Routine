package kiun.com.bvroutine.views.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.base.BaseRecyclerAdapter;
import kiun.com.bvroutine.base.BindingHolder;
import kiun.com.bvroutine.handlers.ListHandler;
import kiun.com.bvroutine.interfaces.presenter.ListViewPresenter;

public class RecyclerSimpleAdapter extends BaseRecyclerAdapter<Object, ListViewPresenter>{

    protected static final int FOOTER_VIEW = -1;
    private View footerView = null;

    public RecyclerSimpleAdapter(ListViewPresenter presenter, int layoutId, int errorLayout, int dataBr, ListHandler handler){
        super(presenter, layoutId, errorLayout, dataBr, handler);
    }

    @Override
    public void addFooterView(View footerView) {
        this.footerView = footerView;
        notifySet();
    }

    @Override
    public void removeFooter(){
        this.footerView = null;
    }

    @Override
    public List showList() {
        return listData;
    }

    @Override
    public int getItemCount() {
        if (isError || showList() == null || showList().size() == 0){
            return 1;
        }
        return showList().size() + (footerView == null ? 0 : 1);
    }

    @Override
    public int getItemViewType(int position) {
        int baseType = super.getItemViewType(position);
        if (baseType < 0){
            return baseType;
        }
        if (position >= showList().size()){
            return FOOTER_VIEW;
        }
        return 0;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType < 0){
            if (viewType == FOOTER_VIEW){
                return new BindingHolder(footerView);
            }
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
                if (viewType == FOOTER_VIEW){
                    return;
                }else{
                    super.onBindViewHolder(holder, position);
                }
            }else{
                holder.getBinding().setVariable(mDataBr, listData.get(position));
                holder.getBinding().executePendingBindings();

                if(listViewPresenter != null && position >= listData.size() - 2){
                    if (footerView == null){
                        listViewPresenter.loadMore();
                    }
                }
            }
        }
    }
}
