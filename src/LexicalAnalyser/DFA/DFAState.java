package LexicalAnalyser.DFA;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.NFA.NFAState;
import LexicalAnalyser.Regex.RegularDefinition;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by alyswidan on 18/03/18.
 */
public class DFAState extends State {

    private Set<NFAState> equivalentNFAStates;

    public DFAState() {
        this.equivalentNFAStates = new HashSet<>();
    }

    public DFAState(Set<NFAState> equivalentNFAStates, String name) {
        super(name);
        this.equivalentNFAStates = new HashSet<>();
        equivalentNFAStates.forEach(this::addNFAState);
    }

    public DFAState(Set<NFAState> equivalentNFAStates) {
        this(equivalentNFAStates,"");
    }
    public DFAState(String s)
    {

        this(new HashSet<>(),s);
    }

    public void addNFAState(NFAState state){
        if(state != null && state.isAccepting()) {
            setAccepting(true);
        }
        equivalentNFAStates.add(state);
    }

    public void setEquivalentNFAStates(Set<NFAState> equivalentNFAStates) {
        equivalentNFAStates.forEach(this::addNFAState);
    }

    public Set<NFAState> getEquivalentNFAStates() {
        return equivalentNFAStates;
    }

    public DFAState dfaTransition(RegularDefinition regDef) {
        if(equivalentNFAStates.isEmpty())
            return (DFAState) transition(regDef).stream().findAny().get();
        DFAState s = new DFAState(equivalentNFAStates
                .stream()
                .map(state -> state.transition(regDef))
                .flatMap(states -> states.stream())
                .map(state -> (NFAState)state)
                .collect(Collectors.toSet()));
        return s;
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

        return getName().hashCode() + getEquivalentNFAStates().hashCode();
    }

    @Override
    public String toString() {
        return getName();
                //super.toString() + "-" + equivalentNFAStates.toString()
                //+ " "+(isAccepting()?"A ":"") + (isStart()?"S":"");
    }
}
