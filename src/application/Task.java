package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Task class representing a task entry
public class Task {

	// Properties to store task information
	private final IntegerProperty taskId;
	private final StringProperty taskName;
	private final StringProperty date;
	private final StringProperty duration;

	// Constructor to initialize task properties
	public Task(int taskId, String taskName, String date, String duration) {
		this.taskId = new SimpleIntegerProperty(taskId);
		this.taskName = new SimpleStringProperty(taskName);
		this.date = new SimpleStringProperty(date);
		this.duration = new SimpleStringProperty(duration);
	}

	// Getter for the taskId property
	public IntegerProperty taskIdProperty() {
		return taskId;
	}

	// Getter for the taskName property
	public StringProperty taskNameProperty() {
		return taskName;
	}

	// Getter for the date property
	public StringProperty dateProperty() {
		return date;
	}

	// Getter for the duration property
	public StringProperty durationProperty() {
		return duration;
	}

}
