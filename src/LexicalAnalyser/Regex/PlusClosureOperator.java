package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;

/**
 * Created by alyswidan on 15/03/18.
 */
public class PlusClosureOperator extends UnaryRegexOperator {

    public PlusClosureOperator() {

        priority = RegexOperator.MINPRIORITY;
    }

    @Override
    public NFA execute(NFA operand) {
        return null;
    }

    public String getRawValue(){
        return "+";
    }

}
