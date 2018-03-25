package LexicalAnalyser.Regex;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;
import com.sun.org.apache.bcel.internal.generic.NEW;
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
        operand.getAcceptingStates().forEach(s->s.addTransition("\\L", operand.getStartState()));
        newNFA.setStartState(operand.getStartState());
        operand.getAcceptingStates().forEach(s-> newNFA.addAcceptingState(s));
        newNFA.addAll(operand.getStates());
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
