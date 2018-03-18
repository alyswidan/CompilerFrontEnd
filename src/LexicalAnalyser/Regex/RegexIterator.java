package LexicalAnalyser.Regex;

import LexicalAnalyser.RegularDefinitionsTable;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * Created by alyswidan on 15/03/18.
 */
public class RegexIterator implements Iterator<RegexElement> {
    private int currentIndex;
    private Regex regex;

    public RegexIterator(Regex regex) {
        if(!regex.isPostfix()){
            regex.toPostfix();
        }
        currentIndex = 0;
        this.regex = regex;
    }

    @Override
    public boolean hasNext() {
        return currentIndex != regex.length();
    }

    @Override
    public RegexElement next() {

        Predicate<Character> isOperator = RegexOperatorFactory::isOperator;
        Predicate<StringBuilder> isRegDef = s->RegularDefinitionsTable.containsKey(s.toString());
        Predicate<StringBuilder> isEpsilon = s-> s.toString().equals("\\L");
        Predicate<Character> isEscape = c -> c == '\\';
        Predicate<Character> isSpace = c-> c == ' ';

        char currentChar = regex.rawRegex.charAt(currentIndex);
        boolean entered = false;
        RegexElement regexElement = null;
        StringBuilder regDefCandidate = new StringBuilder();

        while(isSpace.test(currentChar) && hasNext()){
            currentChar = regex.rawRegex.charAt(++currentIndex);
        }

        while(isOperator.negate().test(currentChar)
                && isRegDef.negate().test(regDefCandidate)
                && isEscape.negate().test(currentChar)) {
            entered = true;
            regDefCandidate.append(currentChar);
            currentChar = regex.rawRegex.charAt(++currentIndex);
        }

        if(isEscape.test(currentChar)){
            currentChar = regex.rawRegex.charAt(++currentIndex);
            if(currentChar == 'L') {
                regexElement = new EpsilonRegularDefinition();

            }
            else {
                regexElement = new RegularDefinition(""+currentChar);
            }
            currentIndex++;
        }
        else if (!entered){
            regexElement = RegexOperatorFactory.getOperator(currentChar);
            currentIndex++;
        }
        else{

            if (isRegDef.test(regDefCandidate)){
                regexElement = RegularDefinitionsTable.get(regDefCandidate.toString());
            }
            else{
                try {
                    regexElement = new RegularDefinition(regDefCandidate.toString());
                    RegularDefinitionsTable.put(regDefCandidate.toString(),(RegularDefinition) regexElement);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        return regexElement;
    }
}
