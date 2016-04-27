package project.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Write a description of class DVD here.
 * 
 * @author AJ Priola
 * @version 1
 */

public class DVD implements Comparable<DVD> {
    
    private StringProperty title, producer, director, company;
    private ObservableList<String> stars;
    private int availability;

    public DVD(String title, String producer, String director, String company, String stars[]) {
        this.title = new SimpleStringProperty(title);
        this.producer = new SimpleStringProperty(producer);
        this.director = new SimpleStringProperty(director);
        this.company = new SimpleStringProperty(company);
        this.stars = FXCollections.observableArrayList(stars);
        this.availability = 0;
    }
    
    public DVD(String title) {
    	this(title, "", "", "", new String[]{});
    }
    
    public int getAvailability() {
    	return availability;
    }
    
    public void increment(int i) {
    	availability += i;
    }
    
    public void increment() {
    	increment(1);
    }
    
    public boolean decrement() {
    	if (availability < 1) {
    		return false;
    	}
    	
    	availability -= 1;
    	return true;
    }
    
    public StringProperty getTitle() {
        return title;
    }
    
    public StringProperty getProducer() {
        return producer;
    }
    
    public StringProperty getCompany() {
        return company;
    }
    
    public StringProperty getDirector() {
        return director;
    }
    
    public ObservableList<String> getStars() {
        return stars;
    }
    
    @Override
    public int hashCode() {
        int hash = title.hashCode();
        
        return hash;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof DVD) {
            return compareTo((DVD)other) == 0;
        } else {
            return false;
        }
    }
    
    @Override
    public int compareTo(DVD other) {
        if (title.equals(other.title)) {
            if (director.equals(other.director)) {
                if (producer.equals(other.producer)) {
                    if (company.equals(other.company)) {
                        return 0;
                    } else {
                        return company.getValue().compareTo(other.company.getValue());
                    }
                } else {
                    return producer.getValue().compareTo(other.producer.getValue());
                }
            } else {
                return director.getValue().compareTo(other.director.getValue());
            }
        } else {
            return title.getValue().compareTo(other.title.getValue());
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s, directed by %s. Produced by %s, %s. Starring %s.", 
                                title, director, producer, company, stars);
    }
}
