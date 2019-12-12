import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class Assignment{

    String name;
    ObservableList<Expectation> expectations = FXCollections.observableArrayList();

    public Assignment(String s, ObservableList<Expectation> e){

        name = s;
        expectations = e;

    }

    public String getName(){
        return name;
    }

    public void setName(String n){
        name = n;
    }

    public void addExpectation(Expectation e){
        expectations.add(e);
    }

    public ObservableList<Expectation> getExpectations(){
        return expectations;
    }
    
    public void setExpectations(ObservableList<Expectation> e){
        expectations = e;
    }
}