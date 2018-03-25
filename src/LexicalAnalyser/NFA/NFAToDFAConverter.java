package LexicalAnalyser.NFA;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.DFA.DFA;
import LexicalAnalyser.DFA.DFAState;
import LexicalAnalyser.DFA.DeadState;
import LexicalAnalyser.Regex.RegularDefinition;

import java.util.*;

/**
 * Created by alyswidan on 15/03/18.
 */
public class NFAToDFAConverter {


    public NFAToDFAConverter() {
    }

    public DFA convert(NFA nfa){
        Deque<DFAState> unmarkedStates = new LinkedList<>();
        DFAState start = new DFAState(nfa.getEpsilonClosure((NFAState)nfa.getStartState()));
        unmarkedStates.add(start);
        DFA resultDFA = new DFA();
        resultDFA.addState(start);
        resultDFA.setStartState(start);
        DFAState currentState;

        while(!unmarkedStates.isEmpty()){
            currentState = unmarkedStates.remove();

            for (RegularDefinition regDef : nfa.getLanguage()) {

                DFAState newState = currentState.dfaTransition(regDef);
                Set<NFAState> epsClosure = nfa.getEpsilonClosure(newState.getEquivalentNFAStates());

                DFAState state = epsClosure.isEmpty()?new DeadState():new DFAState(epsClosure);

                if (state instanceof DeadState){
                    /*do nothing*/
                }
                else if(!resultDFA.hasState(state) ){
                    resultDFA.addState(state);
                    unmarkedStates.add(state);
                }
                else {
                    state = (DFAState) resultDFA.getState(state);
                }

                currentState.addTransition(regDef,state);
            }
        }
        return resultDFA;

    }



}
