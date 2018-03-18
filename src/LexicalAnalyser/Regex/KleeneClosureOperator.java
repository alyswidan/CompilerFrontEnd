package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;

/**
 * Created by alyswidan on 15/03/18.
 */
public class KleeneClosureOperator extends UnaryRegexOperator {

    @Override
    public NFA execute(NFA operand) {
        /**
         * 2 new nodes newStart and newEnd
         * newStart--> epsilon --> start
         * end --> epsilon --> newEnd
         * end --> epsilon --> Start
         * newStart --> epsilon --> newEnd
         * start = newStart
         * end  = newEnd
         * return updated operand
         */

        NFAState start = (NFAState) operand.getStartState();
        NFAState end  = operand.mergeAcceptStates();

        NFAState newStart = new NFAState(false,true,"kleeneClousreStart");
        NFAState newEnd = new NFAState(true, false, "KleeneeClosureEnd");

        newStart.addTransition(new EpsilonRegularDefinition(), start);
        end.addTransition(new EpsilonRegularDefinition(), newEnd);
        end.addTransition(new EpsilonRegularDefinition(), start);
        newStart.addTransition(new EpsilonRegularDefinition(), newEnd);

        operand.setStartState(newStart);
        operand.setEndState(newEnd);

        return operand;
    }

    public String getRawValue(){
        return "*";
    }
}
