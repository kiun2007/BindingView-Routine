package kiun.com.bvroutine.data.viewmodel;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.data.PagerBean;

public class TreeNode {

    //父节点.
    private TreeNode parent = null;

    //包含多少个子节点.
    private int childCount = 0;

    //使用视图Layout.
    private int viewLayoutId;

    //展开或者收缩.
    private boolean expansion = false;

    //附带数据.
    private Object extra;

    //分页信息.
    private PagerBean pager;

    /**
     * 初始化一个节点,包含节点的视图和数据.
     * @param viewLayoutId 视图Layout.
     * @param extra 附带数据.
     */
    public TreeNode(int viewLayoutId, Object extra){
        this.viewLayoutId = viewLayoutId;
        this.extra = extra;
    }

    /**
     * 初始化一个节点被包含在某个父节点,包含节点的视图和数据.
     * @param parent 父节点对象.
     * @param viewLayoutId 视图Layout.
     * @param extra 附带数据.
     */
    public TreeNode(TreeNode parent, int viewLayoutId, Object extra){
        this(viewLayoutId, extra);
        this.parent = parent;
    }

    /**
     * 展开数据.
     * @param expansion true展开, false 收起.
     */
    public void expansion(boolean expansion) {
        this.expansion = expansion;
    }

    public boolean isExpansion() {
        return expansion;
    }

    public TreeNode getParent() {
        return parent;
    }

    public boolean isParentExpansion(){
        return parent == null ? true : parent.isExpansion();
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public int childCount() {
        return childCount;
    }

    public int getLayout() {
        return viewLayoutId;
    }

    public Object getExtra() {
        return extra;
    }

    public PagerBean getPager() {
        if (pager == null){
            pager = new PagerBean(extra);
        }
        return pager;
    }

    public boolean withPager(){
        return pager != null;
    }

    /**
     * 根据数据向节点增加子节点.
     * @param datas 子节点的数据.
     * @param viewLayoutId 子节点使用什么视图.
     * @return 返回的子节点对象.
     */
    public List<TreeNode> addNode(List datas, int viewLayoutId){

        List<TreeNode> list = new LinkedList<>();
        for (Object item : datas){
            list.add(new TreeNode(this, viewLayoutId, item));
            childCount ++;
        }
        return list;
    }

    public static void insertTo(List<TreeNode> root, TreeNode parent, int viewLayoutId, List datas){

        int insertIndex = root.indexOf(parent);
        root.addAll(parent.childCount() + insertIndex + 1, parent.addNode(datas, viewLayoutId));
    }
}