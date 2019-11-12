public class Rubric {
    
    public String expectation;
    public String lvlr;
    public String lvl1m;
    public String lvl1;
    public String lvl1p;
    public String lvl2m;
    public String lvl2;
    public String lvl2p;
    public String lvl3m;
    public String lvl3;
    public String lvl3p;
    public String lvl34;
    public String lvl4m;
    public String lvl4;
    public String lvl4sp;
    public String lvl4p;
    
    //Constructors that allow for default values to exist if nothing is present
        public Rubric(){
            this.expectation = "";
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
            this.lvl4 = "";
            this.lvl4sp = "";
            this.lvl4p = "";
        }

        public Rubric(String expectation, String lvlr,
                String lvl1m, String lvl1, String lvl1p,
                String lvl2m, String lvl2, String lvl2p,
                String lvl3m, String lvl3, String lvl3p,
                String lvl34, String lvl4m, String lvl4, String lvl4sp, String lvl4p){
            this.expectation = expectation;
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
            this.lvl4m = lvl4m;
            this.lvl4 = lvl4;
            this.lvl4sp = lvl4sp;
            this.lvl4p = lvl4p;
        }
    
        
    //Get and Set method for expectation
        public String getExpectation(){
            return expectation;
        }

        public void setExpecation(String expectation){
            this.expectation = expectation;
        }
     
        
        
        
    //Get and Set method for Level R
        public String getlvlr(){
            return lvlr;
        }

        public void setlvlr(String lvlr){
            this.lvlr = lvlr;
        }
        
        
        
        
    //Get and Set method for Level 1-
        public String getlvl1m(){
            return lvlr;
        }

        public void setlvl1m(String lvl1m){
            this.lvl1m = lvl1m;
        }
        
    //Get and Set method for Level 1
        public String getlvl1(){
            return lvl1;
        }

        public void setlvl1(String lvl1){
            this.lvl1 = lvl1;
        }
        
    //Get and Set method for Level 1+
        public String getlvl1p(){
            return lvl1p;
        }

        public void setlvl1p(String lvl1p){
            this.lvl1p = lvl1p;
        }
        
        
        
        
    //Get and Set method for Level 2-
        public String getlvl2m(){
            return lvl2m;
        }

        public void setlvl2m(String lvl2m){
            this.lvl2m = lvl2m;
        }
        
    //Get and Set method for Level 2
        public String getlvl2(){
            return lvl2;
        }

        public void setlvl2(String lvl2){
            this.lvl2 = lvl2;
        }
        
    //Get and Set method for Level 2p
        public String getlvl2p(){
            return lvl2p;
        }

        public void setlvl2p(String lvl2p){
            this.lvl2p = lvl2p;
        }
        
        
        
        
    //Get and Set method for Level 3-
        public String getlvl3m(){
            return lvl2m;
        }

        public void setlvl3m(String lvl3m){
            this.lvl3m = lvl3m;
        }
        
    //Get and Set method for Level 3
        public String getlvl3(){
            return lvl3;
        }

        public void setlvl3(String lvl3){
            this.lvl3 = lvl3;
        }
        
    //Get and Set method for Level 3+
        public String getlvl3p(){
            return lvl3p;
        }

        public void setlvl3p(String lvl3p){
            this.lvl3p = lvl3p;
        }
        
        
        
        
    //Get and Set method for Level 3+/4-
        public String getlvl34(){
            return lvl34;
        }

        public void setlvl34(String lvl34){
            this.lvl34 = lvl34;
        }
        
    //Get and Set method for Level 4-
        public String getlvl4m(){
            return lvl4m;
        }

        public void setlvl4m(String lvl4m){
            this.lvl4m = lvl4m;
        }
        
    //Get and Set method for Level 4
        public String getlvl4(){
            return lvl4;
        }

        public void setlvl4(String lvl4){
            this.lvl4 = lvl4;
        }
        
    //Get and Set method for Level 4/4+
        public String getlvl4sp(){
            return lvl4sp;
        }

        public void setlvl4sp(String lvl4sp){
            this.lvl4sp = lvl4sp;
        }
        
    //Get and Set method for Level 4+
        public String getlvl4p(){
            return lvl4p;
        }

        public void setlvl4p(String lvl4p){
            this.lvl4p = lvl4p;
        }
    
}