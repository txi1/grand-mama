import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class Assignment{

    String name;
    String ID;
    String fullAssignment;
    ObservableList<Expectation> expectations = FXCollections.observableArrayList();

    public Assignment(String s, ObservableList<Expectation> e, String id){

        name = s;
        expectations = e;
        ID = id;
        fullAssignment = ID +" " +name;

    }

    public String getName(){
        return name;
    }

    public void setName(String n){
        name = n;
    }

    public void addExpectation(Expectation ex){
        expectations.add(ex);
    }

    public ObservableList<Expectation> getExpectations(){
        return expectations;
    }
    
    public void setExpectations(ObservableList<Expectation> e){
        expectations = e;
        fullAssignment = ID +" " +name;
    }

    public void setID(String id){
        ID = id;
        fullAssignment = ID +" " +name;
    }

    public String getID(){
        return ID;
    }
    public String getFullAssignment(){
        return fullAssignment;
    }
}