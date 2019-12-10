import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.layout.GridPane;

public class optionsMenu{

        static Student student;
        static boolean displayConfirmation = true;
        static int 

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
        label.setText("Enter the name of your student:");
        layout.setConstraints(label, 1, 1);
        
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        layout.setConstraints(firstName, 1, 2);
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        layout.setConstraints(lastName, 2, 2);

        Button closeButton = new Button("Cancel");
        closeButton.setOnAction(e -> makeClass.close());
        layout.setConstraints(closeButton, 2, 3);
        Button enterButton = new Button("Confirm");
        enterButton.setOnAction(e -> {
            student.setFirstName(firstName.getText().trim());
            System.out.println(firstName.getText());
            
            student.setLastName(lastName.getText().trim());
            System.out.println(lastName.getText());
            makeClass.close();
        });
        layout.setConstraints(enterButton, 1, 3);
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

