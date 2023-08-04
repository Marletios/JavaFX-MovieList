package com.example.project3;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private ListView<String> moviesListView;
    private ObservableList<String> moviesList;
    @FXML
    private TextField movieTitleTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button loadButton;
    @FXML
    private Button saveButton;
    @FXML
    private Label statusLabel;


    @FXML
    private void initialize() {
        // Initialize the moviesList and set it to the ListView
        moviesList = FXCollections.observableArrayList();
        moviesListView.setItems(moviesList);

        // Set the selection mode to allow multiple selections
        moviesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    @FXML
    private void addButtonClicked() {
        String movieTitle = movieTitleTextField.getText().trim();
        if (!movieTitle.isEmpty()) {
            moviesList.add(movieTitle);
            movieTitleTextField.clear();
        }
    }


    @FXML
    private void removeButtonClicked() {
        List<String> selectedMovies = new ArrayList<>(moviesListView.getSelectionModel().getSelectedItems());
        moviesList.removeAll(selectedMovies);
    }



    @FXML
    private void loadButtonClicked() {
        try {
            File file = new File("movies.txt");
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    moviesList.add(line);
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveButtonClicked() {
        try {
            File file = new File("movies.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String movie : moviesList) {
                writer.write(movie + "\n");
            }
            writer.close();

            // Set the text of the statusLabel to "List Saved" when the save operation is successful
            statusLabel.setText("List Saved");

            // Create a PauseTransition to hide the message after 3 seconds (adjust as needed)
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> statusLabel.setText(""));
            pause.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public class MovieListCell extends ListCell<String> {

        private final CheckBox checkBox;
        private final HBox hbox;

        public MovieListCell() {
            checkBox = new CheckBox();
            hbox = new HBox(checkBox);
            hbox.setSpacing(10);
        }

    @Override
    protected void updateItem(String movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setGraphic(null);
        } else {
            checkBox.setText(movie);
            setGraphic(hbox);
        }
    }
}

    //end
}

