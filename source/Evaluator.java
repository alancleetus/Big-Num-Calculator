/** The <tt>Evaluator</tt> class evaluates a postfix expressions 
 *  In response to an assignment, this program echo the new 
 *  value of the variable. In response to an expression, this program 
 *  evaluate the expression and print the result.
 *  @author Alan Cleetus
 *  @version May 13, 2015
 */
import java.util.*;
import java.math.*;

public class Evaluator
{
   private Map<String, String> variableMap;
   
   /** initializes the variable map
    */  
   public Evaluator()
   {
      variableMap = new HashMap<String, String>();
   }
   
   /** evaluates the expression
    *  @param input The user input.
    *  prints error if any kind of exception happens
    */  
   public String evaluate(String input)
   {
      try{  
         String output = "" ;
      //if declaring variable
         if(input.contains("="))
         {
            return evaluateVariableDeclaration(input);
         }
         else        //if expression
         {
            for(String s : variableMap.keySet())//check every key
            {
               if(input.contains(s))
               {
                  input = input.replaceAll(s, variableMap.get(s));  //if variable is inside expression replace
               }
            }
         
            output = postfixEvaluator(input);
         }
      
         return output;
      }
      catch(Exception e){  
         return "Error";}
   
   }
   private String evaluateVariableDeclaration(String input)
   {
      String variableName = (input.substring(0,input.indexOf('='))).trim() ;
      for(char ch : variableName.toCharArray())
      {
         if(!Character.isLetter(ch) || !Character.isLowerCase(ch) )
            return "Error";
      }
      // if more than one = sign
      if(input.indexOf('=') != input.lastIndexOf('='))
         return "Error";
      String variableValue = (input.substring(input.indexOf('=')+1)).trim() ;
     
      //System.out.println("name= "+variableName);
      //System.out.println("value= "+variableValue);
   
      String answer = evaluate(variableValue);
      //System.out.println("answer= "+answer);
      
      //save in variable map
      
      variableMap.put(variableName, answer);
      //System.out.println("map= "+variableMap);
   
      return answer;
   
   }
   
   //postfix evaluator
   private String postfixEvaluator(String input)
   {
      InfixToPostfix test = new InfixToPostfix(input);
      String s  = "";
      String temp = "";
      
      Stack<String> stk = new Stack<String>();
      for(Iterator<String> itr = test.iterator();itr.hasNext();)
      {
         s = itr.next(); 
         
         if(s.equals("+"))
         {
            //stk.push(stk.pop() + stk.pop() );
            
            BigInteger tempBigNum1 = new BigInteger(stk.pop());
            BigInteger tempBigNum2 = new BigInteger(stk.pop());
            stk.push(""+tempBigNum2.add(tempBigNum1));
         }
         else if(s.equals("-"))
         {
            //stk.push(stk.pop() - stk.pop() );
            BigInteger tempBigNum1 = new BigInteger(stk.pop());
            BigInteger tempBigNum2 = new BigInteger(stk.pop());
            stk.push(""+tempBigNum2.subtract(tempBigNum1));
         }
         else if(s.equals("*"))
         {
            //stk.push(stk.pop() * stk.pop() );
            
            BigInteger tempBigNum1 = new BigInteger(stk.pop());
            BigInteger tempBigNum2 = new BigInteger(stk.pop());
            stk.push(""+tempBigNum2.multiply(tempBigNum1));
         }
         else if(s.equals("/"))
         {
            //stk.push(stk.pop() / stk.pop() );
         
            BigInteger tempBigNum1 = new BigInteger(stk.pop());
            BigInteger tempBigNum2 = new BigInteger(stk.pop());
            stk.push(""+tempBigNum2.divide(tempBigNum1));
         }
         else if(s.equals("^"))
         {
            //stk.push(Math.pow(stk.pop(), stk.pop() ));
            int num = Integer.parseInt(stk.pop());
            BigInteger tempBigNum = new BigInteger(stk.pop());
            
            stk.push(""+tempBigNum.pow(num));
         }
         else
         {
            //stk.push(Integer.parseInt(s)*1.0);
            
            stk.push(s);
         }
      }
      temp = ""+stk.pop();
      return temp+"";
   }

   //unit test
   public static void main(String[] args)
   {
      Evaluator test = new Evaluator();
   
      System.out.println(test.evaluate("x = -1").equals("Error"));
      System.out.println(test.evaluate("ads^&^* = 1").equals("Error"));
      System.out.println(test.evaluate("AREEW = 1").equals("Error"));
      System.out.println(test.evaluate("a =  = 1").equals("Error"));
      System.out.println(test.evaluate("x = 2 ^7-5-12+4/8+6").equals("117"));      System.out.println(test.evaluate("y=1").equals("1"));
      System.out.println(test.evaluate("x+y").equals("118"));
      System.out.println(test.evaluate("x++y").equals("Error"));
      System.out.println(test.evaluate("x==y").equals("Error"));
   
   
   }
}