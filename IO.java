import java.io.BufferedReader;
import java.io.BufferedWriter;
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
  
  public void print(String filePath, String text)
  {
    try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
    bufferedWriter.write(text);
} catch (IOException e) {
    // exception handling
}
  }
  
  public void println(String filePath, String text)
  {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
    bufferedWriter.write(text + "\n");
} catch (IOException e) {
    // exception handling
}
  }
  
  public static void closeOutputFile()
  {
    fileOut.close();
  }
  
  public static void openInputFile(String fileName)
  {
    try
    {
      fileIn = new BufferedReader(new FileReader(fileName));
    }
    catch (FileNotFoundException e)
    {
      System.out.println("***Cannot open " + fileName + "***");
    }
  }
  
  public static String readLine() throws IOException    
    //Note: if there's an error in this method it will return IOException
  {
    return fileIn.readLine();
  }
  
  public static void closeInputFile() throws IOException
    //Note: if there's an error in this method it will return IOException
  {
    fileIn.close();
  }
}