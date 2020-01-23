import com.sun.javafx.scene.control.skin.LabeledText;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

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
import javafx.geometry.Orientation;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
    
    Stage mainWindow;
    Scene firstMenu, classMenu, studentMenu, rubricMenu, previousScene;
    ObservableList<AnchorPane> layouts = FXCollections.observableArrayList();
    ObservableList<Classroom> classroom = FXCollections.observableArrayList();
    ObservableList<Student> students = FXCollections.observableArrayList();
    ObservableList<TableColumn<Row, String>> rubricCols;
    ObservableList<Assignment> assignmentsSelected = FXCollections.observableArrayList();
    String selectedItem = "";
    int count = 0;
    String selectedStudent;
    Assignment selectedAssignment;
    TableView<Row> rubric;
    String filePath = "Classroom Information.txt";
    Classroom selectedClass;
    TextField expectationInput, lvlrInput, 
            lvl1mInput, lvl1Input, lvl1pInput, 
            lvl2mInput, lvl2Input, lvl2pInput, 
            lvl3mInput, lvl3Input, lvl3pInput, 
            lvl34Input, lvl4mInput, lvl4smInput, lvl4Input, lvl4spInput, lvl4pInput, lvl4ppInput;
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
            //AnchorPane code that will setup the Choicebox
            //in order to choose which classroom to enter
                AnchorPane menuLayout = new AnchorPane();
                menuLayout.setPadding(new Insets(10,10,10,10));
                
        ChoiceBox<Classroom> classList = new ChoiceBox<>();

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
        String temp = "";
        String temp2 = "";
        for(int j = 0; j < line.length(); j++){
            if(line.charAt(j) == ' ') break;
            temp += line.charAt(j);
            temp2 = line.substring(j+2);
        }
        temp2.trim();
        classroom.get(i).addAssignment(temp2, FXCollections.observableArrayList(), temp);
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
        if(line.equals(classroom.get(i).getAssignments().get(k).getFullAssignment())){
        classroom.get(i).getAssignments().get(k).addExpectation(new Expectation(classroom.get(i).getExpectations().get(j).getSection(), classroom.get(i).getExpectations().get(j).getDetails()));
        }
    }
    }
    io.closeInputFile();
    }
    
}
        ListView<Student> listOfStudents = new ListView<>(students);
        listOfStudents.setPrefWidth(500);
        listOfStudents.setPrefHeight(500);

        Label labelTitle = new Label("Select Classroom");
        labelTitle.getStyleClass().add("label-start-menu");
        
        Label labeltrueTitle = new Label("Grand Mama");
        labeltrueTitle.getStyleClass().add("label-title-menu");

        Button enterClassroom = new Button("Enter this classroom");
            
        enterClassroom.setDisable(true);

        Button deleteButton = new Button("Delete this classroom");
        deleteButton.setOnAction(e -> {
            io.completeDestruction(filePath, classList.getValue().getName());
            for(int i = 0; i < classroom.size(); i++){
                if(classList.getValue() == classroom.get(i)) classroom.remove(classroom.get(i));
            }
            classList.getItems().remove(classList.getValue());
            if(classList.getValue() == null){
                deleteButton.setDisable(true);
                enterClassroom.setDisable(true);
            }
        });
        
            deleteButton.setDisable(true);

        Button makeClass = new Button("Make a new Classroom");
        makeClass.setOnAction(e -> {
            String temp = textWindow.display("Class","Classroom Creation");
            if(!isEmpty(temp)){
            classroom.add(new Classroom(temp, 0));
            io.storeInfo(filePath, temp, "name", temp);
            classList.getItems().setAll(classroom);
            count++;
            }
        });

        classList.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if(newValue != null){
            selectedClass = newValue; 
            enterClassroom.setDisable(false);
            deleteButton.setDisable(false);
            }
        });

            menuLayout.setTopAnchor(labeltrueTitle, 10d);
            menuLayout.setRightAnchor(labeltrueTitle, 375d);
            //AnchorPane for setting the "Select Classroom" label
            menuLayout.setTopAnchor(labelTitle, 250d);
            menuLayout.setRightAnchor(labelTitle, 375d);
            //AnchorPane for setting the 'Classroom Selection Dropbox'
            menuLayout.setBottomAnchor(classList, 475d);
            menuLayout.setLeftAnchor(classList, 450d);
            //AnchorPane for setting the "Enter Classroom" button
            menuLayout.setBottomAnchor(enterClassroom, 200d);
            menuLayout.setLeftAnchor(enterClassroom, 400d);
            menuLayout.setRightAnchor(enterClassroom, 400d);
            //AnchorPane for setting the "Make Classroom" button
            menuLayout.setBottomAnchor(makeClass, 165d);
            menuLayout.setLeftAnchor(makeClass, 295d);
            //AnchorPane for setting the "Delete Classroom" button
            menuLayout.setBottomAnchor(deleteButton, 165d);
            menuLayout.setRightAnchor(deleteButton, 300d);

            //Adding all the elements to the menu
            menuLayout.getChildren().addAll(labeltrueTitle, labelTitle, classList, enterClassroom, deleteButton, makeClass);
        
            //Layout configuration for the intro menu and adding the elements to the menu
            firstMenu = new Scene(menuLayout, 1000, 800);
            firstMenu.getStylesheets().add("mainMenuVisual.css");
            
            //Starting to create the items for the classroom menu
            Menu manageMenu = new Menu("_Classroom");
            Menu navigateMenu = new Menu("_Navigate");
            
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

            //The menu bar
            MenuBar menuBar = new MenuBar();
            menuBar.getMenus().addAll(manageMenu, navigateMenu);
            
            //Layout configuration for the classroom menu and adding the elements to the menu
            BorderPane topLayer = new BorderPane();
            topLayer.setPadding(new Insets(10,10,10,10));
            topLayer.setTop(menuBar);
            
            Button deleteStudent = new Button("Delete Student");
            deleteStudent.setOnAction(e -> { 
                ObservableList<Student> studentSelected, allStudents;
                allStudents = listOfStudents.getItems();
                studentSelected = listOfStudents.getSelectionModel().getSelectedItems();
                io.exodiaObliterate(filePath, studentSelected.get(0).getFullName());
                studentSelected.forEach(allStudents::remove);
            });
            
            AnchorPane studentLayout = new AnchorPane();
            studentLayout.setPadding(new Insets(0,10,10,10));
            studentLayout.setTopAnchor(listOfStudents, 5d);
            studentLayout.setLeftAnchor(listOfStudents, 10d);
            studentLayout.setRightAnchor(listOfStudents, 10d);
            studentLayout.setBottomAnchor(deleteStudent, 00d);
            studentLayout.setLeftAnchor(deleteStudent, 10d);
            studentLayout.setRightAnchor(deleteStudent, 10d);
            studentLayout.getChildren().addAll(listOfStudents, deleteStudent);
            
            studentMenu = new Scene(studentLayout, 400, 300);
            
            classMenu = new Scene(topLayer, 1000, 650);
            classMenu.getStylesheets().add("classroomMenuVisual.css");
            
            ListView<Assignment> listOfAssignments = new ListView<>();
            listOfAssignments.setPrefWidth(500);
            listOfAssignments.setPrefHeight(500);
            
            
            Button deleteAssignment = new Button("Delete Assignment");
            deleteAssignment.setOnAction(e -> { 
                ObservableList<Assignment> assignmentSelected, allAssignments;
                allAssignments = listOfAssignments.getItems();
                assignmentSelected = listOfAssignments.getSelectionModel().getSelectedItems();
                io.deleteLine(filePath, selectedClass.getName() + ".assignment." + assignmentSelected.get(0).getName());
                assignmentSelected.forEach(allAssignments::remove);
            });
            
            AnchorPane assignmentLayout = new AnchorPane();
            assignmentLayout.setPadding(new Insets(0,10,10,10));
            assignmentLayout.setTopAnchor(listOfAssignments, 5d);
            assignmentLayout.setLeftAnchor(listOfAssignments, 10d);
            assignmentLayout.setRightAnchor(listOfAssignments, 10d);
            assignmentLayout.setBottomAnchor(deleteAssignment, 0d);
            assignmentLayout.setLeftAnchor(deleteAssignment, 10d);
            assignmentLayout.setRightAnchor(deleteAssignment, 10d);
            assignmentLayout.getChildren().addAll(listOfAssignments, deleteAssignment);
            
            ListView<Expectation> listOfExpectations = new ListView<>();
            listOfExpectations.setCellFactory(param -> new ListCell<Expectation>() {
                @Override
                protected void updateItem(Expectation item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getExpectation() == null) {
                    setText(null);
                } else {
                        setText(item.getExpectation());
                    }
                }
            });
            listOfExpectations.setPrefWidth(500);
            listOfExpectations.setPrefHeight(500);
            AnchorPane expectationLayout = new AnchorPane();
            Label expectationDetails = new Label("Expectation Details");
            expectationDetails.getStyleClass().add("label-expectationDetails");
            expectationDetails.setPrefWidth(400d);
            expectationDetails.setWrapText(true);
            Label expectationAssignments = new Label("Expectation Assignments");
            expectationAssignments.getStyleClass().add("label-expectationAssignments");
            expectationAssignments.setWrapText(true);
            expectationAssignments.setPrefWidth(400d);
            expectationLayout.setPadding(new Insets(0, 10, 10, 10));
            
            listOfExpectations.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2) {
                        Expectation selectedExpectation = listOfExpectations.getSelectionModel().getSelectedItem();
                            if(selectedExpectation != null){
                                expectationDetails.setText(selectedExpectation.getExpectation());
                                expectationLayout.applyCss();
                                expectationLayout.layout();
                                expectationLayout.setTopAnchor(expectationAssignments, 40d+expectationDetails.getHeight());
                                String allAssignments = "Assignments: ";
                                for(int i = 0; i < selectedClass.getAssignments().size();i++){
                                    Assignment currAssignment = selectedClass.getAssignments().get(i);
                                    for(int j = 0; j < currAssignment.getExpectations().size();j++){
                                        if(currAssignment.getExpectations().get(j).getExpectation().equals(selectedExpectation.getExpectation())){
                                            allAssignments += currAssignment.getName() +" ";
                                            break;
                                        }
                                    }
                                }
                                expectationAssignments.setText(allAssignments);
                        }     
                          
                    }
                    
                }
            });


            expectationLayout.setTopAnchor(listOfExpectations, 10d);
            expectationLayout.setTopAnchor(expectationDetails, 10d);
            expectationLayout.setLeftAnchor(expectationDetails, 550d);
            expectationLayout.setLeftAnchor(expectationAssignments, 550d);
            expectationLayout.setTopAnchor(expectationAssignments, 60d);
            expectationLayout.getChildren().addAll( listOfExpectations, expectationAssignments, expectationDetails);
            
            Button createStudent = new Button("Create Student");
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
            Button createAssignment = new Button("Create Assignment");
                createAssignment.setOnAction(e -> {
                    for(int i = 0; i < classroom.size(); i++){
                    if(classroom.get(i).equals(selectedClass)){
                        createAssignmentWindow c = new createAssignmentWindow();
                        Assignment temp = c.display(classroom.get(i));
                    if(!isEmpty(temp.getName())){
                        classroom.get(i).addAssignment(temp.getName(), temp.getExpectations(),temp.getID());
                        io.storeInfo(filePath, classroom.get(i).getName(), "assignment", temp.getFullAssignment());
                        for(int j = 0; j < temp.getExpectations().size(); j++){
                        io.storeInfo(filePath,classroom.get(i).getName(), temp.getExpectations().get(j).getSection() + "assignment", temp.getFullAssignment());
                                }
                    }
                }
                }});
            Button createExpectation = new Button("Create Expectation");
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
            Button navStudent = new Button("Go to Students");
                navStudent.setOnAction(e -> {
                    for(int i = 0; i < classroom.size(); i++){
                        if(classroom.get(i).equals(selectedClass)) listOfStudents.setItems(classroom.get(i).getStudents());
                    }
                    previousScene = classMenu;
                    topLayer.setCenter(studentLayout);
                    backButton.setDisable(false);
                });
            Button navAssignments = new Button("Go to Assignments");
                navAssignments.setOnAction(e -> {
                    for(int i = 0; i < classroom.size(); i++){
                            if(classroom.get(i).equals(selectedClass))listOfAssignments.setItems(classroom.get(i).getAssignments());
                        }
                    previousScene = classMenu;
                    topLayer.setCenter(assignmentLayout);
                    backButton.setDisable(false);
                });
            Button navExpectations = new Button("Go to Expectations");
                navExpectations.setOnAction(e -> {
                    listOfExpectations.setItems(selectedClass.getExpectations());
                    previousScene = classMenu;
                    topLayer.setCenter(expectationLayout);
                    backButton.setDisable(false);
                });
            
            Label classroomLabel = new Label();
            classroomLabel.getStyleClass().add("label-classLabel");
                
            AnchorPane classLayout = new AnchorPane();
            classLayout.setPadding(new Insets(10,10,10,10));
            //
            classLayout.setTopAnchor(classroomLabel, 30d);
            classLayout.setLeftAnchor(classroomLabel, 450d);
            //
            classLayout.setLeftAnchor(createStudent, 100d);
            classLayout.setTopAnchor(createStudent, 100d);
            //
            classLayout.setLeftAnchor(createExpectation, 400d);
            classLayout.setTopAnchor(createExpectation, 100d);
            //
            classLayout.setLeftAnchor(createAssignment, 700d);
            classLayout.setTopAnchor(createAssignment, 100d);
            //
            classLayout.setLeftAnchor(navStudent, 100d);
            classLayout.setBottomAnchor(navStudent, 100d);
            //
            classLayout.setLeftAnchor(navAssignments, 400d);
            classLayout.setBottomAnchor(navAssignments, 100d);
            //
            classLayout.setLeftAnchor(navExpectations, 700d);
            classLayout.setBottomAnchor(navExpectations, 100d);
            //
            classLayout.getChildren().addAll(classroomLabel, createStudent, createAssignment, createExpectation, navStudent, navAssignments, navExpectations);
            
            topLayer.setCenter(classLayout);

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
            
            /*The layout type that will be used in order to have the rubric 
            displayed along with other features(Such as sidebars) that allow for
            a complete rubric to be created
            */
            AnchorPane rubricLayout = new AnchorPane();
            rubricLayout.setPadding(new Insets(10,10,10,10));
            //Establishes the scene parameters that allow for the rubricMenu
            //scene to exist
            rubricMenu = new Scene(rubricLayout, 1000, 750);
            rubricMenu.getStylesheets().add("rubricVisual.css");
            
            Button MenuButton = new Button("Back to Class");
            MenuButton.setOnAction(e -> {
                mainWindow.setScene(classMenu);
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

                        
                        enterClassroom.setOnAction(e -> {
                            mainWindow.setScene(classMenu);
                            topLayer.setCenter(classLayout);
                            backButton.setDisable(true);
                            navStudent.setDisable(false);
                            previousScene = firstMenu;
                            classroomLabel.setText(selectedClass.getName());
                            });

                            rubric = new TableView<>();   

                            ChoiceBox<String> assignmentList = new ChoiceBox();
            //The crucial line of code that allows the rubric to be displayed
            //when the rubricMenu Scene is selected
            //AnchorPane sets the specific locations of each child in the rubric layout
            AnchorPane.setTopAnchor(rubric, 10d);
            AnchorPane.setLeftAnchor(rubric, 5d);
            AnchorPane.setRightAnchor(rubric, 5d);
            //
            AnchorPane.setBottomAnchor(addButton, 130d);
            AnchorPane.setLeftAnchor(addButton, 440d);
            AnchorPane.setRightAnchor(addButton, 440d);
            //
            AnchorPane.setBottomAnchor(killButton, 100d);
            AnchorPane.setLeftAnchor(killButton, 420d);
            AnchorPane.setRightAnchor(killButton, 420d);
            //
            AnchorPane.setBottomAnchor(MenuButton, 20d);
            AnchorPane.setLeftAnchor(MenuButton, 10d);
            AnchorPane.setRightAnchor(MenuButton, 10d);

            AnchorPane.setBottomAnchor(MenuButton, 50d);
            AnchorPane.setLeftAnchor(MenuButton, 50d);
            AnchorPane.setRightAnchor(MenuButton, 50d);
            
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
                    col.setPrefWidth(40);
                    rubricCols.add(col);
                    final int colIndex = i ;
                    col.setCellValueFactory(cellData -> cellData.getValue().colProperty(colIndex));
                    rubric.getColumns().add(rubricCols.get(i));
                    }

                    rubric.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            
                            String selectedRow = rubric.getFocusModel().getFocusedItem().getFirstCol();
                            if(!selectedItem.equals(selectedRow)){
                                selectedItem = selectedRow;
                                assignmentsSelected.clear();
                                ObservableList<String> assignmentNames = FXCollections.observableArrayList();
                            for(int i = 0; i < selectedClass.getAssignments().size(); i++){
                                Assignment currAssignment = selectedClass.getAssignments().get(i);
                                for(int j = 0; j < currAssignment.getExpectations().size();j++){
                                    if(currAssignment.getExpectations().get(j).getExpectation().equals(selectedRow)){
                                        assignmentNames.add(currAssignment.getID());
                                        assignmentsSelected.add(currAssignment);
                                    }
                                }
                                }
                                assignmentList.setItems(assignmentNames);
                            }
                            
                        }
                    });


            killButton.disableProperty().bind(Bindings.isEmpty(rubric.getSelectionModel().getSelectedItems()));
            
            Button rubricMark = new Button("Set Assignment");
            rubricMark.setOnAction(e -> {
                TablePosition cell = rubric.getFocusModel().getFocusedCell();
                String val = assignmentList.getValue();
                int cellColumn = cell.getColumn();
                Row selectedRow =  rubric.getItems().get(cell.getRow());
                String oldVal = selectedRow.getOtherCols().get(cell.getColumn()-1).get();
                String prevMark = "";
                if(oldVal == null){
                    oldVal = "";
                }
                
                if(cell.getColumn() > 0 && val != null && !val.equals(oldVal)){
                    String fullAssignmentName = "";
                    String oldGrade = "";
                    for(int i = 0; i < rubricCols.size(); i++){
                        oldGrade = selectedRow.getOtherCols().get(i).get();
                        if(oldGrade != null && oldGrade.contains(val)){
                            prevMark = rubricCols.get(i).getText().trim();
                            String newVal = oldGrade.replaceAll(val +"\n", "");
                            selectedRow.setCol(newVal, i);
                            break;
                        }
                    }
                    io.deleteLine(filePath, selectedClass.getName() + "." +selectedRow.getID() +selectedStudent +prevMark +"." +val);
                    io.storeInfo(filePath, selectedClass.getName(), selectedRow.getID() +selectedStudent +rubricCols.get(cellColumn-1).getText(), val);
                    for(int i = 0; i < assignmentsSelected.size(); i++){
                        boolean test = (val.equals(assignmentsSelected.get(i).getID()));
                        if(test){
                            fullAssignmentName = assignmentsSelected.get(i).getName();
                            io.deleteLine(filePath, selectedClass.getName() + "." +fullAssignmentName +selectedStudent +selectedRow.getID() +"." +prevMark);
                            io.storeInfo(filePath, selectedClass.getName(), fullAssignmentName +selectedStudent + selectedRow.getID(), rubricCols.get(cellColumn-1).getText());
                            break;
                        }
                    }
                          
            selectedRow.setCol(oldVal +val +"\n", cellColumn-1);
            }});
            AnchorPane.setBottomAnchor(rubricMark, 20d);
            AnchorPane.setRightAnchor(rubricMark, 300d);
           
            AnchorPane.setBottomAnchor(assignmentList, 20d);
            AnchorPane.setRightAnchor(assignmentList, 400d);
            listOfStudents.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2) {
                            if(listOfStudents.getSelectionModel().getSelectedItem() != null){
                                mainWindow.setScene(rubricMenu);
                                selectedStudent = listOfStudents.getSelectionModel().getSelectedItem().getFullName();
                                System.out.println("Clicked on " + selectedStudent);
                                rubric.setItems(getRubricInfo(selectedStudent));
                        }        
                    }
                    
                }
            });



            rubricLayout.getChildren().addAll(rubric, hbox, MenuButton, assignmentList, rubricMark);
            
            AnchorPane gradingLayout = new AnchorPane();
            gradingLayout.setPadding(new Insets(0,10,10,10));
            Button setMarkButton = new Button("Set this mark");
            
            
            gradingLayout.setTopAnchor(setMarkButton, 70d);
            gradingLayout.setRightAnchor(setMarkButton, 10d);
            
            gradingLayout.setTopAnchor(gradeList, 40d);
            gradingLayout.setRightAnchor(gradeList, 10d);
            
            
            backButton.setOnAction(e -> {
                topLayer.setCenter(classLayout);
                backButton.setDisable(true);
            });
            
            listOfAssignments.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    if(listOfAssignments.getSelectionModel().getSelectedItem() != null){
                    topLayer.setCenter(gradingLayout);
                    selectedAssignment = listOfAssignments.getSelectionModel().getSelectedItem();
                    
                    int numExpectations = selectedAssignment.getExpectations().size();

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
            io.deleteLine(filePath, selectedClass.getName() +"." +selectedAssignment.getName() +selectedRow.getFirstCol() +selectedAssignment.getExpectations().get(cellColumn-1).getSection() +"." +selectedRow.getOtherCols().get(cellColumn-1).get());
            io.deleteLine(filePath, selectedClass.getName() +"." +selectedAssignment.getExpectations().get(cellColumn-1).getSection() +selectedRow.getFirstCol() +selectedRow.getOtherCols().get(cellColumn-1).get() +"." +selectedAssignment.getID());
            selectedRow.setCol(val, cellColumn-1);
            io.storeInfo(filePath, selectedClass.getName(), selectedAssignment.getName() +selectedRow.getFirstCol() + selectedAssignment.getExpectations().get(cellColumn-1).getSection(), val);
            io.storeInfo(filePath, selectedClass.getName(), selectedAssignment.getExpectations().get(cellColumn-1).getSection() +selectedRow.getFirstCol() +val, selectedAssignment.getID());
        }
    });
                }
            }});
            
        //Allows for the first scene to be shown when the program is run
        mainWindow.setScene(firstMenu);
        mainWindow.setTitle("GrandMama");
        mainWindow.centerOnScreen();
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
            String currentExp = rubricInfo.get(i).getID();
            try{
            for(int j = 0; j < rubricCols.size(); j++){
                io.openInputFile(filePath);

                String markName = rubricCols.get(j).getText();
                while((line = io.readLine()) != null){
                String assignment = getValue(line, currentExp +selectedStudent +markName, selectedClass.getName());
                if(assignment == "invalid") continue;
                String oldVal = rubricInfo.get(i).getOtherCols().get(j).get();
                if(oldVal == null){
                    oldVal = "";
                }
                    rubricInfo.get(i).setCol(oldVal +assignment +"\n", j);

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