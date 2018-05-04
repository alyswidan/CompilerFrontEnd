package LexicalAnalyser.Regex;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.BaseModels.StateGraph;
import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by alyswidan on 26/03/18.
 */
public class MultiUnionOperator extends MaryRegexOperator {
    @Override
    public NFA execute(List<NFA> operands) {
        NFA result = new NFA();
        List<NFAState> startStates = operands.stream()
                                          .map(nfa -> (NFAState)nfa.getStartState())
                                          .collect(Collectors.toList());

        List<NFAState> acceptingStates = operands.stream()
                                                .map(StateGraph::getAcceptingStates)
                                                .flatMap(Collection::stream)
                                                .map(state -> (NFAState)state)
                                                .collect(Collectors.toList());


        NFAState newStart = NFAState.epsilonSource(startStates);

        result.addAll(operands.stream()
                              .map(StateGraph::getStates)
                              .flatMap(Collection::stream)
                              .collect(Collectors.toList()));

        result.addState(newStart);
        result.setStartState(newStart);
        acceptingStates.forEach(result::addAcceptingState);
        return result;
    }

    @Override
    public String getRawValue() {
        return null;
    }
}
