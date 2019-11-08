import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{

    Stage mainWindow;
    Scene firstMenu, classMenu, studentMenu, rubricMenu;
    ObservableList<Classroom> classroom = FXCollections.observableArrayList();
    int count = 0;
    TableView<Rubric> rubric;

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
                
        ChoiceBox<Classroom> classList = new ChoiceBox<>();
        menuLayout.setConstraints(classList, 1, 0);

        Label label1 = new Label("Select Classroom");
        menuLayout.setConstraints(label1, 1, 1);

        Button button1 = new Button("Enter this classroom");
        menuLayout.setConstraints(button1, 1, 2);
        button1.setOnAction(e -> {
            mainWindow.setScene(classMenu);
            });

        Button makeClass = new Button("Make a new Classroom");
        menuLayout.setConstraints(makeClass, 1, 3);
        makeClass.setOnAction(e -> {
            String temp = createClassroom.display("Class","Classroom Creation");
            if(!isEmpty(temp)){
            classroom.setAll(new Classroom(temp, 0));
            classList.getItems().addAll(classroom.get(0));
            count++;
            }
        });

            
            //Button in the mainMenu that allows for direct access to the rubric
            //(Can be changed and placed in a different scene later)
            Button Rubric = new Button("Go to Rubric");
            menuLayout.setConstraints(Rubric, 1, 5);
            Rubric.setOnAction(e -> {
                mainWindow.setScene(rubricMenu);
            });
            
            //Adding all the elements to the menu
            menuLayout.getChildren().addAll(classList, label1, button1, makeClass, Rubric);
        
            //Layout configuration for the intro menu and adding the elements to the menu
            firstMenu = new Scene(menuLayout, 200, 200);
            
            
            //Starting to create the items for the classroom menu
            Menu manageMenu = new Menu("_Classroom");
            Menu navigateMenu = new Menu("_Navigate");

            //Adding the menu items
            manageMenu.getItems().add(new MenuItem("Create New Student..."));
            manageMenu.getItems().add(new MenuItem("Create New Assignment..."));
            manageMenu.getItems().add(new MenuItem("Create New Expectation..."));
            manageMenu.getItems().add(new SeparatorMenuItem());
            manageMenu.getItems().add(new MenuItem("Manage Students..."));
            manageMenu.getItems().add(new MenuItem("Manage Assignments..."));
            manageMenu.getItems().add(new MenuItem("Manage Expectations..."));
            manageMenu.getItems().add(new SeparatorMenuItem());
            manageMenu.getItems().add(new MenuItem("Manage Preferences..."));
            manageMenu.getItems().add(new SeparatorMenuItem());
            
            java.awt.MenuItem returnMenuButton = new MenuItem("Return to the Main Menu");
            returnMenuButton.setOnAction(e -> mainWindow.setScene(firstMenu));
            manageMenu.getItems().add(returnMenuButton);
            manageMenu.getItems().add(new MenuItem("Exit the Program"));

            //Adding the navigate menus
            navigateMenu.getItems().add(new MenuItem("Back"));
            navigateMenu.getItems().add(new MenuItem("Forward"));
            navigateMenu.getItems().add(new SeparatorMenuItem());
            navigateMenu.getItems().add(new MenuItem("Students"));
            navigateMenu.getItems().add(new MenuItem("Assignments"));
            navigateMenu.getItems().add(new SeparatorMenuItem());
            navigateMenu.getItems().add(new MenuItem("Expectations"));

            //The menu bar
            MenuBar menuBar = new MenuBar();
            menuBar.getMenus().addAll(manageMenu, navigateMenu);

            //Layout configuration for the classroom menu and adding the elements to the menu
            BorderPane classLayout = new BorderPane();
            classLayout.setTop(menuBar);
            
            classMenu = new Scene(classLayout, 400, 300);
            
            
            /*The layout type that will be used in order to have the rubric 
            displayed along with other features(Such as sidebars) that allow for
            a complete rubric to be created
            */
            GridPane rubricLayout = new GridPane();
               rubricLayout.setPadding(new Insets(10,10,10,10));
               rubricLayout.setVgap(8);
               rubricLayout.setHgap(10);
            //Establishes the scene parameters that allow for the rubricMenu
            //scene to exist
            rubricMenu = new Scene(rubricLayout, 500, 500);
            
            //Expecation Column that will show the expectations that student has to meet in the course
                TableColumn<Rubric, String> expectationColumn = new TableColumn<>("Expectation");
                expectationColumn.setMinWidth(200);
                expectationColumn.setCellValueFactory(new PropertyValueFactory<>("expectation"));
            //Grade Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, Double> percentColumn = new TableColumn<>("Grade");
                percentColumn.setMinWidth(100);
                percentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
            //These lines of code are what allow for the table itself to be
            //generated and shown when called
                rubric = new TableView<>();
                rubric.setItems(getRubricInfo());
                rubric.getColumns().addAll(expectationColumn, percentColumn);
            
            //The crucial line of code that allows the rubric to be displayed
            //when the rubricMenu Scene is selected
            rubricLayout.getChildren().addAll(rubric);
                

        //Allows for the first scene to be shown when the program is run
        mainWindow.setScene(firstMenu);
        mainWindow.setTitle("Main Menu");
        mainWindow.show();

    }

    //Method used in the main method in order to close the program on command
    private void closeProgram(){
        Boolean answer = confirmationWindow.display("Close Window?","Are you sure you want to close the program?");
        if(answer)
            mainWindow.close();
    }

    //Tests to see if a string is empty or not
    private boolean isEmpty(String s){
        boolean test = "".equals(s);
        return test;
    }

    //Method that manually adds each item into the Rubric table(Will change later)
    public ObservableList<Rubric> getRubricInfo(){
        ObservableList<Rubric> rubricInfo = FXCollections.observableArrayList();
        rubricInfo.add(new Rubric("Test 1", 66.00));
        rubricInfo.add(new Rubric("Test 2", 89.00));
        rubricInfo.add(new Rubric("Quiz 1", 98.00));
        rubricInfo.add(new Rubric("Presentation 1", 90.00));
        return rubricInfo;
    }

}
