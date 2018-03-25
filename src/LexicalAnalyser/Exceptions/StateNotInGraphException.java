package LexicalAnalyser.Exceptions;

import LexicalAnalyser.BaseModels.State;

/**
 * Created by alyswidan on 25/03/18.
 */
public class StateNotInGraphException extends Exception {
    public StateNotInGraphException(State state){
        super("state("+state + ") does not belong to the graph");
    }
}
