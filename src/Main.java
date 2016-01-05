import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.*;
import javafx.scene.paint.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Hatachi Samsung on 10/4/2015.
 */
public class Main extends Application {
    Stage window;
    //Background background;
    Button button, button2;
    Button clearButton;
    Button bButton, vButton;
    Button backButton, completeButton;
    Button back, cButton;
    String firstInput, lastInput;
    TextField firstName, lastName;
    Scene scene, scene1, scene2, scene3, scene4;
    TableView<Requests> table;
    TableView<EmergencyRequests>table2;
    static int itemCount = 2;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Login");
        /*window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });*/

        /*
        First scene will ask the User for their name to take that input and place it in the Title
        This layout will use a simple Grid Pane to display the Text field
         */
        //Creating a GridPane container
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        //Define the header
        Label header = new Label("Please Enter Name");
        header.setAlignment(Pos.CENTER);
        header.setFont(Font.font("Times New Roman", 14));
        GridPane.setConstraints(header, 0, 0);
        grid.getChildren().add(header);

        //Defining the Name text field
        firstName = new TextField();
        firstName.setPromptText("Enter your first name.");
        firstName.setPrefColumnCount(10);
        GridPane.setConstraints(firstName, 0, 1);
        grid.getChildren().add(firstName);

        //Defining the Last Name text field
        lastName = new TextField();
        lastName.setPromptText("Enter your last name.");
        GridPane.setConstraints(lastName, 0, 2);
        grid.getChildren().add(lastName);

        //Defining the Submit button
        Button submit = new Button("Submit");
        submit.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        submit.setTextFill(Color.WHITE);
        submit.setOnAction(e -> {
            firstInput = firstName.getText();
            lastInput = lastName.getText();
            createMainScene();
        });
        GridPane.setConstraints(submit, 1, 1);
        grid.getChildren().add(submit);

        //Defining the Clear button
        clearButton = new Button("Clear");
        clearButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        clearButton.setTextFill(Color.WHITE);
        clearButton.setOnAction(e -> {
            firstName.clear();
            lastName.clear();
        });
        GridPane.setConstraints(clearButton, 1, 2);
        grid.getChildren().add(clearButton);

        scene = new Scene(grid, 300, 150);
        scene.getStylesheets().add("Style1.css");

        //setting the primary stage
        window.setScene(scene);
        window.show();
    }

        /*
        This is method creates scene1 which is the Main title screen
        Takes the user input from the first page and places the input in the title
        @scene1 is the main title screen
        @VBox is a vertical layout that holds the button, labels an the image
         */
     public void createMainScene() {
         window.setTitle("Flight Assistant");
         //Load Image
         Image image = new Image("plane.jpeg");

         //Top panel
         HBox top = new HBox ();
         top.setAlignment(Pos.CENTER);
         Label title = new Label();
         title.setText("Welcome " + firstInput + " " + lastInput +" to your In-Flight Assistant!");
         title.setFont(Font.font("Times New Roman",FontWeight.BOLD, 38));

         //Define Label string
         Label string = new Label();
         string.setText("Click below to view your pending request");
         string.setFont(Font.font("Times New Roman", 16));

         //button showing pending request
        button = new Button();
        button.setText("Pending request");
        button.setPrefWidth(250);
        button.setPrefHeight(50);
         button.setTextFill(Color.WHITE);
         button.setFont(Font.font("Times New Roman",FontWeight.BOLD, 14));
        //switch to scene 2
        button.setOnAction(e -> createSceneTwo());

         //Log Out button
         button2 = new Button();
         button2.setText("Log Out");
         button2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
         button2.setPrefWidth(175);
         button2.setPrefHeight(50);
         button2.setTextFill(Color.WHITE);
         button2.setOnAction(e -> {
             firstName.clear();
             lastName.clear();
             window.setScene(scene);
         });

        //Layout 1 and adding elements to the layout
         VBox layout1 = new VBox(30);

        //placing plane image under title
         ImageView iv = new ImageView();
         iv.setImage(image);
         layout1.getChildren().addAll(title, iv, string, button, button2);
         layout1.setAlignment(Pos.CENTER);

        //setting the layout size for the first scene
         scene1 = new Scene(layout1, 900, 650);
         scene1.getStylesheets().add("Style1.css");

         //Set scene1
         window.setScene(scene1);
         window.show();
    }


        /*
        Scene2 will be made using a BorderPane layout
        @bottomMenu holds three buttons at the bottom using HBox
        @centerMenu holds a list view  using VBox
        @topMenu will hold the Title of the scene using VBox
         */
    public void createSceneTwo() {
        window.setTitle("Requests");
        Image image = new Image("plane.jpeg");

        //Define Seat column
        TableColumn<Requests, String> seatColumn = new TableColumn<>("Seat#");
        seatColumn.setPrefWidth(100);
        seatColumn.setCellValueFactory(new PropertyValueFactory<>("seat"));

        //Define Name column
        TableColumn<Requests, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setPrefWidth(250);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Define time column
        TableColumn<Requests, Integer> timeColumn = new TableColumn<>("Time Passed \n" + "(sec)");
        timeColumn.setPrefWidth(100);
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        //Define Order column
        TableColumn<Requests, String> urgentColumn = new TableColumn<>("Order");
        urgentColumn.setPrefWidth(250);
        urgentColumn.setCellValueFactory(new PropertyValueFactory<>("order"));

        //Add items to the table
        table = new TableView<>();
        table.setItems(getRequest());
        table.getColumns().addAll(seatColumn, nameColumn, timeColumn, urgentColumn);

        //Title of Screen and top Menu
        VBox topMenu = new VBox();
        topMenu.setAlignment(Pos.CENTER);
        Label title2 = new Label("Pending Request");
        title2.setFont(Font.font("Times New Roman",FontWeight.BOLD, 38));
        topMenu.getChildren().add(title2);

        //Define the side menu
        VBox leftMenu = new VBox(5);
        leftMenu.setAlignment(Pos.TOP_LEFT);
        leftMenu.setPadding(new Insets(5, 30, 30, 5));
        Button emergency = new Button();
        emergency.setText("Emergency"+"("+itemCount+")");
        emergency.setTextFill(Color.BLACK);
        emergency.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        emergency.setStyle("-fx-background-color: red");
        emergency.setPrefSize(130, 40);
        emergency.setAlignment(Pos.TOP_CENTER);
        emergency.setOnAction(e->{
            createSceneFour();
        });
        ImageView iv = new ImageView();
        iv.setImage(image);
        iv.setFitWidth(130);
        iv.setFitHeight(100);
        leftMenu.getChildren().addAll(iv, emergency);

        //Define bottom Menu
        HBox bottomMenu = new HBox(100);
        bottomMenu.setAlignment(Pos.CENTER);
        //back button for scene 2
        bButton = new Button();
        bButton.setText("Back");
        bButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        bButton.setTextFill(Color.WHITE);
        bButton.setPrefWidth(150);
        bButton.setPrefHeight(30);
        //switch back to first scene
        bButton.setOnAction(e -> createMainScene());

        /*View Button
        vButton = new Button();
        vButton.setText("View");
        vButton.setTextFill(Color.WHITE);
        vButton.setPrefWidth(150);
        vButton.setPrefHeight(50);
        vButton.setOnAction(e -> viewButtonClicked());
        */

        //Complete Button
        completeButton = new Button();
        completeButton.setText("Complete");
        completeButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        completeButton.setTextFill(Color.WHITE);
        completeButton.setPrefWidth(150);
        completeButton.setPrefHeight(30);
        completeButton.setOnAction(e -> {
            boolean result2 = CompleteBox.display("COMPLETING...", "Are you sure you would like to complete this request?");
            //System.out.println(result2);
            if(result2){
                completeButtonClicked();
            }
        });

        //Delete Button
        /*
        dButton = new Button();
        dButton.setText("Delete");
        dButton.setPrefWidth(150);
        dButton.setPrefHeight(50);
        //Handle deletion and return data
        dButton.setOnAction(e -> {
            boolean result = DeleteAlert.display("CAUTION!", "Are you sure you would like to delete this request?");
            //System.out.println(result);
            if(result){
                completeButtonClicked();
            }

        });
        */
        bottomMenu.setPadding(new Insets (5, 10, 10, 5));
        bottomMenu.getChildren().addAll(bButton, completeButton);

        //Create the BorderPane
        BorderPane layout2 = new BorderPane();
        layout2.setTop(topMenu);
        layout2.setBottom(bottomMenu);
        layout2.setLeft(leftMenu);
        layout2.setCenter(table);
        //setting the size of scene 2
        scene2 = new Scene(layout2, 850, 650);
        scene2.getStylesheets().add("Style1.css");

        //Set scene2
        window.setScene(scene2);
        window.show();

    }

        /*
        Scene3 will use BorderPane as the layout
        @top will be the seat number of the customer that placed the request using HBox
        @bottom will hold the buttons to go back or to Complete a request using HBox
        @left will hold all of the information including how much time the customer has been waiting using VBox
         */
    /*public void createSceneThree(ObservableList<Requests> selectedReq){
        window.setTitle("Request View");

        //description.setText(String.valueOf(selectedReq));

        //Top pane
        HBox top = new HBox();
        top.setAlignment(Pos.CENTER);
        Label title3 = new Label("Seat 17B");
        title3.setFont(Font.font("Times New Roman", 24));
        top.getChildren().add(title3);

        //Center Pane
        VBox center = new VBox();
        center.setAlignment(Pos.CENTER);
        Label description = new Label();
        description.setText("Name: Shaye Davis\n\n" + "Urgency: Low\n\n" + "Customer Request:\n" +
                "1. Sprite\n" + "2. Apple\n" + "3. Peanuts\n" + "4. Napkins");
        description.setFont(Font.font("Times New Roman", 16));
        center.getChildren().add(description);

        //Bottom pane
        HBox bottom = new HBox(10);
        bottom.setAlignment(Pos.CENTER);
        backButton = new Button();
        backButton.setText("Back");
        backButton.setPrefWidth(250);
        backButton.setPrefHeight(50);
        backButton.setOnAction(e -> createSceneTwo());

        /*completeButton = new Button();
        completeButton.setText("Complete");
        completeButton.setPrefWidth(150);
        completeButton.setPrefHeight(50);
        completeButton.setOnAction(e -> {
            boolean result2 = CompleteBox.display("COMPLETING...", "Are you sure you would like to complete this request?");
            //System.out.println(result2);
            if(result2){
                deleteButtonClicked();
            }
            createSceneTwo();
        });
        */
        /*bottom.getChildren().addAll(backButton);

        //Left Pane TO-DO

        //Create BorderPane
        BorderPane layout3 = new BorderPane();
        layout3.setBottom(bottom);
        layout3.setTop(top);
        layout3.setCenter(center);

        scene3 = new Scene(layout3, 600, 350);

        //Set scene3
        window.setScene(scene3);
        window.show();

    }
    */

    public void createSceneFour(){
        window.setTitle("Emergency Requests");
        Image image = new Image("plane.jpeg");

        //Top panel
        HBox top = new HBox();
        top.setAlignment(Pos.CENTER);
        Label eTitle = new Label();
        eTitle.setText("Emergency Requests");
        eTitle.setFont(Font.font("Times New Roman",FontWeight.BOLD, 38));
        top.getChildren().add(eTitle);

        //Middle panel

        //Define Seat column
        TableColumn<EmergencyRequests, String> seatColumn = new TableColumn<>("Seat#");
        seatColumn.setPrefWidth(300);
        seatColumn.setCellValueFactory(new PropertyValueFactory<>("seat"));

        //Define Name column
        TableColumn<EmergencyRequests, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setPrefWidth(300);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Put things into the table
        table2 = new TableView<>();
        table2.setItems(getEmergency());
        table2.getColumns().addAll(seatColumn, nameColumn);

        //Left Pane
        VBox left = new VBox();
        left.setAlignment(Pos.TOP_LEFT);
        left.setPadding(new Insets(1, 10, 10, 1));
        ImageView iv = new ImageView();
        iv.setImage(image);
        iv.setFitWidth(130);
        iv.setFitHeight(100);
        left.getChildren().add(iv);


        //Bottom panel
        HBox bottom = new HBox(150);
        bottom.setAlignment(Pos.CENTER);

        //Complete Button
        cButton = new Button();
        cButton.setText("Complete");
        cButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        cButton.setTextFill(Color.WHITE);
        cButton.setPrefSize(150, 30);
        cButton.setOnAction(e -> {
            boolean result2 = CompleteBox.display("COMPLETING...", "Are you sure you would like to complete this request?");
            //System.out.println(result2);
            if(result2){
                cButtonClicked();
            }
        });

        //Back Button
        back = new Button();
        back.setText("Back");
        back.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        back.setTextFill(Color.WHITE);
        back.setPrefSize(150, 30);
        back.setOnAction(e-> createSceneTwo());

        bottom.setPadding(new Insets(5, 10, 10, 5));
        bottom.getChildren().addAll(back, cButton);

        //Define layout
        BorderPane layout4 = new BorderPane();
        layout4.setTop(top);
        layout4.setCenter(table2);
        layout4.setBottom(bottom);
        layout4.setLeft(left);

        //Setting the scene
        scene4 = new Scene(layout4, 700, 650);
        scene4.getStylesheets().add("Style1.css");
        //Set the scene
        window.setScene(scene4);
        window.show();

    }

    /*public void viewButtonClicked(){
        ObservableList<Requests> selectedReq;
        selectedReq = table.getSelectionModel().getSelectedItems();
        createSceneThree(selectedReq);
    }
    */
    //Delete an item from the table
    public void completeButtonClicked(){
        ObservableList<Requests> selectedRequest, allRequest;
        allRequest = table.getItems();
        selectedRequest = table.getSelectionModel().getSelectedItems();

       selectedRequest.forEach(allRequest::remove);
    }

    public void cButtonClicked(){
        ObservableList<EmergencyRequests> selectedRequest, allRequest;
        allRequest = table2.getItems();
        selectedRequest = table2.getSelectionModel().getSelectedItems();

        selectedRequest.forEach(allRequest::remove);
    }

    //Get all of the request
    public ObservableList<Requests> getRequest(){
        ObservableList<Requests> request = FXCollections.observableArrayList();
        request.add(new Requests("17B", "Shaye", 15, "Pepsi x2, Mellow Yellow x2\n" + "\nPeanuts, Chicken Dinner\n"+"\nBlanket"));
        request.add(new Requests("26A", "Lauren", 25, "Sam Adams, Blue Moon, Sprite\n"+"\nSteak Dinners x2, Veggie Dinner\n"
        +"\nHeadphones, Blanket"));
        request.add(new Requests("5D", "Rick", 60, "Dr. Pepper \n" + "\nFish Dinner \n" + "\nBlanket , Pillow, Headphones"));
        request.add(new Requests("22A", "Brad", 120, "Sunkist, Dos Equis, Miller Light x2 \n" + "\nChicken Dinner \n" +
        "\nHeadphones x3, Blanket"));
        request.add(new Requests("3C", "Lumpkin", 5, "Lemonade, Coors Light\n" + "\nHeadphones"));
        request.add(new Requests("20B", "Curtis", 32, "Canada Dry\n" + "\n Veggie Dinner\n" + "\nPillow, Blanket"));
        request.add(new Requests("11A", "Matt", 45, "Water, Dos Equis\n" + "\nSteak Dinner"));

        return request;
    }

    public ObservableList<EmergencyRequests> getEmergency(){
        ObservableList<EmergencyRequests> emergency = FXCollections.observableArrayList();
        emergency.add(new EmergencyRequests("7B", "Bruce"));
        emergency.add(new EmergencyRequests("30C", "Hannah"));
        emergency.add(new EmergencyRequests("1A", "Hatachi"));
        itemCount = emergency.size();
        return emergency;
    }


    /*private void closeProgram(){

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("./output.txt"));
            writer.write("your data here");
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }
    }
    */


}


















