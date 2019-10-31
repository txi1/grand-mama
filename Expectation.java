
public class Expectation{

    String identifier;
    String detail;


    public void Expectation(String i, String d){

        identifier = i;
        detail = d;

    }

    public String getExpectation(){
        return identifier +": " +detail;
    }

    public String getIdentification(){
        return identifier;
    }

    public String getDetails(){
        return detail;
    }

}