package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;

import java.util.List;

/**
 * Created by alyswidan on 15/03/18.
 */
public interface MaryRegexOperator extends RegexOperator{

    NFA execute(List<NFA> operands);

}
