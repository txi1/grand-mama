import java.io.IOException;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
        //while there's still text found within the file that stores information
        //then students continue to be initialized
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

        //A list is created here that will store all the students found inside a classroom
        ListView<Student> listOfStudents = new ListView<>(students);
        listOfStudents.setPrefWidth(500);
        listOfStudents.setPrefHeight(500);

        Label labelTitle = new Label("Select Classroom");
        labelTitle.getStyleClass().add("label-start-menu");
        
        Label labeltrueTitle = new Label("Grand Mama");
        labeltrueTitle.getStyleClass().add("label-title-menu");

        Button enterClassroom = new Button("Enter this classroom");
            
        enterClassroom.setDisable(true);

        //This delete button is meant to delete a selected classroom
        //This includes the removal of absolutely everything, so that nothing is left
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

        //This button is meant to allow the user to create a classroom
        //that will have nothing in it, meaning that user has to create everything from scratch
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

        /*
        This checks to see if a classroom is selected. If no class 
        is selected, then the user won't be able to delete or enter a classroom.
        If a classroom is selected, than the user will be able to enter or delete said classroom
        */
        classList.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if(newValue != null){
            selectedClass = newValue; 
            enterClassroom.setDisable(false);
            deleteButton.setDisable(false);
            }
        });

            //AnchorPane is used in order to setup the
            //position of all the elements of the program
            menuLayout.setTopAnchor(labeltrueTitle, 10d);
            menuLayout.setRightAnchor(labeltrueTitle, 270d);
            //AnchorPane for setting the "Select Classroom" label
            menuLayout.setTopAnchor(labelTitle, 200d);
            menuLayout.setRightAnchor(labelTitle, 390d);
            //AnchorPane for setting the 'Classroom Selection Dropbox'
            menuLayout.setTopAnchor(classList, 250d);
            menuLayout.setRightAnchor(classList, 455d);
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
            
            //This is a button that will allow for the deletion
            //of a student that it selected
            Button deleteStudent = new Button("Delete Student");
            deleteStudent.setOnAction(e -> { 
                ObservableList<Student> studentSelected, allStudents;
                allStudents = listOfStudents.getItems();
                studentSelected = listOfStudents.getSelectionModel().getSelectedItems();
                io.exodiaObliterate(filePath, studentSelected.get(0).getFullName());
                studentSelected.forEach(allStudents::remove);
            });
            
            //This studentLayout anchorpane will add the
            //list of students along with a delete student button
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
            
            //Creates a list that will store all of the assignments of a particular class
            ListView<Assignment> listOfAssignments = new ListView<>();
            listOfAssignments.setPrefWidth(500);
            listOfAssignments.setPrefHeight(500);
            
            //Creates a button that will delete a selected assignment
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
            //Creates the size of the list that will contain the expectations
            listOfExpectations.setPrefWidth(500);
            listOfExpectations.setPrefHeight(500);
            AnchorPane expectationLayout = new AnchorPane();

            //A label is created next to the list of expectations that
            //will display the details of the selected assignment
            Label expectationDetails = new Label("Expectation Details");
            expectationDetails.getStyleClass().add("label-expectationDetails");
            expectationDetails.setPrefWidth(400d);
            expectationDetails.setWrapText(true);

            //A label is created next to the list of expectations that
            //will display the projects that are attached to the selected expectation
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
                                            allAssignments += currAssignment.getName() + ", ";
                                            break;
                                        }
                                    }
                                }
                                expectationAssignments.setText(allAssignments);
                        }     
                          
                    }
                    
                }
            });

            //Button that will delete expectations within the list of expectations
            Button deleteExpectation = new Button("Delete Expectation");
            
            deleteExpectation.setOnAction(e -> {

                ObservableList<Expectation> expectationSelected, allExpectation;
                allExpectation = listOfExpectations.getItems();
                expectationSelected = listOfExpectations.getSelectionModel().getSelectedItems();
                io.deleteLine(filePath, selectedClass.getName() +".expectation." + expectationSelected.get(0).getFirstCol());
                expectationSelected.forEach(allExpectation::remove);

            });

            expectationLayout.setTopAnchor(listOfExpectations, 10d);
            //
            expectationLayout.setBottomAnchor(deleteExpectation, 10d);
            expectationLayout.setLeftAnchor(deleteExpectation, 10d);
            expectationLayout.setRightAnchor(deleteExpectation, 10d);
            //
            expectationLayout.setTopAnchor(expectationDetails, 10d);
            expectationLayout.setLeftAnchor(expectationDetails, 550d);
            //
            expectationLayout.setLeftAnchor(expectationAssignments, 550d);
            expectationLayout.setTopAnchor(expectationAssignments, 60d);
            //
            expectationLayout.getChildren().addAll(deleteExpectation, listOfExpectations, expectationAssignments, expectationDetails);
            
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
            Button addButton = new Button("Add Expectation");
            addButton.setOnAction(e -> addButtonClicked());
            Button killButton = new Button("Delete Expectation");
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
            Label labelForRoller = new Label("");
            
            //When a student is clicked on in the student menu, it will take you to their rubric
            listOfStudents.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    //This if statement makes it so you must double click the student in order to go to the rubric
                    if (event.getClickCount() == 2) {
                            if(listOfStudents.getSelectionModel().getSelectedItem() != null){
                                mainWindow.setScene(rubricMenu);
                                selectedStudent = listOfStudents.getSelectionModel().getSelectedItem().getFullName();
                                System.out.println("Clicked on " + selectedStudent);
                                labelForRoller.setText(selectedStudent);
                                labelForRoller.getStyleClass().add("label-student");
                                rubric.setItems(getRubricInfo(selectedStudent));
                        }        
                    }
                    
                }
            });
            
            AnchorPane gradingLayout = new AnchorPane();
            gradingLayout.setPadding(new Insets(0,10,10,10));
            Button setMarkButton = new Button("Set this mark");
            
            backButton.setOnAction(e -> {
                topLayer.setCenter(classLayout);
                backButton.setDisable(true);
            });
            
            //The crucial line of code that allows the rubric to be displayed
            //when the rubricMenu Scene is selected
            //AnchorPane sets the specific locations of each child in the rubric layout
            AnchorPane.setTopAnchor(rubric, 40d);
            AnchorPane.setLeftAnchor(rubric, 120d);
            //
            AnchorPane.setBottomAnchor(addButton, 130d);
            AnchorPane.setLeftAnchor(addButton, 10d);
            //
            AnchorPane.setBottomAnchor(killButton, 100d);
            AnchorPane.setLeftAnchor(killButton, 10d);
            //
            AnchorPane.setBottomAnchor(assignmentList, 150d);
            AnchorPane.setLeftAnchor(assignmentList, 425d);
            //
            AnchorPane.setBottomAnchor(rubricMark, 200d);
            AnchorPane.setLeftAnchor(rubricMark, 325d);
            //
            AnchorPane.setBottomAnchor(MenuButton, 20d);
            AnchorPane.setLeftAnchor(MenuButton, 10d);
            AnchorPane.setRightAnchor(MenuButton, 10d);
            //
            AnchorPane.setLeftAnchor(labelForRoller, 440d);
            AnchorPane.setTopAnchor(labelForRoller, 5d);

            rubricLayout.getChildren().addAll(rubric, hbox, addButton, killButton, MenuButton, labelForRoller, assignmentList, rubricMark);

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

    gradingLayout.setTopAnchor(setMarkButton, 10d);
    gradingLayout.setRightAnchor(setMarkButton, 10d);
    //
    gradingLayout.setTopAnchor(gradeList, 60d);
    gradingLayout.setRightAnchor(gradeList, 10d);
    //
    gradingLayout.setTopAnchor(table, 120d);
    gradingLayout.setLeftAnchor(table, 10d);
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
    
    //Method that's used in order to add expectations to the rubric when the user is accessing the rubricMenu itself
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
    
    //Method that's used to delete expectations from inside the rubricMenu itself
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
        //If the string value is empty then set test = true
        if("".equals(s) || s == null){
            test = true;
        }
        //If the string value isn't empty, then set test = false
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
        //Will open the file that stores all the data inside of the program
        io.openInputFile(filePath);
        try{
            //Checks to make sure that the string value isn't empty
            while((line = io.readLine()) != null){
                line = getValue(line, "expectation", selectedClass.getName());
                if(line.equals("invalid")) continue;
                String ID = "";
                //For loop will check for the specific expectation that's been created for the selected class
                for(int i = 0; i <= line.length(); i++){
                    if(line.charAt(i) != ':') ID += line.charAt(i);
                            else break;
                }
                rubricInfo.addAll(new Row(line, colSize, ID));
            }
            io.closeInputFile();
        
        //Will throw out an error if something goes wrong    
        }catch(IOException e){
            System.out.println("Error");
        }

        for(int i = 0; i < rubricInfo.size(); i++){
            String currentExp = rubricInfo.get(i).getID();
            try{
                //for loop will keep the counter going up for every grade column that exists
                for(int j = 0; j < rubricCols.size(); j++){
                    io.openInputFile(filePath);
                    //The name of the mark will be equal to the text found in the top of the current column that's value j is on
                    String markName = rubricCols.get(j).getText();
                    while((line = io.readLine()) != null){
                        String assignment = getValue(line, currentExp + selectedStudent + markName, selectedClass.getName());
                        if(assignment == "invalid") continue;
                        String oldVal = rubricInfo.get(i).getOtherCols().get(j).get();
                            if(oldVal == null){
                                oldVal = "";
                            }
                        rubricInfo.get(i).setCol(oldVal +assignment +"\n", j);
                    }
                //Closes the file that contains all the data of the program
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