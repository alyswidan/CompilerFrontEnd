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
        dfa.addAcceptingState(E);
        dfa.addState(B);
        dfa.addState(C);
        dfa.addState(D);
        dfa.addState(E);

        DFAMinimizer mini= new DFAMinimizer();
        DFA ff;
        System.out.println("before \n"+dfa);

        ff=mini.Minimize(dfa);
        ff.getStates().forEach(s->{
            System.out.println(s.getTransitions());
        });
        System.out.println(ff.getStates());
        System.out.println("mini accept "+ff.getAcceptingStates());

    }

    @Test
    void simpleMinimizationTest(){
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
        dfa.addState(B);

        System.out.println(dfa.getStartState());
        System.out.println(dfa.getAcceptingStates());

        dfa.setStartState(A);
        System.out.println(dfa.getStartState());
        System.out.println(dfa.getAcceptingStates());

        dfa.addAcceptingState(B);

        System.out.println(dfa.getStartState());
        System.out.println(dfa.getAcceptingStates());

        System.out.println(dfa);

    }

}