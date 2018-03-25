package LexicalAnalyser.DFA;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.Regex.RegularDefinition;
import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * Created by alyswidan on 15/03/18.
 */
public class DFASimulator {
    DFA dfa;
    DFAState currentState;

    public DFASimulator(DFA dfa) {

        this.dfa = dfa;
        currentState = (DFAState)dfa.getStartState();
    }

    public State step(char input){
        DFAState previousState=currentState;
        DFAState cs=currentState;
        String regx =Character.toString(input);
        RegularDefinition reg= new RegularDefinition(regx);
        currentState = currentState.dfaTransition(reg);
        //cs=;
        System.out.println("From: "+previousState.getName()+"  on input: "+input+" To "+currentState.getName());
        return currentState;
    }

    public void reset(){
        currentState = (DFAState)dfa.getStartState();
        /*
        * resets the simulator to the starting state of the dfa
        * */
    }
}
