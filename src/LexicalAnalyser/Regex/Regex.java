package LexicalAnalyser.Regex;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by alyswidan on 15/03/18.
 */
public class Regex implements Iterable<RegexElement> {
    protected String rawRegex;
    private boolean isPostfix = false;
    private List<RegexElement> elements;
    private String acceptingValue; // the value this regex accepts
    private int acceptingOrder;

    public Regex(String rawRegex){

        elements = new ArrayList<>();
        this.rawRegex = getUnescapedRegex(rawRegex).trim();
    }

    public Regex(String rawRegex, String acceptingValue){
        this(rawRegex);
        setAcceptingValue(acceptingValue);
    }

    public Regex(String rawRegex, String acceptingValue,int acceptingOrder){
        this(rawRegex);
        this.acceptingOrder = acceptingOrder;
        setAcceptingValue(acceptingValue);
    }

    private String getUnescapedRegex(String rawRegex){
        StringBuilder builder = new StringBuilder();
        boolean isEscaped = false;
        for(char curr : rawRegex.toCharArray()){
            if(curr == '\\'){
                isEscaped = true;
                continue;
            }

            if(isEscaped && (RegexOperatorFactory.isOperator(curr) || curr == 'L')){
                builder.append('\\');
            }
            builder.append(curr);
            isEscaped = false;
        }
        return builder.toString();
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
        Consumer<RegexElement> elementConsumer =  regexElement -> {builder.append(regexElement);elements.add(regexElement);};
        Deque<RegexElement> stack = new LinkedList<>();
        for (RegexElement Present : this) {
            if (Present instanceof RegularDefinition) {
                // this means its a charchter ex : a,b,letter, digit
                elementConsumer.accept(Present);
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
                            elementConsumer.accept(Operator);
                        }
                    }
                } else {
                    pushOperator(stack, Present, elementConsumer);
                }
            }
        }
        while (!stack.isEmpty()){
            RegexElement r = stack.removeFirst();
            elementConsumer.accept(r);

        }
        rawRegex = builder.toString();
        isPostfix = true;
    }

    private void pushOperator(Deque<RegexElement> stack, RegexElement Present, Consumer<RegexElement> consumer) {
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
                        consumer.accept(Operator);
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
        if(isPostfix())
            return elements.iterator();
        return new RegexIterator(this);
    }

    public int length() {
        return rawRegex.length();
    }

    public void setAcceptingValue(String acceptingValue) {
        this.acceptingValue = acceptingValue;
    }

    public String getAcceptingValue() {
        return acceptingValue;
    }

    @Override
    public String toString() {
        return rawRegex;
    }

    public int getAcceptingOrder() {
        return acceptingOrder;
    }
}
