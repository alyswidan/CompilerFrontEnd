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
            if(RegexOperatorFactory.isOperator(Element)){// the first element is an operator
                 RegexOperator operator = RegexOperatorFactory.getOperator(Element);
                if(operator instanceof OpenBracketOperator){
                    stack.addFirst(operator);
                }
                else if(operator instanceof ClosedBracketOperator){
                    // we have to pop till we find '('
                    while (!stack.isEmpty()) { // as long as the stack is not empty and the stack element has a higher priority than the element
                        RegexOperator  Operator = stack.removeFirst();
                        if (Operator instanceof OpenBracketOperator)
                            break;
                        else{
                            output = output + Operator.getRawValue();
                        }
                    }
                }
                else{
                    int priority= stack.peekFirst().compareTo(operator);
                    if(priority >=0){
                        // the operator in stack is higher in priority than Element
                        stack.addFirst(operator);

                    }else{
                        // this means the operator in the stack is smaller than the Element

                        while (!stack.isEmpty() && stack.peekFirst().compareTo(operator)<0) { // as long as the stack is not empty and the stack element has a higher priority than the element
                            RegexOperator  Operator = stack.peekFirst();
                            if (Operator instanceof OpenBracketOperator)
                                break;
                            else{
                                Operator = stack.removeFirst();
                                output = output + Operator.getRawValue();
                            }
                        }
                        stack.addFirst(operator);

                    }
                }
            }
            else{
                if(i!=StringSize-1){
                    char NextElement = elements[i+1];
                    output += Element;
                    if(RegexOperatorFactory.isOperator(NextElement)){
                        RegexOperator operator = RegexOperatorFactory.getOperator(Element);
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





        if(isPostfix()){
            return;
        }

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
