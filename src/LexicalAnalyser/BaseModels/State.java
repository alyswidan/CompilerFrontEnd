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

    public State(boolean isAccepting, boolean isStart) {
        this.isAccepting = isAccepting;
        this.isStart = isStart;
    }

    public State(){
        this.isStart = false;
        this.isAccepting = false;
        transitions = new HashMap<>();
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

    public boolean isAccepting() {
        return isAccepting;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setAccepting(boolean accepting) {
        isAccepting = accepting;
    }

    public void setStart(boolean start) {
        isStart = start;
    }
}
