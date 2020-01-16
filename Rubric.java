
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Rubric {
    
    private StringProperty expectation;
    private String expectationID;

    private final List<StringProperty> marks = new ArrayList<>();
    private String lvlr;
    private String lvl1m;
    private String lvl1;
    private String lvl1p;
    private String lvl2m;
    private String lvl2;
    private String lvl2p;
    private String lvl3m;
    private String lvl3;
    private String lvl3p;
    private String lvl34;
    private String lvl4m;
    private String lvl4sm;
    private String lvl4;
    private String lvl4sp;
    private String lvl4p;
    private String lvl4pp;
    
    //Constructors that allow for default values to exist if nothing is present
        public Rubric(){
            this.expectation = new SimpleStringProperty("");
            this.expectationID = "";
            this.lvlr = "";
            this.lvl1m = "";
            this.lvl1 = "";
            this.lvl1p = "";
            this.lvl2m = "";
            this.lvl2 = "";
            this.lvl2p = "";
            this.lvl3m = "";
            this.lvl3 = "";
            this.lvl3p = "";
            this.lvl34 = "";
            this.lvl4m = "";
            this.lvl4sm = "";
            this.lvl4 = "";
            this.lvl4sp = "";
            this.lvl4p = "";
            this.lvl4pp = "";

        }

        public Rubric(String expectation, String lvlr,
                String lvl1m, String lvl1, String lvl1p,
                String lvl2m, String lvl2, String lvl2p,
                String lvl3m, String lvl3, String lvl3p,
                String lvl34, String lvl4m, String lvl4sm, String lvl4, String lvl4sp, String lvl4p, String lvl4pp,
                String expectationID){
            this.expectation = this.expectation = new SimpleStringProperty(expectation);
            this.expectationID = expectationID;
            this.lvlr = lvlr;
            this.lvl1m = lvl1m;
            this.lvl1 = lvl1;
            this.lvl1p = lvl1p;
            this.lvl2m = lvl2m;
            this.lvl2 = lvl2;
            this.lvl2p = lvl2p;
            this.lvl3m = lvl3m;
            this.lvl3 = lvl3;
            this.lvl3p = lvl3p;
            this.lvl34 = lvl34;
            this.lvl4sm = lvl4sm;
            this.lvl4m = lvl4m;
            this.lvl4 = lvl4;
            this.lvl4sp = lvl4sp;
            this.lvl4p = lvl4p;
            this.lvl4pp = lvl4pp;
        }
    
        
    //Get and Set method for expectation
        public String getExpectation(){
            return expectation.get();
        }

        public void setExpectation(String expectation){
            this.expectation = this.expectation = new SimpleStringProperty(expectation);
        }
        
        public StringProperty getExpectationProperty(){
            return expectation;
        }
     
        
        
        
    //Get and Set method for Level R
        public String getLvlr(){
            return lvlr;
        }

        public void setLvlr(String lvlr){
            this.lvlr = lvlr;
        }
        
        
        
        
    //Get and Set method for Level 1-
        public String getLvl1m(){
            return lvl1m;
        }

        public void setLvl1m(String lvl1m){
            this.lvl1m = lvl1m;
        }
        
    //Get and Set method for Level 1
        public String getLvl1(){
            return lvl1;
        }

        public void setLvl1(String lvl1){
            this.lvl1 = lvl1;
        }
        
    //Get and Set method for Level 1+
        public String getLvl1p(){
            return lvl1p;
        }

        public void setLvl1p(String lvl1p){
            this.lvl1p = lvl1p;
        }
        
        
        
        
    //Get and Set method for Level 2-
        public String getLvl2m(){
            return lvl2m;
        }

        public void setLvl2m(String lvl2m){
            this.lvl2m = lvl2m;
        }
        
    //Get and Set method for Level 2
        public String getLvl2(){
            return lvl2;
        }

        public void setLvl2(String lvl2){
            this.lvl2 = lvl2;
        }
        
    //Get and Set method for Level 2p
        public String getLvl2p(){
            return lvl2p;
        }

        public void setLvl2p(String lvl2p){
            this.lvl2p = lvl2p;
        }
        
        
        
        
    //Get and Set method for Level 3- A1
        public String getLvl3m(){
            return lvl3m;
        }

        public void setLvl3m(String lvl3m){
            this.lvl3m = lvl3m;
        }
        
    //Get and Set method for Level 3
        public String getLvl3(){
            return lvl3;
        }

        public void setLvl3(String lvl3){
            this.lvl3 = lvl3;
        }
        
    //Get and Set method for Level 3+
        public String getLvl3p(){
            return lvl3p;
        }

        public void setLvl3p(String lvl3p){
            this.lvl3p = lvl3p;
        }
        
        
        
        
    //Get and Set method for Level 3+/4-
        public String getLvl34(){
            return lvl34;
        }

        public void setLvl34(String lvl34){
            this.lvl34 = lvl34;
        }
        
    //Get and Set method for Level 4-
        public String getLvl4m(){
            return lvl4m;
        }

        public void setLvl4m(String lvl4m){
            this.lvl4m = lvl4m;
        }
        
    //Get and Set method for Level 4-/4
        public String getLvl4sm(){
            return lvl4sm;
        }

        public void setLvl4sm(String lvl4sm){
            this.lvl4sm = lvl4sm;
        }
        
    //Get and Set method for Level 4
        public String getLvl4(){
            return lvl4;
        }

        public void setLvl4(String lvl4){
            this.lvl4 = lvl4;
        }
        
    //Get and Set method for Level 4/4+
        public String getLvl4sp(){
            return lvl4sp;
        }

        public void setLvl4sp(String lvl4sp){
            this.lvl4sp = lvl4sp;
        }
        
    //Get and Set method for Level 4+
        public String getLvl4p(){
            return lvl4p;
        }

        public void setLvl4p(String lvl4p){
            this.lvl4p = lvl4p;
        }
        
    //Get and Set method for Level 4++
        public String getLvl4pp(){
            return lvl4pp;
        }

        public void setLvl4pp(String lvl4pp){
            this.lvl4pp = lvl4pp;
        }
        
        public String getExpectationID() {
        return expectationID;
    }

    public void setExpectationID(String expectationID) {
        this.expectationID = expectationID;
    }
    
}