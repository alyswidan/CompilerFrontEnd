package LexicalAnalyser.NFA;
import LexicalAnalyser.Regex.ConcatenationOperator;
import LexicalAnalyser.Regex.Regex;
import LexicalAnalyser.BaseModels.State;
import LexicalAnalyser.BaseModels.StateGraph;
import LexicalAnalyser.Regex.UnionOperator;

import java.util.Collection;
import java.util.List;

/**
 * Created by alyswidan on 15/03/18.
 */
public class NFA extends StateGraph {

    public static NFA fromRegex(Regex regex){
        //uses Thompson's algorithm to convert the regex string to an nfa
        return new NFA();
    }

    public static NFA fromMultiple(Collection<NFA> NFAs){
        UnionOperator union = new UnionOperator();
        return union.execute((List<NFA>) NFAs);
    }



}
