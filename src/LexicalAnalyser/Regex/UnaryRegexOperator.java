package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;

/**
 * Created by alyswidan on 15/03/18.
 */
public interface UnaryRegexOperator extends RegexOperator{

    NFA execute(NFA operand);

}
