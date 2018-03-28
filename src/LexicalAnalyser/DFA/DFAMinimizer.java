package LexicalAnalyser.DFA;

import LexicalAnalyser.BaseModels.Entry;
import LexicalAnalyser.BaseModels.MultiMap;
import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.Regex.RegularDefinition;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by alyswidan on 15/03/18.
 */
public class DFAMinimizer {


    public DFA Minimize(DFA dfa) {


        Set<State> RemainingStates = dfa.getStates();

        Set<Set<State>> partition = new HashSet<>();
/*
        partition.add(AcceptingStates);
        partition.add(RemainingStates);
*/

        Map<String,List<State>> acceptingPartitions =dfa.getStates().stream()
                                                                    .filter(state -> state.getAcceptingValue() != null)
                                                                    .collect(Collectors.groupingBy(State::getAcceptingValue));
        for (List<State> states : acceptingPartitions.values()) {
            partition.add(new HashSet<>(states));
            RemainingStates.removeAll(states);
        }
        partition.add(RemainingStates);

        Set<Set<State>> Newpartition ;
        Newpartition = New_partition(partition);
        while (!partition.equals((Newpartition))) {
            partition = Newpartition;

            Newpartition = New_partition(partition);
        }

        DFA Final = new DFA();
        Map<State, DFAState> setToState = new HashMap<>();
        setToState.put(new DeadState(),new DeadState());
        for (Set<State> states : partition)
        {
            DFAState newDFAState = new DFAState();
            Final.addState(newDFAState);
            states.forEach(state -> setToState.put(state,newDFAState));
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

            firststate.forEach((r,s)->setToState.get(firststate).addTransition(r,setToState.get(s)));

            if(isStart[0])
            {
                Final.setStartState(setToState.get(firststate));
            }
            if(isAccept[0])
            {
                State acc = setToState.get(firststate);
                acc.setAcceptingValue(firststate.getAcceptingValue());
                acc.setAcceptingOrder(0);
                Final.addAcceptingState(acc);
            }
        }
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
                    int a = parents.getOrDefault(state,-1);
                    State x = parentsTostatue.get((a));
                    if(x != null)
                        transitions.put(regularDefinition, x);
                });
                setTransitions.put(s, transitions);
            }

            ALLsetsTransitions.put(parents.get(states.stream().findFirst().get()),setTransitions);
        }

        Set<State> newStatesSet = new HashSet<>();
        ALLsetsTransitions.forEach((Parentnum, setTransitions) -> {
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
                newpartition.add(newStatesSet);
            }
        });
        return newpartition;
    }
}