package LexicalAnalyser.Regex;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.BaseModels.StateGraph;
import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by alyswidan on 15/03/18.
 */

public class UnionOperator extends BinaryRegexOperator {


    public UnionOperator() {
        priority = RegexOperator.MINPRIORITY + 3;
    }

    public String getRawValue(){
        return "|";
    }

    @Override
    public NFA execute(NFA leftOperand, NFA rightOperand) {

        NFA result = new NFA();

        NFAState unionStartState = new NFAState("unionStartState");
        unionStartState.addTransition(new EpsilonRegularDefinition(), leftOperand.getStartState());
        unionStartState.addTransition(new EpsilonRegularDefinition(), rightOperand.getStartState());
//
//        leftOperand.getStartState().setStart(false);
//        rightOperand.getStartState().setStart(false);

        result.addAll(leftOperand.getStates());
        result.addAll(rightOperand.getStates());

        result.setStartState(unionStartState);
        return result;
    }

    @Override
    public String toString() {
        return "|";
    }
}
