package LexicalAnalyser.Regex;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by alyswidan on 15/03/18.
 */
public class Regex implements Iterable<RegexElement>{
    protected String rawRegex;
    private boolean isPostfix = false;

    Regex(String rawRegex){
        this.rawRegex = rawRegex;
    }



    void toPostfix(){


        if(isPostfix()){
            return;
        }

        // new branch added as InfixToPostfix
        /*
        * perform infix to postfix conversion of the raw regex string to a
        * postfix regex string and replacing the value of raw regex with
        * this postfix expression
        */
        String output = "";
        int StringSize = this.length();
        Deque<RegexOperator> stack = new LinkedList<>();
           /* boolean  isoperator= RegexOperatorFactory.isOperator(element);
            if(isoperator==true){{

                RegexOperator operator = RegexOperatorFactory.getOperator(element);
                stack.pop().compareTo(operator);


            }
            }*/
        char elements [] = this.rawRegex.toCharArray();
        for( int i=0 ; i<StringSize;i++ ){
            char Element = elements[i];
           // System.out.println(Element);
            if(RegexOperatorFactory.isOperator(Element)){// the first element is an operator
                 RegexOperator operator = RegexOperatorFactory.getOperator(Element);
                if(operator instanceof OpenBracketOperator){
                    stack.addFirst(operator);
                }
                else if(operator instanceof ClosedBracketOperator){
                    // we have to pop till we find '('
                    while (!stack.isEmpty()) { // as long as the stack is not empty
                        RegexOperator  Operator = stack.removeFirst();
                        if (Operator instanceof OpenBracketOperator)
                            break;
                        else{
                            output = output + Operator.getRawValue();
                        }
                    }
                }
                else{

                    if(operator instanceof KleenClosureOperator || operator instanceof PlusClosureOperator){
                        if(i!=StringSize-1) {
                            int j = i;
                            char NextElement = elements[j + 1];
                            if (RegexOperatorFactory.isOperator(NextElement)) {

                                RegexOperator Operator = RegexOperatorFactory.getOperator(NextElement);
                                if (Operator instanceof OpenBracketOperator) {

                                    stack.addFirst(operator);
                                    operator=new ConcatenationOperator();
                                }

                            }
                            else{
                                stack.addFirst(operator);
                                operator=new ConcatenationOperator();

                            }
                        }

                    }

                    if(!stack.isEmpty()) {
                        if(stack.peekFirst() instanceof OpenBracketOperator){
                            stack.addFirst(operator);
                        }
                        else {
                            int priority = stack.peekFirst().compareTo(operator);
                            if (priority >= 0) {
                                // the operator in stack is higher in priority than Element
                                stack.addFirst(operator);

                            } else {

                                // this means the operator in the stack is smaller than the Element

                                while (!stack.isEmpty() && stack.peekFirst().compareTo(operator) < 0) { // as long as the stack is not empty and the stack element has a higher priority than the element
                                    RegexOperator Operator = stack.peekFirst();
                                    if (Operator instanceof OpenBracketOperator)
                                        break;
                                    else {
                                        Operator = stack.removeFirst();
                                        output = output + Operator.getRawValue();
                                    }
                                }
                                stack.addFirst(operator);

                            }
                        }
                    }
                    else {
                    stack.addFirst(operator);
                    }

                }
            }
            else{
                 output += Element;
                if(i!=StringSize-1){
                    int j=i;
                    char NextElement = elements[j+1];

                    if(RegexOperatorFactory.isOperator(NextElement)){
                        RegexOperator operator = RegexOperatorFactory.getOperator(NextElement);
                        if(operator instanceof OpenBracketOperator){

                            stack.addFirst(new ConcatenationOperator());
                        }
                    }
                    else{
                        stack.addFirst(new ConcatenationOperator());
                    }
                }

            }
        }

        while (!stack.isEmpty()) { // as long as the stack is not empty add whats rest
            RegexOperator Operator = stack.removeFirst();
              // System.out.println("d5l hnaaa ");
                output = output + Operator.getRawValue();
            }


        this.rawRegex=output;
        isPostfix=true;

    }

    public boolean isPostfix() {
        return isPostfix;
    }

    @Override
    public Iterator<RegexElement> iterator() {
        return new RegexIterator(this);
    }

    public int length(){
        return rawRegex.length();
    }



}
