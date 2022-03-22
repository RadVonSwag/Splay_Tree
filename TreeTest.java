import java.util.List;

public class TreeTest {
    public static void main(String[] args) {
        SplayTree testTree = new SplayTree();

        //Opted to keep the print functions to be called separately from the other functions

        testTree.insert("taco");
        testTree.printPreOrder();
        testTree.insert("burger");
        testTree.printPreOrder();
        testTree.insert("spaghetti");
        testTree.printPreOrder(); 
        testTree.insert("lasagna");
        testTree.printPreOrder();
        testTree.insert("hot dog");
        testTree.printPreOrder();
        testTree.insert("macaroni");
        testTree.printPreOrder();
        testTree.insert("chicken nugget");
        testTree.printPreOrder();
        testTree.search("apple pie");
        testTree.printPreOrder();
        testTree.delete("hot dog");
        testTree.printPreOrder();
        testTree.insert("quesadilla");
        testTree.printPreOrder();
        testTree.insert("dumplings");
        testTree.delete("quesadilla");
        testTree.printPreOrder();
        testTree.delete("taco");
        testTree.printPreOrder();
        testTree.search("dumplings");
        testTree.printPreOrder();
        testTree.search("taco");

        //System.out.println(testTree.toString(testTree.search(15)));
    }
}
