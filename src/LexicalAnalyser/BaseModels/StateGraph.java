package LexicalAnalyser.BaseModels;

import LexicalAnalyser.DFA.DFA;
import LexicalAnalyser.DFA.DFAState;
import LexicalAnalyser.Exceptions.StateNotInGraphException;
import LexicalAnalyser.Regex.EpsilonRegularDefinition;
import LexicalAnalyser.Regex.RegularDefinition;
import LexicalAnalyser.Utils;

import javax.swing.table.TableRowSorter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by alyswidan on 14/03/18.
 */
public class StateGraph{
    private Map<State,State> states;
    private State startState;
    private Set<RegularDefinition> language;
    private Set<State> acceptingStates;
    private static int currentStateNumber;
    private Map<String,Integer> StateNameCounts;

    public StateGraph() {
        StateNameCounts = new HashMap<>();

        states = new HashMap<>();
        acceptingStates = new HashSet<>();
        language = new HashSet<>();
    }

    public State getStartState() {
        return startState;
    }

    public Set<State> getAcceptingStates() {
        return new HashSet<>(acceptingStates);
    }

    public void addState(State state){
        StateGraph previousParent = state.getParentGraph();
        if(previousParent != null){
            previousParent.removeState(state);
        }
        state.setParentGraph(this);
        state.setName(getAdjustedName(state.getName()));
        states.put(state,state);
        /*add all regular definitions on edges out of state to this graph's language*/
        state.forEach((regDef, s) -> {
            if(!(regDef instanceof EpsilonRegularDefinition))
                language.addAll(regDef.getParts());
        });
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

    public void addAcceptingState(State state) {
        acceptingStates.add(state);
    }

    public void removeAcceptingState(State state){
        acceptingStates.remove(state);
    }

    public void removeState(State state){
        if(acceptingStates.contains(state))
            acceptingStates.remove(state);
        states.remove(state);
    }

    public void addAll(Collection<State> states){
        new CopyOnWriteArraySet<>(states).forEach(this::addState);
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
        if(!states.containsKey(startState))
            addState(startState);
        this.startState = startState;
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

    public String getAcceptingValue(){
        if(acceptingStates.size() == 1){
            return acceptingStates.stream().findAny().get().getAcceptingValue();
        }
        return "__multiple_Ends__";
    }

    public boolean setAcceptingValue(String acceptingValue){
        if(acceptingStates.size() == 1){
            acceptingStates.stream().findAny().ifPresent(state -> state.setAcceptingValue(acceptingValue));
            return true;
        }
        return false;
    }

    public boolean setAcceptingOrder(int acceptingOrder){
        if(acceptingStates.size() == 1){
            acceptingStates.stream().findAny().ifPresent(state -> state.setAcceptingOrder(acceptingOrder));
            return true;
        }
        return false;
    }


}
