package LexicalAnalyser.DFA;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.NFA.NFAState;

import java.util.Set;

/**
 * Created by alyswidan on 18/03/18.
 */
public class DFAState extends State {

    private Set<State> equivalentNFAStates;

    public void addNFAState(NFAState state){
        equivalentNFAStates.add(state);
    }

    public Set<State> getEquivalentNFAStates() {
        return equivalentNFAStates;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DFAState)) return false;
        if (!super.equals(o)) return false;

        DFAState dfaState = (DFAState) o;

        return getEquivalentNFAStates().equals(dfaState.getEquivalentNFAStates());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getEquivalentNFAStates().hashCode();
        return result;
    }
}
