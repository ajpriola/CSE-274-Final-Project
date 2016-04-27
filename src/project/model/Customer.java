package project.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.BinaryTree;

/**
 * Write a description of class Customer here.
 * 
 * @author AJ Priola
 * @version 1
 */

public class Customer implements Comparable<Customer> {
    
    private StringProperty firstName, lastName;
    private IntegerProperty accountNumber;
    private BinaryTree<DVD> rented;
    
    public Customer(int id) {
    	this("", "", id);
    }
    
    public Customer(String firstName, String lastName, int accountNumber) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.accountNumber = new SimpleIntegerProperty(accountNumber);
        rented = new BinaryTree<DVD>();
    }
    
    public ObservableList<DVD> getRented() {
        return FXCollections.observableArrayList(rented.inorder());
    }
    
    public void addRented(DVD dvd) {
        rented.add(dvd);
    }
    
    public void removeRented(DVD dvd) {
        rented.remove(dvd);
    }
    
    public int getAccoutNumber() {
        return accountNumber.getValue();
    }
    
    public boolean setName(String name) {
    	String[] arr = name.split("\\s+");
    	if (arr.length != 2) {
    		return false;
    	}
    	firstName = new SimpleStringProperty(arr[0]);
    	lastName = new SimpleStringProperty(arr[1]);
    	return true;
    }
    
    public String getFirstName() {
        return firstName.getValue();
    }
    
    public String getLastName() {
        return lastName.getValue();
    }
    
    public String fullName() {
        return String.format("%s, %s", lastName.getValue(), firstName.getValue());
    }
    
    @Override
    public int hashCode() {
        return accountNumber.getValue();
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Customer) {
            return ((Customer)other).accountNumber == accountNumber;
        } else {
            return false;
        }
    }
    
    @Override
    public int compareTo(Customer other) {
        return fullName().compareTo(other.fullName());
    }
    
    @Override
    public String toString() {
        return fullName();
    }
}

