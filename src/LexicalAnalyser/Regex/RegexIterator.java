package LexicalAnalyser.Regex;

import LexicalAnalyser.RegularDefinitionsTable;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by alyswidan on 15/03/18.
 */
public class RegexIterator implements Iterator<RegexElement> {
    private int currentIndex;
    private Regex regex;
    private RegexElement previousElement;
    private RegexElement pendingElement;

    public RegexIterator(Regex regex) {
        pendingElement = null;
        previousElement = null;
        currentIndex = 0;
        this.regex = regex;
    }

    @Override
    public boolean hasNext() {

        return pendingElement != null || currentIndex != regex.length();
    }

    @Override
    public RegexElement next() {
        Function<Integer,Character> getCharacter = i->regex.rawRegex.charAt(i);
        Predicate<Integer> inRange = i->i<regex.length();
        Predicate<Integer> isOperator = i->inRange.test(i) && RegexOperatorFactory.isOperator(getCharacter.apply(i))
                && !(!regex.isPostfix() && getCharacter.apply(i)=='.');
        Predicate<StringBuilder> isRegDef = s->RegularDefinitionsTable.containsKey(s.toString());
        Predicate<StringBuilder> isEpsilon = s-> s.toString().equals("\\L");
        Predicate<Integer> isEscape = i -> inRange.test(i) && getCharacter.apply(i) == '\\';
        Predicate<Integer> isSpace = i-> inRange.test(i) && getCharacter.apply(i) == ' ';
        Predicate<Character> isUnaryOp = c-> RegexOperatorFactory.getOperator(c) instanceof UnionOperator;
        Predicate<Character> isOpenBracket = c-> c == '(';

        if(pendingElement != null){
            RegexElement regexElement = pendingElement;
            pendingElement = null;
            return previousElement = regexElement;
        }



        char currentChar = getCharacter.apply(currentIndex);
        boolean entered = false;
        RegexElement regexElement = null;
        StringBuilder regDefCandidate = new StringBuilder();


        /*consume all spaces*/
        while(isSpace.test(currentIndex) && currentIndex<regex.length()){
            currentIndex++;
        }

        /*loop to collect regular definition names in a string*/
        while(  currentIndex < regex.length()&&
                isOperator.negate().test(currentIndex)
                && isRegDef.negate().test(regDefCandidate)
                && isEscape.negate().test(currentIndex)
                && isSpace.negate().test(currentIndex)) {

            entered = true;
            regDefCandidate.append(getCharacter.apply(currentIndex));
            currentIndex++;
        }
        if(inRange.test(currentIndex))
            currentChar = getCharacter.apply(currentIndex);
        /*if we exited because of an escaped character*/
        if(isEscape.test(currentIndex)){
            currentChar = getCharacter.apply(++currentIndex);
            if(currentChar == 'L') {
                regexElement = new EpsilonRegularDefinition();
            }
            else {
                regexElement = new RegularDefinition(""+currentChar);
            }
            currentIndex++;
        }

        /*if we didn't enter the loop then this must have been an operator*/
        else if (!entered){
            regexElement = RegexOperatorFactory.getOperator(currentChar);
            currentIndex++;
        }
        else{
            if (isRegDef.test(regDefCandidate)){
                regexElement = RegularDefinitionsTable.get(regDefCandidate.toString());
            }
            else{
                regexElement = new RegularDefinition(regDefCandidate.toString());
                RegularDefinitionsTable.put(regDefCandidate.toString(),(RegularDefinition) regexElement);
            }
        }


        /*if we need to insert a concatenation between this and the previous character
        * hold this character for now and return a concatenation operation
        * */
        if(!regex.isPostfix() && previousElement != null &&
          (previousElement instanceof UnaryRegexOperator || previousElement instanceof RegularDefinition
                  || previousElement instanceof ClosedBracketOperator) &&
          (regexElement instanceof RegularDefinition || regexElement instanceof OpenBracketOperator)){

            pendingElement = regexElement;
            regexElement = RegexOperatorFactory.getOperator('.');
        }

        return (previousElement = regexElement);
    }
}
