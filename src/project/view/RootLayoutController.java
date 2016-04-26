package project.view;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

public class RootLayoutController {

	@FXML private MenuBar menu;
	
	@FXML private void initialize() {
		
	}
	
	public void toggleMenu(boolean on) {
		menu.setVisible(on);
	}
	
	@FXML private void logoutButtonClicked() {
		
	}
}
