package LexicalAnalyser.Regex;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Predicate;

/**
 * Created by alyswidan on 15/03/18.
 */
public class Regex implements Iterable<RegexElement> {
    protected String rawRegex;
    private boolean isPostfix = false;

    Regex(String rawRegex) {


        this.rawRegex = rawRegex;
    }

    void oldStuff() {
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

        StringBuilder builder = new StringBuilder();
        Deque<RegexElement> stack = new LinkedList<>();
        for (RegexElement Present : this) {

            if (Present instanceof RegularDefinition) {
                // this means its a charchter ex : a,b,letter, digit
                builder.append(Present);
            } else {
                // this means this is an operator
                /*
                * note: this is repeated in the function push operator so I removed it from the function
                * */
                if (Present instanceof OpenBracketOperator) {
                    stack.addFirst(Present);
                } else if (Present instanceof ClosedBracketOperator) {
                    // we have to pop till we find '('
                    while (!stack.isEmpty()) { // as long as the stack is not empty
                        RegexElement Operator = stack.removeFirst();
                        if (Operator instanceof OpenBracketOperator) {
                            break;
                        } else {
                            builder.append(Operator);
                        }
                    }
                } else {
                    pushOperator(stack, Present, builder);
                }
            }
        }
        while (!stack.isEmpty()){
            builder.append(stack.removeFirst());
        }
        rawRegex = builder.toString();
    }

    private void pushOperator(Deque<RegexElement> stack, RegexElement Present, StringBuilder builder) {
        /*
        * Pops the stack until it's top has a lower priority than the current element
        *
        * */

        Predicate<RegexElement> stackPriorityIsHigherThan = regexElement -> ((RegexOperator) stack.peekFirst()).compareTo((RegexOperator) regexElement) == -1;

        if (!stack.isEmpty()) {
            if (stackPriorityIsHigherThan.negate().test(Present)) {
                // the present element has a higher priority than top of the stack
                stack.addFirst(Present);
            } else {
                // the present element has a lower priority than the top of the stack
                while (!stack.isEmpty() && stackPriorityIsHigherThan.test(Present)) {
                    // as long as the stack is not empty and the stack element has
                    // a higher priority than the element
                    RegexElement Operator = stack.peekFirst();
                    if (Operator instanceof OpenBracketOperator)
                        break;
                    else {
                        Operator = stack.removeFirst();
                        builder.append(Operator);
                    }
                }
                stack.addFirst(Present);
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
