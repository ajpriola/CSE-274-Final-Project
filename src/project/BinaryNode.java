package project;

/**
 * Write a description of class BinaryNode here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BinaryNode<T extends Comparable<T>> {

    private T data;
    private BinaryNode<T> left;
    private BinaryNode<T> right;

    public BinaryNode(T data) {
        this.data = data;
        left = null;
        right = null;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setLeft(BinaryNode<T> BinaryNode) {
        left = BinaryNode;
    }

    public void setRight(BinaryNode<T> BinaryNode) {
        right = BinaryNode;
    }

    public BinaryNode<T> getLeft() {
        return left;
    }

    public BinaryNode<T> getRight() {
        return right;
    }
}
