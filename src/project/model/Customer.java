package project.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Write a description of class Customer here.
 * 
 * @author AJ Priola
 * @version 1
 */

public class Customer implements Comparable<Customer> {
    
    private StringProperty firstName, lastName;
    private IntegerProperty accountNumber;
    private ObservableList<DVD> rented;

    public Customer(String firstName, String lastName, int accountNumber) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.accountNumber = new SimpleIntegerProperty(accountNumber);
        rented = FXCollections.observableArrayList();
    }
    
    public ObservableList<DVD> getRented() {
        return rented;
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
    
    public String getFirsName() {
        return firstName.getValue();
    }
    
    public String getLastName() {
        return lastName.getValue();
    }
    
    public String fullName() {
        return String.format("%s, %s", lastName, firstName);
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

