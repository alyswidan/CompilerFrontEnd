package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;

/**
 * Created by alyswidan on 15/03/18.
 */
public class ConcatenationOperator extends BinaryRegexOperator{


    public ConcatenationOperator() {
        priority = RegexOperator.MINPRIORITY + 2;
    }

    @Override
    public NFA execute(NFA leftOperand, NFA rightOperand) {
        return null;
    }

    public String getRawValue(){
        return ".";
    }
}
