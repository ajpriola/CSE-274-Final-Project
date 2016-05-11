package project.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import project.Main;
import project.model.DVD;
import project.model.Store;

public class NewDVDViewController {

	@FXML TextField titleField, directorField, producerField, companyField, starsField, countField;
	
	public NewDVDViewController() {
		
	}
	
	@FXML private void initialize() {
		
	}
	
	@FXML private void cancelButtonClicked() {
		Main.sharedInstance().showHome();
	}
	
	@FXML private void doneButtonClicked() {
		String[] stars = starsField.getText().split(",");
		for (String s : stars) {
			s = s.trim();
		}
		
		String title = titleField.getText();
		String director = directorField.getText();
		String producer = producerField.getText();
		String company = companyField.getText();
		
		DVD dvd = new DVD(title, director, producer, company, stars);
		
		try {
			int c = Integer.parseInt(countField.getText());
			dvd.increment(c - 1);
		} catch (Exception e) {
			dvd.increment(1);
		}
		
		Store.sharedInstance().addDVD(dvd);
		
		Main.sharedInstance().showHome();
	}
}
