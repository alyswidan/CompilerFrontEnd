package LexicalAnalyser.NFA;
import LexicalAnalyser.DFA.DeadState;
import LexicalAnalyser.Exceptions.StateNotInGraphException;
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
        RegexParser parser = new RegexParser();
        return parser.parse(regex);
    }

    public static NFA newCombinedNFA(List<Regex> regex){
        List<NFA> nfas = regex.stream().map(NFA::fromRegex).collect(Collectors.toList());
        return (new MultiUnionOperator()).execute(nfas);
    }

    public Set<NFAState> getEpsilonClosure(NFAState state) {
        if(state == null){
            return new HashSet<>();
        }
        this.unVisitAll();
        Set<NFAState> closure = dfs(state);
        return closure;
    }

    public Set<NFAState> getEpsilonClosure(Set<NFAState> states){
        Set<NFAState> s = states.stream()
                .map(this::getEpsilonClosure)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        return s;
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
    public void setStartState(State startState) {
        if(startState instanceof NFAState){
            NFAState nfaState = (NFAState) startState;
            if (getStartState() == null) {
                super.setStartState(nfaState);
            }
            else {
                Set<NFAState> states = new HashSet<>(Arrays.asList(nfaState, (NFAState) getStartState()));
                NFAState newStart = NFAState.epsilonSource(states);
                super.setStartState(newStart);
            }
        }
    }

    public NFAState mergeAcceptStates() {
        Set<NFAState> acceptStates = getAcceptingStates().stream()
                                                         .map(state -> (NFAState)state)
                                                         .collect(Collectors.toSet());
        NFAState newEnd;
        if (acceptStates.size()>1){
            newEnd = NFAState.epsilonSink(acceptStates);
            acceptStates.forEach(this::removeAcceptingState);
            addState(newEnd);
            addAcceptingState(newEnd);
        }else {
            newEnd = acceptStates.stream().findFirst().get();
        }
        return newEnd;
    }
}
