package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;

import java.util.List;

/**
 * Created by alyswidan on 15/03/18.
 */
public class UnionOperator extends BinaryRegexOperator {


    public UnionOperator() {
        priority = RegexOperator.MINPRIORITY + 3;
    }


    public String getRawValue(){
        return "|";
    }

    @Override
    public NFA execute(NFA leftOperand, NFA rightOperand) {
        return null;
    }


}
