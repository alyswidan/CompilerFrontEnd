package LexicalAnalyser;

import LexicalAnalyser.DFA.DFA;
import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAToDFAConverter;
import LexicalAnalyser.Regex.Regex;
import LexicalAnalyser.Regex.RegexElement;
import LexicalAnalyser.Regex.RegexParser;
import LexicalAnalyser.Regex.RegularDefinition;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

/**
 * Created by alyswidan on 16/03/18.
 */
public class Generator {


    NFA generate(String grammarPath){
        GrammarParser grammarParser = new GrammarParser();
        List<Regex> regexes = grammarParser.parseBareGrammar(grammarParser.parseFile(grammarPath));
        return NFA.newCombinedNFA(regexes);
    }

}
