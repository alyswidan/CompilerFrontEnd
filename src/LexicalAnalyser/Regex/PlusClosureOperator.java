package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;

/**
 * Created by alyswidan on 15/03/18.
 */
public class PlusClosureOperator extends UnaryRegexOperator {

    public PlusClosureOperator() {

        priority = RegexOperator.MINPRIORITY;
    }

    @Override
    public NFA execute(NFA operand) {
        /**
         * end --> epsilon --> start
         */
        NFAState start = (NFAState) operand.getStartState();
        NFAState end = operand.mergeAcceptStates();
        end.addTransition(new EpsilonRegularDefinition(), start);
        operand.setEndState(end);
        return operand;
    }

    public String getRawValue(){
        return "+";
    }

}
