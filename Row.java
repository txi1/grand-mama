import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Row {
    private final StringProperty student = new SimpleStringProperty();
    private final List<StringProperty> expectations = new ArrayList<>();

    public Row(String s, int numExpectations) {
        setStudent(s);
        for (int i = 0 ; i < numExpectations ; i++) {
            expectations.add(new SimpleStringProperty());
        }
    }

    public List<StringProperty> getExpectations() {
        return expectations;
    }

    public void setExpectation(String val, int index){
        expectations.get(index).set(val);
    }
    
    public StringProperty studentProperty() { 
        return student;
    }

    public final String getStudent() {
        return studentProperty().get();
    }

    public final void setStudent(String student) {
        studentProperty().set(student);
    }
}