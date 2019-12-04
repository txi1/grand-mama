import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Classroom{

    private String name;
    private int ID;
    private ObservableList<Student> students = FXCollections.observableArrayList();
    private ObservableList<Expectation> expectations = FXCollections.observableArrayList();

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
        expectations.setAll(new Expectation(e.getSection(), e.getDetails()));
    }
    
    public ObservableList<Student> getStudents(){
        return students;
    }


}