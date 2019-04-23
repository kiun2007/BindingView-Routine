package kiun.com.bvroutine.views.adapter;

import android.content.Context;
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
import kiun.com.bvroutine.base.BaseRecyclerAdapter;
import kiun.com.bvroutine.base.BindingHolder;
import kiun.com.bvroutine.data.viewmodel.TreeNode;
import kiun.com.bvroutine.data.viewmodel.TreeViewNode;
import kiun.com.bvroutine.interfaces.view.LoadAdapter;
import kiun.com.bvroutine.interfaces.view.TreeLoadAdapter;
import kiun.com.bvroutine.interfaces.view.TreeStepView;
import kiun.com.bvroutine.presenters.StepTreePresenter;

public class StepTreeAdapter extends BaseRecyclerAdapter<TreeNode, StepTreePresenter> implements LoadAdapter<TreeNode> {

    TreeStepView mTreeStepView;
    int expHandlerBr;
    List<TreeNode> showTreeNodes = new LinkedList<>();

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
    public int getItemCount() {
        return showTreeNodes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return showTreeNodes.get(position).getLayout();
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {

        TreeNode treeNode = showTreeNodes.get(position);

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        holder.itemView.setVisibility(treeNode.isParentExpansion() ? View.VISIBLE : View.GONE);
        layoutParams.leftMargin = treeNode.parentLevel() * 60;

        holder.itemView.setLayoutParams(layoutParams);

        holder.getBinding().setVariable(mDataBr, treeNode.getExtra());
        holder.getBinding().setVariable(expHandlerBr, new TreeHandler(treeNode, position));
        holder.getBinding().executePendingBindings();
        if(listViewPresenter != null && position >= listViewPresenter.lastPosition()){
            listViewPresenter.loadMore();
        }
    }

    @Override
    public void add(List<TreeNode> list) {
        super.add(list);
    }

    @Override
    public void notifySet() {

        showTreeNodes.clear();
        for (int i = 0; i < listData.size(); i ++){
            showTreeNodes.add(listData.get(i));
            if (!listData.get(i).isExpansion() && listData.get(i).childCount() > 0){
                i += listData.get(i).childCount();
            }
        }
        super.notifySet();
    }

    public class TreeHandler extends BaseHandler{

        //展开.
        public final static int EVENT_EXP = 0;
        //选中.
        public final static int EVENT_CHECK = 1;

        private TreeNode treeNode;
        private int position;
        public TreeHandler(TreeNode treeNode, int position) {
            this.treeNode = treeNode;
            this.position = position;
        }

        @Override
        public void onClick(Context context, int tag, Object data) {

            if (EVENT_EXP == tag){
                if(!treeNode.isExpansion()){
                    if (treeNode.withPager()){
                    }else{
                        TreeViewNode treeViewNode = mTreeStepView.children(treeNode.getExtra());
                        listViewPresenter.loadTree(treeNode, treeViewNode);
                    }
                }
                treeNode.expansion(!treeNode.isExpansion());
                notifySet();
            }else if (EVENT_CHECK == tag){
                treeNode.setCheck();
                for (int i = position; i <= position + (treeNode.childCount() < 0 ? 0:treeNode.childCount()); i++) {
                    notifyItemChanged(i);
                }

                for (TreeNode parentTree = treeNode; parentTree != null; parentTree = parentTree.getParent()){
                    notifyItemChanged(showTreeNodes.indexOf(parentTree));
                }
            }
        }

        public TreeNode getTreeNode() {
            return treeNode;
        }
    }
}
