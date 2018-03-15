package LexicalAnalyser.DFA;

import LexicalAnalyser.BaseModels.State;

/**
 * Created by alyswidan on 15/03/18.
 */
public class DFASimulator {
    DFA dfa;

    public DFASimulator(DFA dfa) {
        this.dfa = dfa;
    }

    public State step(char input){
        return new State();
    }

    public void reset(){
        /*
        * resets the simulator to the starting state of the dfa
        * */
    }
}
