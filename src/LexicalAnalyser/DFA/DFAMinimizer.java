package LexicalAnalyser.DFA;

import LexicalAnalyser.BaseModels.MultiMap;
import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.Regex.RegularDefinition;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by alyswidan on 15/03/18.
 */
public class DFAMinimizer {

    DFA Minimize(DFA dfa) {


        Set<State> AcceptingStates = dfa.getAcceptingStates();
        Set<State> RemainingStates = dfa.getStates();
        RemainingStates.removeAll(AcceptingStates);

        Set<Set<State>> partition = new HashSet<>();
        partition.add(AcceptingStates);
        partition.add(RemainingStates);

        Set<Set<State>> Newpartition ;
        Newpartition = New_partition(partition);
        while (!partition.equals((Newpartition))) {
            partition = Newpartition;
            Newpartition = New_partition(partition);
        }

        Map<State, Integer> parents = new HashMap<>();
        Map<Integer, State> parentsTostatue = new HashMap<>();
        int ParentCount =1 ;
        for (Set<State> states : partition) {
            parentsTostatue.put(ParentCount,states.stream().findFirst().get());
            for (State s : states) {
                parents.put(s,ParentCount);
            }
            ParentCount++;
        }

        DFA FINALdfa = new DFA();
        DFAState newStart = (DFAState) dfa.getStartState();
        Set<State> newAcceptStates = dfa.getAcceptingStates();
        System.out.println("new start state: " + newStart);

        DFAState parentStart = (DFAState) parentsTostatue.get(parents.get(newStart));///need to get parent of start state
        newAcceptStates.forEach(s -> s = (DFAState) parentsTostatue.get(parents.get(s)));//need to get parent of accepting states

        for (Set<State> states : partition) {

                DFAState FINALstate = new DFAState();
                FINALstate.setName((parentsTostatue.get(parents.get(states.stream().findFirst().get()))).getName());

                (states.stream().findFirst().get()).forEach((regularDefinition, state) -> {
                    FINALstate.addTransition(regularDefinition, parentsTostatue.get(parents.get(state)));
                });
                FINALdfa.addState(FINALstate);/////////



        }
        newAcceptStates.forEach(s -> FINALdfa.addAcceptingState(s));
        FINALdfa.setStartState(parentStart);
        return FINALdfa;
    }


    Set<Set<State>> New_partition(Set<Set<State>> partition) {

        Set<Set<State>> newpartition = new HashSet<>();

        Map<State, Integer> parents = new HashMap<>();
        Map<Integer, State> parentsTostatue = new HashMap<>();
        ArrayList<Integer> dummy= new ArrayList<>();
        dummy.add(0);
        int ParentCount =1 ;
        for (Set<State> states : partition) {
            parentsTostatue.put(ParentCount,states.stream().findFirst().get());
            for (State s : states) {
                parents.put(s,ParentCount);
            }
            ParentCount++;
        }


       Map<Integer,Map<State, Map<RegularDefinition, State>>> ALLsetsTransitions = new ConcurrentHashMap <>();

        for (Set<State> states : partition) {
            Map<State, Map<RegularDefinition, State>> setTransitions = new ConcurrentHashMap<>();
            for (State s : states) {
                Map<RegularDefinition, State> transitions = new ConcurrentHashMap<>();
                s.forEach((regularDefinition, state) -> {
                    transitions.put(regularDefinition, parentsTostatue.get((parents.get(state))));
                });
                setTransitions.put(s, transitions);
            }

            ALLsetsTransitions.put(parents.get(states.stream().findFirst().get()),setTransitions);
        }

        Set<State> newStatesSet = new HashSet<>();
        ALLsetsTransitions.forEach((Parentnum, setTransitions) -> {
            newStatesSet.clear();
            Map<RegularDefinition, State> compareTransition = setTransitions.get(parentsTostatue.get(Parentnum));
            setTransitions.forEach((currentstate, currenttransitions) ->{
                if(compareTransition.equals(currenttransitions))
                {
                    if (dummy.get(0)==0)
                    {
                        dummy.set(0,dummy.get(0)+1);
                    }
                    else
                    {
                     ;
                    }
                }
                else
                {
                    newStatesSet.add(currentstate);
                    setTransitions.remove(currentstate);
                }
            });

            if(!newStatesSet.isEmpty())
            {
                newpartition.add(newStatesSet);
            }
                newpartition.add(setTransitions.keySet());

        });

        return newpartition;
    }
}

//            Map<State, Map<RegularDefinition, State>> setTransitionscopy = setTransitions;
//            System.out.println("setTransitions: "+setTransitions);

//            setTransitionscopy.forEach((state2, transitions2) -> {
//                System.out.println("state2: "+state2+"transitions2: "+transitions2);
//                if (setTransitions.containsValue(transitions2)) {
//                    //  state2.setTransitions(regularDefinitionStateMap1);
//                    newStatesSet.add(state2);
//                    setTransitions.remove(state2);
//                    System.out.println("setTransitions after removing: "+setTransitions);
//                    }
//                    else{
//                        System.out.println("leave state ");
//                        }
//                });