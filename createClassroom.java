import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.*;

public class createClassroom{

        static String name;

    public static String display(String title, String message){
        Stage makeClass = new Stage();
        name = null;

        //Blocks user interaction with other windows until this one is taken care of
        makeClass.initModality(Modality.APPLICATION_MODAL);
        makeClass.setTitle(title);
        makeClass.setMinWidth(300);
        makeClass.setMinHeight(300);
        

        Label label = new Label();
        label.setText(message);
        
        
        TextField className = new TextField();
        className.setPromptText("Enter your classroom name here");

        Button closeButton = new Button("Cancel");
        closeButton.setOnAction(e -> makeClass.close());

        Button enterButton = new Button("Confirm");
        enterButton.setOnAction(e -> {
            name = className.getText();
            makeClass.close();

        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, className, enterButton, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        makeClass.setScene(scene);
        makeClass.showAndWait();

        return name;
    }

    private static boolean isEmpty(String t) {
        boolean test = t.equals(null);
        return test;
    }

    }

