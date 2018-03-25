package LexicalAnalyser.DFA;

import LexicalAnalyser.BaseModels.MultiMap;
import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.Regex.RegularDefinition;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by alyswidan on 15/03/18.
 */
public class DFAMinimizer {

    DFA Minimize(DFA dfa){
        DFA FINALdfa= new DFA();
        Set<State> AcceptingStates= dfa.getAcceptingStates();
        Set<State> RemainingStates= dfa.getStates();
        RemainingStates.removeAll(AcceptingStates);
        Set<Set<State>> partition= new HashSet<>();
        partition.add(AcceptingStates);
        partition.add(RemainingStates);
        Set<Set<State>> Newpartition= new HashSet<>();

        Newpartition = New_partition(partition);
        while (!partition.equals((Newpartition))){
            partition= Newpartition;
            Newpartition=New_partition(partition);
        }

        Map<State,State> parents=new HashMap<>();
        for (Set<State> states : Newpartition) {
            for (State s:states) {
                parents.put(s,states.stream().findFirst().get());
            }
        }
        for (Set<State> states : Newpartition) {
            Map<State,Map<RegularDefinition,State>> labels = new HashMap<>();
            DFAState FINALstate =new DFAState();
            FINALstate.setName((parents.get(states.stream().findFirst().get())).getName());
            Map<RegularDefinition,State> transitions = new HashMap<>();
            (states.stream().findFirst().get()).forEach((regularDefinition, state) -> {FINALstate.addTransition(regularDefinition,parents.get(state));});
            FINALdfa.addState(FINALstate);
        }

        return FINALdfa;
    }

    Set<Set<State>> New_partition(Set<Set<State>> partition)
    {
        Set<Set<State>> newpartition = new HashSet<>();
        Map<State,State> parents=new HashMap<>();
        for (Set<State> states : partition) {
            for (State s:states) {
                parents.put(s,states.stream().findFirst().get());
            }
        }
        Map<State,Map<State,Map<RegularDefinition,State>>> setslabels= new HashMap<>();

        for (Set<State> states : partition) {
            Map<State,Map<RegularDefinition,State>> labels = new HashMap<>();
            for (State s:states) {
                Map<RegularDefinition,State> transitions = new HashMap<>();
                s.forEach((regularDefinition, state) -> {transitions.put(regularDefinition,parents.get(state));});
                labels.put(s,transitions);
            }
            setslabels.put(states.stream().findFirst().get(),labels);
        }
        Set<State> newstate=new HashSet<>();
       setslabels.forEach((state, stateMapMap) -> {
           stateMapMap.forEach((state1, regularDefinitionStateMap) -> {
                newstate.clear();
               Map<State,Map<RegularDefinition,State>> old = stateMapMap;
               old.forEach((state2, regularDefinitionStateMap1) -> {
                   if(regularDefinitionStateMap.equals(regularDefinitionStateMap1)){
                     //  state2.setTransitions(regularDefinitionStateMap1);
                       newstate.add(state2);
                       stateMapMap.remove(state2);
                   }
               });

           });
           newpartition.add(newstate);
       });

        return newpartition;
    }
}
