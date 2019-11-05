
public class Classroom{

    private String name;
    private int ID; 
    private int students;

    public Classroom(String n, int i){
        this.name = n;
        this.ID = i;
    }

    public int getID(){
        return ID;
    }

    public String getName(){
        return name;
    }
    
    public String toString(){
        return this.getName();
    }



    

}