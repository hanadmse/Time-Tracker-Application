package application;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {

	// Establishes a connection to the database
	public static Connection Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:data/tasks.db");
			
			//"jdbc:sqlite:C:\\Users\\Fowsia\\eclipse-workspace\\HelloFX/tasks.db"
			//"jdbc:sqlite:C:/Users/Fowsia/tasks.db"
			return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Database connection unsuccesful");
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("org.sqlite not found");
			return null;
		}
	}

	// Inserts a new task into the database
	public static boolean insertTask(String taskName, String date, String duration) {
		Connection connection = Database.Connector();
		String sql = "INSERT INTO tasks (taskName, date, duration) VALUES (?, ?, ?)";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			// Set the values using placeholders and PreparedStatement's methods
			preparedStatement.setString(1, taskName);
			preparedStatement.setString(2, date);
			preparedStatement.setString(3, duration);

			// Execute the prepared statement
			preparedStatement.executeUpdate();

			// Close the prepared statement
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			System.out.println("Statement execution unsuccessful: " + e.getMessage());
			return false;
		}

	}

	// Loads tasks from the database into the provided ObservableList
	public static void loadTasksFromDatabase(ObservableList<Task> data) {
		try {
			Connection connection = Database.Connector();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT rowid, taskName, date, duration FROM tasks");

			while (resultSet.next()) {
				int taskId = resultSet.getInt("rowid");
				String taskName = resultSet.getString("taskName");
				String date = resultSet.getString("date");
				String duration = resultSet.getString("duration");

				data.add(new Task(taskId, taskName, date, duration));
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Loading tasks from database unsuccesful!");
		}
	}

	// Searches the database based on provided search criteria
	public static ObservableList<Task> searchDatabase(String taskName, String date, String duration) {
		ObservableList<Task> searchResults = FXCollections.observableArrayList();
		try {
			Connection connection = Database.Connector();
			Statement statement = connection.createStatement();
			StringBuilder query = new StringBuilder("SELECT rowid, taskName, date, duration FROM tasks WHERE 1=1");

			if (taskName != null) {
				query.append(" AND taskName LIKE '").append(taskName).append("'");
			}
			if (duration != null) {
				query.append(" AND duration = '").append(duration).append("'");
			}
			if (date != null) {
				query.append(" AND date = '").append(date).append("'");
			}
			ResultSet resultSet = statement.executeQuery(query.toString());
			while (resultSet.next()) {
				int taskId = resultSet.getInt("rowid");
				;
				String taskNameResult = resultSet.getString("taskName");
				String dateResult = resultSet.getString("date");
				String durationResult = resultSet.getString("duration");
				searchResults.add(new Task(taskId, taskNameResult, dateResult, durationResult));
			}
			resultSet.close();
			statement.close();
			connection.close();
			return searchResults;

		} catch (Exception e) {
			System.out.println("Search in database failed!");
			return null;
		}
	}
}
