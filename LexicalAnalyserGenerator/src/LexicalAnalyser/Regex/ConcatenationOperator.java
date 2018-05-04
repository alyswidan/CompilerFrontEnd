package LexicalAnalyser.Regex;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by alyswidan on 15/03/18.
 */
public class ConcatenationOperator extends BinaryRegexOperator{


    public ConcatenationOperator() {
        priority = RegexOperator.MINPRIORITY + 2;
    }

    @Override
    public NFA execute(NFA leftOperand, NFA rightOperand) {
        NFA newNFA = new NFA();
        NFAState endLeft = leftOperand.mergeAcceptStates();

        Set<State> endRight = rightOperand.getAcceptingStates();
        NFAState startLeft = (NFAState) leftOperand.getStartState();

        endLeft.addTransition("\\L",rightOperand.getStartState());
        newNFA.addAll(leftOperand.getStates());
        newNFA.addAll(rightOperand.getStates());
        newNFA.setStartState(startLeft);
        endRight.forEach(newNFA::addAcceptingState);

        return newNFA;
    }

    public String getRawValue(){
        return ".";
    }

    @Override
    public String toString() {
        return ".";
    }
}
