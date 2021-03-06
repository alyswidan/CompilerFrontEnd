package LexicalAnalyser;

import LexicalAnalyser.DFA.DFA;
import LexicalAnalyser.DFA.DFAMinimizer;
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


    DFA generate(String grammarPath){
        GrammarParser grammarParser = new GrammarParser();
        List<Regex> regexes = grammarParser.parseBareGrammar(grammarParser.parseFile(grammarPath));
        NFAToDFAConverter converter = new NFAToDFAConverter();
        NFA nfa = NFA.newCombinedNFA(regexes);
//        System.out.println(nfa);
        DFA dfa = converter.convert(nfa);
        DFAMinimizer minimizer= new DFAMinimizer();
        DFA miniDFA = minimizer.Minimize(dfa);
        return miniDFA;
    }

}
