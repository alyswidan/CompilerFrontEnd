package LexicalAnalyser.BaseModels;

import LexicalAnalyser.DFA.DFA;
import LexicalAnalyser.DFA.DFAState;
import LexicalAnalyser.Regex.EpsilonRegularDefinition;
import LexicalAnalyser.Regex.RegularDefinition;

import javax.swing.table.TableRowSorter;
import java.util.*;

/**
 * Created by alyswidan on 14/03/18.
 */
public class StateGraph {
    private Map<State,State> states;
    private State startState;
    private State endState;
    private Set<RegularDefinition> language;
    private Set<State> acceptingStates;

    public StateGraph() {
        states = new HashMap<>();
        acceptingStates = new HashSet<>();
        language = new HashSet<>();
    }

    public State getStartState() {
        return startState;
    }

    public Set<State> getAcceptingStates() {
        return acceptingStates;
    }

    public void addState(State state){
        states.put(state,state);

        /*add all regular definitions on edges out of state to this graph's language*/
        state.forEach((regDef, s) -> {
            if(!(regDef instanceof EpsilonRegularDefinition))
                language.addAll(regDef.getParts());
        });

        if(state.isAccepting()){
            acceptingStates.add(state);
        }
        if(state.isStart()){
            startState = state;
        }

    }

    public void removeState(State state){
        states.remove(state);
    }

    public void addAll(Collection<State> states){
        states.forEach(this::addState);
    }

    public void unVisitAll() {
        states.forEach((state, state2) -> state.unVisit());
    }

    public boolean hasState(State state) {
        return states.containsKey(state);
    }

    public State getState(State state) {
        return states.get(state);
    }

    public void setStartState(State startState) {
        this.startState = startState;
    }

    public void setEndState(State endState) {
        this.endState = endState;
    }

    private StringBuilder DFSUtil(State state)
    {
        state.visit();

        StringBuilder builder = new StringBuilder();


        state.forEach((regDef,neighbour) ->{
            builder.append("from: ")
                    .append(state)
                    .append(" to: ")
                    .append(neighbour)
                    .append(" value: ")
                    .append(regDef)
                    .append("\n");
            if(!neighbour.isVisited()){
                builder.append(DFSUtil(neighbour));
            }
        });
        return builder;
    }

    public Set<RegularDefinition> getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        this.unVisitAll();
        return DFSUtil(getStartState()).toString();
    }
}
