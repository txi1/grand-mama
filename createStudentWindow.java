import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.*;

public class textWindow{

        static Student student;

    public static Student display(){
        Stage makeClass = new Stage();

        GridPane layout = new GridPane();
                 layout.setPadding(new Insets(10,10,10,10));
                 layout.setVgap(8);
                 layout.setHgap(10);

        //Blocks user interaction with other windows until this one is taken care of
        makeClass.initModality(Modality.APPLICATION_MODAL);
        makeClass.setTitle(title);
        makeClass.setMinWidth(300);
        makeClass.setMinHeight(300);
        

        Label label = new Label();
        label.setText("Enter the first and last name of your student:");
        layout.setConstraints(label, 1, 2);
        
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        layout.setConstraints(label, 2, 1);
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        layout.setConstraints(label, 2, 3);

        Button closeButton = new Button("Cancel");
        closeButton.setOnAction(e -> makeClass.close());
        layout.setConstraints(label, 3, 3);
        Button enterButton = new Button("Confirm");
        enterButton.setOnAction(e -> {
            student = new Student(firstName, lastName);
            makeClass.close();
        });
        layout.setConstraints(label, 3, 2);

        Scene scene = new Scene(layout);
        makeClass.setScene(scene);
        makeClass.showAndWait();

        return student;
    }

    private static boolean isEmpty(String t) {
        boolean test = t.equals(null);
        return test;
    }

    }

