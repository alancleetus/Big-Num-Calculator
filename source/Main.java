/** The <tt>Main</tt> promts the user for  an expression or variable 
 *  declaretion to be evaluted by the Evaluator class
 *  @author Alan Cleetus
 *  @version May 13, 2015
 */
import java.util.*;
import java.math.*;
public class Main
{
   public static void main(String[] args)
   {
      Evaluator test = new Evaluator();
   
      Scanner in = new Scanner(System.in);
       
      System.out.println(
         "Welcome to the Bignum Calculator\n**input -1 to end program**");
        
                     //Read
      String input = in.nextLine();
      while(!input.equals("-1"))
      { 
          
         String output = "";                       //Evaluate
         output = test.evaluate(input);      
         System.out.println(output);               //Print
         input = in.nextLine();
      }   
   }
}