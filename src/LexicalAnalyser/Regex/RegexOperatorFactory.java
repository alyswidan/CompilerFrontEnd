package LexicalAnalyser.Regex;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alyswidan on 15/03/18.
 */
public class RegexOperatorFactory {


    private static Map<Character, RegexOperator> operators;

    static {
        operators = new HashMap<>();
        operators.put('+', new PlusClosureOperator());
        operators.put('*', new KleenClosureOperator());
        operators.put('.', new ConcatenationOperator());
        operators.put('|', new UnionOperator());
        operators.put('-', new RangeOperator());
    }

    static RegexOperator getOperator(char operator){
        return operators.get(operator);
    }

    static boolean isOperator(char candidate){
        return operators.containsKey(candidate);
    }

}
