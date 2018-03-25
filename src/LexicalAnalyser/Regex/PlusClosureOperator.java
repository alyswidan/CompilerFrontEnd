package LexicalAnalyser.Regex;

import LexicalAnalyser.BaseModels.State;
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

        NFA newNFA = new NFA();
        newNFA.addAll(operand.getStates());
        newNFA.getAcceptingStates()
                .forEach(s-> s.addTransition(new EpsilonRegularDefinition(), operand.getStartState()));
        newNFA.setStartState(operand.getStartState());
        return newNFA;
    }

    public String getRawValue(){
        return "+";
    }

    @Override
    public String toString() {
        return "+";
    }
}
