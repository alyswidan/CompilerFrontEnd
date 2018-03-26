package LexicalAnalyser.DFA;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by alyswidan on 26/03/18.
 */
class DFATest {
    @Test
    void addState() {
        DFA dfa = new DFA();
        DFAState state1 = new DFAState();
        DFAState state2 = new DFAState();
        dfa.addState(state2); //0
        dfa.addState(state1);//1


        dfa.setStartState(state1);
        System.out.println(dfa.getStates());
    }

}