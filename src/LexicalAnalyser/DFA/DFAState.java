package LexicalAnalyser.DFA;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.NFA.NFAState;
import LexicalAnalyser.Regex.RegularDefinition;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by alyswidan on 18/03/18.
 */
public class DFAState extends State {

    private Set<NFAState> equivalentNFAStates;

    public DFAState() {
    }

    public DFAState(Set<NFAState> equivalentNFAStates, String name) {
        super(name);
        this.equivalentNFAStates = equivalentNFAStates;
    }

    public DFAState(Set<NFAState> equivalentNFAStates) {
        this.equivalentNFAStates = equivalentNFAStates;
    }

    public void addNFAState(NFAState state){
        equivalentNFAStates.add(state);
    }

    public void setEquivalentNFAStates(Set<NFAState> equivalentNFAStates) {
        this.equivalentNFAStates = equivalentNFAStates;
    }

    public Set<NFAState> getEquivalentNFAStates() {
        return equivalentNFAStates;
    }

    public DFAState transition(RegularDefinition regDef) {
        return new DFAState(equivalentNFAStates.stream()
                .map(state -> (NFAState)state.transition(regDef))
                .collect(Collectors.toSet()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DFAState)) return false;

        DFAState dfaState = (DFAState) o;

        return dfaState.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        int result = 31 * getEquivalentNFAStates().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + "-" + equivalentNFAStates.toString();
    }
}
