package LexicalAnalyser.BaseModels;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void setStartState(State startState) {
        this.startState = startState;
    }

    public void setEndState(State endState) {
        this.endState = endState;
    }

    public void dsf(){

    }

    public void getEdges(){

    }
}
