package project;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import project.model.Store;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private static Main sharedInstance;
    
    @Override
    public void start(Stage primaryStage) {
    	sharedInstance = this;
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Final Project");

        initRootLayout();
        
        Store.initialize();

        showHome();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane)loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Accesses the static shared instance of Main
     * 
     * @return Main shared instance
     */
    public static Main sharedInstance() {
    	return sharedInstance;
    }
    
    /**
     * Shows the home screen. If no user is logged in, will show the login screen.
     */
    public void showHome() {
    	try {
    		FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(Main.class.getResource("view/HomeView.fxml"));
        	BorderPane homeView = (BorderPane)loader.load();
			
			rootLayout.setCenter(homeView);
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
    
    /**
     * Shows the window for adding a new DVD.
     */
    public void showAddWindow() {
    	try {
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(Main.class.getResource("view/NewDVDView.fxml"));
    		AnchorPane addView = (AnchorPane)loader.load();
    		
    		rootLayout.setCenter(addView);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public void showErrorMessage(String title, String error, String message) {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle(title);
    	alert.setHeaderText(error);
    	alert.setContentText(message);
    	
    	alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
