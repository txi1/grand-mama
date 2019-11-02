import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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
        mainWindow.setTitle("GrandMAMA");
        mainWindow.setOnCloseRequest(e -> {
            e.consume();   
            closeProgram();
            });

            //Setup for intro menu
            GridPane menuLayout = new GridPane();
            menuLayout.setPadding(new Insets(10,10,10,10));
            menuLayout.setVgap(8);
            menuLayout.setHgap(10);
            
            Label label1 = new Label("Select Classroom");
            menuLayout.setConstraints(label1, 1, 0);


            Button button1 = new Button("Enter this classroom");
            menuLayout.setConstraints(button1, 1, 1);
            button1.setOnAction(e -> mainWindow.setScene(classMenu));

            Button makeClass = new Button("Make a new Classroom");
            menuLayout.setConstraints(makeClass, 1, 2);
            makeClass.setOnAction(e -> System.out.println(createClassroom.display("Class","Classroom Creation")));
        
            menuLayout.getChildren().addAll(label1, button1, makeClass);

        
            //Layout configuration for the intro menu and adding the elements to the menu
            
            firstMenu = new Scene(menuLayout, 200, 200);

            //Setup for classroom menu
            Button button2 = new Button("Go back to the main menu");
            button2.setOnAction(e -> mainWindow.setScene(firstMenu));
            
            //Layout configuration for the intro menu and adding the elements to the menu
            StackPane classLayout = new StackPane();
            classLayout.getChildren().add(button2);
            classMenu = new Scene(classLayout, 400, 300);

        //Button that will pop up the classroom creation window
        

        mainWindow.setScene(firstMenu);
        mainWindow.setTitle("mainMenu");
        mainWindow.show();



    }

    private void closeProgram(){
        Boolean answer = confirmationWindow.display("Close Window?","Are you sure you want to close the program?");
        if(answer)
            mainWindow.close();
        

    }
}
