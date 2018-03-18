package LexicalAnalyser.Regex;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by alyswidan on 15/03/18.
 */
public class Regex implements Iterable<RegexElement> {
    protected String rawRegex;
    private boolean isPostfix = false;

    Regex(String rawRegex) {


        this.rawRegex = rawRegex;
    }


    void toPostfix() {


        if (isPostfix()) {
            return;
        }

        // new branch added as InfixToPostfix
        /*
         * perform infix to postfix conversion of the raw regex string to a
         * postfix regex string and replacing the value of raw regex with
         * this postfix expression
         */

        /*

        int StringSize = this.length();
        String output = "";
        Deque<RegexOperator> stack = new LinkedList<>();
           /* boolean  isoperator= RegexOperatorFactory.isOperator(element);
            if(isoperator==true){{

                RegexOperator operator = RegexOperatorFactory.getOperator(element);
                stack.pop().compareTo(operator);


            }
            }*/
      /*  char elements [] = this.rawRegex.toCharArray();
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

                    if(operator instanceof KleeneClosureOperator || operator instanceof PlusClosureOperator)
                    {
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
        isPostfix=true;*/

        String output = "";
        Deque<RegexElement> stack = new LinkedList<>();
        RegexElement Previous = null;
        RegexElement dot = null;
        for (RegexElement Present : this) {

            if (Previous != null) {

                if (Previous instanceof KleeneClosureOperator || Previous instanceof PlusClosureOperator || Previous instanceof RegularDefinition) {

                    if (Present instanceof RegularDefinition) {
                        dot = new ConcatenationOperator();//!!!! where will i put the output(b:character) **

                    } else {

                        if (Present instanceof OpenBracketOperator) {

                            dot = new ConcatenationOperator(); // where will put the open bracket
                        }

                    }
                    checkPriority(stack, dot, output);


                }

            }


            if (Present instanceof RegularDefinition) {
                // this means its a charchter ex : a,b,letter, digit

                output += Present;


            } else {
                // this means this is an operator
                if (Present instanceof OpenBracketOperator) {
                    stack.addFirst(Present);
                } else if (Present instanceof ClosedBracketOperator) {
                    // we have to pop till we find '('
                    while (!stack.isEmpty()) { // as long as the stack is not empty
                        RegexElement Operator = stack.removeFirst();
                        if (Operator instanceof OpenBracketOperator)
                            break;
                        else {
                            output = output + Operator.getRawValue();

                        }
                    }
                }
                checkPriority(stack, Present, output);


            }

        }
    }

    private void checkPriority(Deque<RegexElement> stack, RegexElement Present, String output) {
        if (!stack.isEmpty()) {
            if (stack.peekFirst() instanceof OpenBracketOperator) {
                stack.addFirst(Present);
            } else {
                int priority = ((RegexOperator) stack.peekFirst()).compareTo((RegexOperator) Present);
                if (priority >= 0) {
                    // the operator in stack is higher in priority than Element
                    stack.addFirst(Present);

                } else {

                    // this means the operator in the stack is smaller than the Element

                    while (!stack.isEmpty() && ((RegexOperator) stack.peekFirst()).compareTo((RegexOperator) Present) < 0) { // as long as the stack is not empty and the stack element has a higher priority than the element
                        RegexElement Operator = stack.peekFirst();
                        if (Operator instanceof OpenBracketOperator)
                            break;
                        else {
                            Operator = stack.removeFirst();
                            output = output + Operator.getRawValue();
                        }
                    }
                    stack.addFirst(Present);

                }


            }

        } else {
            stack.addFirst(Present);
        }


    }


    public boolean isPostfix() {
        return isPostfix;
    }

    @Override
    public Iterator<RegexElement> iterator() {
        return new RegexIterator(this);
    }

    public int length() {
        return rawRegex.length();
    }


}
