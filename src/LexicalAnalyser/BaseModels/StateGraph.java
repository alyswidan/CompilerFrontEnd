package LexicalAnalyser.BaseModels;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by alyswidan on 14/03/18.
 */
public class StateGraph {
    private Set<State> states;
    private Set<State> startStates;
    private Set<State> acceptingStates;

    public StateGraph() {
        states = new HashSet<>();
        acceptingStates = new HashSet<>();
        startStates = new HashSet<>();
    }

    void addState(State state){
        states.add(state);
        if(state.isAccepting()){
            acceptingStates.add(state);
        }
        if(state.isStart()){
            startStates.add(state);
        }
        state.getTransitions().forEach((regdef, nxtState) -> {
            if(!states.contains(nxtState)){
                states.add(nxtState);
            }
        });

    }


    public void unVisitAll() {
        states.forEach(State::unVisit);
    }

    public boolean hasState(State state) {
        return states.contains(state);
    }
}
