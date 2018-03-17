package LexicalAnalyser.Regex;

import java.util.Deque;
import java.util.Iterator;

/**
 * Created by alyswidan on 15/03/18.
 */
public class Regex implements Iterable<RegexElement>{
    protected String rawRegex;
    private boolean isPostfix = false;

    Regex(String rawRegex){
        this.rawRegex = rawRegex;
    }



    void toPostfix(){

        /*
        * perform infix to postfix conversion of the raw regex string to a
        * postfix regex string and replacing the value of raw regex with
        * this postfix expression
        */
          // new branch added
        if(isPostfix()){
            return;
        }

    }

    public boolean isPostfix() {
        return isPostfix;
    }

    @Override
    public Iterator<RegexElement> iterator() {
        return new RegexIterator(this);
    }

    public int length(){
        return rawRegex.length();
    }



}
