package LexicalAnalyser.NFA;
import LexicalAnalyser.Regex.ConcatenationOperator;
import LexicalAnalyser.Regex.EpsilonRegularDefinition;
import LexicalAnalyser.Regex.Regex;
import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.BaseModels.StateGraph;
import LexicalAnalyser.Regex.UnionOperator;
import org.jetbrains.annotations.NotNull;

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

    public Set<State> getEpsilonClosure(State state) {
        if(!this.hasState(state))
            return null;
        this.unVisitAll();
        return dfs(state);
    }

    private Set<State> dfs(State state) {
        if(state.isVisited()){
            return new HashSet<>();
        }
        state.visit();
        Set<State> closure = new HashSet<>();
        closure.add(state);
        state.getTransitions().forEach((regDef, neighbour) ->{
            if (regDef instanceof EpsilonRegularDefinition){
                closure.addAll(dfs(neighbour));
            }
        } );

        return closure;
    }

    @Override
    public void addState(State state) {
        if(state.isStart()){
            if(getStartState() == null){
                super.setStartState(state);
            }
            else{
                Set<State> states = new HashSet<>(Arrays.asList(state, getStartState()));
                NFAState newStart = NFAState.epsilonSource(states);
                setStartState(newStart);
                state = newStart;
            }
        }
        super.addState(state);
    }

    public NFAState mergeAcceptStates(){
        Set<State> acceptStates = getAcceptingStates();
        NFAState newEnd;
        if (acceptStates.size()>1){
            newEnd = NFAState.epsilonSink(getAcceptingStates());
            acceptStates.forEach(acceptState -> acceptState.setAccepting(false));
        }else {
            newEnd = (NFAState) getAcceptingStates();
        }
        return newEnd;
    }
}
