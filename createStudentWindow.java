import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.layout.GridPane;

public class createStudentWindow{

        static Student student;

    public static Student display(){
        Stage makeClass = new Stage();
        student = new Student(null,null);
        
        GridPane layout = new GridPane();
                 layout.setPadding(new Insets(10,10,10,10));
                 layout.setVgap(8);
                 layout.setHgap(10);

        //Blocks user interaction with other windows until this one is taken care of
        makeClass.initModality(Modality.APPLICATION_MODAL);
        makeClass.setTitle("Create a new Student");
        makeClass.setMinWidth(300);
        makeClass.setMinHeight(300);
        

        Label label = new Label();
        label.setText("Enter the first and last name of your student:");
        layout.setConstraints(label, 2, 1);
        
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        layout.setConstraints(firstName, 1, 2);
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        layout.setConstraints(lastName, 3, 2);

        Button closeButton = new Button("Cancel");
        closeButton.setOnAction(e -> makeClass.close());
        layout.setConstraints(closeButton, 3, 3);
        Button enterButton = new Button("Confirm");
        enterButton.setOnAction(e -> {
            student.setFirstName(firstName.getText());
            System.out.println(firstName.getText());
            
            student.setLastName(lastName.getText());
            System.out.println(lastName.getText());
            makeClass.close();
        });
        layout.setConstraints(enterButton, 2, 3);
        layout.getChildren().setAll(label, firstName, lastName, closeButton, enterButton);
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

