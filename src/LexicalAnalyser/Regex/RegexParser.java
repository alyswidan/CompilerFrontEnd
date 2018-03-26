package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;

import javax.swing.text.Position;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alyswidan on 15/03/18.
 */
public class RegexParser {

      /*
    * This class implements the algorithm used to build an
    * NFA from a regex(Thompson's algorithm)
    * */

    private NFA currentNFA;
    private Deque<NFA> currentStack = new LinkedList<>(); // using a deque as our stack

    public Collection<NFA> parseAll(Collection<Regex> regexes){
        return regexes.stream().map(this::parse).collect(Collectors.toList());
    }

    public NFA parse(String rawRegex){
        return parse(new Regex(rawRegex));
    }

    public NFA parse(Regex regex){
        /*
        * iterate over elements of the regex and use the execute element method to build
        * the NFA incrementally
        *
        * */
        if(!regex.isPostfix()){
            regex.toPostfix();
        }

        for (RegexElement element : regex){
            if(element instanceof RegularDefinition){
                NFA b = ((RegularDefinition) element).getBasis();
                currentStack.addFirst(b);
            }
            if(element instanceof  UnaryRegexOperator){
                NFA res = ((UnaryRegexOperator) element).execute(currentStack.removeFirst());
                currentStack.addFirst(res);
            }
            if(element instanceof BinaryRegexOperator){
                NFA a = currentStack.removeFirst();
                NFA b = currentStack.removeFirst();
                currentStack.addFirst(((BinaryRegexOperator) element).execute(b,a));
            }

        }
        NFA result = currentStack.removeFirst();
        result.setAcceptingValue(regex.getAcceptingValue()) ;
        System.out.println(regex);
        System.out.println("________________________________");
        System.out.println(result);
        System.out.println("----------------------------------------");
        return result;
    }



}
