package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;

/**
 * Created by alyswidan on 15/03/18.
 */
public class KleeneClosureOperator extends UnaryRegexOperator {


    public KleeneClosureOperator() {

        priority = RegexOperator.MINPRIORITY;
    }
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

        NFA newNFA = new NFA();
        NFAState newStart = new NFAState("kleeneStart");
        NFAState newEnd = new NFAState("kleeneEnd");

        newStart.addTransition("\\L",operand.getStartState());
        operand.getAcceptingStates().forEach(s->s.addTransition("\\L",newEnd));
        operand.getAcceptingStates().forEach(s->s.addTransition("\\L", operand.getStartState()));

        newStart.addTransition("\\L", newEnd);
        newNFA.addAll(operand.getStates());
        newNFA.addState(newStart);
        newNFA.setStartState(newStart);
        newNFA.addAcceptingState(newEnd);
        return newNFA;
    }

    public String getRawValue(){
        return "*";
    }

    @Override
    public String toString() {
        return "*";
    }
}
