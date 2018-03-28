package LexicalAnalyser.DFA;

import LexicalAnalyser.BaseModels.Entry;
import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.Regex.RegularDefinition;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by alyswidan on 15/03/18.
 */
public class DFASimulator implements Iterator<Entry<String,String>>{
    private DFA dfa;
    private DFAState currentState;
    private String inputString;
    private int currentIndex;


    public DFASimulator(DFA dfa,String input) {
        this.inputString = input;
        this.dfa = dfa;
        reset();
        this.currentIndex = 0;
    }

    private void step(char input){
        RegularDefinition reg= new RegularDefinition(Character.toString(input));
        if(Character.isWhitespace(input)){
            currentState = new DeadState();
        }
        else {
            currentState = currentState.dfaTransition(reg);
        }
    }

    private void reset(){
        currentState = (DFAState)dfa.getStartState();
        /*
        * resets the simulator to the starting state of the dfa
        * */
    }

    @Override
    public boolean hasNext() {
        return currentIndex < inputString.length();
    }

    @Override
    public Entry<String, String> next() {
        Deque<DFAState> path = new LinkedList<>();
        StringBuilder builder = new StringBuilder();
        while(currentIndex < inputString.length() && Character.isWhitespace(inputString.charAt(currentIndex))) {
            currentIndex++;
        }
        path.addFirst(currentState);
        while(!(currentState instanceof DeadState)
                && currentIndex < inputString.length()){
            builder.append(inputString.charAt(currentIndex));
            step(inputString.charAt(currentIndex++));
            path.addFirst(currentState);
        }

        DFAState TOS = path.removeFirst();
        String candidateString = builder.toString().trim();
        int endIndex = currentIndex;
        while(!path.isEmpty() && !TOS.isAccepting()){
            builder.deleteCharAt(builder.length()-1);
            currentIndex--;
            TOS = path.removeFirst();
        }

        reset();
        if(builder.length() == 0)
        {
            currentIndex = endIndex;
            return new Entry<>("invalid",candidateString);

        }
        return new Entry<>(TOS.getAcceptingValue(),builder.toString());

    }
}
