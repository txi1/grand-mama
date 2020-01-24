import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class confirmationWindow{
    
        static boolean answer;

    public static boolean display(String title, String message){
        Stage confirmationWindow = new Stage();

        //Blocks user interaction with other windows until this one is taken care of
        confirmationWindow.initModality(Modality.APPLICATION_MODAL);
        confirmationWindow.setTitle(title);
        confirmationWindow.setMinWidth(300);
        confirmationWindow.setMinHeight(300);

        Label label = new Label();
        label.setText(message);
        
        Button noButton = new Button("No");
        noButton.setOnAction(e -> {
            answer = false;
            confirmationWindow.close();
        });

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            answer = true;
            confirmationWindow.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, noButton, yesButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        confirmationWindow.setScene(scene);
        confirmationWindow.showAndWait();

        return answer;
    }
}

