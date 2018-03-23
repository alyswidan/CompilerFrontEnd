package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;

/**
 * Created by alyswidan on 15/03/18.
 */
public class ConcatenationOperator implements BinaryRegexOperator{


    @Override
    public NFA execute(NFA leftOperand, NFA rightOperand) {

        NFAState startRightOperand = (NFAState) rightOperand.getStartState();
        startRightOperand.setStart(false);

        NFAState endLeftOperand = leftOperand.mergeAcceptStates();
        endLeftOperand.setAccepting(false);

        endLeftOperand.addTransition(new EpsilonRegularDefinition(), startRightOperand);

        NFAState endRightOperand = rightOperand.mergeAcceptStates();
        leftOperand.addState(endLeftOperand);
        leftOperand.addState(endRightOperand);
        leftOperand.setEndState(endLeftOperand);
        return leftOperand;
    }

    public String getRawValue(){
        return ".";
    }
}
