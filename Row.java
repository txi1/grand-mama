import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Row {
    private final StringProperty student = new SimpleStringProperty();
    private final List<SimpleObjectProperty<Expectation>> expectations = new ArrayList<>();

    public Row(String s, int numAttendances) {
        setStudent(s);
        for (int i = 0 ; i < numAttendances ; i++) {
            expectations.add(new SimpleObjectProperty<>());
        }
    }

    public List<SimpleObjectProperty<Expectation>> getExpectations() {
        return expectations;
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