package LexicalAnalyser.BaseModels;

import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;
import LexicalAnalyser.Regex.EpsilonRegularDefinition;
import LexicalAnalyser.Regex.RegularDefinition;
//import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by alyswidan on 14/03/18.
 */
public class State {
    private MultiMap<RegularDefinition,State> transitions;
    private boolean isVisited;
    private String name;
    private StateGraph parentGraph;
    private String acceptingValue; // if this is an accepting state this is the value it accepts
    private int acceptingOrder = Integer.MAX_VALUE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State(){
        this("");
    }

    public State(String name) {
        this.name = name;
        transitions = new MultiMap<>();
        parentGraph = null;
    }

    public List<State> transition(RegularDefinition input){
        if(!transitions.containsKey(input))
            return new ArrayList<>();
        return transitions.get(input);
    }

    public void addTransition(RegularDefinition regDef, State nextState){
        regDef.getParts().forEach(r -> transitions.put(r,nextState));
    }

    public void addTransition(String string, State nextState){
        RegularDefinition regDef = string.equals("\\L")
                                    ?new EpsilonRegularDefinition()
                                    :new RegularDefinition(string);
        addTransition(regDef,nextState);
    }

    public MultiMap<RegularDefinition,State> getTransitions() {

        return transitions;
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

    public boolean isStart(){return parentGraph.getStartState().equals(this);}

    public boolean isAccepting(){
        return parentGraph.getAcceptingStates().contains(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;

        return this.hashCode() == state.hashCode();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return this.name +(isAccepting()?"_(Acc_":"")+(isStart()?"_St":"")
                +(isAccepting()&&getAcceptingValue()!=null?"val="+getAcceptingValue()+", ord="+getAcceptingOrder()+")":"");
    }

    public void forEach(BiConsumer<? super RegularDefinition, ? super State> consumer) {
        for (Entry<RegularDefinition, State> entry : transitions) {
            consumer.accept(entry.getKey(), entry.getValue());
        }
    }

    public void setParentGraph(StateGraph parentGraph) {
        this.parentGraph = parentGraph;
    }

    public StateGraph getParentGraph() {
        return parentGraph;
    }

    public String getAcceptingValue() {
        return acceptingValue;
    }

    public void setAcceptingValue(String acceptingValue) {
        this.acceptingValue = acceptingValue;
    }

    public int getAcceptingOrder() {
        return acceptingOrder;
    }

    public void setAcceptingOrder(int acceptingOrder) {
        this.acceptingOrder = acceptingOrder;
    }
}
