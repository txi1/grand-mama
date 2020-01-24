import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.layout.GridPane;

public class createAssignmentWindow{

         Assignment assignment;
         ObservableList<Expectation> expectations;
         ObservableList<CheckBox> checkboxes = FXCollections.observableArrayList();

    public Assignment display(Classroom c){
        Stage makeClass = new Stage();
        assignment = new Assignment(null, FXCollections.observableArrayList(), null);
        
        GridPane layout = new GridPane();
                 layout.setPadding(new Insets(10,10,10,10));
                 layout.setVgap(8);
                 layout.setHgap(10);

        //Blocks user interaction with other windows until this one is taken care of
        makeClass.initModality(Modality.APPLICATION_MODAL);
        makeClass.setTitle("Create a new Assignment");
        makeClass.setMinWidth(300);
        makeClass.setMinHeight(500);
        
        //These lines of code create and set the text field that will be used to enter the name of the expectation
        Label assignmentLabel = new Label();
        assignmentLabel.setText("Enter the Assignment's name");
        layout.setConstraints(assignmentLabel, 1, 1);
        TextField assignmentName = new TextField();
        assignmentName.setPromptText("Test 1");
        layout.setConstraints(assignmentName, 2, 1);

        //These lines of code create and set the text field that will be used to enter the details of the expectation
        Label IDLabel = new Label();
        IDLabel.setText("Enter the Assignment's Identifier");
        layout.setConstraints(IDLabel, 1, 2);
        TextField assignmentID = new TextField();
        assignmentID.setPromptText("T1");
        layout.setConstraints(assignmentID, 2, 2);

        //This code here will generate all the expectations that were created
        //in order to be able to select which expectations the assignment will cover
        expectations = c.getExpectations();
        int column = 0;
        int row = 3;
        for(int i = 0; i < expectations.size(); i++){
            if(i % 6 == 0){
                column++;
                row = 3;
            } 
            checkboxes.add(new CheckBox(expectations.get(i).getSection()));
            layout.setConstraints(checkboxes.get(i), column, row);
            row++;
        }

        //Will close the window incase the user doesn't want to interact with it
        Button closeButton = new Button("Cancel");
        closeButton.setOnAction(e -> makeClass.close());
        layout.setConstraints(closeButton, 1, 8);
        //Will confirm the settings that have been made in order to allow for the user to finalize
        //their decision on the details of the new assignment
        Button enterButton = new Button("Confirm");
        enterButton.setOnAction(e -> {
            handleOptions(checkboxes);
            assignment.setName(assignmentName.getText());
            assignment.setID(assignmentID.getText());
            makeClass.close();
                });
        layout.setConstraints(enterButton, 2, 8);
        layout.getChildren().addAll(checkboxes);
        layout.getChildren().addAll(assignmentLabel, assignmentName, IDLabel, assignmentID, closeButton, enterButton);

        Scene scene = new Scene(layout);
        makeClass.setScene(scene);
        makeClass.showAndWait();

        return assignment;
    }

    private static boolean isEmpty(String t) {
        boolean test = t.equals(null);
        return test;
    }

    private void handleOptions(ObservableList<CheckBox> c){
        for(int i = 0; i < c.size(); i++){
            if(c.get(i).isSelected()){
                assignment.addExpectation(expectations.get(i));
            }
        }
    }

    }

