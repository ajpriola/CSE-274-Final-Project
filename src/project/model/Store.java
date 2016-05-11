package project.model;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.BinaryTree;

public class Store {
    private BinaryTree<DVD> dvds;
    private BinaryTree<Customer> customers;
    private HashMap<Integer, ArrayList<DVD>> rented;
    
    private static Store sharedInstance;
    
    private Store() {
        dvds = new BinaryTree<DVD>();
        customers = new BinaryTree<Customer>();
        rented = new HashMap<Integer, ArrayList<DVD>>();
    }
    
    public static void initialize() {
    	Store store = new Store();
        
        sharedInstance = store;
    }
    
    public static Store sharedInstance() {
    	return sharedInstance;
    }
    
    public ObservableList<DVD> getRented(int id) {
    	return FXCollections.observableArrayList(rented.get(id));
    }
    
    public void rentDVD(DVD dvd, int id) {
    	if (dvd.decrement()) {
    		if (rented.containsKey(id)) {
    			rented.get(id).add(dvd);
    		} else {
    			ArrayList<DVD> list = new ArrayList<DVD>();
    			list.add(dvd);
    			rented.put(id, list);
    		}
    		
    		
    	}
    }
    
    public void returnDVD(DVD dvd, Customer customer) {
    	dvd.increment();
    	rented.get(customer.getAccoutNumber()).remove(dvd);
    }
    
    public ObservableList<Customer> getCustomers() {
    	return FXCollections.observableArrayList(customers.inorder());
    }
    
    public void addCustomer(String name) {
    	String[] arr = name.split("\\s+");
    	int size = customers.size();
    	Customer customer = new Customer(arr[0], arr[1], size + 1);
    	customers.add(customer);
    }
    
    public ObservableList<DVD> getDVDList() {
        return FXCollections.observableArrayList(dvds.inorder());
    }
    
    public void addDVD(DVD dvd) {
    	if (dvds.contains(dvd)) {
    		dvds.get(dvd).increment();
    	} else {
    		dvds.add(dvd);
    	}
    }
    
    public void setDVDCount(DVD dvd, int count) {
        for (int i = 0; i < count; i++) {
            addDVD(dvd);
        }
    }
}