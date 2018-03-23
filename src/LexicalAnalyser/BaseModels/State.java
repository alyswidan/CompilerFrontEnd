package LexicalAnalyser.BaseModels;

import LexicalAnalyser.Regex.EpsilonRegularDefinition;
import LexicalAnalyser.Regex.RegularDefinition;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by alyswidan on 14/03/18.
 */
public class State {
    private boolean isAccepting;
    private boolean isStart;
    private MultiMap<RegularDefinition,State> transitions;
    private boolean isVisited;
    private String name;


    public State(String name) {
        this(false,false,name);
    }

    public State(boolean isAccepting, boolean isStart, String name) {
        this.isAccepting = isAccepting;
        this.isStart = isStart;
        this.name = name;
        transitions = new MultiMap<>();
    }

    public State(boolean isAccepting, boolean isStart) {
        this(isAccepting,isStart,"");
    }

    public State(){
        this(false,false);
    }

    public State transition(RegularDefinition input){

        if(!transitions.containsKey(input))
            return null;
        return transitions.get(input).get(0);
    }

    public void addTransition(RegularDefinition regdef, State nextState){
        transitions.put(regdef,nextState);
    }

    public void addTransition(String string, State nextState){
        RegularDefinition regDef = string.equals("\\L")?new EpsilonRegularDefinition():new RegularDefinition(string);
        regDef.getParts().forEach(r -> addTransition(r,nextState));
    }

    public MultiMap<RegularDefinition,State> getTransitions() {

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

        return this.hashCode() == state.hashCode();
    }

    @Override
    public int hashCode() {
//        int result = (isAccepting() ? 1 : 0);
//
//        for (Entry<RegularDefinition, State> entry : getTransitions()) {
//            RegularDefinition reg = entry.getKey();
//            State s = entry.getValue();
//            result += reg.hashCode() + s.name.hashCode();
//        }
        return name.hashCode();
    }

    public void setAccepting(boolean accepting) {
        isAccepting = accepting;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public String toString() {
        return this.name + " "+(isAccepting()?"A ":"") + (isStart()?"S":"");
    }


    public void forEach(BiConsumer<? super RegularDefinition, ? super State> consumer) {
        for (Entry<RegularDefinition, State> entry : transitions) {
            consumer.accept(entry.getKey(), entry.getValue());
        }
    }
}
