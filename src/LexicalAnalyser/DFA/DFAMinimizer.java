package LexicalAnalyser.DFA;

import LexicalAnalyser.BaseModels.Entry;
import LexicalAnalyser.BaseModels.MultiMap;
import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.Regex.RegularDefinition;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by alyswidan on 15/03/18.
 */
public class DFAMinimizer {

//    private Entry Entrymap;

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

        DFA Final = new DFA();
        Map<Set<State>, DFAState> setToState = new HashMap<>();
        for (Set<State> states : partition)
        {
            DFAState newDFAState = new DFAState();
            Final.addState(newDFAState);
            setToState.put(states, newDFAState);
        }

        for (Set<State> states : partition)
        {
            boolean[] isStart = {false};
            boolean[] isAccept = {false};
            states.forEach(s -> {
                if(s.isStart())
                {
                    isStart[0] = true;
                }
                if (s.isAccepting())
                {
                    isAccept[0] = true;
                }
            });

            State firststate = (states.stream().findFirst().get());

            firststate.forEach((regDef, s)->{
                s = setToState.get(s);
            });
            firststate.forEach((r,s)->setToState.get(states).addTransition(r,s));

            //state.addTransition(states.stream().findFirst().get().getTransitions());
            //Final.addState(state);
            if(isStart[0])
            {
                Final.setStartState(setToState.get(states));
            }
            if(isAccept[0])
            {
                Final.addAcceptingState(setToState.get(states));
            }
        }
//            System.out.println("returned partition "+partition);
//        Map<State, Integer> parents = new HashMap<>();
//        Map<Integer, State> parentsTostatue = new HashMap<>();
//        int ParentCount =0 ;
//        for (Set<State> states : partition) {
//           parentsTostatue.put(ParentCount,states.stream().findFirst().get());
//            //parents.put(states.stream().findFirst().get(),ParentCount);
//            for (State s : states) {
//                parents.put(s,ParentCount);
//            }
//        ParentCount++;
//        }
//        System.out.println("parents "+ parents);
//        System.out.println("parents to state "+ parentsTostatue);
//        DFA FINALdfa = new DFA();
//        DFAState newStart = (DFAState) parentsTostatue.get(parents.get(dfa.getStartState()));
//        Set<State> newAcceptStates = dfa.getAcceptingStates();
//        newAcceptStates.forEach(s-> s = parentsTostatue.get(parents.get(s)));
//        System.out.println("new start state: " + newStart);
//
////        DFAState parentStart = (DFAState) parentsTostatue.get(parents.get(newStart));///need to get parent of start state
////        newAcceptStates.forEach(s -> s = (DFAState) parentsTostatue.get(parents.get(s)));//need to get parent of accepting states
//
//        for (Set<State> states : partition) {
//
//                DFAState FINALstate = new DFAState();
//                FINALstate.setName((parentsTostatue.get(parents.get(states.stream().findFirst().get()))).getName());
//
//                (states.stream().findFirst().get()).forEach((regularDefinition, state) -> {
//                    FINALstate.addTransition(regularDefinition, parentsTostatue.get(parents.get(state)));
//                });
//                FINALdfa.addState(FINALstate);/////////
//
//
//
//        }
//        newAcceptStates.forEach(s -> FINALdfa.addAcceptingState(s));
//        FINALdfa.setStartState(newStart);
//        return FINALdfa;
        return Final;
    }


    Set<Set<State>> New_partition(Set<Set<State>> partition) {

        Set<Set<State>> newpartition = new HashSet<>();

        Map<State, Integer> parents = new HashMap<>();
        Map<Integer, State> parentsTostatue = new HashMap<>();
        ArrayList<Integer> dummy= new ArrayList<>();
        dummy.add(0);
        int ParentCount =1 ;
        for (Set<State> states : partition) {
//            System.out.println("the partition is " +partition);
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
//            this.Entrymap  = new Entry<>(setTransitions.entrySet().stream().findFirst().get(), states.stream().findFirst().get());
        }

        Set<State> newStatesSet = new HashSet<>();
        ALLsetsTransitions.forEach((Parentnum, setTransitions) -> {
            //newStatesSet.clear();


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


            newpartition.add(setTransitions.keySet());
            if(!newStatesSet.isEmpty())
            {
//                System.out.println("newSet is "+newStatesSet);
                newpartition.add(newStatesSet);
//                System.out.println("new partition size is "+newpartition.size()+" and it is "+newpartition);
            }
        });
//        System.out.println("before return "+newpartition);
        return newpartition;
    }
}

//            Map<State, Map<RegularDefinition, State>> setTransitionscopy = setTransitions;
//            System.out.println("setTransitions: "+setTransitions);

//            setTransitionscopy.forEach((state2, transitions2) -> {
//                System.out.println("state2: "+state2+"transitions2: "+transitions2);
//                if (setTransitions.containsValue(transitions2)) {
//                    //  state2.setTransitions(regularDefinitionSt)ateMap1);
//                    newStatesSet.add(state2);
//                    setTransitions.remove(state2);
//                    System.out.println("setTransitions after removing: "+setTransitions);
//                    }
//                    else{
//                        System.out.println("leave state ");
//                        }
//                });