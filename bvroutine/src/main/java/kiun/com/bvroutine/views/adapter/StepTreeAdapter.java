package kiun.com.bvroutine.views.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.base.BaseRecyclerAdapter;
import kiun.com.bvroutine.base.BindingHolder;
import kiun.com.bvroutine.data.viewmodel.TreeNode;
import kiun.com.bvroutine.data.viewmodel.TreeViewNode;
import kiun.com.bvroutine.interfaces.view.TreeStepView;
import kiun.com.bvroutine.presenters.StepTreePresenter;

public class StepTreeAdapter extends BaseRecyclerAdapter<TreeNode, StepTreePresenter> {

    TreeStepView mTreeStepView;
    int expHandlerBr = 0;
    public StepTreeAdapter(StepTreePresenter presenter, int expHandlerBr, int dataBr, BaseHandler handler, TreeStepView treeStepView) {
        super(presenter, 0, dataBr, handler);
        this.expHandlerBr = expHandlerBr;
        mTreeStepView = treeStepView;
    }

    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        if(mHandler != null && mHandler.getBR() > 0){
            binding.setVariable(mHandler.getBR(), mHandler);
        }

        BindingHolder holder = new BindingHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return listData.get(position).getLayout();
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {

//
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        holder.itemView.setVisibility(listData.get(position).isParentExpansion() ? View.VISIBLE : View.GONE);
        if (listData.get(position).isParentExpansion()){
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        }else{
            layoutParams.height = 0;
            layoutParams.width = 0;
        }
        holder.itemView.setLayoutParams(layoutParams);

        holder.getBinding().setVariable(mDataBr, listData.get(position).getExtra());
        holder.getBinding().setVariable(expHandlerBr, new TreeHandler(listData.get(position)));
        holder.getBinding().executePendingBindings();
        if(listViewPresenter != null && position >= listData.size() - 2){
            listViewPresenter.loadMore();
        }
    }

    public class TreeHandler extends BaseHandler{

        private TreeNode treeNode;
        public TreeHandler(TreeNode treeNode) {
            this.treeNode = treeNode;
        }

        @Override
        public void onClick(Context context, int tag, Object data) {

            if (treeNode.withPager()){

            }else{
                TreeViewNode treeViewNode = mTreeStepView.children(treeNode.getExtra());
                listViewPresenter.loadTree(treeNode, treeViewNode);
            }
            treeNode.expansion(!treeNode.isExpansion());
            notifyDataSetChanged();
        }

        public TreeNode getTreeNode() {
            return treeNode;
        }
    }
}
