package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;

	// Method to switch to the Create Time Entry screen
	public void switchToCreate(ActionEvent e) throws IOException {
		// Load the CreateTimeEntry.fxml file
		Parent root = FXMLLoader.load(getClass().getResource("CreateTimeEntry.fxml"));

		// Get the current stage (window) from the event source
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

		// Set the new scene to the stage and show it
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	// Method to switch to the View Submits screen
	public void switchToView(ActionEvent e) throws IOException {
		// Load the ViewSubmits.fxml file
		Parent root = FXMLLoader.load(getClass().getResource("ViewSubmits.fxml"));

		// Get the current stage (window) from the event source
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

		// Set the new scene to the stage and show it
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
