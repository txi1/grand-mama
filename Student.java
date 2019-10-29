
public class Student{

    private String firstName;
    private String lastName;
    private int averageGrade;
    private int[] grade[];

    public void student(String n){
        int temp = 1;
        for(int i = 0; i < n.length() && n.charAt(i) != ' '; i++){
                firstName += n.charAt(i); 
                temp ++;
            }
            
        for(int i = temp; i < n.length() && n.charAt(i) != ' '; i++){
                lastName += n.charAt(i);
        }
    }


    public int getGrade(int a){
        return grade[i];

    }


}