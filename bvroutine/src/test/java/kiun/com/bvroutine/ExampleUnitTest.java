package kiun.com.bvroutine;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import kiun.com.bvroutine.data.viewmodel.TreeNode;

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

    @Test
    public void treeNodeTest(){

        List<TreeNode> rootNodes = new LinkedList<>();

        TreeNode root1 = new TreeNode(0, "Root1");
        TreeNode root2 = new TreeNode(0, "Root2");
        TreeNode root3 = new TreeNode(0, "Root3");
        rootNodes.add(root1);
        rootNodes.add(root2);
        rootNodes.add(root3);

        List datas = new LinkedList();
        datas.add("-Node1");
        datas.add("-Node2");
        datas.add("-Node3");

        List datas1 = new LinkedList();
        datas1.add("-DATA_1");
        datas1.add("-DATA_2");
        datas1.add("-DATA_3");

        TreeNode.insertTo(rootNodes, root1, 0, datas);
        TreeNode.insertTo(rootNodes, root2, 0, datas);
        TreeNode.insertTo(rootNodes, root3, 0, datas);

        for (TreeNode node : rootNodes){
            System.out.println(node.getExtra());
        }
        System.out.println();

        TreeNode.insertTo(rootNodes, root2, 0, datas1);
        TreeNode.insertTo(rootNodes, root2, 0, datas1);
        TreeNode.insertTo(rootNodes, root2, 0, datas1);

        for (TreeNode node : rootNodes){
            System.out.println(node.getExtra());
        }
        int a = 0;
    }
}