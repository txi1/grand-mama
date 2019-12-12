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
        assignment = new Assignment(null);
        
        GridPane layout = new GridPane();
                 layout.setPadding(new Insets(10,10,10,10));
                 layout.setVgap(8);
                 layout.setHgap(10);

        //Blocks user interaction with other windows until this one is taken care of
        makeClass.initModality(Modality.APPLICATION_MODAL);
        makeClass.setTitle("Create a new Assignment");
        makeClass.setMinWidth(300);
        makeClass.setMinHeight(900);
        

        Label sectionLabel = new Label();
        sectionLabel.setText("Enter the Assignment's name");
        layout.setConstraints(sectionLabel, 1, 1);
        TextField expectationSection = new TextField();
        expectationSection.setPromptText("Test 1");
        layout.setConstraints(expectationSection, 2, 1);

        expectations = c.getExpectations();
        int column = 0;
        int row = 2;
        for(int i = 0; i < expectations.size(); i++){
            if(i % 6 == 0){
                column++;
                row = 2;
            } 
            checkboxes.add(new CheckBox(expectations.get(i).getSection()));
            System.out.println(expectations.get(i).getSection());
            layout.setConstraints(checkboxes.get(i), column, row);
            row++;
        }


        Button closeButton = new Button("Cancel");
        closeButton.setOnAction(e -> makeClass.close());
        layout.setConstraints(closeButton, 1, 8);
        Button enterButton = new Button("Confirm");
        enterButton.setOnAction(e -> handleOptions(checkboxes));
        layout.setConstraints(enterButton, 2, 8);
        layout.getChildren().addAll(checkboxes);
        layout.getChildren().addAll(sectionLabel, expectationSection, closeButton, enterButton);

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
                System.out.println(assignment.getExpectations().get(i));
            }
        }
    }

    }

