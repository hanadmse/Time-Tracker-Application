package application;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Load the main FXML file that defines the UI layout
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

		// Create a new scene with a white background
		Scene scene = new Scene(root, Color.WHITE);

		// Load and set the application icon
		Image appIcon = new Image("time tracker app logo.png");
		stage.getIcons().add(appIcon);

		// Set the title and dimensions of the application window
		stage.setTitle("Time Tracker App");
		stage.setWidth(650);
		stage.setHeight(650);

		// Set the initial position of the window on the screen
		stage.setX(0);
		stage.setY(0);

		// Set the scene to the stage (window)
		stage.setScene(scene);

		// Disable window resizing
		stage.setResizable(false);

		// Show the application window
		stage.show();

	}
}
