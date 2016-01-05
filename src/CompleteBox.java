import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * Created by Hatachi Samsung on 10/4/2015.
 */
public class CompleteBox {

    static boolean answer;

    public static boolean display(String title, String message){
        Stage window = new Stage();
        window.setTitle(title);
        window.setMinWidth(250);
        window.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label();
        label.setText(message);

        //Confirm button
        Button confirm = new Button();
        confirm.setText("Confirm");
        //cancel button
        Button cancel = new Button();
        cancel.setText("Cancel");

        //Layout 1 for first alert
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, confirm, cancel);
        layout.setAlignment(Pos.CENTER);

        Scene alert1 = new Scene(layout);

        //New label for new scene
        /*Label label2 = new Label("Completed");
        label2.setAlignment(Pos.CENTER);
        //Ok button for second scene
        Button ok = new Button();
        ok.setText("OK");
        ok.setOnAction(e -> window.close());
        */

        //Layout 2 for second confirmation message
        /*VBox layout2 = new VBox(10);
        layout2.getChildren().addAll(label2, ok);
        layout2.setAlignment(Pos.CENTER);
        //set the scene
        Scene alert2 = new Scene(layout2);
        */

        //create a dialog box confirming the completion of the request
        confirm.setOnAction(e -> {
            answer = true;
            window.close();
        });
        cancel.setOnAction(e -> {
            answer = false;
            window.close();

        });

        window.setScene(alert1);
        window.showAndWait();

        return answer;


    }
}