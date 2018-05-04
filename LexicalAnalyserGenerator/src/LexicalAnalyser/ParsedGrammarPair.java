package LexicalAnalyser;

import LexicalAnalyser.Regex.RegexElement;

/**
 * Created by alyswidan on 16/03/18.
 */
public class ParsedGrammarPair {

    /*
    * represents a key value pair of grammar elemnts after being converted into regex objects
    * */

    RegexElement value;
    String key;

    public ParsedGrammarPair(RegexElement value, String key) {
        this.value = value;
        this.key = key;
    }
}
