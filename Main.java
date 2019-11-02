import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{

    Stage mainWindow;
    Scene firstMenu, classMenu, studentMenu, rubricMenu;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage)throws Exception{
        mainWindow = primaryStage;

            //Setup for the intro menu
            Label label1 = new Label("Select Classroom");
            Button button1 = new Button("Enter this classroom");
            button1.setOnAction(e -> mainWindow.setScene(classMenu));
        
            //Layout configuration for the intro menu and adding the elements to the menu
            VBox menuLayout = new VBox(20);
            menuLayout.getChildren().addAll(label1, button1);
            firstMenu = new Scene(menuLayout, 200, 200);

            //Setup for classroom menu
            Button button2 = new Button("Go back to the main menu");
            button2.setOnAction(e -> mainWindow.setScene(firstMenu));
            
            //Layout configuration for the intro menu and adding the elements to the menu
            StackPane classLayout = new StackPane();
            classLayout.getChildren().add(button2);
            classMenu = new Scene(classLayout, 400, 300);

        //Button that will pop up the classroom creation window
        Button makeClass = new Button("Make a new Classroom");
        makeClass.setOnAction(e -> createClassroom.display("Class","Classroom Creation"));
        
        menuLayout.getChildren().addAll(makeClass);

        mainWindow.setScene(firstMenu);
        mainWindow.setTitle("mainMenu");
        mainWindow.show();

    }

    

}
