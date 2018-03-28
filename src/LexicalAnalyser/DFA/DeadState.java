package LexicalAnalyser.DFA;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.NFA.NFAState;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alyswidan on 18/03/18.
 */
public class DeadState extends DFAState {
    public DeadState() {
        super();
        setName("dead");
    }

    @Override
    public String toString() {
        return "DeadState";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DeadState;
    }

    @Override
    public boolean isAccepting() {
        return false;
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
