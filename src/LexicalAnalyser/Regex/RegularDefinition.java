package LexicalAnalyser.Regex;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by alyswidan on 14/03/18.
 */
public class RegularDefinition implements RegexElement {


    protected String rawRegdef;
    protected Set<RegularDefinition> parts;
    public RegularDefinition(String rawRegdef)  {
        this.rawRegdef = rawRegdef;
        Set<String> stringParts = parse(rawRegdef);
        parts = new HashSet<>();
        if(stringParts.size() > 1 && rawRegdef.charAt(0)!='\\'){
            this.parts = stringParts.stream()
                    .map(RegularDefinition::new).collect(Collectors.toSet());
        }

    }

    public Set<RegularDefinition> getParts() {
        if (parts.size() == 0){
            this.parts.add(new RegularDefinition(rawRegdef));
        }
        return this.parts;
    }

    private List<String> split(String s, char sep){
        List<String> out = new ArrayList<>();
        StringBuilder currentString = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c != sep)
                currentString.append(c);
            else {
                out.add(currentString.toString());
                currentString = new StringBuilder();
            }
        }
        if (currentString.length() != 0)
            out.add(currentString.toString());

        return out;
    }


    private Set<String> parse(String rawRegdef) {

        List<String> oredRanges = split(rawRegdef, '|');
        Set<String> parsedStrings = new HashSet<>();
        for (String range : oredRanges) {
            List<String> startAndEnd = split(range, '-');
            if (startAndEnd.size() > 2)
                System.out.println("exception regdef");
            if (startAndEnd.size() == 1)
                parsedStrings.add(startAndEnd.get(0));
            else
                parsedStrings.addAll(rangeToChars(startAndEnd.get(0),startAndEnd.get(1)));
        }
        return parsedStrings;
    }


    private List<String> rangeToChars(String start_str, String end_str){
        return IntStream.rangeClosed(start_str.charAt(0), end_str.charAt(0))
                .mapToObj(c -> (char) c + "")
                .collect(Collectors.toList());
    }


    NFA getBasis() {
        NFAState start = NFAState.newStartState("start "+getRawValue());
        NFAState end = NFAState.newAcceptingState("end "+getRawValue());
        start.addTransition(this,end);
        NFA nfa = new NFA();
        nfa.addState(start);
        nfa.addState(end);
        return nfa;
    }


    public String getRawValue(){
        return this.rawRegdef;
    }

    public String toString() {
        return this.rawRegdef;
    }


    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof RegularDefinition)) return false;
        RegularDefinition that = (RegularDefinition)o;
        return that.toString().equals(this.toString());
    }

    @Override
    public int hashCode() {
        return rawRegdef != null ? rawRegdef.hashCode() : 0;
    }
}
