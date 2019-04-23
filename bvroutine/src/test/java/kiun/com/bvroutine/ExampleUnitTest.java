package kiun.com.bvroutine;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.data.viewmodel.TreeNode;
import kiun.com.bvroutine.utils.ObjectUtil;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    //TreeNode 数据模型测试.
    @Test
    public void treeNodeTest(){

        List<TreeNode> rootNodes = new LinkedList<>();

        TreeNode root1 = new TreeNode(rootNodes, 0, "Root1");
        TreeNode root2 = new TreeNode(rootNodes, 0, "Root2");
        TreeNode root3 = new TreeNode(rootNodes, 0, "Root3");

        rootNodes.add(root1);
//        rootNodes.add(root2);
//        rootNodes.add(root3);

        List datas = new LinkedList();
        datas.add("-Node1");
        datas.add("-Node2");
        datas.add("-Node3");

        List datas1 = new LinkedList();
        datas1.add("-DATA_1");
        datas1.add("-DATA_2");
        datas1.add("-DATA_3");

        List datas3 = new LinkedList();
        datas3.add("--VALUE_1");
        datas3.add("--VALUE_2");
        datas3.add("--VALUE_3");

        TreeNode.insertTo(rootNodes, root1, 0, datas, true);
//        TreeNode.insertTo(rootNodes, root2, 0, datas, true);
//        TreeNode.insertTo(rootNodes, root3, 0, datas, true);

        List<TreeNode> children = root1.findChildren();
        int a = 0;
        TreeNode node1 = children.get(0), node2 = children.get(1), node3 = children.get(2);

        TreeNode.insertTo(rootNodes, node1, 0 , datas3,true);
        TreeNode.insertTo(rootNodes, node2, 0 , datas3,true);
        TreeNode.insertTo(rootNodes, node3, 0 , datas3,true);

//        TreeNode.insertTo(rootNodes, root2, 0, datas1, true);
//        TreeNode.insertTo(rootNodes, root2, 0, datas1, true);
//        TreeNode.insertTo(rootNodes, root2, 0, datas1, true);

//        for (TreeNode node : rootNodes){
//            System.out.println(node.getExtra() + ":" + node.childCount());
//        }

        children = root1.findChildren();


//        printf("start",rootNodes);
        ObjectUtil.batchCall(item -> item.setCheck(), children.get(1), children.get(2), children.get(3),
                                                          children.get(5), children.get(6), children.get(7),
                                                          children.get(9), children.get(10), children.get(11));
        printf("batchCall",rootNodes);
        node1.setCheck();
        printf("node1.setCheck()",rootNodes);
        node1.setCheck();
        printf("node1.setCheck();",rootNodes);

//        children.get(1).setCheck();
//        children.get(2).setCheck();
//        children.get(3).setCheck();
//
//        children.get(5).setCheck();
//        children.get(6).setCheck();
//        children.get(7).setCheck();
//
//        children.get(9).setCheck();
//        children.get(10).setCheck();
//        children.get(11).setCheck();
//        printf("get(1)",rootNodes);

        children.get(1).setCheck();
        children.get(2).setCheck();
        children.get(3).setCheck();
        printf("get(1)",rootNodes);
        a = 0;
    }

    public void printf(String line, List<TreeNode> treeNodes){
        System.out.println();
        System.out.println(line);
        for (TreeNode node : treeNodes){
            System.out.println(node.getExtra() + ":" + node.childCount() + "," + node.checkedCount());
        }
    }
}