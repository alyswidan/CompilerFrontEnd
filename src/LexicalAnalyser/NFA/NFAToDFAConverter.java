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
    private Deque<DFAState> unmarkedStates;

    public NFAToDFAConverter() {

        this.unmarkedStates = new LinkedList<>();
    }

    /*
    * todo: equals is not right this way
    * todo: whole project needs refactoring
    * */
    public DFA convert(NFA nfa){
        int currentStateNumber = 0;
        DFAState start = new DFAState(nfa.getEpsilonClosure((NFAState)nfa.getStartState()),""+currentStateNumber++);
        unmarkedStates.add(start);
        DFA resultDFA = new DFA();
        resultDFA.addState(start);
        resultDFA.setStartState(start);
        DFAState currentState;
        while(!unmarkedStates.isEmpty()){
            currentState = unmarkedStates.remove();

            for (RegularDefinition regDef : nfa.getLanguage()) {

                DFAState newState = currentState.transition(regDef);
                Set<NFAState> epsClosure = nfa.getEpsilonClosure(newState.getEquivalentNFAStates());

                DFAState state = epsClosure.isEmpty()?new DeadState():new DFAState(epsClosure,""+(currentStateNumber++));

                if (state instanceof DeadState){

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
