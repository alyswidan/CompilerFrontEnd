package LexicalAnalyser.NFA;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.DFA.DFA;
import LexicalAnalyser.DFA.DFAState;
import LexicalAnalyser.DFA.DeadState;
import LexicalAnalyser.Regex.RegularDefinition;

//import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alyswidan on 15/03/18.
 */
public class NFAToDFAConverter {
    private Map<DFAState,Set<NFAState>> mappingDFAToNFA;
    private Map<Set<NFAState>,DFAState> mappingNFAToDFA;
    private DFA resultDFA;
    private Deque<DFAState> unExploredStates;

    public NFAToDFAConverter() {
        mappingDFAToNFA = new HashMap<>();
        mappingNFAToDFA = new HashMap<>();
    }

    public DFA convert(NFA nfa){
        resultDFA = new DFA();
        unExploredStates = new LinkedList<>();
        Set<NFAState> equivalentOfStart = nfa.getEpsilonClosure((NFAState)nfa.getStartState());
        DFAState start = addToResultDFA(equivalentOfStart);
        resultDFA.setStartState(start);
        DFAState currentState;

        while(!unExploredStates.isEmpty()){
            currentState = unExploredStates.remove();

            for (RegularDefinition regDef : nfa.getLanguage()) {

                Set<NFAState> equivalentStates = move(regDef,currentState);
                Set<NFAState> epsClosure = nfa.getEpsilonClosure(equivalentStates);

                DFAState state;

                if (epsClosure.isEmpty()){
                    state = new DeadState();
                }
                else if(!mappingNFAToDFA.containsKey(epsClosure)){
                    state = addToResultDFA(epsClosure);
                }
                else {
                    state = mappingNFAToDFA.get(epsClosure);
                }
                currentState.addTransition(regDef,state);
            }
        }
        return resultDFA;
    }


    private DFAState addToResultDFA(Set<NFAState> equivalentStates) {
        DFAState state = new DFAState();
        resultDFA.addState(state);
        unExploredStates.add(state);
        mappingNFAToDFA.put(equivalentStates,state);
        mappingDFAToNFA.put(state, equivalentStates);
        Optional<String> acceptingValue = getAcceptingValue(equivalentStates);
        if(acceptingValue.isPresent()){
            state.setAcceptingValue(acceptingValue.get());
            resultDFA.addAcceptingState(state);
        }
        return state;
    }

    private Set<NFAState> move(RegularDefinition regDef,DFAState state) {
        Set<NFAState> equivalentStates = mappingDFAToNFA.get(state);
        return equivalentStates
                .stream()
                .map(s -> s.transition(regDef))
                .flatMap(Collection::stream)
                .map(s -> (NFAState)s)
                .collect(Collectors.toSet());
    }

    private Optional<String> getAcceptingValue(Set<NFAState> states){
        Optional<String> result = states.stream()
                                   .map(State::getAcceptingValue)
                                   .filter(Objects::nonNull)
                                   .findAny();
        return result;

    }




}
