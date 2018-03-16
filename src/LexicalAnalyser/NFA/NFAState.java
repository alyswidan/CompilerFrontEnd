package LexicalAnalyser.NFA;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.Regex.EpsilonRegularDefinition;

import java.util.Collection;
import java.util.List;

/**
 * Created by alyswidan on 16/03/18.
 */
public class NFAState extends State {

    public NFAState(boolean isAccepting, boolean isStart) {
        super(isAccepting, isStart);
    }

    public NFAState(){
        super();
    }
    public Collection<State> getEpsilonClosure(State state) {
        return null;
    }

    public static NFAState epsilonSink(Collection<NFAState> inStates){
        NFAState thisState = new NFAState();
        inStates.forEach(inState -> inState.addTransition(new EpsilonRegularDefinition(),thisState));
        return thisState;
    }

    public static NFAState epsilonSource(Collection<NFAState> outStates){
        NFAState thisState = new NFAState();
        outStates.forEach(outState -> thisState.addTransition(new EpsilonRegularDefinition(),outState));
        return thisState;
    }
}
