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

}
