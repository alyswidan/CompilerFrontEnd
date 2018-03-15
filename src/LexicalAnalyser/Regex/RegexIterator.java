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
        char currentChar = regex.rawRegex.charAt(currentIndex);
        boolean entered = false;
        RegexElement regexElement = null;
        StringBuilder regDefCandidate = new StringBuilder();

        while(isOperator.negate().test(currentChar) &&  isRegDef.negate().test(regDefCandidate)) {
            entered = true;
            regDefCandidate.append(currentChar);

            currentChar = regex.rawRegex.charAt(++currentIndex);
        }


        if (!entered){
            regexElement = RegexOperatorFactory.getOperator(currentChar);
            currentIndex++;
        }
        else{
            if (isRegDef.test(regDefCandidate)){
                regexElement = RegularDefinitionsTable.get(regDefCandidate.toString());
            }
            else{
                System.out.println("exception");
                // TODO: 15/03/18 this should throw a parse exception to be created
            }
        }

        return regexElement;
    }
}
