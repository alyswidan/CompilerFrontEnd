package LexicalAnalyser.NFA;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by alyswidan on 18/03/18.
 */
class NFATest {
    static List<NFAState> states ;
    static NFA graph = new NFA();

    @BeforeAll
    static void Setup(){
        states = new ArrayList<>();
        states.add(new NFAState("0"));
        for (int i = 1; i <=5 ; i++) {
            states.add(new NFAState(""+i));
        }
        states.get(0).addTransition("\\L",states.get(1));
        states.get(0).addTransition("\\L",states.get(3));
        states.get(0).addTransition("a",states.get(1));
        states.get(1).addTransition("\\L", states.get(2));
        states.get(3).addTransition("\\L", states.get(4));
        states.get(5).addTransition("a", states.get(3));
        states.get(5).addTransition("\\L", states.get(4));
        states.get(4).addTransition("\\L", states.get(1));
        graph = new NFA();
        states.forEach(graph::addState);
    }
    @Test
    void getEpsilonClosure() {
        Set<NFAState> closure = graph.getEpsilonClosure(states.get(0));
        System.out.println(closure);
    }

    @Test
    void getEpsilonBatch(){
        Set<NFAState> testStates = new HashSet<>(Arrays.asList(states.get(0),states.get(5)));
        System.out.println(graph.getEpsilonClosure(testStates));
    }

}