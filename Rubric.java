public class Rubric {
    
    public String expectation;
    public Double percent;
    
    //Constructors that allow for default values to exist if nothing is present
        public Rubric(){
            this.expectation = "";
            this.percent = 0.00;
        }

        public Rubric(String expectation, Double percent){
            this.expectation = expectation;
            this.percent = percent;
        }
    
        
    //Get and Set method for expectation
        public String getExpectation(){
            return expectation;
        }

        public void setExpecation(String expectation){
            this.expectation = expectation;
        }
        
        
    //Get and Set method for percent value
        public Double getpercent(){
            return percent;
        }

        public void setPercent(Double percent){
            this.percent = percent;
        }
    
}