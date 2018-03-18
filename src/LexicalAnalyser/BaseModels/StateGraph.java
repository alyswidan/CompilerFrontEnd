package LexicalAnalyser.BaseModels;

import LexicalAnalyser.Regex.RegularDefinition;

import javax.swing.table.TableRowSorter;
import java.util.*;

/**
 * Created by alyswidan on 14/03/18.
 */
public class StateGraph {
    private Set<State> states;
    private State startState;
    private State endState;

    private Set<State> acceptingStates;

    public StateGraph() {
        states = new HashSet<>();
        acceptingStates = new HashSet<>();
    }

    public State getStartState() {
        return startState;
    }

    public Set<State> getAcceptingStates() {
        return acceptingStates;
    }

    public void addState(State state){
        states.add(state);
        if(state.isAccepting()){
            acceptingStates.add(state);
        }
        if(state.isStart()){
            startState = state;
        }

    }


    public void unVisitAll() {
        states.forEach(State::unVisit);
    }

    public boolean hasState(State state) {
        return states.contains(state);
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

        Map<RegularDefinition, State> adj = state.getTransitions();
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<RegularDefinition, State> entry : adj.entrySet())
        {
            builder.append("from: ")
                    .append(state)
                    .append(" to: ")
                    .append(entry.getValue())
                    .append(" value: ")
                    .append(entry.getKey())
                    .append("\n");
            if(!entry.getValue().isVisited()){
                builder.append(DFSUtil(entry.getValue()));
            }
        }
        return builder;
    }


    @Override
    public String toString() {
        this.unVisitAll();
        return DFSUtil(getStartState()).toString();
    }
}
