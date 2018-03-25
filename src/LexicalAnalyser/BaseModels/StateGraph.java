package LexicalAnalyser.BaseModels;

import LexicalAnalyser.DFA.DFA;
import LexicalAnalyser.DFA.DFAState;
import LexicalAnalyser.Regex.EpsilonRegularDefinition;
import LexicalAnalyser.Regex.RegularDefinition;
import LexicalAnalyser.Utils;

import javax.swing.table.TableRowSorter;
import java.util.*;

/**
 * Created by alyswidan on 14/03/18.
 */
public class StateGraph implements Cloneable{
    private Map<State,State> states;
    private State startState;
    private State endState;
    private Set<RegularDefinition> language;
    private Set<State> acceptingStates;
    private int currentStateNumber;
    private Map<String,Integer> StateNameCounts;



    public StateGraph() {
        StateNameCounts = new HashMap<>();
        currentStateNumber = 0;
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

        state.setName(getAdjustedName(state.getName()));

        System.out.println(state.getName());
        states.put(state,state);
        System.out.println(states);
        state.addParentGraph(this);
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

    private String getAdjustedName(String name){
        /*
        *  if the name is an integer or an empty string we adjust it to
        *  the next state number.
        *  if it's not we append the number of times we saw this name before in the graph.
        *  This ensures state names are unique within the graph
        *  ex:
        *  if we already have a state named AA
        *  and we get it again we rename it to AA1
        *
        * */

        if(name == null || name.equals("")){
            return  Integer.toString(currentStateNumber++);
        }
        if(Utils.isInteger(name)){
           return   Integer.toString(currentStateNumber++);
        }
        else{
            int nameCount = StateNameCounts.getOrDefault(name,0);
            addStateName(name);
            return name+(nameCount>0?nameCount:"");
        }
    }

    private void addStateName(String name){
        StateNameCounts.computeIfPresent(name, (key,cnt)->cnt + 1);
        StateNameCounts.putIfAbsent(name,1);

    }

    public void addAcceptingState(State state){
        acceptingStates.add(state);
    }

    public void removeAcceptingState(State state){
        acceptingStates.remove(state);
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
        if(startState != null && !startState.isStart())
            startState.setStart(true);
        this.startState = startState;
    }

    public void setEndState(State endState) {
        if(endState != null)
            endState.setAccepting(true);
        this.endState = endState;
    }

    private StringBuilder DFSUtil(State state) {
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

    public Set<State> getStates() {
        return states.keySet();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        StateGraph clone = (StateGraph) super.clone();
        acceptingStates.forEach(clone::addAcceptingState);
        clone.language.addAll(language);
        getStates().forEach(state -> clone.addState(state));
        StateNameCounts.forEach((s, i) -> clone.StateNameCounts.put(s,i));
        return clone;
    }

}
