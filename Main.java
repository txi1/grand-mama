import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.beans.Observable;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
    
    Stage mainWindow;
    Scene firstMenu, classMenu, studentMenu, rubricMenu;
    ObservableList<AnchorPane> layouts = FXCollections.observableArrayList();
    ObservableList<Classroom> classroom = FXCollections.observableArrayList();
    ObservableList<Student> students = FXCollections.observableArrayList();
    int count = 0;
    String selectedStudent;
    TableView<Rubric> rubric;
    String filePath = "Classroom Information.txt";
    String selectedClass;
    Scene previousScene;
    TextField expectationInput, lvlrInput, lvl1mInput, lvl1Input, lvl1pInput, lvl2mInput, lvl2Input, lvl2pInput, lvl3mInput, lvl3Input, lvl3pInput, lvl34Input, lvl4mInput, lvl4smInput, lvl4Input, lvl4spInput, lvl4pInput, lvl4ppInput;
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage)throws Exception{
        
        IO io = new IO();
        
        //Lines of code beneath are what set up the window that is shown
        //when the program is run
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
        

        Label classroomLabel = new Label();

        String line;
        int t = 0;
        io.openInputFile(filePath);
        while((line = io.readLine()) != null){
           
            line = getValue(line, "name", "unicornpotatollama");
            if(line.equals("invalid")) continue;
            classroom.add(new Classroom(line, 0));
            classList.getItems().addAll(classroom.get(t));
            t++;
            System.out.println(t);
        }
        io.closeInputFile();

        for(int i = 0; i < classroom.size(); i++){
        io.openInputFile(filePath);
        while((line = io.readLine()) != null){
            line = getValue(line, "studentName", classroom.get(i).getName());
            System.out.println(classroom.get(i).getName());
            System.out.println(line);
            if(line.equals("invalid")) continue;
            String fn = "";
            String ln = "";
            boolean switched = false;
            for(int j = 0; j < line.length(); j++){
                if(!switched){
                    fn += line.charAt(j);
                }
                if(switched){
                    ln += line.charAt(j);
                }
                if(line.charAt(j) == ' ') switched = true; 
            }
            System.out.println(fn);
            System.out.println(ln);
            classroom.get(i).addStudent(new Student(fn, ln));
        }
        io.closeInputFile();
}
  
        
        ListView<Student> listOfStudents = new ListView<>(students);
        
        Label label1 = new Label("Select Classroom");
        menuLayout.setConstraints(label1, 1, 1);

        Button button1 = new Button("Enter this classroom");
        
            
            button1.setDisable(true);

        Button deleteButton = new Button("Delete this classroom");
        menuLayout.setConstraints(deleteButton, 2, 2);
        deleteButton.setOnAction(e -> {
            System.out.println(classList.getValue().getName());
            io.completeDestruction(filePath, classList.getValue().getName());
            classList.getItems().remove(classList.getValue());
            if(classList.getValue() == null){
                deleteButton.setDisable(true);
                button1.setDisable(true);
            }
        });
        
            deleteButton.setDisable(true);

        Button makeClass = new Button("Make a new Classroom");
        menuLayout.setConstraints(makeClass, 1, 3);
        makeClass.setOnAction(e -> {
            String temp = textWindow.display("Class","Classroom Creation");
            if(!isEmpty(temp)){
                System.out.println(temp);
            classroom.setAll(new Classroom(temp, 0));
            io.storeInfo(filePath, temp, "name", temp);
            classList.getItems().addAll(classroom.get(0));
            count++;
            }
        });

        classList.getSelectionModel().selectedItemProperty().addListener(( v, oldValue, newValue) -> {
            if(newValue != null){
            selectedClass = newValue.getName(); 
            button1.setDisable(false);
            deleteButton.setDisable(false);
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
            menuLayout.getChildren().addAll(classList, label1, button1, deleteButton, makeClass, Rubric);
        
            //Layout configuration for the intro menu and adding the elements to the menu
            firstMenu = new Scene(menuLayout, 400, 400);
            
            
            //Starting to create the items for the classroom menu
            Menu manageMenu = new Menu("_Classroom");
            Menu navigateMenu = new Menu("_Navigate");

            //Adding the menu items
            MenuItem createStudent = new MenuItem("Create New Student...");
            createStudent.setOnAction(e -> {
                Student temp = createStudentWindow.display();
                if(!isEmpty(temp.getFirstName()) && !isEmpty(temp.getLastName())){
                    System.out.println(temp.getFullName());
                for(int i = 0; i < classroom.size(); i++){
                    if(selectedClass.equals(classroom.get(i).getName())) {
                        classroom.get(i).addStudent(temp);
                        io.storeInfo(filePath, classroom.get(i).getName(), "studentName", temp.getFullName());
                    }
                }
                }
            });
            //Adding the new assignment button
            manageMenu.getItems().add(createStudent);

            MenuItem createAssignment = new MenuItem("Create New Assignment...");
            createAssignment.setOnAction(e -> {
                String temp = textWindow.display("Class","Create a new Assignment...");
                if(!isEmpty(temp)){
                }
            });
            //Adding the new expecation button
            manageMenu.getItems().add(createAssignment);

            MenuItem createExpectation = new MenuItem("Create New Expectation...");
            createExpectation.setOnAction(e -> {
                Expectation temp = createExpectationWindow.display();
                if(!isEmpty(temp.getDetails()) && !isEmpty(temp.getSection())){
                    for(int i = 0; i < classroom.size(); i++){
                        if(selectedClass.equals(classroom.get(i).getName())) {
                            classroom.get(i).addExpectation(temp);
                            for(int j = 0; j < classroom.get(i).getStudents().size(); j++){
                                io.storeInfo(filePath, classroom.get(i).getName(), classroom.get(i).getStudents().get(j).getFullName() +"expectation", temp.getExpectation());
                            }
                        }
                    }
                }
            });
            manageMenu.getItems().add(createExpectation);
            
            manageMenu.getItems().add(new SeparatorMenuItem());
            manageMenu.getItems().add(new MenuItem("Manage Students..."));
            manageMenu.getItems().add(new MenuItem("Manage Assignments..."));
            manageMenu.getItems().add(new MenuItem("Manage Expectations..."));
            manageMenu.getItems().add(new SeparatorMenuItem());
            manageMenu.getItems().add(new MenuItem("Manage Preferences..."));
            manageMenu.getItems().add(new SeparatorMenuItem());
            
            MenuItem returnMenuButton = new MenuItem("Return to the Main Menu");
            returnMenuButton.setOnAction(e -> {
                mainWindow.setScene(firstMenu);
            });
            manageMenu.getItems().add(returnMenuButton);
            MenuItem exitProgram = new MenuItem("Exit the Program");
            exitProgram.setOnAction(e -> {
                closeProgram();
            });
            manageMenu.getItems().add(exitProgram);

            //Adding the navigate menus
            MenuItem backButton = new MenuItem("Back");
           
            navigateMenu.getItems().add(backButton);
            navigateMenu.getItems().add(new MenuItem("Forward"));
            navigateMenu.getItems().add(new SeparatorMenuItem());
            MenuItem navStudent = new MenuItem("Students");
            
            navigateMenu.getItems().add(navStudent);
            navigateMenu.getItems().add(new MenuItem("Assignments"));
            navigateMenu.getItems().add(new SeparatorMenuItem());
            navigateMenu.getItems().add(new MenuItem("Expectations"));

            //The menu bar
            MenuBar menuBar = new MenuBar();
            menuBar.getMenus().addAll(manageMenu, navigateMenu);
            
            //Layout configuration for the classroom menu and adding the elements to the menu
            BorderPane topLayer = new BorderPane();
            topLayer.setPadding(new Insets(0,10,10,10));
            topLayer.setTop(menuBar);
            
            AnchorPane studentLayout = new AnchorPane();
            studentLayout.setPadding(new Insets(0,10,10,10));
            studentLayout.setTopAnchor(listOfStudents, 0d);
            studentLayout.getChildren().addAll(listOfStudents);
            
            studentMenu = new Scene(studentLayout, 400, 300);
            
            classMenu = new Scene(topLayer, 400, 300);
            
            AnchorPane classLayout = new AnchorPane();
            classLayout.setPadding(new Insets(10,10,10,10));
            classLayout.setTopAnchor(classroomLabel, 30d);
            classLayout.getChildren().addAll(classroomLabel);
            
            topLayer.setCenter(classLayout);

            listOfStudents.setCellFactory(param -> new ListCell<Student>() {
            
                @Override
            protected void updateItem(Student item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null || item.getFullName() == null) {
                setText(null);
            } else {
                    setText(item.getFullName());
                }
            }
        });
            navStudent.setOnAction(e -> {
                for(int i = 0; i < classroom.size(); i++){
                    if(classroom.get(i).getName().equals(selectedClass)) listOfStudents.setItems(classroom.get(i).getStudents());
                }
                previousScene = classMenu;
                topLayer.setCenter(studentLayout);
                backButton.setDisable(false);
                navStudent.setDisable(true);
            });
            
            backButton.setOnAction(e -> {
                topLayer.setCenter(classLayout);
                navStudent.setDisable(false);
                backButton.setDisable(true);
            });
            
            /*The layout type that will be used in order to have the rubric 
            displayed along with other features(Such as sidebars) that allow for
            a complete rubric to be created
            */
            AnchorPane rubricLayout = new AnchorPane();
                rubricLayout.setPadding(new Insets(10,10,10,10));
            //Establishes the scene parameters that allow for the rubricMenu
            //scene to exist
            rubricMenu = new Scene(rubricLayout, 1000, 500);
            
            Button MenuButton = new Button("Return to Main Menu");
            MenuButton.setOnAction(e -> {
                mainWindow.setScene(firstMenu);
            });
            
            //Expecation Column that will show the expectations that student has to meet in the course
                TableColumn<Rubric, String> expectationColumn = new TableColumn<>("Expectation");
                expectationColumn.setMinWidth(100);
                expectationColumn.setCellValueFactory(new PropertyValueFactory<>("expectation")); 
            //R Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> rColumn = new TableColumn<>("R");
                rColumn.setMinWidth(50);
                rColumn.setCellValueFactory(new PropertyValueFactory<>("lvlr"));
            //1- Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> onemColumn = new TableColumn<>("1-");
                onemColumn.setMinWidth(50);
                onemColumn.setCellValueFactory(new PropertyValueFactory<>("lvl1m"));
            //1 Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> oneColumn = new TableColumn<>("1");
                oneColumn.setMinWidth(50);
                oneColumn.setCellValueFactory(new PropertyValueFactory<>("lvl1"));
            //1+ Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> onepColumn = new TableColumn<>("1+");
                onepColumn.setMinWidth(50);
                onepColumn.setCellValueFactory(new PropertyValueFactory<>("lvl1p"));
            //2- Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> twomColumn = new TableColumn<>("2-");
                twomColumn.setMinWidth(50);
                twomColumn.setCellValueFactory(new PropertyValueFactory<>("lvl2m"));
            //2 Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> twoColumn = new TableColumn<>("2");
                twoColumn.setMinWidth(50);
                twoColumn.setCellValueFactory(new PropertyValueFactory<>("lvl2"));
            //2+ Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> twopColumn = new TableColumn<>("2+");
                twopColumn.setMinWidth(50);
                twopColumn.setCellValueFactory(new PropertyValueFactory<>("lvl2p"));
            //3- Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> threemColumn = new TableColumn<>("3-");
                threemColumn.setMinWidth(50);
                threemColumn.setCellValueFactory(new PropertyValueFactory<>("lvl3m"));
            //3 Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> threeColumn = new TableColumn<>("3");
                threeColumn.setMinWidth(50);
                threeColumn.setCellValueFactory(new PropertyValueFactory<>("lvl3"));
            //3+ Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> threepColumn = new TableColumn<>("3+");
                threepColumn.setMinWidth(50);
                threepColumn.setCellValueFactory(new PropertyValueFactory<>("lvl3p"));
            //3+/4- Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> threefourColumn = new TableColumn<>("3+/4-");
                threefourColumn.setMinWidth(50);
                threefourColumn.setCellValueFactory(new PropertyValueFactory<>("lvl34"));
            //4- Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> fourmColumn = new TableColumn<>("4-");
                fourmColumn.setMinWidth(50);
                fourmColumn.setCellValueFactory(new PropertyValueFactory<>("lvl4m"));
            //4-/4 Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> foursmColumn = new TableColumn<>("4-/4");
                foursmColumn.setMinWidth(50);
                foursmColumn.setCellValueFactory(new PropertyValueFactory<>("lvl4sm"));
            //4 Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> fourColumn = new TableColumn<>("4");
                fourColumn.setMinWidth(50);
                fourColumn.setCellValueFactory(new PropertyValueFactory<>("lvl4"));
            //4/4+ Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> fourspColumn = new TableColumn<>("4/4+");
                fourspColumn.setMinWidth(50);
                fourspColumn.setCellValueFactory(new PropertyValueFactory<>("lvl4sp"));
            //4+ Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> fourpColumn = new TableColumn<>("4+");
                fourpColumn.setMinWidth(50);
                fourpColumn.setCellValueFactory(new PropertyValueFactory<>("lvl4p"));
            //4++ Column that will show the grades that student got during the duration of the course
                TableColumn<Rubric, String> fourppColumn = new TableColumn<>("4++");
                fourppColumn.setMinWidth(50);
                fourppColumn.setCellValueFactory(new PropertyValueFactory<>("lvl4pp"));
            //These lines of code are what allow for the table itself to be
            //generated and shown when called
            
            //Following 3 lines of code are used in order to set up the textfield
            //that will be used to add in expectation manually

            //Buttons used to add in or delete the expectations
            Button addButton = new Button("Add");
            addButton.setOnAction(e -> addButtonClicked());
            Button killButton = new Button("Delete");
            killButton.setOnAction(e -> killButtonClicked());
            //The area in the bottom on the rubric that will allow for
            //the manipulation of rows
            HBox hbox = new HBox();
            hbox.setPadding(new Insets(10, 10, 10, 10));
            hbox.setSpacing(10);
            hbox.getChildren().addAll(addButton, killButton);
            
                rubric = new TableView<>();
                rubric.getColumns().addAll(expectationColumn, rColumn, 
                        onemColumn, oneColumn, onepColumn,
                        twomColumn, twoColumn, twopColumn,
                        threemColumn, threeColumn, threepColumn,
                        threefourColumn, fourmColumn, foursmColumn, fourColumn, fourspColumn, fourpColumn, fourppColumn);
            
                        menuLayout.setConstraints(button1, 1, 2);
                        button1.setOnAction(e -> {
                            mainWindow.setScene(classMenu);
                            backButton.setDisable(true);
                            previousScene = firstMenu;
                            classroomLabel.setText(selectedClass);
                            });

            //The crucial line of code that allows the rubric to be displayed
            //when the rubricMenu Scene is selected
            rubricLayout.getChildren().addAll(rubric, hbox, MenuButton);
            //AnchorPane sets the specific locations of each child in the rubric layout
            AnchorPane.setTopAnchor(rubric, 10d);
            AnchorPane.setBottomAnchor(hbox, 10d);
            AnchorPane.setLeftAnchor(hbox, 325d);
            AnchorPane.setBottomAnchor(MenuButton, 20d);
            AnchorPane.setLeftAnchor(MenuButton, 10d);
            //Line below is what makes the table editable
            rubric.setEditable(true);
            //Lines below state which columns can be edited
            expectationColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            expectationColumn.setOnEditCommit(
                    new EventHandler<CellEditEvent<Rubric, String>>(){
                        public void handle(CellEditEvent<Rubric, String> t){
                            io.deleteLine(filePath, selectedClass +selectedStudent +".expectation." +t.getOldValue());
                            io.storeInfo(filePath, selectedClass, selectedStudent+ "expectation", t.getNewValue());
                            ((Rubric) t.getTableView().getItems().get(t.getTablePosition().getRow())).setExpectation(t.getNewValue());
                        }
                    }
            );
            rColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            rColumn.setOnEditCommit(
                    new EventHandler<CellEditEvent<Rubric, String>>(){
                        public void handle(CellEditEvent<Rubric, String> t){
                            io.deleteLine(filePath, selectedClass +"." +selectedStudent +t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getExpectationID()+
                                    "lvlr." +t.getOldValue());
                            io.storeInfo(filePath, selectedClass, selectedStudent +t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getExpectationID()+"lvlr", t.getNewValue());
                            ((Rubric) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                            ).setLvlr(t.getNewValue());
                        }
                    }
            );
            onemColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            oneColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            onepColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            twomColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            twoColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            twopColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            threemColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            threeColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            threepColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            threefourColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            fourmColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            foursmColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            fourColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            fourspColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            fourpColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
            fourppColumn.setCellFactory(TextFieldTableCell.<Rubric>forTableColumn());
                
            killButton.disableProperty().bind(Bindings.isEmpty(rubric.getSelectionModel().getSelectedItems()));
            
            listOfStudents.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    if(listOfStudents.getSelectionModel().getSelectedItem() != null){
                    mainWindow.setScene(rubricMenu);
                    selectedStudent = listOfStudents.getSelectionModel().getSelectedItem().getFullName();
                    System.out.println("Clicked on " + selectedStudent);
                    rubric.setItems(getRubricInfo(selectedStudent));
                }
            }
            });

        //Allows for the first scene to be shown when the program is run
        mainWindow.setScene(firstMenu);
        mainWindow.setTitle("Main Menu");
        mainWindow.show();
    }
    
    //Method that's used in order to add expectations to the rubric
    public void addButtonClicked(){
        IO io = new IO();
        Rubric addColumn = new Rubric();
        Expectation temp = createExpectationWindow.display();
                if(!isEmpty(temp.getDetails()) && !isEmpty(temp.getSection())){
                    for(int i = 0; i < classroom.size(); i++){
                        if(selectedClass.equals(classroom.get(i).getName())) {
                            classroom.get(i).addExpectation(temp);
                            addColumn.setExpectation(temp.getExpectation());
                            rubric.getItems().add(addColumn);
                            for(int j = 0; j < classroom.get(i).getStudents().size(); j++){
                                io.storeInfo(filePath, classroom.get(i).getName(), classroom.get(i).getStudents().get(j).getFullName() +"expectation", temp.getExpectation());
                                
                            }
                        }
                    }
                }
        
    }
    //Method that's used to delete expectations in the rubric
    public void killButtonClicked(){
        IO io = new IO();
        ObservableList<Rubric> expectationSelected, allExpectation;
        allExpectation = rubric.getItems();
        expectationSelected = rubric.getSelectionModel().getSelectedItems();
        io.deleteLine(filePath, selectedClass +"." +selectedStudent +"expectation." +expectationSelected.get(0).getExpectation());
        expectationSelected.forEach(allExpectation::remove);
        
    }

    //Method used in the main method in order to close the program on command
    //when the 'X' button is clicked at the top right corner
    private void closeProgram(){
        Boolean answer = confirmationWindow.display("Close Window?","Are you sure you want to close the program?");
        if(answer)
            mainWindow.close();
    }

    //Tests to see if a string is empty or not
    private boolean isEmpty(String s){
        boolean test;
        if("".equals(s) || s == null){
            test = true;
        }
        else {
            test = false;
        }
        return test;
    }

    //Method that manually adds each item into the Rubric table(Will change later)
    public ObservableList<Rubric> getRubricInfo(String s){
        
        ObservableList<Rubric> rubricInfo = FXCollections.observableArrayList();
       
        //Row 1 in the rubric
        IO io = new IO();
        String expectation = "";
        String lvlr = "";
        io.openInputFile(filePath);
        try{
            while((line = io.readLine()) != null){
                line = getValue(line, s+"expectation", "unicornpotatollama");
                if(line.equals("invalid")) continue;
                String ID = "";
                for(int i = 0; i <= line.length(); i++){
                    if(line.charAt(i) != ':') ID += line.charAt(i);
                            else break;
                }
                rubricInfo.addAll(new Rubric(line, "",
                    "", "", "", 
                    "", "", "", 
                    "", "", "", 
                    "", "", "", "", "", "", "", ID));
            }
            io.closeInputFile();
            
        }catch(IOException e){
            System.out.println("Error");
        }
        
        
        try{
            for(int i = 0; i < rubricInfo.size(); i++){
                io.openInputFile(filePath);
            while((line = io.readLine()) != null){
                 line = getValue(line, s+ rubricInfo.get(i).getExpectationID()+"lvlr", "unicornpotatollama");
                 if(line.equals("invalid")) continue;
                 rubricInfo.get(i).setLvlr(line);
            } 
            io.closeInputFile();
            }
        }catch(IOException e){
            System.out.println("Error");
        }

        
        return rubricInfo;
    }

    public String getValue(String l, String info, String classroom){
        String trimmedLine = l.trim();
        int counter = 0;
        String infoName = "";
        String className = "";
        
        for(int i = 0; i < trimmedLine.length(); i++){
            if(trimmedLine.charAt(i) != '.'){
                className += trimmedLine.charAt(i);
                counter ++;
            }
            else break;
        }
        counter ++;
        if(className.equals(classroom) || classroom.equals("unicornpotatollama")){
        for(int i = counter; i < trimmedLine.length(); i++){
            if(trimmedLine.charAt(i) != '.'){
            infoName += trimmedLine.charAt(counter);
            counter++;
            }else break;
        }
        counter ++;
        if(infoName.equals(info)){
            String value = "";
            for(int i = counter; i < trimmedLine.length(); i++){
            value += trimmedLine.charAt(counter);
            counter++;
            }
            return value;
        }else return "invalid";
    }else return "invalid";
    }
    
}