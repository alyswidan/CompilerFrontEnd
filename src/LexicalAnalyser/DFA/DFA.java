package LexicalAnalyser.DFA;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.BaseModels.StateGraph;
import LexicalAnalyser.NFA.NFAToDFAConverter;

/**
 * Created by alyswidan on 15/03/18.
 */
public class DFA extends StateGraph {

    DFA fromNFA(NFA nfa){
        NFAToDFAConverter converter = new NFAToDFAConverter();
        return converter.convert(nfa);
        /*this should also minimize the dfa before returning it*/
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
