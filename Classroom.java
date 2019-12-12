import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Classroom{

    private String name;
    private int ID;
    private ObservableList<Student> students = FXCollections.observableArrayList();
    private ObservableList<Expectation> expectations = FXCollections.observableArrayList();
    private ObservableList<Assignment> assignments = FXCollections.observableArrayList();

    public Classroom(String n, int i){
        this.name = n;
        this.ID = i;
    }

    public int getID(){
        return ID;
    }

    public String getName(){
        return name;
    }
    
    public String toString(){
        return this.getName();
    }

    public void addStudent(Student s){
        students.add(new Student(s.getFirstName(), s.getLastName()));
    }
    
    public void addExpectation(Expectation e){
        expectations.add(new Expectation(e.getSection(), e.getDetails()));
    }
    
    public ObservableList<Expectation> getExpectations(){
        return expectations;
    }

    public ObservableList<Student> getStudents(){
        return students;
    }

    public ObservableList<Assignment> getAssignments() {
        return assignments;
    }

    public void addAssignment(String a) {
        assignments.add(new Assignment(a));
    }
    
    


}