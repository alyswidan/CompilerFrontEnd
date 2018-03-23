package LexicalAnalyser.NFA;

import LexicalAnalyser.DFA.DFA;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by alyswidan on 18/03/18.
 */
class NFAToDFAConverterTest {
    static List<NFAState> states ;
    static NFA graph = new NFA();

    @BeforeAll
    static void Setup(){

        states = new ArrayList<>();
        states.add(new NFAState(false,true,"0"));
        for (int i = 1; i <=10 ; i++) {
            states.add(new NFAState(""+i));
        }


        states.get(0).addTransition("\\L",states.get(1));
        states.get(0).addTransition("\\L",states.get(7));

        states.get(1).addTransition("\\L",states.get(2));
        states.get(1).addTransition("\\L", states.get(4));

        states.get(2).addTransition("a", states.get(3));

        states.get(4).addTransition("b", states.get(5));

        states.get(3).addTransition("\\L", states.get(6));

        states.get(5).addTransition("\\L", states.get(6));

        states.get(6).addTransition("\\L", states.get(7));
        states.get(6).addTransition("\\L", states.get(1));
        states.get(6).addTransition("a",states.get(10));

        states.get(7).addTransition("a", states.get(8));

        states.get(8).addTransition("b-f", states.get(9));

        states.get(9).addTransition("b", states.get(10));

        states.get(10).setAccepting(true);


        graph = new NFA();
        states.forEach(graph::addState);

    }
    @Test
    void convert() {
        NFAToDFAConverter converter = new NFAToDFAConverter();
        DFA dfa = converter.convert(graph);
        System.out.println(dfa);

    }

}