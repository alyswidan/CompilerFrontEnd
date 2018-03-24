package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;
import org.junit.jupiter.api.Test;

/**
 * Created by alyswidan on 24/03/18.
 */
public class OperatorsTest {

    @Test
    void concatConcatKleeneConcat(){
        /*  (ab)*b  */
        NFA a = new RegularDefinition("a").getBasis();
        NFA b = new RegularDefinition("b").getBasis();

        ConcatenationOperator concat = new ConcatenationOperator();
        KleeneClosureOperator kleene = new KleeneClosureOperator();

        System.out.println(a);
        System.out.println(b);

        NFA result = concat.execute(a,b);
        System.out.println(result);

    }

}
