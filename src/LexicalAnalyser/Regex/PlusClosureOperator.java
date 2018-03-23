package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

/**
 * Created by alyswidan on 15/03/18.
 */
public class PlusClosureOperator implements UnaryRegexOperator {

    @Override
    public NFA execute(NFA operand) {
        /**
         * end --> epsilon --> start
         */
        NFAState start = (NFAState) operand.getStartState();
        NFAState end = operand.mergeAcceptStates();

//        end.addTransition(start
//                .getTransitions()
//                .keySet()
//                .stream()
//                .findFirst()
//                .get(), end);
        return operand;
        /**
         * it can be done like this yet to organize
         */
//        return  new ConcatenationOperator()
//                .execute(operand, new KleeneClosureOperator().execute(operand));
    }

    public String getRawValue(){
        return "+";
    }

}
