package project.view;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import project.Main;
import project.model.Customer;
import project.model.DVD;
import project.model.Store;

public class HomeViewController {

	@FXML Button dvdCheckoutButton, dvdInfoButton, returnButton;
	@FXML Label availabilityLabel, nameLabel, idLabel;
	@FXML TabPane tabPane;
	
	@SuppressWarnings("rawtypes")
	@FXML ListView dvdListView, customerListView, checkedOutListView;
	
	public HomeViewController() {
		
	}
	
	@SuppressWarnings("unchecked")
	@FXML private void initialize() {
		
		dvdListView.setItems(Store.sharedInstance().getDVDList());
		
		dvdListView.setCellFactory(new Callback<ListView<DVD>, ListCell<DVD>>() {
			@Override
			public ListCell<DVD> call(ListView<DVD> param) {
				ListCell<DVD> cell = new ListCell<DVD>(){
                    @Override
                    protected void updateItem(DVD item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getTitle().getValue());
                        }
                    }
                };
                return cell;
			}
		});
		
		dvdListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue == null || ((DVD)newValue).getAvailability() == 0) {
				dvdCheckoutButton.setDisable(true);
			} else {
				dvdCheckoutButton.setDisable(false);
			}
			dvdInfoButton.setDisable(newValue == null);
			availabilityLabel.setText(String.format("Available: %d", newValue == null ? 0 : ((DVD)newValue).getAvailability()));
		});
		
		customerListView.setItems(Store.sharedInstance().getCustomers());
		
		customerListView.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>() {
			@Override
			public ListCell<Customer> call(ListView<Customer> param) {
				ListCell<Customer> cell = new ListCell<Customer>(){
                    @Override
                    protected void updateItem(Customer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.fullName());
                        }
                    }
                };
                return cell;
			}
		});
		
		customerListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue == null) {
				nameLabel.setText("");
				idLabel.setText("");
			} else {
				nameLabel.setText(String.format("%s %s", ((Customer)newValue).getFirstName(), ((Customer)newValue).getLastName()));
				idLabel.setText(String.format("Account: %d", ((Customer)newValue).getAccoutNumber()));
			}
			updateCheckedOutList();
		});
		
		tabPane.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			dvdListView.getSelectionModel().clearSelection();
			customerListView.getSelectionModel().clearSelection();
		});
		
		checkedOutListView.setPlaceholder(new Label("No items checked out"));
		
		checkedOutListView.setCellFactory(new Callback<ListView<DVD>, ListCell<DVD>>() {
			@Override
			public ListCell<DVD> call(ListView<DVD> param) {
				ListCell<DVD> cell = new ListCell<DVD>(){
                    @Override
                    protected void updateItem(DVD item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getTitle().getValue());
                        }
                    }
                };
                return cell;
			}
		});
		
		checkedOutListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			returnButton.setDisable(newValue == null);
		});
	}
	
	@FXML private void newDVDClicked() {
		Main.sharedInstance().showAddWindow();
		//Store.sharedInstance().addDVD(dvd);
	}
	
	@SuppressWarnings("unchecked")
	private void updateCheckedOutList() {
		Customer selected = (Customer)customerListView.getSelectionModel().getSelectedItem();
		if (selected == null) {
			checkedOutListView.setItems(null);
		} else {
			ObservableList<DVD> items = Store.sharedInstance().getRented(selected.getAccoutNumber());
			if (items != null && items.size() != 0) {
				checkedOutListView.setItems(items);
			} else {
				checkedOutListView.setItems(null);
				checkedOutListView.refresh();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void showEditDialog(Customer customer) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Customer");
		dialog.setHeaderText(String.format("%s customer", customer == null ? "New" : "Edit"));
		dialog.setContentText("Customer name:");

		dialog.getEditor().setPromptText("Ex: Han Solo");
		
		if (customer != null) {
			dialog.getEditor().setText(String.format("%s %s", customer.getFirstName(), customer.getLastName()));
		}
		
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(name -> {
			if (customer != null) {
				if (!customer.setName(name)) {
					Main.sharedInstance().showErrorMessage("Error", null, "You must enter a first and last name!");
				}
			} else {
				String[] arr = name.split("\\s+");
				if (arr.length != 2) {
					Main.sharedInstance().showErrorMessage("Error", null, "You must enter a first and last name!");
				} else {
					Store.sharedInstance().addCustomer(name);
				}
			}
			customerListView.setItems(Store.sharedInstance().getCustomers());
		});
	}
	
	@FXML private void newButtonClicked() {
		showEditDialog(null);
	}
	
	@FXML private void infoButtonClicked() {
		DVD selected = (DVD)dvdListView.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("DVD");
	    	alert.setHeaderText(selected.getTitle().getValue());
	    	alert.setContentText(String.format("Director: %s\n"
	    									 + "Producer: %s\n"
	    									 + "%s\n"
	    									 + "Starring %s", 
	    									 selected.getDirector().getValue(), 
	    									 selected.getProducer().getValue(),
	    									 selected.getCompany().getValue(),
	    									 selected.getStarString()));
	    	
	    	alert.showAndWait();
		}
	}
	
	@FXML private void returnButtonClicked() {
		DVD dvd = (DVD)checkedOutListView.getSelectionModel().getSelectedItem();
		Customer customer = (Customer)customerListView.getSelectionModel().getSelectedItem();
		if (dvd != null && customer != null) {
			Store.sharedInstance().returnDVD(dvd, customer);
			updateCheckedOutList();
		}
	}
	
	@FXML private void checkoutButtonClicked() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Check Out");
		dialog.setHeaderText("Customer");
		dialog.setContentText("Customer ID:");
		Optional<String> result = dialog.showAndWait();
		
		DVD selected = (DVD)dvdListView.getSelectionModel().getSelectedItem();
		
		result.ifPresent(id -> {
			if (selected != null) {
				try {
					int idInt = Integer.parseInt(id);
					Store.sharedInstance().rentDVD(selected, idInt);
					availabilityLabel.setText(String.format("Available: %d", selected.getAvailability()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
