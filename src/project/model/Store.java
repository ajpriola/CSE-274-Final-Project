package project.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.BinaryTree;

public class Store {
    
    private HashMap<DVD, Integer> dvdCount;
    private ObservableList<DVD> dvdList;
    private BinaryTree<DVD> dvds;
    private BinaryTree<Customer> customers;
    private HashMap<Integer, ArrayList<DVD>> rented;
    
    private static Store sharedInstance;
    
    private Store() {
        dvdCount = new HashMap<DVD, Integer>();
        dvdList = FXCollections.observableArrayList();
        dvds = new BinaryTree<DVD>();
        customers = new BinaryTree<Customer>();
        rented = new HashMap<Integer, ArrayList<DVD>>();
    }
    
    public static void initialize() {
    	Store store = new Store();

        DVD aNewHope = new DVD("Star Wars: IV", "George Lucas", 
                            "George Lucas", "Lucasfilm",
                            new String[]{"Mark Hamill","Harrison Ford","Carrie Fisher"});
        DVD empireStrikesBack = new DVD("Star Wars: V", "George Lucas", 
                                        "George Lucas", "Lucasfilm", 
                                        new String[]{"Mark Hamill","Harrison Ford","Carrie Fisher"});
        
        aNewHope.increment();
        empireStrikesBack.increment(3);
        
        store.dvds.add(aNewHope);
        store.dvds.add(empireStrikesBack);
        store.dvds.add(new DVD("Star Wars: I"));
        store.dvds.add(new DVD("Star Wars: II"));
        store.dvds.add(new DVD("Star Wars: III"));
        store.dvds.add(new DVD("Star Wars: VI"));
        store.dvds.add(new DVD("Star Wars: VII"));
        store.dvds.add(new DVD("Batman Begins"));
        store.dvds.add(new DVD("The Dark Knight"));
        store.dvds.add(new DVD("The Dark Knight Rises"));
        store.dvds.add(new DVD("Indiana Jones and the Raiders of the Lost Ark"));
        
        store.addCustomer("AJ Priola");
        
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
    	/*
    	if (dvd.decrement()) {
    		for (Customer customer : customers.preorder()) {
        		if (customer.getAccoutNumber() == id) {
        			customer.addRented(dvd);
        		}
        	}
    	}*/
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
    
    public int getAvailability(DVD dvd) {
        if (dvdList.contains(dvd)) {
            return dvdCount.get(dvd);
        }
        return 0;
    }
    
    public void addDVD(DVD dvd) {
        if (!dvdList.contains(dvd)) {
            dvdList.add(dvd);
            dvdCount.put(dvd, 1);
        } else {
            dvdCount.put(dvd, dvdCount.get(dvd) + 1);
        }
    }
    
    public void setDVDCount(DVD dvd, int count) {
        for (int i = 0; i < count; i++) {
            addDVD(dvd);
        }
    }
    
    /*
    public static void main(String[] args) {
        Store store = new Store();

        DVD aNewHope = new DVD("Star Wars: IV", "George Lucas", 
                            "George Lucas", "Lucasfilm",
                            new String[]{"Mark Hamill","Harrison Ford","Carrie Fisher"});
        DVD empireStrikesBack = new DVD("Star Wars: V", "George Lucas", 
                                        "George Lucas", "Lucasfilm", 
                                        new String[]{"Mark Hamill","Harrison Ford","Carrie Fisher"});
        store.setDVDCount(aNewHope, 5);
        store.setDVDCount(empireStrikesBack, 4);
    }*/
}