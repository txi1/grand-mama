import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class IO
{
  private static PrintWriter fileOut;
  private static BufferedReader fileIn;
  
  //Creates an output file. Do not use, will wipe entire text file
  public static void createOutputFile(String fileName)
  {
    try
    {
      fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
    }
    catch (IOException e)
    {
      System.out.println("*** Cannot create file: " + fileName + " ***");
    }
  }
  
  //Same thing, except minus the file wiping part
  public static void appendOutputFile(String fileName)
  {
    try
    {
      fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
    }
    catch (IOException e)
    {
      System.out.println("*** Cannot open file: " + fileName + " ***");
    }
  }
  
  //Prints a line in the selected file
  public void print(String filePath, String text)
  {
    try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
    bufferedWriter.write(text);
} catch (IOException e) {
    // exception handling
}
  }
  
  //Prints a line with a newline character at the very end of the selected file
  public void println(String filePath, String text)
  {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
    bufferedWriter.write(text + "\n");
} catch (IOException e) {
    // exception handling
}
  }
  
  //Closes the output file
  public static void closeOutputFile()
  {
    fileOut.close();
  }
  
  //Opens an input file for reading
  public static void openInputFile(String fileName)
  {
    try
    {
      fileIn = new BufferedReader(new FileReader(fileName));
    }
    catch (FileNotFoundException e)
    {
        createOutputFile(fileName);
      System.out.println("***Cannot open " + fileName + "***");
    }
  }
  
  //Returns the String that the reader is currently on in the text file
  public static String readLine() throws IOException    
    //Note: if there's an error in this method it will return IOException
  {
    return fileIn.readLine();
  }
  
  //Close the input file
  public static void closeInputFile() throws IOException
    //Note: if there's an error in this method it will return IOException
  {
    fileIn.close();
  }

  //Deletes a line that has the indicated text within it
  public static void deleteLine(String n, String delete){
      
      File fileName = new File(n);
      File tempFileName = new File("myTempFile.txt");
try
    {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFileName));

        String lineToRemove = delete;
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
          // trim newline when comparing with lineToRemove
          String trimmedLine = currentLine.trim();
          if(trimmedLine.equals(lineToRemove)) continue;
          writer.write(trimmedLine + System.getProperty("line.separator"));
      }
      writer.close(); 
      reader.close(); 
      boolean deletedFile = fileName.delete();
      boolean successful = tempFileName.renameTo(fileName);
      

    }
    catch (FileNotFoundException e)
    {
      System.out.println("***Cannot open " + fileName + "***");
    }
    catch (IOException e){
        System.out.println("*** Cannot create file: " + n + " ***");
    }
  }

  //Simply the println method, but prints the information with a specific format. The name of the classroom, followed by the type of information, followed by its value
  public void storeInfo(String filePath, String identifier, String name, String value)
    {
          try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
      bufferedWriter.write(identifier + "." + name + "." + value + "\n");
  } catch (IOException e) {
      // exception handling
  }
    }  

    //I think it's literally identical to deleteLine for now
 public static void completeDestruction(String n, String delete){
      
      File fileName = new File(n);
      File tempFileName = new File("myTempFile.txt");
try
    {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFileName));

        String lineToRemove = delete;
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
          // trim newline when comparing with lineToRemove
          String trimmedLine = currentLine.trim();
          trimmedLine = trimmedLine.replaceFirst("^\uFEFF", "");
          System.out.println(trimmedLine);
            String searchAndDestroy = "";
          for(int i = 0; i < trimmedLine.length();i++){
            if(trimmedLine.charAt(i) != '.'){
             searchAndDestroy += trimmedLine.charAt(i);
            }else break;
          }
          if(searchAndDestroy.equals(lineToRemove)){
              System.out.println("Deleted line: " +trimmedLine);
              continue;
          } 
          writer.write(trimmedLine + System.getProperty("line.separator"));
      }
      writer.close(); 
      reader.close(); 
      boolean deletedFile = fileName.delete();
      boolean successful = tempFileName.renameTo(fileName);

    }
    catch (FileNotFoundException e)
    {
      System.out.println("***Cannot open " + fileName + "***");
    }
    catch (IOException e){
        System.out.println("*** Cannot create file: " + n + " ***");
    }
  }


  //Reads through the entire text file and returns the first time the indicated information is found within the selected classroom
public static String getInfo(String n, String classroomName, String info){
      
  File fileName = new File(n);
  String value = "";
try
{
    BufferedReader reader = new BufferedReader(new FileReader(fileName));
    
    String currentLine;
    String searchAndIdentify = "";
    int counter = 0;

    while((currentLine = reader.readLine()) != null) {
      // trim newline when comparing with lineToRemove
      String trimmedLine = currentLine.trim();
      for(int i = 0; i < trimmedLine.length();i++){
        if(trimmedLine.charAt(i) != '\"' ){
         searchAndIdentify += trimmedLine.charAt(i);
         
        }else counter = i + 1; break;
      }
      if(searchAndIdentify.equals(classroomName + "." + info + ".")){
        for(int i = counter; i < trimmedLine.length();i++){
          if(trimmedLine.charAt(i) != '\"' ){
           value += trimmedLine.charAt(i);
          }else break;
          return value;
      }
      
    }
    reader.close(); 
  }
}catch(FileNotFoundException e){
  System.out.println("***Cannot open " + fileName + "***");
}
catch(IOException e){
  System.out.println("*** Cannot create file: " + n + " ***");
}
return value;
}

}