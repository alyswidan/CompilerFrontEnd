package LexicalAnalyser.DFA;

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
        Set<State> AcceptingStates= dfa.getAcceptingStates();
        Set<State> RemainingStates= dfa.getStates();
        RemainingStates.removeAll(AcceptingStates);
        Set<Set<State>> partition= new HashSet<>();
        partition.add(AcceptingStates);
        partition.add(RemainingStates);
        Set<Set<State>> Newpartition= new HashSet<>();

        Newpartition = New_partition(partition);
        return new DFA();
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
        



        return newpartition;
    }
}
