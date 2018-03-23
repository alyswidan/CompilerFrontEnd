package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;

/**
 * Created by alyswidan on 15/03/18.
 */
public class ConcatenationOperator implements BinaryRegexOperator{


    @Override
    public NFA execute(NFA leftOperand, NFA rightOperand) {
        /**
         * make end of left start of right
         * unEnd end of left
         * unStart start of right
         */
        NFAState startRightOperand = (NFAState) rightOperand.getStartState();
        startRightOperand.setStart(false);
        NFAState endLeftOperand = leftOperand.mergeAcceptStates();
        endLeftOperand.setAccepting(false);

        endLeftOperand.addTransition(new EpsilonRegularDefinition(), startRightOperand);

        leftOperand.addState(endLeftOperand);
        return leftOperand;
    }

    public String getRawValue(){
        return ".";
    }
}
