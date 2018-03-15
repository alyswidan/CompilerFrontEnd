package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;

/**
 * Created by alyswidan on 15/03/18.
 */
public class RangeOperator implements BinaryRegexOperator{

    /*
    * a class to represent the - operator as in A-Z, should return an NFA representing this expression
    * */

    @Override
    public NFA execute(NFA leftOperand, NFA rightOperand) {
        return null;
    }

}
