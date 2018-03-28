package LexicalAnalyser;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.DFA.DFA;
import LexicalAnalyser.DFA.DFAMinimizer;
import LexicalAnalyser.DFA.DFASimulator;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by alyswidan on 26/03/18.
 */
class GeneratorTest {
    @Test
    void generate() {

    }
    @Test
    void simulation(){
        Generator g = new Generator();
        DFA dfa = g.generate("temp.txt");

        DFASimulator simulator = new DFASimulator(dfa,"  boolean float int x >= <= == 0 12 > < 10*2 11+23 1/3 ) ( { } if else while = ; ,");

        while (simulator.hasNext()){
            System.out.println(simulator.next());
        }

    }
}