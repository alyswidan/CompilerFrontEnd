package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlusClosureOperatorTest {

    @Test
    void execute() {
        RegularDefinition regDef = new RegularDefinition("a");
        RegularDefinition regDef1 = new RegularDefinition("b");
        NFA basis = regDef.getBasis();
        NFA basis1 = regDef1.getBasis();
        ConcatenationOperator concatenate = new ConcatenationOperator();
        NFA temp = concatenate.execute(basis, basis1);
        PlusClosureOperator plus = new PlusClosureOperator();
        System.out.println("concatenation:\n"+temp);
        System.out.println("plus closure:\n"+plus.execute(temp));
    }
}