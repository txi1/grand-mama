
public class Classroom{

    private static int counter = 0;
    private String name;
    private int id; 
    private int students;

    public Classroom(String n){
        name = n;
        id = counter;
        counter ++;
    }

    public int getid(){
        return id;
    }
    
    public int getCounter(){
        return counter;
    }



    

}