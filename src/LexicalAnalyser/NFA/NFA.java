package LexicalAnalyser.NFA;
import LexicalAnalyser.DFA.DeadState;
import LexicalAnalyser.Regex.*;
import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.BaseModels.StateGraph;
//import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alyswidan on 15/03/18.
 */
public class NFA extends StateGraph {


    public static NFA fromRegex(Regex regex){
        //uses Thompson's algorithm to convert the regex string to an nfa
        return new NFA();
    }

//    public static NFA fromMultiple(Collection<NFA> NFAs){
//        UnionOperator union = new UnionOperator();
//        return union.execute((List<NFA>) NFAs);
//        //return null;
//    }

    public Set<NFAState> getEpsilonClosure(NFAState state) {
        if(state == null){
            return new HashSet<>();
        }
        this.unVisitAll();
        return dfs(state);
    }

    public Set<NFAState> getEpsilonClosure(Set<NFAState> states){
        return states.stream()
                .map(this::getEpsilonClosure)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<NFAState> dfs(NFAState state) {
        if(state.isVisited()){
            return new HashSet<>();
        }
        state.visit();
        Set<NFAState> closure = new HashSet<>();
        closure.add(state);
        state.forEach((regDef, neighbour) ->{
            if (regDef instanceof EpsilonRegularDefinition && neighbour instanceof NFAState){
                closure.addAll(dfs((NFAState) neighbour));
            }
        } );

        return closure;
    }

    @Override
    public void addState(State state) {

        if (state instanceof NFAState) {
            NFAState nfaState = (NFAState) state;
            if (nfaState.isStart()) {
                if (getStartState() == null) {
                    setStartState(nfaState);
                } else {
                    Set<NFAState> states = new HashSet<>(Arrays.asList(nfaState, (NFAState) getStartState()));
                    NFAState newStart = NFAState.epsilonSource(states);
                    setStartState(newStart);
                    state = newStart;
                }
            }
            super.addState(state);
        }
    }


    public NFAState mergeAcceptStates(){
        Set<NFAState> acceptStates = getAcceptingStates().stream().map(state -> (NFAState)state).collect(Collectors.toSet());
        NFAState newEnd;
        if (acceptStates.size()>1){
            newEnd = NFAState.epsilonSink(acceptStates);
            acceptStates.forEach(acceptState -> acceptState.setAccepting(false));
        }else {

            newEnd = acceptStates.stream().findFirst().get();
        }
        return newEnd;
    }


}
