/** The <tt>InfixToPostfix</tt> class convert infix expressions 
 *  to postfix expressions. The possible operators are +, -, *, /, and ^.
 *  Parenthesis are also allowed.  
 *  @author Alan Cleetus
 *  @version April 15, 2015
 */
 
import java.util.*;

public class InfixToPostfix 
{
   private Deque<String> infix; 
   private Deque<String> postfix; 

   /** Constructs a deque of strings that hold the infix expression
    *  from a string that holds a mathematical expression.
    *  @param s The string to be interpreted.
    *  @throws IllegalArgumentException if any kind of error occurs
    */   
   public InfixToPostfix(String s)
   {
      try {
         buildInfix(s);
         buildPostfix();
      }
      catch (Exception e) 
      {
         throw new IllegalArgumentException();
      }
      
   }
   
   /** Builds the infix deque from the infix expression given by the user. 
    */  
   private void buildInfix(String s)
   {
      
      infix = new LinkedList<String>();
      String tempString = "";
      
      //loops though whole expression
      for(int i = 0; i<s.length(); i++)
      {
         char ch = s.charAt(i);              //holds each character in the expression
         
         if(Character.isDigit(ch) || Character.isLetter(ch))   //if letter or number
         {
            tempString += ch;
         }
         else if(ch == '(' || ch == ')' || ch == '+'           //if operator or space
                  || ch == '-' || ch == '/' || ch == '*' 
                  || ch == '^' || ch == ' ')                   
         {
            if(ch !=' ')
            {
               if(tempString != "")
               {
                  infix.add(tempString);
               
                  tempString = "";
               }
               infix.add(""+ch);
            }
         }
         else                                      //if illegal character
         {         
            throw new IllegalArgumentException();
         }         
         
         if(i == s.length()-1 &&tempString != "")
            infix.add(tempString);
      }
      
   }
   
   /** Builds the postfix expression from the infix expression given by the user. 
    */  
   private void buildPostfix()
   {
      postfix = new LinkedList<String>();
      
      Stack<String> stk = new Stack<String>();
      Queue<String> que = new LinkedList<String>();
   
      for(Iterator<String> itr = infix.iterator(); itr.hasNext();)
      {
         String temp = itr.next(); 
         if(temp.equals("+") || temp.equals("-") || temp.equals("/") || temp.equals("*"))
         {
            while(!stk.isEmpty() && precedence(stk.peek()) >= precedence(temp))
            {
               que.add(stk.pop());
            }
               
            stk.push(temp);
         }
         else if(temp.equals(")"))
         {
            while(!stk.peek().equals("("))
            {
               que.add(stk.pop());
            }
            if(stk.peek().equals("("))  stk.pop();
         }
         else if(temp.equals("("))
         {
            stk.push(temp);
         }
         else if(temp.equals("^"))
         {
            while(!stk.isEmpty() && precedence(stk.peek()) > precedence(temp))
            {
               que.add(stk.pop());
            }
            stk.push(temp);
         }
         else
         {
            que.add(temp);         
         }
         
      }
      
      while(!stk.isEmpty())
      {
         que.add(stk.pop());
      }
      while(!que.isEmpty())
      {
         postfix.add(que.remove());
      }
   }
   
   /** Evaluate the precedence of any operator that is passed in.
    *  
    *  @param  s holds the operator
    *  @return the integer value of the operator aka the precedence of the operator
    */
   private static int precedence(String s)
   {
      int precedence = 0;
      switch(s)
      {
         case "-":
            precedence = 1;
            break;
         case "+":
            precedence = 1;
            break;
         case "/":
            precedence = 2;
            break;
         case "*":
            precedence = 2;
            break;
         case "^":
            precedence = 4;
            break;
         default:
            break;
      }    
      return precedence;
   }
   
   /** This method iterate through each token of the postfix expression one by one.
    *  
    *  @return a string iterator for the postfix expression
    */
   public Iterator<String> iterator() 
   {
      if(postfix == null) this.buildPostfix();
      return new PostfixIterator(postfix) ;
   }
   
   //Unit Test
   public static void main(String [] args)
   {
      InfixToPostfix test = new InfixToPostfix("1 +2    -3*5/ (6+0  )+1^89^1");
   
      String res = "" ;
      for (Iterator<String> itr = test.iterator();itr.hasNext();) {
         res += " " + itr.next(); 
      }
      //spacing test
      System.out.println(res.equals(" 1 2 + 3 5 * 6 0 + / - 1 89 1 ^ ^ +"));
      
      test = new InfixToPostfix("(((3)))*2 ^7-5-12+4+8+6");
      res = "" ;
      for (Iterator<String> itr = test.iterator();itr.hasNext();) {
         res += " " + itr.next(); 
      }
      //parenthesis test
      System.out.println(res.equals(" 3 2 7 ^ * 5 - 12 - 4 + 8 + 6 +"));
      
      test = new InfixToPostfix("H-e+ll*o/^(world)");
      res = "" ;
      for (Iterator<String> itr = test.iterator();itr.hasNext();) {
         res += " " + itr.next(); 
      }
      //variable test
      System.out.println(res.equals(" H e - ll o * world ^ / +"));
      
      test = new InfixToPostfix("123456789123456789+6-0*(12354687/129565)");
      res = "" ;
      for (Iterator<String> itr = test.iterator();itr.hasNext();) {
         res += " " + itr.next(); 
      }
      //bignum test
      System.out.println(res.equals(" 123456789123456789 6 + 0 12354687 129565 / * -"));
      
      //error test
      try 
      {
         test = new InfixToPostfix("!& - 123+456");
      }
      catch (Exception IllegalArgumentException) 
      {
         System.out.println(true);
      }
             
      test = new InfixToPostfix(" 12");
      res = "" ;
      for (Iterator<String> itr = test.iterator();itr.hasNext();) {
         res +=  itr.next()+" " ; 
      }
      //bignum test
      System.out.println(res);
      
   }
   
   class PostfixIterator implements Iterator<String> 
   {
      private Deque<String> tempPostfix;
   
      public PostfixIterator(Deque<String> deq) 
      {
         tempPostfix = deq;
      }
   
      public boolean hasNext() 
      {
      
         return tempPostfix.size()>0;
      }
   
      public String next() 
      {
         if(tempPostfix == null)
            return "null";
         return ""+tempPostfix.remove();
      }
   }
}