import java.util.LinkedList;
import java.util.List;

public class SplayTree<E extends Comparable<E>> {
    private Node<E> root;
/**
 * Constructor, creates an empty Splay tree
 */
    public SplayTree() {
        root = null;
    }

    private void splay(Node<E> node) {
        while (node != root) {
            Node<E> parent = node.parent;
            // If Parent Node is root node. The while loop should always circle back around
            // to this case.
            if (node.parent == root) {
                if (node == node.parent.left) {
                    // is left child, zig
                    rightRotate(node.parent);
                } else if (node == node.parent.right) {
                    // is right child, zag
                    leftRotate(node.parent);
                }
                break;
            }
            Node<E> grandParent = node.parent.parent;
            // If node is left child and parent is left child of grandparent, zig-zig
            // rotation
            if (node == parent.left && parent == grandParent.left) {
                rightRotate(grandParent);
                rightRotate(parent);
                // If node is right child and parent is right child of grandparent, zag-zag
                // rotation
            } else if (node == parent.right && parent == grandParent.right) {
                leftRotate(grandParent);
                leftRotate(parent);
                // Node is right child, parent is left child of grandparent, zig-zag rotation
            } else if (node == parent.right && parent == grandParent.left) {
                leftRotate(parent);
                rightRotate(grandParent);
                // Node is left child, parent is right child of grandparent
            } else if (node == parent.left && parent == grandParent.right) {
                rightRotate(parent);
                leftRotate(grandParent);
            }
        }
    }
/**
 * Performs right rotation of target node in the tree
 * @param node
 * @return
 */
    private Node<E> rightRotate(Node<E> node) {
        Node<E> tempNode = node.left;
        tempNode.parent = node.parent;
        node.left = tempNode.right;

        if (node.left != null) {
            node.left.parent = node;
        }

        tempNode.right = node;
        node.parent = tempNode;

        if (tempNode.parent != null) {
            if (node == tempNode.parent.left) {
                tempNode.parent.left = tempNode;
            } else {
                tempNode.parent.right = tempNode;
            }
        } else {
            root = tempNode;
        }

        return tempNode;
    }
/**
 * Performs left rotation of target node in the tree
 * @param node
 * @return
 */
    private Node<E> leftRotate(Node<E> node) {
        Node<E> tempNode = node.right;
        tempNode.parent = node.parent;

        node.right = tempNode.left;
        if (node.right != null) {
            node.right.parent = node;
        }

        tempNode.left = node;
        node.parent = tempNode;

        if (tempNode.parent != null) {
            if (node == tempNode.parent.left) {
                tempNode.parent.left = tempNode;
            } else {
                tempNode.parent.right = tempNode;
            }
        } else {
            root = tempNode;
        }

        return tempNode;
    }

    /**
     * Creates a new node and inserts it with the specified data into the Splay Tree
     * 
     * @param data
     */
    public void insert(E data) {
        if (root == null) {
            root = new Node<E>(null, data, null);
            return;
        } else if (data == root.data) {
            System.out.println("Error inserting: " + data + ". This data already exists!");
            return;
        }
        Node<E> currentNode = root;
        Node<E> newNode = Insertion(currentNode, data);
        splay(newNode);
    }

    /**
     * Insertion function uses the search function and rescursively finds the location to insert the new node
     * @param currentNode
     * @param data
     * @return
     */
    private Node<E> Insertion(Node<E> currentNode, E data) {
        if (data.compareTo(currentNode.data) < 0) {
            if (currentNode.left == null) {
                currentNode.left = new Node<E>(null, data, null);
            }
            Node<E> fosterNode = currentNode; //temporary parent
            currentNode = currentNode.left;
            currentNode.parent = fosterNode;
            Insertion(currentNode, data);
            while (currentNode.data != data) {
                currentNode = search(data);
            }
        } else if (data.compareTo(currentNode.data) > 0) {
            if (currentNode.right == null) {
                currentNode.right = new Node<E>(null, data, null);
            }
            Node<E> fosterNode = currentNode; //temporary parent
            currentNode = currentNode.right;
            currentNode.parent = fosterNode;
            Insertion(currentNode, data);
            while (currentNode.data != data) {
                currentNode = search(data);
            }
        }
        return currentNode;
    }
/**
 * Removes an elements from the tree
 * @param data
 */
    public void delete(E data) {
        root = deleteFunction(this.root, data);
    }
/**
 * Function for delete. Uses searchFunction to find the node, then re-adjusts parent and child nodes accordingly 
 * @param rootNode
 * @param data
 * @return
 */
    private Node<E> deleteFunction(Node<E> rootNode, E data) {
        Node<E> nodeToDelete = new Node<E>(null, data, null);
        splay(searchFunction(nodeToDelete, rootNode));
        if (root.right == null && root.left == null) {
            root = null;
        } else if (root.right == null) {
            root.left.parent = null;
            root = root.left;
        } else if (root.left == null) {
            root.right.parent = null;
            root = root.right;
        } else {
            Node<E> rightTree = root.right; //right tree to be stiched back to the left tree
            Node<E> newRoot = root.left;
            while (newRoot.right != null) {
                newRoot = newRoot.right;
            }
            splay(newRoot);
            root.right = rightTree;
        }
        return root;
    }

    /**
     * Takes a piece of data and searches the tree for a node with matching data
     * 
     * @param data
     * @return
     */
    public Node<E> search(E data) {
        Node<E> desiredNode = new Node<E>(null, data, null);
        Node<E> foundNode = searchFunction(desiredNode, this.root);
        if (foundNode == null) {
            System.out.println("Error. Could not find " + data + " in the tree.");
            return foundNode;
        }
        splay(foundNode);
        return foundNode;
    }

    /**
     * searchFunction for the search method searches the tree recursively from the
     * root node
     * 
     * @param desiredNode
     * @param currentNode
     * @return
     */
    private Node<E> searchFunction(Node<E> desiredNode, Node<E> currentNode) {
        Node<E> foundNode = null;
        if (currentNode == null) {
            return currentNode;
        } else if (currentNode.data == desiredNode.data) {
            foundNode = currentNode;
            currentNode = root;
            return foundNode;
        }
        if (desiredNode.data.compareTo(currentNode.data) < 0) {
            currentNode = currentNode.left;
            foundNode = searchFunction(desiredNode, currentNode);
            return foundNode;

        } else if (desiredNode.data.compareTo(currentNode.data) > 0) {
            currentNode = currentNode.right;
            foundNode = searchFunction(desiredNode, currentNode);
            return foundNode;
        } else if (desiredNode.data.compareTo(currentNode.data) == 0) {
            foundNode = currentNode;
            return foundNode;
        }
        currentNode = root;
        return foundNode;

    }

    /**
     * Iterates through the tree and stores each element in a LinkedList
     * 
     * @return
     */
    private List preOrder() {
        List preOrder = new LinkedList(); // initialize the list
        preOrder = preOrderFunction(root, preOrder); // call the preOrderFunction to sort through the tree
        return preOrder;
    }

    /**
     * Function for the preOrder method. Sorts through the tree using the preorder
     * method and stores each element in a list with a letter denoting root, left,
     * or right child.
     * 
     * @param currentNode
     * @param list
     * @return
     */
    private List preOrderFunction(Node<E> currentNode, List list) {
        if (currentNode != null) {
            if (currentNode == root) {
                list.add(currentNode.data.toString() + "RT"); // the currentNode is the root node, add to list with "RT"
            } else if (currentNode == currentNode.parent.left) {
                list.add(currentNode.data.toString() + "L"); // the currentNode is a left childm, add to list with "L"
            } else if (currentNode == currentNode.parent.right) {
                list.add(currentNode.data.toString() + "R"); // the currentNode is a right child add to list with "R"
            }
            preOrderFunction(currentNode.left, list); // recurse through the left side of the tree
            preOrderFunction(currentNode.right, list); // recurse through the right side of the tree
        }
        return list;
    }

    /**
     * Prints the elements in the tree in using the PreOrder method.
     */
    public void printPreOrder() {
        List list = preOrder(); // set list to the result of the preOrder function
        System.out.println(list.toString()); // print string of result
    }

    /**
     * toString is for testing purposes
     * 
     * @param node
     * @return
     */
    public String toString(Node<E> node) {
        String string = node.data.toString();
        return string;
    }

    private class Node<T> {
        /**
         * The element stored in the node
         */
        E data;
        /**
         * points to the right child node
         */
        Node<E> right;
        /**
         * points to the left child node
         */
        Node<E> left;
        /**
         * points to parent node
         */
        Node<E> parent;

        /**
         * Creates Node object to be used and manipulated in InventoryLinkedList
         * 
         * @param left
         * @param data
         * @param right
         */
        private Node(Node<E> left, E data, Node<E> right) {
            this.data = data;
            this.right = right;
            this.left = left;
            this.parent = null;
        }
    }

}