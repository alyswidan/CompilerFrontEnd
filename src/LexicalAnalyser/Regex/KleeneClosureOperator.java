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

//        NFAState start = (NFAState) operand.getStartState();
//        NFAState end  = operand.mergeAcceptStates();
//
//
//        NFAState newStart = NFAState.newStartState("kleeneClousreStart");
//        NFAState newEnd = NFAState.newAcceptingState("KleeneeClosureEnd");
//
//        newStart.addTransition(new EpsilonRegularDefinition(), start);
//        end.addTransition(new EpsilonRegularDefinition(), newEnd);
//        end.addTransition(new EpsilonRegularDefinition(), start);
//        newStart.addTransition(new EpsilonRegularDefinition(), newEnd);
//
//        operand.setStartState(newStart);
//        operand.setEndState(newEnd);

        NFA newNFA = new NFA();
        newNFA.addAll(operand.getStates());

        NFAState newStart = new NFAState("KleeneClosureStart");
        NFAState newEnd = new NFAState("KleeneeClosureEnd");

        newNFA.addState(newStart);
        newNFA.addState(newEnd);

        newStart.addTransition(new EpsilonRegularDefinition(), operand.getStartState());
        newNFA.getAcceptingStates().forEach(s->s.addTransition(new EpsilonRegularDefinition(), newEnd));

        newNFA.getAcceptingStates().forEach(s->s.addTransition(new EpsilonRegularDefinition(), operand.getStartState()));

//        operand.getStartState().setStart(false);
//        operand.getAcceptingStates().forEach(s->s.setAccepting(false));
//
//        newStart.addTransition(new EpsilonRegularDefinition(), newEnd);
//
//        newNFA.setStartState(newStart);
//        newNFA.setEndState(newEnd);
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
