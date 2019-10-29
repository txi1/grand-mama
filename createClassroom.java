import javafx.*;
import javafx.scene.*;
import javafx.scene.layout;
import javafx.scene.control;
import javafx.geometry.*;

public class createClassroom{

    public static void display(String title, String message){
        Stage makeClass = new Stage();
        
        //Blocks user interaction with other windows until this one is taken care of
        makeClass.initModality(Modality.APPLICATION_MODAL);
        makeClass.setTitle("Classroom Creation");
        makeClass.setMinWidth(300);

        Label label = new Label();
        label.setText("This is where you can create new classrooms!");
        Button closeButton = new Button("Exit the Window");
        closeButton.setOnAction(e ->);
    }

}