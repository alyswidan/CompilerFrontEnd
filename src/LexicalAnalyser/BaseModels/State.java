package LexicalAnalyser.BaseModels;

import LexicalAnalyser.Regex.RegularDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alyswidan on 14/03/18.
 */
public class State {
    private boolean isAccepting;
    private boolean isStart;
    private Map<RegularDefinition, State> transitions;

    public State() {
        transitions = new HashMap<>();
        isAccepting = false;
        isStart = false;
    }

    public State transition(RegularDefinition input){
        //transitions to a new state based on the input regular definition
        return transitions.get(input);
    }

    public void addTransition(RegularDefinition regdef, State nextState){
        transitions.put(regdef,nextState);
    }

    public Map<RegularDefinition, State> getTransitions() {
        return transitions;
    }



}
