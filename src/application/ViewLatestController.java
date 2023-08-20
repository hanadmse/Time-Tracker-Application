package application;

import java.io.IOException;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ViewLatestController {
	@FXML
	private Stage stage;
	@FXML
	private Scene scene;
	@FXML
	private Parent root;

	@FXML
	private TableView<Task> tableView;

	@FXML
	private TableColumn<Task, Integer> taskIdColumn;

	@FXML
	private TableColumn<Task, String> taskNameColumn;

	@FXML
	private TableColumn<Task, String> dateColumn;

	@FXML
	private TableColumn<Task, String> durationColumn;

	// Switch to the main view
	public void switchToMain(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	// Switch to the submits view
	public void switchToSubmits(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ViewSubmits.fxml"));
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	// Initialize method for the controller
	public void initialize() {
		taskIdColumn.setCellValueFactory(cellData -> cellData.getValue().taskIdProperty().asObject());
		taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		durationColumn.setCellValueFactory(cellData -> cellData.getValue().durationProperty());

		ObservableList<Task> data = FXCollections.observableArrayList();
		Database.loadTasksFromDatabase(data);

		tableView.setItems(data);
	}
}
