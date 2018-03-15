package LexicalAnalyser.Regex;

import java.util.Iterator;

/**
 * Created by alyswidan on 15/03/18.
 */
public class Regex implements Iterable<RegexElement>{

    private String rawRegex;
    private boolean isPostfix = false;

    Regex(String rawRegex){
        this.rawRegex = rawRegex;
    }

    Regex toPostfix(){
        if(isPostfix()){
            return this;
        }
        //perform infix to postfix conversion of the raw regex string to a
        //postfix regex string and replacing the value of raw regex with
        //this postfix expression

        return null;
    }

    public boolean isPostfix() {
        return isPostfix;
    }

    @Override
    public Iterator<RegexElement> iterator() {
        return new RegexIterator(this);
    }


}
