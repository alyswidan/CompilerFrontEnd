package LexicalAnalyser;

import LexicalAnalyser.Regex.Regex;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by zeina on 3/17/2018.
 */
class GrammarParserTest {
    @Test

    void parseFileTest() {
        GrammarParser parser = new GrammarParser();
        List<BareGrammarPair>pairs = parser.parseFile("temp.txt");
        System.out.println(pairs.stream().map(BareGrammarPair::toString).collect(Collectors.joining("\n")));

//        String x = "asddas";
//        assert x.equals("abc");
    }

    @Test
    void parseBareGrammar() {
        GrammarParser parser = new GrammarParser();
        List<BareGrammarPair> PairList=parser.parseFile("temp.txt");
        Collection<Regex> RegexList = parser.parseBareGrammar(PairList);
    }

}