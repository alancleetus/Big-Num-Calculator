import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Calculator
{
   static final Font font = new Font( "MONOSPACED", Font.PLAIN, 16 );
   static final Font font2 = new Font( "Century Gothic", Font.ITALIC, 20 );
   static final Font font3 = new Font( "Century Gothic", Font.ITALIC, 16 );
   
   private JFrame frame;
   private JPanel mainPanel;
   private JPanel buttonPanel;
   public JTextField infixBox;
   public JTextField postfixBox;
   public JTextField answerBox;
   
   public Calculator()
   {
      frame = new JFrame();
      //Frame Edit
      frame.setUndecorated(true);
      frame.setLocation( 500, 200 );   //location on monitor
      frame.setTitle( "Bignum Calculator" );             //title of frame
      frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); //what happens when the box is closed
      frame.setSize( new Dimension( 490, 335 ) );
      frame.setResizable( false );
      
      
      mainPanel = new JPanel( new GridLayout() );
      
      //mainPanel edit
      mainPanel.setBackground( new java.awt.Color( 122,183,179 ) );
   
      mainPanel.add( displayPanel() );
      mainPanel.add( buttonPanel() );
      
      frame.add( mainPanel, BorderLayout.CENTER );
      
      frame.setVisible( true );
   }
   
   private JPanel buttonPanel()
   {
      buttonPanel = new JPanel( new FlowLayout( FlowLayout.RIGHT ) );      
      {
         buttonPanel.setBackground( new java.awt.Color( 0,0,0,0 ) );//transparent
         buttonPanel.setSize( new Dimension( 204, 306 ) );
          
         
         //all buttons
         { 
            Dimension d =  new Dimension( 45, 45 );
            Dimension d2 =  new Dimension( 45*2+6, 45 );
            
            buttonPanel.add( makeButton( "Clear", d2 ) );
            buttonPanel.add( makeButton( "(", d ) );
            buttonPanel.add( makeButton( ")", d ) );
            buttonPanel.add( makeButton( "^", d ) );
            buttonPanel.add( makeButton( "/", d ) );
            buttonPanel.add( makeButton( "Del|<",d2 ) );
         
            for( int i = 1; i<=9; i++ )
            {
               buttonPanel.add( makeButton( ""+i , d ) );
            
               if( i == 3 )buttonPanel.add( makeButton( "*", d ) );
               if( i == 6 )buttonPanel.add( makeButton( "-", d ) );
               if( i == 9 )buttonPanel.add( makeButton( "+", d ) );            
            }
         
            buttonPanel.add( makeButton( "0", d2 ) );
            buttonPanel.add( makeButton( ".", d ) );
            buttonPanel.add( makeButton( "=", d ) );
         }
      
      }
            
      return buttonPanel;
   }
   
   private JPanel displayPanel()
   {
      JPanel displayPanel = new JPanel( new FlowLayout( FlowLayout.LEFT ) );//GridLayout( 6,1 )
      displayPanel.setBackground( new java.awt.Color( 0,0,0,0 ) );
      displayPanel.setForeground( Color.WHITE );
      displayPanel.setPreferredSize( new Dimension( 250, 50 ) );     //size of the whole panel
      
      //TITLE
      {
         JLabel title = new JLabel( "Bignum Calculator                " );
         title.setForeground( Color.WHITE );
         title.setFont( font2 );
         
         displayPanel.add( title );
         
      }
      //Infix
      {
         JLabel infix = new JLabel( "InFix: " );
         infix.setForeground( Color.WHITE );
         infix.setFont( font2 );
         
         
         displayPanel.add( infix );
         
         infixBox = makeDisplayBox();
         displayPanel.add( infixBox );                                 //adds the text box into the panel
      }
      //Postfix
      {
         JLabel postfix = new JLabel( "PostFix: " );
         postfix.setForeground( Color.WHITE );
         postfix.setFont( font2 );
         
         displayPanel.add( postfix );
         
         postfixBox = makeDisplayBox();
         displayPanel.add( postfixBox );                                 //adds the text box into the panel
      }
      //Answer
      {
         JLabel answer = new JLabel( "Answer: " );
         answer.setForeground( Color.WHITE );
         answer.setFont( font2 );
         
         displayPanel.add( answer );
         answerBox = makeDisplayBox();
         displayPanel.add( answerBox );                                 //adds the text box into the panel
      }
            
      return displayPanel;
   }
   

   private JButton makeButton( String label, Dimension d )
   {
      
      JButton button = new JButton();
      button.setText( label );
      button.setForeground( Color.WHITE );
      
      button.setBackground( new java.awt.Color( 28,28,28 ) );
      
      button.setFont( font );
      
      button.setPreferredSize( new Dimension( d ) );
      MyListener listener = new MyListener(infixBox , postfixBox, answerBox);
      button.addActionListener(listener);
      return button;
   }
   
   private JTextField makeDisplayBox()
   {
      JTextField display = new JTextField( 17 );                   //making a text field 
         
      display.setEditable( true );                               //makes so we can input stuff to the text box
      //display.setComponentOrientation( ComponentOrientation.RIGHT_TO_LEFT ); //make the text start in the right
      display.setPreferredSize( new Dimension( 260, 40 ) );        //size of the text box
      display.setBackground( new java.awt.Color( 28,28,28 ) );                      //bgcolor
      display.setForeground( Color.WHITE );                      // txt color
      display.setFont( font3 ); //changing font style and size
      display.setText("0");
      return display;
   }

   public static void main( String[] args ) 
   {
      Calculator test = new Calculator();
   } 
}

class MyListener implements ActionListener
{
   private JTextField infixTextBox;
   private JTextField postfixTextBox;
   private JTextField answerTextBox;
   
      
   public MyListener( JTextField infix, JTextField postfix,JTextField answer  )
   {  
      infixTextBox = infix;
      postfixTextBox = postfix;
      answerTextBox = answer;
   }
   
   public void actionPerformed( ActionEvent event )
   {
      if( infixTextBox.getText().equals( "0" ) )
         infixTextBox.setText( "" );
      
      JButton button = ( JButton )event.getSource();
      if( button.getText().equals( "Del|<" ) )
      {
         infixTextBox.setText( 
             infixTextBox.getText().substring( 
                  0,infixTextBox.getText().length()-1 ) );
      }
      else if( button.getText().equals( "Clear" ) )
      {
         infixTextBox.setText( "0" );
         postfixTextBox.setText( "0" );
         answerTextBox.setText( "0" );
      }
      else if( button.getText().equals( "=" ) )
      {
         InfixToPostfix test = new InfixToPostfix( infixTextBox.getText() );
         Evaluator evaluatorTest = new Evaluator();
         
         String res = "" ;
         for (Iterator<String> itr = test.iterator();itr.hasNext();) {
            res += " " + itr.next(); 
         }
         postfixTextBox.setText( res );
         
         answerTextBox.setText( evaluatorTest.evaluate(infixTextBox.getText()));
      }
      else {
         String s = infixTextBox.getText() + button.getText();
         infixTextBox.setText(s);
      }
      
   }
   
}