package LexicalAnalyser;

import LexicalAnalyser.Regex.Regex;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zeina on 3/17/2018.
 */
class GrammarParserTest {
    @Test

    void parseFileTest() {
        GrammarParser parser = new GrammarParser();
        List<GrammarTuple>pairs = parser.parseFile("temp.txt");
        System.out.println(pairs.stream().map(GrammarTuple::toString).collect(Collectors.joining("\n")));

    }

    @Test
    void parseBareGrammar() {
        GrammarParser parser = new GrammarParser();
        List<GrammarTuple> PairList=parser.parseFile("temp.txt");
        Collection<Regex> RegexList = parser.parseBareGrammar(PairList);
        System.out.println(RegexList
                            .stream()
                            .map(Regex::toString)
                            .collect(Collectors.joining("\n")));
        System.out.println("-----------------------------------");
        System.out.println(RegularDefinitionsTable
                            .regularDefinitions
                            .entrySet()
                            .stream()
                            .map(entry-> entry.getKey()+"="+entry.getValue().getParts())
                            .collect(Collectors.joining("\n")));
    }

}