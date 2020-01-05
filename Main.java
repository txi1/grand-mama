import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.beans.Observable;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.TablePosition;
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
    ObservableList<TableColumn<Row, String>> rubricCols;
    int count = 0;
    String selectedStudent;
    Assignment selectedAssignment;
    TableView<Row> rubric;
    String filePath = "Classroom Information.txt";
    Classroom selectedClass;
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

        //initializing each classroom
        while((line = io.readLine()) != null){
           
            line = getValue(line, "name", "unicornpotatollama");
            if(line.equals("invalid")) continue;
            classroom.add(new Classroom(line, 0));
            classList.getItems().addAll(classroom.get(t));
            t++;
        }
        io.closeInputFile();

        //initializing each student within those classrooms
        for(int i = 0; i < classroom.size(); i++){
        io.openInputFile(filePath);
        while((line = io.readLine()) != null){
            line = getValue(line, "studentName", classroom.get(i).getName());
            if(line.equals("invalid")) continue;
            String fn = "";
            String ln = "";
            boolean switched = false;
            for(int j = 0; j < line.length(); j++){
                if(line.charAt(j) == ' '){
                    switched = true;
                    j++;
                } 
                if(!switched){
                    fn += line.charAt(j);
                }
                if(switched){
                    ln += line.charAt(j);
                }
            }
            classroom.get(i).addStudent(new Student(fn, ln));
        }
        io.closeInputFile();
}
        //initializing each expectation within those classrooms
         for(int i = 0; i < classroom.size(); i++){
        io.openInputFile(filePath);
        while((line = io.readLine()) != null){
            line = getValue(line, "expectation", classroom.get(i).getName());
            if(line.equals("invalid")) continue;
            String fn = "";
            String ln = "";
            boolean switched = false;
            for(int j = 0; j < line.length(); j++){
                if(line.charAt(j) == ':'){
                    switched = true;
                    j+=2;
                } 
                if(!switched){
                    fn += line.charAt(j);
                }
                if(switched){
                    ln += line.charAt(j);
                }
            }
            classroom.get(i).addExpectation(new Expectation(fn, ln));
        }
        io.closeInputFile();
}

//initializing each assignment within those classrooms
for(int i = 0; i < classroom.size(); i++){
    io.openInputFile(filePath);
    while((line = io.readLine()) != null){
        line = getValue(line, "assignment", classroom.get(i).getName());
        if(line.equals("invalid")) continue;
        classroom.get(i).addAssignment(line, FXCollections.observableArrayList());
    }
    io.closeInputFile();
}

//initializing each expectation within those assignments within those classrooms
for(int i = 0; i < classroom.size(); i++){
for(int j = 0; j < classroom.get(i).getExpectations().size();j++){
    io.openInputFile(filePath);
    while((line = io.readLine()) != null){
        line = getValue(line, classroom.get(i).getExpectations().get(j).getSection() +"assignment", classroom.get(i).getName());
        if(line.equals("invalid")) continue;
        for(int k = 0; k < classroom.get(i).getAssignments().size(); k++){
        if(line.equals(classroom.get(i).getAssignments().get(k).getName())){
        classroom.get(i).getAssignments().get(k).addExpectation(new Expectation(classroom.get(i).getExpectations().get(j).getSection(), classroom.get(i).getExpectations().get(j).getDetails()));
        }
    }
    }
    io.closeInputFile();
    }
    
}
  
        
        ListView<Student> listOfStudents = new ListView<>(students);
        
        Label label1 = new Label("Select Classroom");
        menuLayout.setConstraints(label1, 1, 1);

        Button button1 = new Button("Enter this classroom");
        
            
            button1.setDisable(true);

        Button deleteButton = new Button("Delete this classroom");
        menuLayout.setConstraints(deleteButton, 2, 2);
        deleteButton.setOnAction(e -> {
            io.completeDestruction(filePath, classList.getValue().getName());
            for(int i = 0; i < classroom.size(); i++){
                if(classList.getValue() == classroom.get(i)) classroom.remove(classroom.get(i));
            }
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
            classroom.add(new Classroom(temp, 0));
            io.storeInfo(filePath, temp, "name", temp);
            classList.getItems().setAll(classroom);
            count++;
            }
        });

        classList.getSelectionModel().selectedItemProperty().addListener(( v, oldValue, newValue) -> {
            if(newValue != null){
            selectedClass = newValue; 
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
                for(int i = 0; i < classroom.size(); i++){
                    if(selectedClass.equals(classroom.get(i))) {
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
                for(int i = 0; i < classroom.size(); i++){
                if(classroom.get(i).equals(selectedClass)){
                    createAssignmentWindow c = new createAssignmentWindow();
                    Assignment temp = c.display(classroom.get(i));
                if(!isEmpty(temp.getName())){
                    classroom.get(i).addAssignment(temp.getName(), temp.getExpectations());
                    io.storeInfo(filePath, classroom.get(i).getName(), "assignment", temp.getName());
                    for(int j = 0; j < temp.getExpectations().size(); j++){
                    io.storeInfo(filePath,classroom.get(i).getName(), temp.getExpectations().get(j).getSection() +"assignment", temp.getName());
                            }
                }
            }
            }});
            //Adding the new expecation button
            manageMenu.getItems().add(createAssignment);

            MenuItem createExpectation = new MenuItem("Create New Expectation...");
            createExpectation.setOnAction(e -> {
                Expectation temp = createExpectationWindow.display();
                if(!isEmpty(temp.getDetails()) && !isEmpty(temp.getSection())){
                    for(int i = 0; i < classroom.size(); i++){
                        if(selectedClass.equals(classroom.get(i))) {
                            classroom.get(i).addExpectation(temp);
                                io.storeInfo(filePath, classroom.get(i).getName(), "expectation", temp.getExpectation());
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
            MenuItem navAssignments = new MenuItem("Assignments");
            navigateMenu.getItems().add(navAssignments);
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
                    if(classroom.get(i).equals(selectedClass)) listOfStudents.setItems(classroom.get(i).getStudents());
                }
                previousScene = classMenu;
                topLayer.setCenter(studentLayout);
                backButton.setDisable(false);
                navStudent.setDisable(true);
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

                        menuLayout.setConstraints(button1, 1, 2);
                        button1.setOnAction(e -> {
                            mainWindow.setScene(classMenu);
                            topLayer.setCenter(classLayout);
                            backButton.setDisable(true);
                            previousScene = firstMenu;
                            classroomLabel.setText(selectedClass.getName());
                            });

                            rubric = new TableView<>();   

                            ChoiceBox<String> assignmentList = new ChoiceBox();
            //The crucial line of code that allows the rubric to be displayed
            //when the rubricMenu Scene is selected
            rubricLayout.getChildren().addAll(rubric, hbox, MenuButton, assignmentList);
            //AnchorPane sets the specific locations of each child in the rubric layout
            AnchorPane.setTopAnchor(rubric, 10d);
            AnchorPane.setBottomAnchor(hbox, 10d);
            AnchorPane.setLeftAnchor(hbox, 325d);
            AnchorPane.setBottomAnchor(MenuButton, 20d);
            AnchorPane.setLeftAnchor(MenuButton, 10d);
            //Line below is what makes the table editable
            rubric.setEditable(true);
            //Lines below state which columns can be edited

            ChoiceBox<String> gradeList = new ChoiceBox();
            gradeList.getItems().addAll("R", "1-", "1", "1+", "2-", "2", "2+", "3-", "3", "3+", "3+/4-", "4-", "4-/4",
                    "4", "4/4+", "4+", "4++");

             
            rubric.getSelectionModel().setCellSelectionEnabled(true);
                    TableColumn<Row, String> studentCol = new TableColumn<>("Expectations");
                    studentCol.setCellValueFactory(cellData -> cellData.getValue().firstColProperty());
                    rubric.getColumns().add(studentCol);
                    rubricCols = FXCollections.observableArrayList();
                    for (int i = 0 ; i < gradeList.getItems().size(); i++) {
                    TableColumn<Row, String> col = new TableColumn<>(gradeList.getItems().get(i));
                    rubricCols.add(col);
                    final int colIndex = i ;
                    col.setCellValueFactory(cellData -> cellData.getValue().colProperty(colIndex));
                    rubric.getColumns().add(rubricCols.get(i));
                    }

                    rubric.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            ObservableList<String> assignmentNames = FXCollections.observableArrayList();
                            String selectedRow = rubric.getFocusModel().getFocusedItem().getFirstCol();
                            for(int i = 0; i < selectedClass.getAssignments().size(); i++){
                                Assignment currAssignment = selectedClass.getAssignments().get(i);
                                for(int j = 0; j < currAssignment.getExpectations().size();j++){
                                    if(currAssignment.getExpectations().get(j).getExpectation().equals(selectedRow)){
                                        assignmentNames.add(currAssignment.getName());
                                    }
                                }
                            }
                            assignmentList.setItems(assignmentNames);
                        }
                    });


            killButton.disableProperty().bind(Bindings.isEmpty(rubric.getSelectionModel().getSelectedItems()));
            
           
            AnchorPane.setBottomAnchor(assignmentList, 20d);
            AnchorPane.setLeftAnchor(assignmentList, 200d);
                    System.out.println(rubricCols.size());
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
            
            ListView<Assignment> listOfAssignments = new ListView<>();
            listOfAssignments.setCellFactory(param -> new ListCell<Assignment>() {
            
                @Override
            protected void updateItem(Assignment item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null || item.getName() == null) {
                setText(null);
            } else {
                    setText(item.getName());
                }
            }
        });
           
           AnchorPane assignmentLayout = new AnchorPane();
           assignmentLayout.setPadding(new Insets(0,10,10,10));
           assignmentLayout.setTopAnchor(listOfAssignments, 0d);
           assignmentLayout.getChildren().add(listOfAssignments);
         
            navAssignments.setOnAction(e -> {
            topLayer.setCenter(assignmentLayout);
            for(int i = 0; i < classroom.size(); i++){
                    if(classroom.get(i).equals(selectedClass)) listOfAssignments.setItems(classroom.get(i).getAssignments());
                }
            navAssignments.setDisable(true);
            backButton.setDisable(false);
            });

            AnchorPane gradingLayout = new AnchorPane();
            gradingLayout.setPadding(new Insets(0,10,10,10));

            Button setMarkButton = new Button("Set this mark");
            
            
            gradingLayout.setTopAnchor(setMarkButton, 70d);
            gradingLayout.setRightAnchor(setMarkButton, 10d);
            
            gradingLayout.setTopAnchor(gradeList, 40d);
            gradingLayout.setRightAnchor(gradeList, 10d);
            
            
            backButton.setOnAction(e -> {
                topLayer.setCenter(classLayout);
                navStudent.setDisable(false);
                navAssignments.setDisable(false);
                backButton.setDisable(true);
            });
            
            listOfAssignments.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    if(listOfAssignments.getSelectionModel().getSelectedItem() != null){
                    topLayer.setCenter(gradingLayout);
                    selectedAssignment = listOfAssignments.getSelectionModel().getSelectedItem();
                    System.out.println("Clicked on " + selectedAssignment.getName());
                    
                    int numExpectations = selectedAssignment.getExpectations().size();
                    System.out.println(numExpectations);
                    TableView<Row> table = new TableView<>();
                    table.getSelectionModel().setCellSelectionEnabled(true);
                    TableColumn<Row, String> studentCol = new TableColumn<>("Students");
                    studentCol.setCellValueFactory(cellData -> cellData.getValue().firstColProperty());
                    table.getColumns().add(studentCol);
                    ObservableList<TableColumn<Row, String>> cols = FXCollections.observableArrayList();
                    for (int i = 0 ; i < numExpectations ; i++) {
                    TableColumn<Row, String> col = new TableColumn<>(selectedAssignment.getExpectations().get(i).getSection());
                    cols.add(col);
                    final int colIndex = i ;
                    col.setCellValueFactory(cellData -> cellData.getValue().colProperty(colIndex));
                    table.getColumns().add(cols.get(i));
                    
                    }
                    //table.setEditable(true);

                    ObservableList<Row> rows = FXCollections.observableArrayList();
                    for(int i = 0; i < selectedClass.getStudents().size(); i++){
                        rows.add(new Row(selectedClass.getStudents().get(i).getFullName(), numExpectations));
}

    for(int i = 0; i < rows.size(); i++){
        String currentStudent = rows.get(i).getFirstCol();
        try{
        for(int j = 0; j < cols.size(); j++){
            io.openInputFile(filePath);
            String expName = cols.get(j).getText();
            String line = "";
            while((line = io.readLine()) != null){
            String mark = getValue(line, selectedAssignment.getName() +currentStudent +expName, selectedClass.getName());
            if(mark == "invalid") continue;
                rows.get(i).setCol(mark, j);
        }
        io.closeInputFile();
    }
    }catch(IOException e){

    }
    }

table.setItems(rows);
    gradingLayout.setTopAnchor(table, 0d);
    gradingLayout.getChildren().setAll(table,gradeList,setMarkButton);
    
    
    setMarkButton.setOnAction(e -> {
        TablePosition cell = table.getFocusModel().getFocusedCell();
        String val = gradeList.getValue();
        int cellColumn = cell.getColumn();
        Row selectedRow =  table.getItems().get(cell.getRow());
        if(cell.getColumn() > 0 && val != null){
            selectedRow.setCol(val, cellColumn-1);
            io.storeInfo(filePath, selectedClass.getName(), selectedAssignment.getName() +selectedRow.getFirstCol() + selectedAssignment.getExpectations().get(cellColumn-1).getSection(), val);
        }
    });
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
        Row addColumn = new Row("", 17);
        Expectation temp = createExpectationWindow.display();
                if(!isEmpty(temp.getDetails()) && !isEmpty(temp.getSection())){
                    for(int i = 0; i < classroom.size(); i++){
                        if(selectedClass.equals(classroom.get(i))) {
                            classroom.get(i).addExpectation(temp);
                            addColumn.setFirstCol(temp.getExpectation());
                            rubric.getItems().add(addColumn);
                            io.storeInfo(filePath, classroom.get(i).getName(), "expectation", temp.getExpectation());
                        }
                    }
                }
        
    }
    //Method that's used to delete expectations in the rubric
    public void killButtonClicked(){
        IO io = new IO();
        ObservableList<Row> expectationSelected, allExpectation;
        allExpectation = rubric.getItems();
        expectationSelected = rubric.getSelectionModel().getSelectedItems();
        io.deleteLine(filePath, selectedClass.getName() +".expectation." +expectationSelected.get(0).getFirstCol());
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
    public ObservableList<Row> getRubricInfo(String s){
        
        ObservableList<Row> rubricInfo = FXCollections.observableArrayList();
        int colSize = rubricCols.size();
        //Row 1 in the rubric
        IO io = new IO();
        String line = "";
        io.openInputFile(filePath);
        try{
            while((line = io.readLine()) != null){
                line = getValue(line, "expectation", selectedClass.getName());
                if(line.equals("invalid")) continue;
                String ID = "";
                for(int i = 0; i <= line.length(); i++){
                    if(line.charAt(i) != ':') ID += line.charAt(i);
                            else break;
                }
                rubricInfo.addAll(new Row(line, colSize, ID));
            }
            io.closeInputFile();
            
        }catch(IOException e){
            System.out.println("Error");
        }
        for(int i = 0; i < rubricInfo.size(); i++){
            String currentExp = rubricInfo.get(i).getFirstCol();
            try{
            for(int j = 0; j < colSize; j++){
                io.openInputFile(filePath);
                String gradeName = rubricCols.get(j).getText();
                while((line = io.readLine()) != null){
                String mark = getValue(line, selectedStudent +currentExp +gradeName, selectedClass.getName());
                if(mark == "invalid") continue;
                    rubricInfo.get(i).setCol(mark, j);
            }
            io.closeInputFile();
        }
        }catch(IOException e){
    
        }
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