package LexicalAnalyser.DFA;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.BaseModels.StateGraph;
import LexicalAnalyser.NFA.NFAToDFAConverter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by alyswidan on 15/03/18.
 */
public class DFA extends StateGraph {


    DFA fromNFA(NFA nfa){
        NFAToDFAConverter converter = new NFAToDFAConverter();
        return converter.convert(nfa);
        /*this should also minimize the dfa before returning it*/

    }
    //private  Set<State> acceptingStates;

    public void setStartState(State startState) {
        if(startState instanceof DFAState){
            DFAState dfaState = (DFAState) startState;
            if (getStartState() == null) {
                super.setStartState(dfaState);
            }
            else {
                System.out.println("DFA has start state \n");
            }
        }
    }

    @Override
    public void addState(State state) {
        if (state instanceof DFAState) {
            DFAState dfaState = (DFAState) state;
            boolean isAccepting = ((DFAState) state).getEquivalentNFAStates()
                    .stream()
                    .anyMatch(State::isAccepting);
            if (isAccepting) {
                addAcceptingState(dfaState);
            }
        }
        super.addState(state);
    }


    public void addAcceptingState(State state) {
        System.out.println("received: "+state);
        if (state instanceof DFAState) {
            DFAState dfaState = (DFAState) state;
            addState(dfaState);
            acceptingStates.add(dfaState);
            System.out.println("add accept state got instance of DFA \n");
            System.out.println(acceptingStates);
        }
        else
        {
            System.out.println("add accept state didn't get instance of DFA");
        }
    }

}