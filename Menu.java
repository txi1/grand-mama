import javafx.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu extends Application{

    Stage classroom;
    Scene mainMenu, rubric;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage)throws Exception{
        classroom = primaryStage;

        //Button 1
            Label label1 = new Label("Select Classroom");
            Button button1 = new Button("Go to Classroom");
            button1.setOnAction(e -> classroom.setScene(rubric));
        //Layout 1
            VBox layout1 = new VBox(20);
            layout1.getChildren().addAll(label1, button1);
            Scene scene1 = new Scene(layout1, 200, 200);

        //Button 2
            Button button2 = new Button("Go back");
            button2.setOnAction(e -> classroom.setScene(mainMenu));
        //Layout 2
            StackPane layout2 = new StackPane();
            layout2.getChildren().add(button2);
            Scene scene2 = new Scene(layout2, 400, 300);

        //Button that will pop up the classroom creation window
        Button makeClass = new Button("Make a new Classroom");
        makeClass.setOnAction(e -> createClassroom.display("Class","Classroom Creation"));

        classroom.setScene(mainMenu);
        classroom.setTitle("mainMenu");
        classroom.show();

    }

}
