public class Rubric {
    
    public int percent;
    public String expectation;
    
    //Constructors that allow for default values to exist if nothing is present
        public Rubric(){
            this.percent = 0;
            this.expectation = "";
        }

        public Rubric(int percent, String expectation){
            this.percent = percent;
            this.expectation = expectation;
        }
    
        
    //Get and Set method for percent value
        public int getpercent(){
            return percent;
        }

        public void setPercent(int percent){
            this.percent = percent;
        }
    
        
    //Get and Set method for expectation
        public String getExpectation(){
            return expectation;
        }

        public void setExpecation(String expectation){
            this.expectation = expectation;
        }
    
}