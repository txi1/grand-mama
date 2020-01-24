import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.layout.GridPane;

public class createExpectationWindow{

        static Expectation expectation;

    public static Expectation display(){
        Stage makeClass = new Stage();
        expectation = new Expectation(null,null);
        
        GridPane layout = new GridPane();
                 layout.setPadding(new Insets(10,10,10,10));
                 layout.setVgap(8);
                 layout.setHgap(10);

        //Blocks user interaction with other windows until this one is taken care of
        makeClass.initModality(Modality.APPLICATION_MODAL);
        makeClass.setTitle("Create a new Expectation");
        makeClass.setMinWidth(300);
        makeClass.setMinHeight(300);
        
        //A label is created to prompt the user to enter the expectation section
        Label sectionLabel = new Label();
        sectionLabel.setText("Enter the Expectation's Section:");
        layout.setConstraints(sectionLabel, 1, 1);
        TextField expectationSection = new TextField();
        expectationSection.setPromptText("A1");
        layout.setConstraints(expectationSection, 2, 1);

        //The user has to also enter the details of the expectation within this text field
        Label detailLabel = new Label();
        detailLabel.setText("Enter the Expectation's detail:");
        layout.setConstraints(detailLabel, 1, 2);
        TextField expectationDetail = new TextField();
        expectationDetail.setPromptText("Expectation Detail");
        layout.setConstraints(expectationDetail, 2, 2);

        //Button that will allow the user to either close the window or confirm their settings
        Button closeButton = new Button("Cancel");
        closeButton.setOnAction(e -> makeClass.close());
        layout.setConstraints(closeButton, 1, 3);
        Button enterButton = new Button("Confirm");
        enterButton.setOnAction(e -> {
            expectation.setSection(expectationSection.getText().trim());
            System.out.println(expectationSection.getText());
            
            expectation.setDetail(expectationDetail.getText().trim());
            System.out.println(expectationDetail.getText());
            makeClass.close();
        });
        layout.setConstraints(enterButton, 2, 3);
        layout.getChildren().setAll(sectionLabel, detailLabel, expectationSection, expectationDetail, closeButton, enterButton);
        Scene scene = new Scene(layout);
        makeClass.setScene(scene);
        makeClass.showAndWait();

        return expectation;
    }

    private static boolean isEmpty(String t) {
        boolean test = t.equals(null);
        return test;
    }

    }

