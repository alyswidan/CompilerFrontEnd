package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;

/**
 * Created by alyswidan on 15/03/18.
 */
public interface BinaryRegexOperator extends RegexOperator{

    NFA execute(NFA leftOperand, NFA rightOperand);

}
