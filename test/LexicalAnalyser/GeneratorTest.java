package LexicalAnalyser;

import LexicalAnalyser.DFA.DFA;
import LexicalAnalyser.DFA.DFASimulator;
import org.junit.jupiter.api.Test;

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
        DFASimulator simulator = new DFASimulator(dfa,"if(x>=10){this is awesome;}else if(x == 20){awesome}");
        while (simulator.hasNext()){
            System.out.println(simulator.next());
        }

    }
}