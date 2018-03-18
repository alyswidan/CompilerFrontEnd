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
    private boolean isVisited;
    private String name;

    public State(boolean isAccepting, boolean isStart, String name) {
        this.isAccepting = isAccepting;
        this.isStart = isStart;
        this.name = name;
        transitions = new HashMap<>();
    }

    public State(boolean isAccepting, boolean isStart) {
        this(isAccepting,isStart,"");
    }

    public State(){
        this(false,false);
    }

    public State transition(RegularDefinition input){
        //transitions to a new state based on the input regular definition
        return transitions.get(input);
    }

    public void addTransition(RegularDefinition regdef, State nextState){
        transitions.put(regdef,nextState);
    }

    public void addTransition(String string, State nextState){
        transitions.put(new RegularDefinition(string),nextState);
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


    public boolean isVisited() {
        return isVisited;
    }

    public void visit() {
        isVisited = true;
    }

    public void unVisit() {
        isVisited = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o;

        if (isAccepting() != state.isAccepting()) return false;
        return getTransitions().equals(state.getTransitions());
    }

    @Override
    public int hashCode() {
        int result = (isAccepting() ? 1 : 0);
        result = 31 * result + getTransitions().hashCode();
        return result;

    }
    public void setAccepting(boolean accepting) {
        isAccepting = accepting;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public String toString() {
        return this.name;
    }
}
