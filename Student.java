import java.util.stream.*;

public class Student{

    private String firstName;
    private String lastName;
    private String fullName;
    private int averageGrade;
    private int[] grade;

    public Student(String f, String l){
        firstName = f;
        firstName = l;
        fullName = firstName +" " +lastName;
    }


    public int getGrade(int i){
        return grade[i];

    }

    public void setFirstName(String n){
        firstName = n;
    }

    public void setLastName(String n){
        lastName = n;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setGrade(int i, int g){
        grade[i] = g;
        averageGrade = IntStream.of(grade).sum() / grade.length;
    }

    public int getAverage(){
        return averageGrade;
    }

    public static boolean isBetween(int x, int l, int u) {
        return l <= x && x <= u;
      }
    
    public String toString(int g){
  
        return "Temp";

    }
    
    public String getFullName(){
        return fullName;
    }

}