package LexicalAnalyser.DFA;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.BaseModels.StateGraph;

/**
 * Created by alyswidan on 15/03/18.
 */
public class DFA extends StateGraph {

    DFA fromNFA(NFA nfa){
        return new DFA();
    }

    @Override
    public void addState(State state) {
        if(state instanceof DFAState){
            DFAState dfaState = (DFAState) state;
            boolean isAccepting = ((DFAState) state).getEquivalentNFAStates()
                                                    .stream()
                                                    .anyMatch(State::isAccepting);
            if(isAccepting){
                addAcceptingState(dfaState);
            }
        }

        super.addState(state);
    }
}
