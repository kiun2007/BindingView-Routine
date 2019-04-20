package kiun.com.bvroutine.interfaces.view;

import kiun.com.bvroutine.data.viewmodel.TreeNode;
import kiun.com.bvroutine.data.viewmodel.TreeViewNode;

public interface TreeStepView extends ListRequestView<TreeNode>{

    /**
     * 子节点是否还拥有扩展功能.
     * @param extra 节点拓展数据.
     * @return 是否有扩展.
     */
    TreeViewNode children(Object extra);
}