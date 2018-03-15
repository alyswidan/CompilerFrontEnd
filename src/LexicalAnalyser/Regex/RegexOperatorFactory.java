package LexicalAnalyser.Regex;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alyswidan on 15/03/18.
 */
public class RegexOperatorFactory {


    private Map<Character, RegexOperator> operators = new HashMap<>();

    public RegexOperatorFactory() {
        operators.put('+', new PlusClosureOperator());
        operators.put('*', new KleenClosureOperator());
        operators.put('.', new ConcatenationOperator());
        operators.put('|', new UnionOperator());
        operators.put('-', new RangeOperator());
    }

    RegexOperator getOperator(char operator){
        return operators.get(operator);
    }



}
