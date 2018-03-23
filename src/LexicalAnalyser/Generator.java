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

    DFA generate(Path grammarPath){
//        GrammarParser grammarParser = new GrammarParser();
//        RegexParser regexParser = new RegexParser();
//        NFAToDFAConverter NFAToDFA = new NFAToDFAConverter();
//
//        Collection<BareGrammarPair> bareGrammarPairs = grammarParser.parseFile(grammarPath);
//        Collection<Regex> parsedGrammar = grammarParser.parseBareGrammar(bareGrammarPairs);
//        Collection<NFA>NFAs = regexParser.parseAll(parsedGrammar);
//        NFA combinedNFA = NFA.fromMultiple(NFAs);
//        DFA dfa = NFAToDFA.convert(combinedNFA);

        //return dfa;
        return null;
    }

}
