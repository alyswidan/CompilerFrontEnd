package LexicalAnalyser.DFA;


import LexicalAnalyser.NFA.NFAState;
import LexicalAnalyser.Regex.RegularDefinition;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by zeina on 3/25/2018.
 */
class DFASimulatorTest {
    @Test
    void step() {

        DFAState A = new DFAState();
        DFAState B = new DFAState();
        DFAState C = new DFAState();
        DFAState D = new DFAState();
        DFAState E = new DFAState();

        RegularDefinition reg1= new RegularDefinition("a");
        RegularDefinition reg2= new RegularDefinition("b");
        A.addTransition(reg1,B);
        A.addTransition(reg2,C);
        B.addTransition(reg1,B);
        B.addTransition(reg2,D);
        C.addTransition(reg1,B);
        C.addTransition(reg2,C);
        D.addTransition(reg1,B);
        D.addTransition(reg2,E);
        E.addTransition(reg1,B);
        E.addTransition(reg2,C);

        DFA dfa = new DFA();
        dfa.addState(A);
        dfa.setStartState(A);
        dfa.addState(B);
        dfa.addState(C);
        dfa.addState(D);
        dfa.addState(E);

        DFASimulator dfas= new DFASimulator(dfa);
        System.out.println(dfa.getStates());
        dfas.step('a');
        dfas.step('b');
        dfas.step('a');
        dfas.step('a');
        dfas.step('a');
        dfas.step('a');
        dfas.step('a');
    }

}