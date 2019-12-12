import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class Assignment{

    String name;
    ObservableList<Expectation> expectations = FXCollections.observableArrayList();

    public Assignment(String s, Expectation... e){

        name = s;
        for(int i = 0; i < e.length; i++){
            addExpectation(e[i]);
        }

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
}