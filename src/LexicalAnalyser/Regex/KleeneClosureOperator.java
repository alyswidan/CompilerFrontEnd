package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;

/**
 * Created by alyswidan on 15/03/18.
 */
public class KleeneClosureOperator implements UnaryRegexOperator {

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
        // I did the next 2 lines as the coming NFA may have many start and accept status
        NFAState start = NFAState.epsilonSource(operand.getStartStates());
        NFAState end = NFAState.epsilonSink(operand.getAcceptingStates());

        NFAState newStart = new NFAState();
        NFAState newEnd = new NFAState();
        newStart.addTransition(new EpsilonRegularDefinition(), start);
        end.addTransition(new EpsilonRegularDefinition(), newEnd);
        end.addTransition(new EpsilonRegularDefinition(), start);
        newStart.addTransition(new EpsilonRegularDefinition(), newEnd);
        start = newStart;
        end = newEnd;

        return operand;
    }

    public String getRawValue(){
        return "*";
    }
}
