package LexicalAnalyser.Regex;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by alyswidan on 15/03/18.
 */
public class ConcatenationOperator implements BinaryRegexOperator{


    @Override
    public NFA execute(NFA leftOperand, NFA rightOperand) {


        /*get hold of starts and accepting states of left and right*/
        NFAState endLeftOperand = leftOperand.mergeAcceptStates();
        NFAState startRightOperand = (NFAState) rightOperand.getStartState();

        endLeftOperand.setAccepting(false);
        leftOperand.removeAcceptingState(endLeftOperand);

        startRightOperand.setStart(false);
        rightOperand.setStartState(null);


/*        System.out.println("L"+leftOperand.getStartState());
        System.out.println("L"+leftOperand.getAcceptingStates());
        System.out.println("------------------------------");
        System.out.println("R"+rightOperand.getStartState());
        System.out.println("R"+rightOperand.getAcceptingStates());
        System.out.println("--------------------------------");*/

        NFA result = new NFA();
        result.addAll(leftOperand.getStates());
        result.addAll(rightOperand.getStates());

/*        System.out.println("r"+result.getStartState());
        System.out.println("r"+result.getAcceptingStates());
        System.out.println("--------------------------------");*/


        result.getState(endLeftOperand)
              .addTransition(new EpsilonRegularDefinition(),result.getState(startRightOperand));

        return result;
    }

    public String getRawValue(){
        return ".";
    }
}
