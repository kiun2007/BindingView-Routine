package kiun.com.bvroutine.presenters;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import java.util.LinkedList;
import java.util.List;
import kiun.com.bvroutine.base.BaseHandler;
import kiun.com.bvroutine.base.BaseRecyclerAdapter;
import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.data.QueryBean;
import kiun.com.bvroutine.data.viewmodel.TreeNode;
import kiun.com.bvroutine.data.viewmodel.TreeViewNode;
import kiun.com.bvroutine.interfaces.view.LoadAdapter;
import kiun.com.bvroutine.interfaces.view.TreeStepView;
import kiun.com.bvroutine.views.adapter.StepTreeAdapter;

/**
 *
 */
public class StepTreePresenter extends RecyclerListPresenter<Object, QueryBean, TreeStepView, LoadAdapter> {

    private int rootLayout;
    private int expHandlerBr;

    private TreeNode lastParent;
    private TreeViewNode lastViewNode;

    public StepTreePresenter(RecyclerView recyclerView, SwipeRefreshLayout refreshLayout, int expHandlerBr) {
        super(recyclerView, refreshLayout);
        this.expHandlerBr = expHandlerBr;
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter(int itemLayout, int dataBr, BaseHandler<Object> hanlder) {
        rootLayout = itemLayout;
        return new StepTreeAdapter(this, expHandlerBr, dataBr, hanlder, mRequestView);
    }

    @Override
    public void reload() {
        loadAdapter.clear();
        presenter.addRequest(()->mRequestView.requestPager(presenter, null),
                            v -> onDataComplete(v, null, null));
        lastParent = null;
    }

    protected void onDataComplete(List<Object> v, TreeNode parent, TreeViewNode treeViewNode) {

        if (parent == null){
            if (v != null){
                List<TreeNode> rootNodes = new LinkedList<>();
                for (Object item:v) {
                    rootNodes.add(new TreeNode(loadAdapter.getAll(), rootLayout, item));
                }
                loadAdapter.add(rootNodes);
            }
        }else{
            parent.setLoading(false);
            if (v != null){
                TreeNode.insertTo(loadAdapter.getAll(), parent, treeViewNode.getLayoutId(), v, treeViewNode.isChildren());
                loadAdapter.notifySet();
            }
        }
        mRefreshLayout.setRefreshing(false);
    }

    public void loadTree(TreeNode treeNode, TreeViewNode treeViewNode){
        presenter.addRequest(()->mRequestView.requestPager(presenter, treeNode),
                               v -> onDataComplete(v, treeNode, treeViewNode));
        lastParent = treeNode;
        lastViewNode = treeViewNode;
        lastParent.setLoading(true);
    }

    public int lastPosition(){

        int treeIndex;
        if (lastParent == null || (treeIndex = lastParent.treeIndex()) < 0){
            return Integer.MAX_VALUE;
        }

        return treeIndex + lastParent.childCount();
    }

    @Override
    public void loadMore() {

        if (lastParent == null){
            return;
        }

        PagerBean pager = lastParent.getPager();
        if (pager.getPageNum() >= pager.getPages()){
            return;
        }
        pager.addPage();
        lastParent.setLoading(true);
        presenter.addRequest(()->mRequestView.requestPager(presenter, lastParent),
                v -> onDataComplete(v, lastParent, lastViewNode));
    }
}