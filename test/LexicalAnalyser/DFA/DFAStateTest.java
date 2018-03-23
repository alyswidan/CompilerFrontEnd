package LexicalAnalyser.DFA;

import LexicalAnalyser.NFA.NFAState;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by alyswidan on 20/03/18.
 */
class DFAStateTest {
    @Test
    void hasState(){
        Set<NFAState> s1 = new HashSet<>();
        Set<NFAState> s2 = new HashSet<>();
        Set<NFAState> s3 = new HashSet<>();
        Set<NFAState> s4 = new HashSet<>();
        Set<NFAState> s5 = new HashSet<>();


        for (int i = 0; i <=10 ; i++) {
            s1.add(new NFAState(""+i));
        }

        for (int i = 0; i <=2 ; i++) {
            s5.add(new NFAState(""+i));
        }

        for (int i = 0; i <=10 ; i+=2) {
            s2.add(new NFAState(""+i));
        }

        for (int i = 5; i <=10 ; i++) {
            s3.add(new NFAState(""+i));
        }

        for (int i = 10; i <=15 ; i++) {
            s4.add(new NFAState(""+i));
        }


        DFAState ds1 = new DFAState(s1);
        DFAState ds2 = new DFAState(s2);
        DFAState ds3 = new DFAState(s3);
        DFAState ds4 = new DFAState(s4);
        DFAState ds5 = new DFAState(s5);

        DFA dfa = new DFA();
        dfa.addState(ds1);
        dfa.addState(ds2);
        dfa.addState(ds3);
        dfa.addState(ds4);
        dfa.addState(ds4);
        dfa.addState(ds4);


        System.out.println(dfa.hasState(ds5));


    }

}