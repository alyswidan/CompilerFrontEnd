package LexicalAnalyser.NFA;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.DFA.DFA;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by alyswidan on 15/03/18.
 */
public class NFAToDFAConverter {

    private NFA currentNFA;
    public DFA convert(NFA nfa){
        currentNFA = nfa;


        return new DFA();

    }

    Set<State> epsilonClosureAll(Set<State> states){
        return states.stream()
                .map(state -> currentNFA.getEpsilonClosure(state))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

}
