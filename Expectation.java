
public class Expectation{

    String section;
    String detail;
    static int id;
    private static int counter = 0;


    public Expectation(String s, String d){

        section = s;
        detail = d;
        id = counter;
        counter++;

    }

    public String getExpectation(){
        return section +": " +detail;
    }

    public int getIdentification(){
        return id;
    }

    public String getDetails(){
        return detail;
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

}