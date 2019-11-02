import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.*;

public class createClassroom{

    public static void display(String title, String message){
        Stage makeClass = new Stage();
        
        //Blocks user interaction with other windows until this one is taken care of
        makeClass.initModality(Modality.APPLICATION_MODAL);
        makeClass.setTitle(title);
        makeClass.setMinWidth(300);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Exit the Window");
        closeButton.setOnAction(e -> makeClass.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        makeClass.setScene(scene);
        makeClass.showAndWait();
    }

}