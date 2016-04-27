package project;

import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class BinaryTree here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BinaryTree<T extends Comparable<T>> implements BinarySearchTree<T> {

    protected BinaryNode<T> root;

    public BinaryTree() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    private int size(BinaryNode<T> tree) {
        if (tree == null) {
            return 0;
        } else {
            return size(tree.getLeft()) + size(tree.getRight()) + 1;
        }
    }
    
    public T get(T element) {
    	if (root == null) {
            return null;
        } else {

            BinaryNode<T> current = root;

            while (true) {
                if (current.getData().compareTo(element) == 0) {
                    return current.getData();
                } else if (current.getData().compareTo(element) <= 0) {
                    if (current.getRight() != null) {
                        current = current.getRight();
                    } else {
                        return null;
                    }
                } else {
                    if (current.getLeft() != null) {
                        current = current.getLeft();
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    public void add(T element) {
        BinaryNode<T> node = new BinaryNode<T>(element);

        if (root == null) {
            root = node;
        } else {

            BinaryNode<T> current = root;

            while (true) {
                if (current.getData().compareTo(element) == 0) {
                    break;
                } else if (current.getData().compareTo(element) <= 0) {
                    if (current.getRight() != null) {
                        current = current.getRight();
                    } else {
                        current.setRight(node);
                        break;
                    }
                } else {
                    if (current.getLeft() != null) {
                        current = current.getLeft();
                    } else {
                        current.setLeft(node);
                        break;
                    }
                }
            }
        }
    }

    public boolean contains(T element) {
        if (root == null) {
            return false;
        } else {

            BinaryNode<T> current = root;

            while (true) {
                if (current.getData().compareTo(element) == 0) {
                    return true;
                } else if (current.getData().compareTo(element) <= 0) {
                    if (current.getRight() != null) {
                        current = current.getRight();
                    } else {
                        return false;
                    }
                } else {
                    if (current.getLeft() != null) {
                        current = current.getLeft();
                    } else {
                        return false;
                    }
                }
            }
        }
    }

    public List<T> preorder() {
        List<T> list = new ArrayList<T>();
        preorder(root, list);
        return list;
    }

	private void preorder(BinaryNode<T> tree, List<T> list) {
        if (tree != null) {
            list.add(tree.getData());
            preorder(tree.getLeft(), list);
            preorder(tree.getRight(), list);
        }
    }

    public List<T> inorder() {
        List<T> list = new ArrayList<T>();
        inorder(root, list);
        return list;
    }

    private void inorder(BinaryNode<T> tree, List<T> list) {
        if (tree != null) {
            inorder(tree.getLeft(), list);
            list.add(tree.getData());
            inorder(tree.getRight(), list);
        }
    }

    public List<T> postorder() {
        List<T> list = new ArrayList<T>();
        postorder(root, list);
        return list;
    }

    private void postorder(BinaryNode<T> tree, List<T> list) {
        if (tree != null) {
            postorder(tree.getLeft(), list);
            postorder(tree.getRight(), list);
            list.add(tree.getData());
        }
    }

    public void remove(T element) {
        if (root != null) {

            BinaryNode<T> previous = root, current = root;

            while (true) {
                if (current.getData().compareTo(element) == 0) {
                    replace(current, previous);
                    break;
                } else if (current.getData().compareTo(element) <= 0) {
                    if (current.getRight() != null) {
                        previous = current;
                        current = current.getRight();
                    } else {
                        break;
                    }
                } else {
                    if (current.getLeft() != null) {
                        previous = current;
                        current = current.getLeft();
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public void replace(BinaryNode<T> replaced, BinaryNode<T> previous) {
        if (previous != replaced) {

            BinaryNode<T> current = replaced;

            if (current.getLeft() == null && current.getRight() == null) {
                if (previous.getLeft() == replaced) {
                    previous.setLeft(null);
                } else {
                    previous.setRight(null);
                }
            } else if (current.getLeft() == null && current.getRight() != null) {
                if (previous.getLeft() == replaced) {
                    previous.setLeft(current.getRight());
                } else {
                    previous.setRight(current.getRight());
                }
            } else if (current.getLeft() != null && current.getRight() == null) {
                if (previous.getRight() == replaced) {
                    previous.setLeft(current.getLeft());
                } else {
                    previous.setRight(current.getLeft());
                }
            } else {
                BinaryNode<T> temp = replaced;
                current = current.getLeft();
                
                while (current.getRight() != null) {
                    temp = current;
                    current = current.getRight();
                }
                
                temp.setRight(null);
                
                if (previous.getLeft() == replaced) {
                    previous.setLeft(current);
                } else {
                    previous.setRight(current);
                }
            }
        }
    }
    
    public void balance() {
        List<T> list = inorder();
        root = null;
        refill(0, list.size() - 1, list);
    }
    
    public void refill(int low, int high, List<T> list) {
        if (low == high) {
            add(list.get(low));
        } else if (low + 1 == high) {
            add(list.get(low));
            add(list.get(high));
        } else {
            int mid = (low + high) / 2;
            add(list.get(mid));
            refill(low, mid - 1, list);
            refill(mid + 1, high, list);
        }
    }

    public String toString() {
        return toString(root, 0);
    }

    private String toString(BinaryNode<T> tree, int level) {
        String str = "";
        if (tree != null) {
            str += toString(tree.getRight(), level + 1);
            for (int i = 1; i <= level; ++i) {
                str = str + "| ";
            }
            str += tree.getData().toString() + "\n";
            str += toString(tree.getLeft(), level + 1);
        }
        return str;
    }
}


