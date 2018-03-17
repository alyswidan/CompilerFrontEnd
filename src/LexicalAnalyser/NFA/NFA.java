package LexicalAnalyser.NFA;
import LexicalAnalyser.Regex.ConcatenationOperator;
import LexicalAnalyser.Regex.Regex;
import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.BaseModels.StateGraph;
import LexicalAnalyser.Regex.UnionOperator;

import java.util.*;

/**
 * Created by alyswidan on 15/03/18.
 */
public class NFA extends StateGraph {

    public static NFA fromRegex(Regex regex){
        //uses Thompson's algorithm to convert the regex string to an nfa
        return new NFA();
    }

    public static NFA fromMultiple(Collection<NFA> NFAs){
        UnionOperator union = new UnionOperator();
        return union.execute((List<NFA>) NFAs);
    }

    @Override
    public void addState(State state) {
        if(state.isStart()){
            Set<State> states = new HashSet<>(Arrays.asList(state, getStartState()));
            states.remove(null);
            NFAState newStart = NFAState.epsilonSource(states);
            setStartState(newStart);
            state = newStart;
        }
        super.addState(state);
    }

    public NFAState mergeAcceptStates(){
        Set<State> acceptStates = super.getAcceptingStates();
        NFAState newEnd;
        if (acceptStates.size()>1){
            newEnd = NFAState.epsilonSink(super.getAcceptingStates());
            acceptStates.forEach(acceptState -> acceptState.setAccepting(false));
        }else {
            newEnd = (NFAState) super.getAcceptingStates();
        }
        return newEnd;
    }
}
