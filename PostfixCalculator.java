import java.util.Stack;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class PostfixCalculator{
    static int pred(char x){
        /* to check the precedence of given operator by 
        returning value according to precedence
        */
        switch (x) {
            case '+': case '-':
                return 1;
            case '*': case '/':
                return 2;
            case '^':
                return 3;
            default:
                break;
        }
        return 0;  //return 0 if other than these 5 operators
    }
    //Method to convert infix to postfix
    static String In_to_Post(String Q){
        System.out.println("Infix Expression = " + Q);  //To display the infix expression
        Q = Q + ')';  //Adding ')' at end of infix
        String P = "";  //creating empty string to store Postfix expression
        Stack<Character> stack = new Stack<>();  //creating Stack object
        stack.push('(');  //adding '(' to empty stack
        int i = 0;  //intializing counter variable
        while ( !stack.isEmpty() ){  //while stack is not empty, perform following operations
            char c = Q.charAt(i); //storing string at index 'i' to character
            //if an operand is encountered then adding it to postfix
            if ( Character.isDigit(c) ){
                P = P + c;
                while( Character.isDigit(Q.charAt(i+1)) ){ //if more than 1 digit encountered
                    c = Q.charAt(i+1);
                    P = P + c;
                    i++;
                }
                P += ' '; //then add space after whole value
            }
            //if left paranthesis is encountered then puhing it to stack
            else if( c == '(' ){
                stack.push(c);
            }
            else if ( c == '+' || c == '-' || c == '*' || c == '/' || c == '^' ){  //if operator is encountered
                if ( pred(stack.peek()) >= pred(c) ){  //checking if precedence of operator at top of stack is greator than current operator
                    P = P +stack.pop(); //taking out higher precedence operator from stack and adding to postfix
                }
                stack.push(c);  //adding current operator to stack
            }
            // if right paranthesis is encountered than
            // taking out elements from stack until left paranthesis is encountered
            else if( c == ')' ){
                while ( stack.peek() != '('){
                    P = P + stack.pop();
                }
                stack.pop();  //removing left paranthesis
            }
            i++;  //increamenting counter
        }
        return Postfix_Evl(P); //calling postfix evaluation method
    }
    static String Postfix_Evl(String P){
        Stack<Integer> stk = new Stack<>();  //object for stack class
        int i = 0;
        // Scan all characters one by one
        while( i < P.length())
        {  //scanning P from left to right
            char c = P.charAt(i);  //storing string at index 'i' to character
            //to push numbers in stack
            if(c == ' ')
            i++;
              
            // If the scanned character is an operand 
            // (number here),extract the number
            // Push it to the stack.
            else if(Character.isDigit(c))
            {
                int n = 0;
                  
                //extract the characters and store it in num
                while(Character.isDigit(c))
                {
                    n = n*10 + (int)(c-'0');
                    i++;
                    c = P.charAt(i);
                }                
  
                //push the number in stack
                stk.push(n);
            }
            /*
            if operator is encountered then 
            taking aout last two num and 
            applying operator on it
            */
            else{
                int A = stk.pop();
                int B = stk.pop();
                switch(c){
                    case '+':
                        stk.push(B+A);
                        break;
                    case '-':
                        stk.push(B-A);
                        break;
                    case '*':
                        stk.push(B*A);
                        break;
                    case '/':
                        stk.push(B/A);
                        break;
                }i++;
            }
        }
        String value = ""+stk.pop(); //converting from int to string and Storing final value after evaluation
        return value;   
    }
}
class Evaluator extends Frame implements ActionListener{    
    Button calculate,clearAll; 
    JLabel L1,L2,L3;
    JTextField infix,postfix; 
    public Evaluator(){
        /* Using labels to print text on GUI */
        L1 = new JLabel("Enter the Infix Expression:");
        L1.setBounds(20, 80, 200, 30);
        L2 = new JLabel("After Postfix Evaluation: ");
        L2.setBounds(20,200,140,30);
        L3 = new JLabel("Result ");
        L3.setBounds(20,240,100,30);
        /* Using Buttons to perform action on clicking */
        calculate = new Button("Calculate");
        calculate.setBounds(120,170,200,30);
        calculate.addActionListener(this);
        clearAll = new Button("Clear");
        clearAll.setBounds(120,300,140,30);
        clearAll.addActionListener(this);
        /* Using textField to take input and print output */
        infix = new JTextField();
        infix.setBounds(120, 120, 200, 30);
        postfix = new JTextField();
        postfix.setBounds(120, 240, 140, 30);
        /* Creating border around text field */
        Border bord = BorderFactory.createLineBorder(Color.BLACK, 3);
        infix.setBorder(bord);
        postfix.setBorder(bord);
        /* Adding all elements on frame */
        add(L1);add(L2);add(L3);
        add(calculate);add(clearAll);
        add(infix);add(postfix);
        /*setting Frame*/
        setBackground(Color.pink);
        setSize(500,500);
        setLayout(null);
        setVisible(true);
        setTitle("Postfix_Calculator");
    }
    /* method to clear every thing on text fields*/
    public void clearAll(){
        JTextField[] list = {infix,postfix};
        for(JTextField field : list){
            field.setText("");
        }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        String str = e.getActionCommand(); //to get what button is pressed
        if(str.equals("Calculate")){  //if calculate button is pressed
            String inf = infix.getText(); //getting input from first field
            String post = PostfixCalculator.In_to_Post(inf); //calling evaluation methods and storing its data
            postfix.setText(post); //printing the stored data on second text field
        }
        else if(str.equals("Clear")){
            clearAll();  //calling clear method
        }
    }
    public static void main(String[] args){
        new Evaluator(); //calling gui containing file
    }
}
