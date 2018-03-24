package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

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

        operand.getAcceptingStates()
                .forEach(s-> s.addTransition(new EpsilonRegularDefinition(), operand.getStartState()));
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

    @Override
    public String toString() {
        return "+";
    }
}
