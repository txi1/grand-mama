import java.util.stream.*;

public class Student{

    private String firstName;
    private String lastName;
    private String fullName;
    private int averageGrade;
    private int[] grade;

    public Student(String f, String l){
        firstName = f;
        lastName = l;
        fullName = firstName +" " +lastName;
    }

    public void setFirstName(String n){
        firstName = n;
        fullName = firstName +" " +lastName;
    }

    public void setLastName(String n){
        lastName = n;
        fullName = firstName +" " +lastName;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }
    
    
    public String toString(int g){
  
        return "Temp";

    }
    
    public String getFullName(){
        return fullName;
    }

}