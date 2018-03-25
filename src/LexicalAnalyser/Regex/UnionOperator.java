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

    public enum Mode{MERGE_ACCEPT, LEAVE_ACCEPT}
    Mode mode;
    public UnionOperator() {
        mode = Mode.LEAVE_ACCEPT;
        priority = RegexOperator.MINPRIORITY + 3;

    }
    public UnionOperator(Mode mode){
        this();
        this.mode = mode;
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

        Set<State> acceptingStates = new HashSet<>();
        acceptingStates.addAll(leftOperand.getAcceptingStates());
        acceptingStates.addAll(rightOperand.getAcceptingStates());

        result.addState(unionStartState);
        result.addAll(leftOperand.getStates());
        result.addAll(rightOperand.getStates());
        result.addAll(acceptingStates);
        result.setStartState(unionStartState);
        acceptingStates.forEach(s -> result.addAcceptingState(s));

        if(mode == Mode.MERGE_ACCEPT){
            result.mergeAcceptStates();
        }


        return result;
    }

    @Override
    public String toString() {
        return "|";
    }
}
