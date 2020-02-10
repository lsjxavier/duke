package duke;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Duke extends Application {

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private Image user = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image duke = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    List<String> log;
    TaskList tasklist;

    public Duke() {
        this.log = new ArrayList<String>();
        this.tasklist = Storage.load();
    }

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        List<String> log = new ArrayList<String>();

        Ui.showLogo();
        TaskList tasklist = Storage.load();
        System.out.println("");
        System.out.println(Ui.WELCOME_MESSAGE);

        boolean didNotExit = true;
        while (didNotExit) {
            String thisResult = Ui.readNextCommand(scanner, tasklist);
            log.add(thisResult);

            if (thisResult.equals("exit")) {
                if (log.isEmpty() || tasklist.getList().isEmpty()) {
                    didNotExit = false;
                } else {
                    didNotExit = Ui.askBeforeQuitting(scanner, tasklist);
                }
            } else {
                System.out.println(thisResult);
            }
            System.out.println("");
        }

        System.out.println(Ui.EXIT_MESSAGE);
        scanner.close();
    }

    @Override
    public void start(Stage stage) {
        //Step 1. Setting up required components


        //The container for the content of the chat to scroll.
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.show();

        //Step 2. Formatting the window to look as expected
        stage.setTitle("Duke");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        // You will need to import `javafx.scene.layout.Region` for this.
        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput , 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);


        Label welcomeMessage = new Label(Ui.WELCOME_MESSAGE);
        Label startupUpcomingWeekView = new Label(Parser.upcomingCommand(new String[] {"upcoming", "7"}, this.tasklist));
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(welcomeMessage, new ImageView(duke)),
                DialogBox.getDukeDialog(startupUpcomingWeekView, new ImageView(duke))
        );

        //Step 3. Add functionality to handle user input.
        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });

        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        //Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));


    }


    /**
     * Iteration 1:
     * Creates a label with the specified text and adds it to the dialog container.
     * @param text String containing text to add
     * @return a label with the specified text that has word wrap enabled.
     */
    private Label getDialogLabel(String text) {
        // You will need to import `javafx.scene.control.Label`.
        Label textToAdd = new Label(text);
        textToAdd.setWrapText(true);

        return textToAdd;
    }

    /**
     * Iteration 2:
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput() {

        String commandInput = userInput.getText();
        Label userText = new Label(commandInput);
        Label dukeText = new Label(getResponse(commandInput));

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, new ImageView(user)),
                DialogBox.getDukeDialog(dukeText, new ImageView(duke))
        );
        userInput.clear();
    }

    /**
     * Processes the user input and returns a result.
     * @return the result after processing the user input.
     */
    private String getResponse(String input) {
        return Ui.readNextCommand(input, this.tasklist);
    }





}