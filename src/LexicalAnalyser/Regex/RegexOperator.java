package LexicalAnalyser.Regex;

import java.util.Comparator;

/**
 * Created by alyswidan on 15/03/18.
 */
public abstract class RegexOperator implements RegexElement,Comparable<RegexOperator>{
    protected static final int MINPRIORITY = 1;
    protected int priority = 0;
    @Override
    public int compareTo(RegexOperator regexOperator) {




        Comparator<RegexOperator> comparator = Comparator.comparingInt(regexOp -> regexOp.priority);
        return comparator.compare(this, regexOperator);
    }
}
