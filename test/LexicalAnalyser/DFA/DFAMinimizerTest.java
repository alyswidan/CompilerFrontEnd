package LexicalAnalyser.DFA;

import LexicalAnalyser.Regex.RegularDefinition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by zeina on 3/25/2018.
 */
class DFAMinimizerTest {
    @Test
    void minimize() {

        DFAState A = new DFAState("A");
        DFAState B = new DFAState("B");
        DFAState C = new DFAState("C");
        DFAState D = new DFAState("D");
        DFAState E = new DFAState("E");

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

        DFAMinimizer mini= new DFAMinimizer();
        DFA ff= new DFA();
        ff=mini.Minimize(dfa);
        //System.out.println(ff);


    }

}