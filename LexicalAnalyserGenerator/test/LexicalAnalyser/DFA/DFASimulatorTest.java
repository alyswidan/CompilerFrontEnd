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

        DFAState A = new DFAState("A");
        DFAState B = new DFAState("B");
        DFAState C = new DFAState("C");
        DFAState D = new DFAState("D");
        DFAState E = new DFAState("E");

        RegularDefinition a= new RegularDefinition("a");
        RegularDefinition b= new RegularDefinition("b");
        A.addTransition(a,B);
        A.addTransition(b,C);
        B.addTransition(a,B);
        B.addTransition(b,D);
        C.addTransition(a,B);
        C.addTransition(b,C);
        D.addTransition(a,B);
        D.addTransition(b,E);
        E.addTransition(a,B);
        E.addTransition(b,C);

        DFA dfa = new DFA();
        dfa.addState(A);
        dfa.setStartState(A);
        dfa.addState(B);
        dfa.addState(C);
        dfa.addState(D);
        dfa.addState(E);


    }

    @Test
    void testWithAcceptingValue(){
        //a dfa that accepts aa+ and ab+
        RegularDefinition a= new RegularDefinition("a");
        RegularDefinition b= new RegularDefinition("b");

        DFAState A = new DFAState("A");
        DFAState B = new DFAState("B");
        DFAState C = new DFAState("C");
        DFAState D = new DFAState("D");
        DeadState dead = new DeadState();
        DFA dfa = new DFA();


        A.addTransition(a,B);
        A.addTransition(b,dead);
        dfa.addState(A);
        dfa.setStartState(A);

        B.addTransition(a,D);
        B.addTransition(b,C);
        dfa.addState(B);

        C.addTransition(a,dead);
        C.addTransition(b,C);
        C.setAcceptingValue("ab+");
        dfa.addState(C);
        dfa.addAcceptingState(C);

        D.addTransition(a,D);
        D.addTransition(b,dead);
        D.setAcceptingValue("aa+");
        dfa.addState(D);
        dfa.addAcceptingState(D);

        dfa.addState(dead);

        System.out.println(dfa);

        String testString = "abbbaaa abba";
        DFAState currentState=null,previousState=null;
        DFASimulator sim = new DFASimulator(dfa,testString);
        while(sim.hasNext()){
            System.out.println(sim.next());
        }

    }


    @Test
    void multipleBackTraks(){
        //a dfa that accepts aa+ and ab+
        RegularDefinition a= new RegularDefinition("a");
        RegularDefinition b= new RegularDefinition("b");

        DFAState A = new DFAState("A");
        DFAState B = new DFAState("B");
        DFAState C = new DFAState("C");
        DFAState D = new DFAState("D");
        DFAState E = new DFAState("E");
        DFAState F = new DFAState("E");
        DFAState G = new DFAState("E");
        DFAState H = new DFAState("E");
        DeadState dead = new DeadState();
        DFA dfa = new DFA();


        A.addTransition(a,B);
        A.addTransition(b,F);
        dfa.addState(A);
        dfa.setStartState(A);

        B.addTransition(a,dead);
        B.addTransition(b,C);
        dfa.addState(B);

        C.addTransition(a,F);
        C.addTransition(b,D);
        C.setAcceptingValue("C");
        dfa.addState(C);
        dfa.addAcceptingState(C);

        D.addTransition(a,E);
        D.addTransition(b,dead);
        dfa.addState(D);


        E.addTransition(a,dead);
        E.addTransition(b,G);
        E.setAcceptingValue("E");
        dfa.addState(E);
        dfa.addAcceptingState(E);

        F.addTransition(a,G);
        F.addTransition(b,H);
        dfa.addState(F);

        G.addTransition(a,dead);
        G.addTransition(b,H);
        dfa.addState(G);

        H.addTransition(a,H);
        H.addTransition(b,dead);
        H.setAcceptingValue("H");
        dfa.addState(H);
        dfa.addAcceptingState(H);

        dfa.addState(dead);

        System.out.println(dfa);

        String testString = "abaaabbab";
        DFASimulator sim = new DFASimulator(dfa,testString);
        while(sim.hasNext()){
            System.out.println(sim.next());
        }

    }

}