package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;

import java.util.List;

/**
 * Created by alyswidan on 15/03/18.
 */
public abstract class MaryRegexOperator extends RegexOperator{

    abstract NFA execute(List<NFA> operands);

}
