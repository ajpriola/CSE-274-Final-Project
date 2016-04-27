package project;

import java.util.List;

/**
 * Write a description of class BinarySearchTree here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface BinarySearchTree<T extends Comparable<T>> {

    public boolean isEmpty();

    public int size();

    public void add (T element);

    public boolean contains (T element);
    
    public T get(T element);

    public String toString();

    public List<T> preorder();

    public List<T> inorder();

    public List<T> postorder();

    public void remove (T element);

    public void balance();

}
