
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Expectation{

    private String section;
    private String detail;
    private ObservableList<Assignment> assignments = FXCollections.observableArrayList();
    private final StringProperty firstCol = new SimpleStringProperty();

    public Expectation(String s, String d){

        section = s;
        detail = d;

    }

    public StringProperty firstColProperty() { 
        return firstCol;
    }

    public final String getFirstCol() {
        return firstColProperty().get();
    }

    public final void setFirstCol(String s) {
        firstColProperty().set(s);
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

    public void addAssignment(String a, ObservableList<Expectation> e, String i){
        assignments.add(new Assignment(a, e, i));
    }
    
}