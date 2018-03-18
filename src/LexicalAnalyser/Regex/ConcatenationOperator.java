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
         * make a joint node joining
         * the accept states of the leftOperand
         * and the start state fo the rightOperand
         * left end --> epsilon --> joint
         * joint --> epsilon --> right start
         */
        NFAState startRightOperand = (NFAState) rightOperand.getStartState();
        NFAState endLeftOperand = leftOperand.mergeAcceptStates();
        NFAState jointState = new NFAState();
        endLeftOperand.addTransition(new EpsilonRegularDefinition(), jointState);
        leftOperand.addState(jointState);
        jointState.addTransition(new EpsilonRegularDefinition(), startRightOperand);
        return leftOperand;
    }

    public String getRawValue(){
        return ".";
    }
}
