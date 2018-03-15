package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;

import java.util.List;

/**
 * Created by alyswidan on 15/03/18.
 */
public class UnionOperator implements MaryRegexOperator {

    @Override
    public NFA execute(List<NFA> operands) {
        return null;
    }

    public String getRawValue(){
        return "|";
    }
}
