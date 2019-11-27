
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Expectation{

    private String section;
    private String detail;
    private ObservableList<Assignment> assignment = FXCollections.observableArrayList();

    public Expectation(String s, String d){

        section = s;
        detail = d;

    }

    public String getExpectation(){
        return section +": " +detail;
    }

    public String getDetails(){
        return detail;
    }

    public void setSection(String s){
        section = s;
    }

    public void setDetail(String d){
        detail = d;
    }

    public void updateExpectation(String i, String d){
        if(i != null){
            section = i;
        }
        if(d != null){
            detail = d;
        }
    }

    public String getSection(){
        return section;
    }

    public void addAssignment(String a){
        assignment.setAll(new Assignment(a));
    }
    
}