package LexicalAnalyser.NFA;

import LexicalAnalyser.BaseModels.State;

import java.util.Collection;
import java.util.List;

/**
 * Created by alyswidan on 16/03/18.
 */
public class NFAState extends State {

    public List<State> getEpsilonClosure(State state) {
        return null;
    }

    public static NFAState epsilonSink(Collection<NFAState> inStates){
        NFAState thisState = new NFAState();

    }
}
