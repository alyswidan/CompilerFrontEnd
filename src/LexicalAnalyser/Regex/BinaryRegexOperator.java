package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;

/**
 * Created by alyswidan on 15/03/18.
 */
public abstract class BinaryRegexOperator extends RegexOperator{

    abstract NFA execute(NFA leftOperand, NFA rightOperand);

}
