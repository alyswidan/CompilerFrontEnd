package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;

/**
 * Created by alyswidan on 14/03/18.
 */
public class RegularDefinition extends Regex implements RegexElement {

/*
* Even though a regular definition is part of a regular expression
* it could be a regex it self
* */


    public RegularDefinition(String rawRegex) {
        super(rawRegex);
    }

    NFA getBasis(){
        return new NFA();
    }

    public String getRawValue(){
        return rawRegex;
    }
}
