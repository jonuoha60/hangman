package com.example.hangman1;


import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class HelloApplication extends Application {
    private ArrayList<Sports> sportsList = new ArrayList<>();
    private Pane gallowsPane;

    private String currentSportName = null;
    private int guessCount = 0;
    private ArrayList<Label> letterLabels = new ArrayList<>();

    // Hangman parts as instance variables so they can be reset
    private Line line1, line2, line3, line4, line5, line6, line7, line8, line9, line10;
    private Circle circle1;

    @Override
    public void start(Stage stage) {
        try {
            // Create UI components
            Button bt1 = new Button("Start");
            ChoiceBox<String> choiceBox = new ChoiceBox<>();
            choiceBox.getItems().addAll("sports", "books", "fruits");
            choiceBox.setValue("sports");

            Label lb1 = new Label("Welcome to the hangman game");
            Label lb2 = new Label("Pick a category to guess e.g sports, celebrities");
            Label lb3 = new Label();

            lb1.setFont(new Font("Times New Roman", 32));
            lb1.setTextFill(Color.BLACK);
            lb3.setTextFill(Color.RED);

            VBox vbox = new VBox(20, lb1, lb2, choiceBox, lb3);
            vbox.setAlignment(Pos.TOP_CENTER);

            Label lb4 = new Label("");
            Label lb5 = new Label("Category: ");
            lb5.setFont(new Font("Times New Roman", 29));
            lb5.setTextFill(Color.BLACK);
            Label lb6 = new Label();
            lb6.setFont(new Font("Times New Roman", 25));
            lb6.setTextFill(Color.BLACK);

            Label lb7 = new Label("Description: ");
            lb7.setFont(new Font("Times New Roman", 27));
            lb7.setTextFill(Color.BLACK);

            Label lb8 = new Label();
            lb8.setFont(new Font("Times New Roman", 20));
            lb8.setTextFill(Color.BLACK);

            Label sportNameStored = new Label();
            sportNameStored.setFont(new Font("Times New Roman", 23));
            sportNameStored.setTextFill(Color.BLACK);

            TextField guessing = new TextField();
            guessing.setMaxWidth(300);

            Button submitButton = new Button("Submit");
            Button clearButton = new Button("Clear");
            Button newGameButton = new Button("New Game");

            gallowsPane = new Pane();
            gallowsPane.setPrefSize(400, 400);

            // Initialize hangman parts (lines and circle)
            initHangmanParts();

            // Add gallows and hangman parts to the pane initially (full hangman visible)
            gallowsPane.getChildren().addAll(line1, line2, line3, line4, line5, line6, line7, line8, line9, line10, circle1);

            // Sample sports data
            sportsList.add(new Sports(1, "Soccer", "A team sport played with a round ball."));
            sportsList.add(new Sports(2, "Basketball", "A team sport played with a hoop and ball in usually 5 teams."));
            sportsList.add(new Sports(3, "Tennis", "A racket sport played by two or four players."));
            sportsList.add(new Sports(4, "Baseball", "A bat-and-ball game played between two teams."));
            sportsList.add(new Sports(5, "Hockey", "A fast-paced sport played on ice or field."));
            sportsList.add(new Sports(6, "Cricket", "A bat-and-ball game played with wickets and a ball."));
            sportsList.add(new Sports(7, "Rugby", "A contact team sport played with an oval ball."));
            sportsList.add(new Sports(8, "Volleyball", "A sport where teams hit a ball over a net."));
            sportsList.add(new Sports(9, "Golf", "A sport where players aim to hit balls into holes in few strokes."));
            sportsList.add(new Sports(10, "Swimming", "A competitive water sport with various styles and distances."));
            sportsList.add(new Sports(11, "Boxing", "A combat sport with punches in a ring."));
            sportsList.add(new Sports(12, "Cycling", "A sport of racing bicycles on tracks or roads."));
            sportsList.add(new Sports(13, "Wrestling", "A sport of grappling and pinning opponents."));
            sportsList.add(new Sports(14, "Badminton", "A racket sport played with a shuttlecock over a net."));
            sportsList.add(new Sports(15, "TableTennis", "A fast-paced indoor sport played with small paddles and a light ball."));
            sportsList.add(new Sports(16, "Track", "An athletic sport involving running races of various distances."));
            sportsList.add(new Sports(17, "Surfing", "Riding waves on a board in the ocean."));
            sportsList.add(new Sports(18, "Skating", "Gliding on ice or hard surfaces using skates."));
            sportsList.add(new Sports(19, "Archery", "A precision sport using a bow to shoot arrows at a target."));
            sportsList.add(new Sports(20, "Karate", "A martial art focusing on strikes, kicks, and defensive blocks."));


            HBox buttonBox = new HBox(20, submitButton, clearButton, newGameButton);
            buttonBox.setAlignment(Pos.TOP_CENTER);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            grid.add(sportNameStored, 0, 0, 2, 1);
            GridPane.setHalignment(sportNameStored, HPos.CENTER);
            grid.add(lb7, 0, 1);
            grid.add(lb8, 1, 1);
            grid.add(gallowsPane, 0, 2, 2, 1);
            grid.add(lb4, 0, 3, 2, 1);
            GridPane.setHalignment(lb4, HPos.CENTER);
            grid.add(lb5, 0, 4);
            GridPane.setHalignment(lb5, HPos.CENTER);
            grid.add(lb6, 1, 4);
            grid.add(guessing, 0, 5, 2, 1);
            GridPane.setHalignment(guessing, HPos.CENTER);
            grid.add(buttonBox, 0, 6, 2, 1);
            GridPane.setHalignment(buttonBox, HPos.CENTER);

            grid.setAlignment(Pos.CENTER);

            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 500, 650);
            root.setCenter(bt1);
            root.setTop(vbox);

            // Start button: load the game UI and start a new game
            bt1.setOnAction(e -> getChoice(choiceBox, lb3, lb6, root, grid, vbox, lb2, lb8, sportNameStored, submitButton));
            // Submit guess button
            submitButton.setOnAction(e -> getAnswers(guessing, sportNameStored, submitButton));
            // Clear input button
            clearButton.setOnAction(e -> {
                guessing.clear();
                sportNameStored.setText("");
            });
            // New game button - restart game
            newGameButton.setOnAction(e -> getChoice(choiceBox, lb3, lb6, root, grid, vbox, lb2, lb8, sportNameStored, submitButton));

            stage.setTitle("Hangan game");
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to initialize hangman parts (lines and circle)
    private void initHangmanParts() {
        line1 = new Line();
        line1.startXProperty().bind(gallowsPane.widthProperty().multiply(0.125));
        line1.startYProperty().bind(gallowsPane.heightProperty().multiply(0.875));
        line1.endXProperty().bind(gallowsPane.widthProperty().multiply(0.625));
        line1.endYProperty().bind(gallowsPane.heightProperty().multiply(0.875));

        line2 = new Line();
        line2.startXProperty().bind(gallowsPane.widthProperty().multiply(0.375));
        line2.startYProperty().bind(gallowsPane.heightProperty().multiply(0.875));
        line2.endXProperty().bind(gallowsPane.widthProperty().multiply(0.375));
        line2.endYProperty().bind(gallowsPane.heightProperty().multiply(0.25));

        line3 = new Line();
        line3.startXProperty().bind(gallowsPane.widthProperty().multiply(0.375));
        line3.startYProperty().bind(gallowsPane.heightProperty().multiply(0.25));
        line3.endXProperty().bind(gallowsPane.widthProperty().multiply(0.675));
        line3.endYProperty().bind(gallowsPane.heightProperty().multiply(0.25));

        line4 = new Line();
        line4.startXProperty().bind(gallowsPane.widthProperty().multiply(0.375));
        line4.startYProperty().bind(gallowsPane.heightProperty().multiply(0.45));
        line4.endXProperty().bind(gallowsPane.widthProperty().multiply(0.625));
        line4.endYProperty().bind(gallowsPane.heightProperty().multiply(0.25));

        line5 = new Line();
        line5.startXProperty().bind(gallowsPane.widthProperty().multiply(0.675));
        line5.startYProperty().bind(gallowsPane.heightProperty().multiply(0.25));
        line5.endXProperty().bind(gallowsPane.widthProperty().multiply(0.675));
        line5.endYProperty().bind(gallowsPane.heightProperty().multiply(0.35));

        //Hangmans head
        circle1 = new Circle();
        circle1.setRadius(20);
        circle1.setStroke(Color.BLACK);
        circle1.setFill(Color.TRANSPARENT);
        circle1.centerXProperty().bind(gallowsPane.widthProperty().multiply(0.675));
        circle1.centerYProperty().bind(gallowsPane.heightProperty().multiply(0.40));

        //Hangmans torso
        line6 = new Line();
        line6.startXProperty().bind(circle1.centerXProperty());
        line6.endXProperty().bind(circle1.centerXProperty());
        line6.startYProperty().bind(circle1.centerYProperty().add(circle1.radiusProperty()));
        line6.endYProperty().bind(line6.startYProperty().add(100));

        //Hangmans arms
        line7 = new Line();
        line7.startXProperty().bind(line6.startXProperty());
        line7.startYProperty().bind(line6.startYProperty().add(30));
        line7.endXProperty().bind(line6.startXProperty().subtract(40));
        line7.endYProperty().bind(line6.startYProperty().add(60));

        line8 = new Line();
        line8.startXProperty().bind(line6.startXProperty());
        line8.startYProperty().bind(line6.startYProperty().add(30));
        line8.endXProperty().bind(line6.startXProperty().add(40));
        line8.endYProperty().bind(line6.startYProperty().add(60));

        //Hangmans legs
        line9 = new Line();
        line9.startXProperty().bind(line6.endXProperty());
        line9.startYProperty().bind(line6.endYProperty());
        line9.endXProperty().bind(line6.endXProperty().subtract(30));
        line9.endYProperty().bind(line6.endYProperty().add(50));

        line10 = new Line();
        line10.startXProperty().bind(line6.endXProperty());
        line10.startYProperty().bind(line6.endYProperty());
        line10.endXProperty().bind(line6.endXProperty().add(30));
        line10.endYProperty().bind(line6.endYProperty().add(50));
    }

    // Called to start a new game or reset current game
    private void getChoice(ChoiceBox<String> choiceBox, Label lb3, Label lb6, BorderPane root, GridPane grid, VBox vbox, Label lb2, Label lb8, Label sportNameStored, Button submitButton) {
        String games = choiceBox.getValue();

        if (games == null) {
            lb3.setText("Error pick a category!");
            return;
        }

        lb3.setText("");
        lb6.setText(games);

        // Show game grid
        if (vbox.getChildren().contains(choiceBox)) vbox.getChildren().remove(choiceBox);
        if (vbox.getChildren().contains(lb2)) vbox.getChildren().remove(lb2);
        root.setCenter(grid);

        if (games.equals("sports")) {
            Random random = new Random();
            int index = random.nextInt(sportsList.size());
            Sports randomSport = sportsList.get(index);
            submitButton.setDisable(false);

            lb8.setText(randomSport.getDescription());

            // Clear previous letters
            gallowsPane.getChildren().removeIf(node -> node instanceof Label);
            letterLabels.clear();

            // Reset guess count and current word
            guessCount = 0;
            currentSportName = randomSport.getName();

            // Reset hangman parts: clear and re-add all
            gallowsPane.getChildren().clear();
            gallowsPane.getChildren().addAll(line1, line2, line3, line4, line5, line6, line7, line8, line9, line10, circle1);

            // Create underscores for the new word
            double spacing = 20;
            double startX = gallowsPane.getPrefWidth() / 2 - (currentSportName.length() * spacing) / 2;
            double yPosition = 20;

            for (int i = 0; i < currentSportName.length(); i++) {
                Label letterLabel = new Label("_");
                letterLabel.setLayoutX(startX + i * spacing);
                letterLabel.setLayoutY(yPosition);
                letterLabel.setFont(new Font("Arial", 24));
                letterLabels.add(letterLabel);
                gallowsPane.getChildren().add(letterLabel);
            }


            sportNameStored.setText("");
        }
    }

    // Handle user guesses and update hangman figure accordingly
    private void getAnswers(TextField guessing, Label sportNameStored, Button submitButton) {
        String input = guessing.getText().trim().toLowerCase();

        if (input.isEmpty()) {
            sportNameStored.setText("Please enter a letter.");
            sportNameStored.setTextFill(Color.RED);
            return;
        }

        if (input.length() > 1) {
            sportNameStored.setText("Enter only one letter at a time.");
            sportNameStored.setTextFill(Color.RED);
            guessing.clear();
            return;
        }

        if (currentSportName == null) {
            sportNameStored.setText("Start a game first!");
            sportNameStored.setTextFill(Color.RED);
            return;
        }

        boolean correctGuess = false;

        for (int i = 0; i < currentSportName.length(); i++) {
            if (String.valueOf(currentSportName.charAt(i)).equalsIgnoreCase(input)) {
                letterLabels.get(i).setText(String.valueOf(currentSportName.charAt(i)));
                correctGuess = true;
            }
        }

        if (!correctGuess) {
            guessCount++;
            // Remove hangman parts in order on wrong guesses
            switch (guessCount) {
                case 1: gallowsPane.getChildren().remove(line10); break;
                case 2: gallowsPane.getChildren().remove(line9); break;
                case 3: gallowsPane.getChildren().remove(line8); break;
                case 4: gallowsPane.getChildren().remove(line7); break;
                case 5: gallowsPane.getChildren().remove(line6); break;
                case 6: gallowsPane.getChildren().remove(circle1); break;
            }

            sportNameStored.setText(
                    guessCount == 6 ? "Game Over! You lost. Hangman is Dead." : "Incorrect guess. Try again."
            );
            sportNameStored.setTextFill(Color.RED);

            if (guessCount == 6) {
                guessing.setDisable(true);
                submitButton.setDisable(true);
            }

        } else {
            sportNameStored.setText("Correct guess!");
            sportNameStored.setTextFill(Color.GREEN);

            // Check if all letters are revealed (game won)
            boolean allRevealed = letterLabels.stream().noneMatch(label -> label.getText().equals("_"));

            if (allRevealed) {
                sportNameStored.setText("Congratulations! You won the game!");
                sportNameStored.setTextFill(Color.BLUE);

                guessing.setDisable(true);
                submitButton.setDisable(true);
            }
        }

        guessing.clear();
    }



    public static void main(String[] args) {
        launch(args);
    }

    // Sports class to hold category data

}
