package LexicalAnalyser.Regex;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.BaseModels.StateGraph;
import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by alyswidan on 15/03/18.
 */
public class UnionOperator implements BinaryRegexOperator {

//    @Override
//    public NFA execute(List<NFA> operands) {
//        Set<NFAState> startStates =
//        operands.stream().map(state->(NFAState)state.getStartState()).collect(Collectors.toSet());
//        NFAState newStart = NFAState.epsilonSink(startStates);
//        newStart.setName("new Start");
//        System.out.println("start states are now working and they are:\n"+startStates);
//        Set<NFAState> endStates =  new HashSet<>();
//        for (int i = 0; i < operands.size(); i++) {
//            operands.get(i).getAcceptingStates().forEach(s -> endStates.add((NFAState) s));
//            //System.out.println(operands.get(i));
//        }
//        System.out.println("end states are now working and they are:\n"+endStates.size());
//        NFAState endState = NFAState.epsilonSource(endStates);
//        endState.setName("new End");
//        operands.get(0).setStartState(newStart);
//        operands.get(0).setEndState(endState);
//        return operands.get(0);
//    }

    public String getRawValue(){
        return "|";
    }

    @Override
    public NFA execute(NFA leftOperand, NFA rightOperand) {

        NFAState leftStart = (NFAState) leftOperand.getStartState();
        NFAState rightStart = (NFAState) rightOperand.getStartState();
        leftStart.setStart(false);leftStart.setName("leftOperandStart");
        rightStart.setStart(false);rightStart.setName("rightOperandStart");

        NFAState leftEnd = leftOperand.mergeAcceptStates();
        NFAState rightEnd = rightOperand.mergeAcceptStates();
        leftEnd.setAccepting(false);leftEnd.setName("leftOperandEnd");
        rightEnd.setAccepting(false);rightEnd.setName("rightOperandEnd");

        NFAState newStart = new NFAState(false,true,"newStart");
        NFAState newEnd = new NFAState(true,false,"newEnd");

        newStart.addTransition(new EpsilonRegularDefinition(), leftStart);
        newStart.addTransition(new EpsilonRegularDefinition(), rightStart);
        leftEnd.addTransition(new EpsilonRegularDefinition(), newEnd);
        rightEnd.addTransition(new EpsilonRegularDefinition(), newEnd);

        leftOperand.setStartState(newStart);
        leftOperand.setEndState(newEnd);
        return leftOperand;
    }


}
