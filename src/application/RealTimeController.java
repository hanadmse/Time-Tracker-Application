package application;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RealTimeController {
	@FXML
	private Stage stage;
	@FXML
	private Scene scene;
	@FXML
	private Parent root;
	@FXML
	private TextField taskInput;
	@FXML
	private TextField dateInput;
	@FXML
	private Button taskSubmitButton;
	@FXML
	private Button dateSubmitButton;
	@FXML
    private Label timer;
	@FXML 
	private Button startTaskButton;
	@FXML 
	private Button pauseTaskButton;
	@FXML 
	private Button endTaskButton;
	@FXML
	private Label taskNameLabel;
	@FXML
	private Label dateLabel;
	@FXML
	private Label durationLabel;
	@FXML
	private TextField finalTaskDetails;
	@FXML
	private Button clearTaskDetails;
	@FXML
	private Button submitTaskDetails;
	
	private Timeline timeline;
    private long startTimeMillis = 0;
    private long pauseTimeMillis = 0;
    private long pauseStartTimeMillis = 0;
    private boolean isPaused = false;
    
    private String taskName;
    private String date;
    private String duration;
    private boolean validTaskName = false;
    private boolean validDate = false;
    private boolean validDuration = false;
    
	
    //Method that gets task name from user
	@FXML
	public void getTaskNameInput(ActionEvent event) {
	    String userInput = taskInput.getText().trim(); // Trim leading/trailing whitespace

	    if (userInput.isEmpty()) {
	        showAlert("Error", "Task name is required.");
	    } else if (userInput.length() < 3) {
	        showAlert("Error", "Task name must be at least 3 characters.");
	    } else if (userInput.length() > 50) {
	        showAlert("Error", "Task name cannot exceed 50 characters.");
	    } else if (!isValidTaskName(userInput)) {
	        showAlert("Error", "Invalid task name. Task names can only contain letters and spaces.");
	    } else {
	        taskName = userInput;
	        validTaskName = true;
	    }
	}
	
	//Method that checks if task name entered by user is a valid one
	private boolean isValidTaskName(String input) {
	    // This method checks if the input contains only letters and spaces

	    // Regular expression pattern to match valid task names
	    String pattern = "^[A-Za-z\\s]+$";

	    return input.matches(pattern);
	}
	
	
	//Method that gets user input for date
	@FXML
	public void getDateInput(ActionEvent event) {
	    String userInput = dateInput.getText().trim(); // Trim leading/trailing whitespace

        if (userInput.isEmpty()) {
            showAlert("Error", "Date is required.");
        } else {
            // Perform data type validation (date)
            try {
                // Check for MM/DD/YYYY format
                if (!userInput.matches("^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/(\\d{4})$")) {
                    showAlert("Error", "Invalid date format. Please use MM/DD/YYYY. Add a zero before entering single digit days and months.");
                    return; // Exit method if format is invalid
                }

                SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
                inputFormat.setLenient(false);

                SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy");

                // Parse the input and format it with leading zeros
                String formattedDate = outputFormat.format(inputFormat.parse(userInput));
                dateInput.setText(formattedDate); // Update the input field
                date = formattedDate;
                validDate = true;

            } catch (ParseException e) {
                showAlert("Error", "Invalid date format. Please use MM/DD/YYYY. Add a zero before entering single digit days and months.");
            }
        }
	}
	
	//Method that initializes the timer.
	@FXML
	public void initialize() {
		pauseTimeMillis = 0;
	    timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
	        updateTimer();
	    }));
	    timeline.setCycleCount(Timeline.INDEFINITE);

	    // Disable the Start Timer button initially
	    startTaskButton.setDisable(true);

	    // Create a custom BooleanBinding to validate the date format
	    BooleanBinding validDateFormat = Bindings.createBooleanBinding(() -> {
	        String dateInputText = dateInput.getText();
	        return dateInputText.matches("^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/(19|20)\\d\\d$");
	    }, dateInput.textProperty());

	    // Bind the disableProperty of the Start Timer button
	    startTaskButton.disableProperty().bind(
	        taskInput.textProperty().isEmpty()
	            .or(dateInput.textProperty().isEmpty())
	    );
    }
	
	//Method that starts the timer.
	@FXML
	private void startTimer(ActionEvent event) {
	    if (!startTaskButton.isDisabled()) {
	        if (!isPaused) {
	            startTimeMillis = System.currentTimeMillis();
	        } else {
	            long pauseDuration = System.currentTimeMillis() - pauseStartTimeMillis;
	            startTimeMillis += pauseDuration;
	            isPaused = false;
	        }
	        timeline.play();
	    }
	}
	

	//Method that pauses the timer. 
	@FXML
	private void pauseTimer(ActionEvent event) {
	    if (!isPaused) {
	        timeline.pause();
	        pauseStartTimeMillis = System.currentTimeMillis();
	        isPaused = true;
	    }
	}

	//Method that stops the timer. 
	@FXML
	private void stopTimer(ActionEvent event) {
	    if (!isPaused) {
	        timeline.stop();

	        // Calculate elapsed time (excluding pause duration)
	        long currentTimeMillis = System.currentTimeMillis();
	        long elapsedMillis = currentTimeMillis - startTimeMillis;

	        // Convert elapsedMillis to hours, minutes, and seconds
	        int hours = (int) (elapsedMillis / (1000 * 60 * 60));
	        int minutes = (int) ((elapsedMillis / (1000 * 60)) % 60);
	        int seconds = (int) ((elapsedMillis / 1000) % 60);

	        // Record the duration and update the timer label
	        duration = String.format("%02d:%02d:%02d", hours, minutes, seconds);
	        finalTaskDetails.setText("Task name: " + taskName + "\t \t Date: " + date + "\t \t Duration: " + duration);
	        validDuration = true;

	        // Clear text entries
	        taskInput.clear();
	        dateInput.clear();

	        // Reset the timer text
	        timer.setText("00:00:00");
	    }
	    isPaused = false;
	}


	 //Method that clears the task details
	 @FXML
	 public void clearTaskDetails(ActionEvent event) {
		 finalTaskDetails.setText("");
	 }
	 
	 
	 //Method that submits the task details
	 @FXML
	 public void submitDetails(ActionEvent event) {
		 if (validTaskName && validDate && validDuration) {
			 boolean submitted = Database.insertTask(taskName, date, duration);
			 if (submitted) {
				// Show success popup and clear text
			     showCustomPopup("Success", "Submission successful!");
			     finalTaskDetails.setText("");
			 }
		 } else {
			 showAlert("Error", "Invalid details entered, please try again!");
			 finalTaskDetails.setText("");
		 }
	 }
	 
	 
	 //Method that updates the timer to reflect the correct time that has elapsed.
	 @FXML
	 private void updateTimer() {
	     if (!isPaused) {
	         long currentTimeMillis = System.currentTimeMillis();
	         long elapsedMillis = currentTimeMillis - startTimeMillis;

	         // Subtract pause duration from elapsed time
	         elapsedMillis -= pauseTimeMillis;

	         // Convert elapsedMillis to hours, minutes, and seconds
	         int hours = (int) (elapsedMillis / (1000 * 60 * 60));
	         int minutes = (int) ((elapsedMillis / (1000 * 60)) % 60);
	         int seconds = (int) ((elapsedMillis / 1000) % 60);

	         // Update the timer label
	         String timeText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
	         timer.setText(timeText);
	     }
	 }
	 
	 
	//Method that switches to the main screen
	public void switchToMain(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//Method that switches back to the create a time entry page
	public void switchToCreate(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("CreateTimeEntry.fxml"));
		stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//Method that shows an alert message.
	private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
	
	//Method that shows a custom popup, such as to inform user of succesful submission
	private void showCustomPopup(String title, String message) {
	    Dialog<Void> dialog = new Dialog<>();
	    dialog.setTitle(title);

	    // Remove the default header with icon and close button
	    dialog.setDialogPane(new DialogPane() {
	        {
	            getButtonTypes().addAll(ButtonType.OK);
	            setContentText(message);
	            setHeaderText(null); // Remove header text
	            setGraphic(null);    // Remove header icon
	            setMinHeight(Region.USE_PREF_SIZE);
	        }
	    });

	    dialog.showAndWait();
	}
	
}
