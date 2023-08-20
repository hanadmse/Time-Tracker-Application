# TimeTrackerApplication
 
Time Tracker App
The Time Tracker App is a JavaFX application designed to help users track and manage their tasks, including recording task names, dates, and durations. This README provides an overview of the project structure, functionalities, and how to set up and run the app.

Getting Started
Before you begin, ensure you have the following:
        1.) Java Development Kit (JDK) installed.
        2.) Eclipse IDE (or any Java IDE of your choice) for code development.
        3.) SQLite database engine.

Installation
1.) Clone this repository to your local machine using Git:
2.) Open the project in your Java IDE.
3.) Configure your IDE to use the JavaFX library if not set up already.
4.) Set up the SQLite database:
        -Ensure that you have an SQLite database engine installed.
        -Create a tasks.db SQLite database file and place it in the project directory.
        -Run the SQL script to create the necessary table structure for the app.

Usage
1.) Launch the application and use it to:
        1.) Add new tasks, providing task names, dates, and durations.
        2.) Start, pause, and stop timers to track the duration of tasks.
        3.) View, search, and manage submitted tasks.
2.) Use the navigation buttons within the app to switch between different views.

Project Structure
The project is structured as follows:
        1.) src/application: Contains the Java code for the application.
        2.) src/application/resources: Contains the FXML files for the user interface.
        3.) tasks.db: SQLite database file for storing task data.
        
License
This project is licensed under the MIT License.
