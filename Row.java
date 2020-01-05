import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Row {
    private final StringProperty firstCol = new SimpleStringProperty();
    private final List<StringProperty> otherCols = new ArrayList<>();
    private String ID;

    public Row(String s, int numCols) {
        setFirstCol(s);
        for (int i = 0 ; i < numCols ; i++) {
            otherCols.add(new SimpleStringProperty());
        }
        ID = null;  
    }

    public Row(String s, int numCols, String opID) {
        setFirstCol(s);
        for (int i = 0 ; i < numCols ; i++) {
            otherCols.add(new SimpleStringProperty());
        }
        ID = opID;   
    }

    public List<StringProperty> getOtherCols() {
        return otherCols;
    }

    public void setCol(String val, int index){
        otherCols.get(index).setValue(val);
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
    
    public StringProperty colProperty(int index){
        return otherCols.get(index);
    }

    public String getID(){
        return ID;
    }
}