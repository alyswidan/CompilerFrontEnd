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


    public DFA fromNFA(NFA nfa) {
        NFAToDFAConverter converter = new NFAToDFAConverter();
        DFAMinimizer minimizer= new DFAMinimizer();
        return minimizer.Minimize(converter.convert(nfa));
    }

    public void setStartState(State startState) {
        if (startState instanceof DFAState) {
            DFAState dfaState = (DFAState) startState;
            if (getStartState() == null) {
                super.setStartState(dfaState);
            } else {
                System.out.println("DFA has start state \n");
            }
        }
    }
}

