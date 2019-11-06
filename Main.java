import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{

    Stage mainWindow;
    Scene firstMenu, classMenu, studentMenu, rubricMenu;
    Classroom[] classroom = new Classroom();
    int count = 0;


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
            //GridPane code that will setup the Choicebox
            //in order to choose which classroom to enter
                GridPane menuLayout = new GridPane();
                menuLayout.setPadding(new Insets(10,10,10,10));
                menuLayout.setVgap(8);
                menuLayout.setHgap(10);
                
                ChoiceBox<Classroom> classList = new ChoiceBox<>();
                menuLayout.setConstraints(classList, 1, 0);

                Label label1 = new Label("Select Classroom");
                menuLayout.setConstraints(label1, 1, 1);

            //Individual button that allows for the user to enter the classroom
                Button button1 = new Button("Enter this classroom");
                menuLayout.setConstraints(button1, 1, 2);
                button1.setOnAction(e -> {
                    mainWindow.setScene(classMenu);
                    
                });

            //Button that will open the popup needed in order to make a classroom
            Button makeClass = new Button("Make a new Classroom");
            menuLayout.setConstraints(makeClass, 1, 3);
            makeClass.setOnAction(e -> {
                String temp = createClassroom.display("Class","Classroom Creation");
                if(!isEmpty(temp)){
                classroom[count] = new Classroom("hi", 0);
                classList.getItems().addAll(classroom[count]);
                count++;
                }
                
            });
            menuLayout.getChildren().addAll(classList, label1, button1, makeClass);
        
            //Layout configuration for the intro menu and adding the elements to the menu
            firstMenu = new Scene(menuLayout, 200, 200);

            //Setup for classroom menu
            Button button2 = new Button("Go back to the main menu");
            button2.setOnAction(e -> mainWindow.setScene(firstMenu));
            
            //Layout configuration for the intro menu and adding the elements to the menu
            StackPane classLayout = new StackPane();
            classLayout.getChildren().add(button2);
            classMenu = new Scene(classLayout, 400, 300);

        //Allows for the first scene to be shown when the program is run
        mainWindow.setScene(firstMenu);
        mainWindow.setTitle("mainMenu");
        mainWindow.show();

    }

    //Method used in the main method in order to close the program on command
    private void closeProgram(){
        Boolean answer = confirmationWindow.display("Close Window?","Are you sure you want to close the program?");
        if(answer);

    }

    //Tests to see if a string is empty or not
    private boolean isEmpty(String s){
        boolean test = "".equals(s);
        return test;
    }
}
