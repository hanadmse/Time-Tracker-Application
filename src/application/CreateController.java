package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreateController {
	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;

	// Method to switch to the Real Time Entry screen
	public void switchToRealTimeEntry(ActionEvent e) throws IOException {
		// Load the RealTimeEntry.fxml file
		Parent root = FXMLLoader.load(getClass().getResource("RealTimeEntry.fxml"));

		// Get the current stage (window) from the event source
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

		// Set the new scene to the stage and show it
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	// Method to switch back to the Main screen
	public void switchToMain(ActionEvent e) throws IOException {
		// Load the Main.fxml file
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

		// Get the current stage (window) from the event source
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

		// Set the new scene to the stage and show it
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	// Method to switch to the Past Event Entry screen
	public void switchToPastEventEntry(ActionEvent e) throws IOException {
		// Load the PastEventEntry.fxml file
		Parent root = FXMLLoader.load(getClass().getResource("PastEventEntry.fxml"));

		// Get the current stage (window) from the event source
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

		// Set the new scene to the stage and show it
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
