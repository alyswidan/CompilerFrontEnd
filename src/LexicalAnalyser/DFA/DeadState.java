package LexicalAnalyser.DFA;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.NFA.NFAState;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alyswidan on 18/03/18.
 */
public class DeadState extends DFAState {
    @Override
    public String toString() {
        return "DeadState";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DeadState;
    }
}
