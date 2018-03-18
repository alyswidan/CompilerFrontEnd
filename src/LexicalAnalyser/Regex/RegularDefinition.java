package LexicalAnalyser.Regex;

import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.NFA.NFA;
import LexicalAnalyser.NFA.NFAState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by alyswidan on 14/03/18.
 */
public class RegularDefinition implements RegexElement {

/*
* Even though a regular definition is part of a regular expression
* it could be a regex it self
* */

    private String rawRegdef;
    private Set<AsciiRange> asciiRanges;

    public RegularDefinition(String rawRegdef)  {
        this.rawRegdef = rawRegdef;
        this.asciiRanges = parse(rawRegdef);
    }


    List<String> split(String s, char sep){
        List<String> out = new ArrayList<>();
        StringBuilder currentString = new StringBuilder();
        for (char c : s.toCharArray()){
            if(c != sep)
                currentString.append(c);
            else{
                out.add(currentString.toString());
                currentString = new StringBuilder();
            }
        }
        if(currentString.length() != 0)
            out.add(currentString.toString());

        return out;
    }


    Set<AsciiRange> parse(String rawRegdef) {
        List<String> oredRanges = split(rawRegdef,'|');
        Set<AsciiRange> parsedRanges = new HashSet<>();
        for(String range : oredRanges){
            List<String> startAndEnd = split(range,'-');
            if(startAndEnd.size() > 2)
                System.out.println("exception regdef");
            if(startAndEnd.size() == 1)
                parsedRanges.add(new AsciiRange(startAndEnd.get(0),startAndEnd.get(0)));

            else
                parsedRanges.add(new AsciiRange(startAndEnd.get(0), startAndEnd.get(1)));

        }
        return parsedRanges;
    }

    boolean matches(char input){
        return asciiRanges.stream().anyMatch(asciiRange -> asciiRange.belongs(input));
    }

    NFA getBasis(){
        NFAState start = new NFAState(false,true,"start "+getRawValue());
        NFAState end = new NFAState(true,false, "end "+getRawValue());
        start.addTransition(this,end);
        NFA nfa = new NFA();
        //System.out.println("start state is: "+start);
        nfa.addState(start);
        //System.out.println("end state is: "+end);
        nfa.addState(end);
        return nfa;
    }

    public String getRawValue(){
        return this.rawRegdef;
    }

    public String toString() {
        return this.rawRegdef;
    }

    private class AsciiRange{
        private int start, end;

        public AsciiRange(String start_str, String end_str) {
            if(start_str.length() != 1 || end_str.length() != 1)
                System.out.println("exception ascii range");
            start = start_str.charAt(0);
            end = end_str.charAt(0);
        }



        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public boolean belongs(char c){
            return c>=start && c<=end;
        }


    }
}
