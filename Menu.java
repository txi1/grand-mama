import javafx.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Menu extends Application{

    Stage classroom;
    Scene mainMenu, rubric;

    public static void main(String[] args) {
        laungh(args);
    }

    public void start(Stage primaryStage)throws Exception{
        classroom = primaryStage;

        //Button 1
            Label label1 = new Label1("Select Classroom");
            Button button1 = newButton("Go to Classroom");
            button2.setOnAction(e -> classroom.setScene(rubric));
        //Layout 1
            VBox layout1 = new VBox(20);
            layout1.getChildren().addAll(label1, button1);
            scene1 = new Scene(layout1, 200, 200);

        //Button 2
            Button button2 = newButton("Go back");
            button2.setOnAction(e -> classroom.setScene(mainMenu));
        //Layout 2
            StackPane layout2 = new StackPane();
            layout2.getChildren().add(button2);
            scene2 = new Scene(layout2, 400, 300);

        classroom.setScene(mainMenu);
        classroom.setTitle("mainMenu");
        window.show();

    }

}
