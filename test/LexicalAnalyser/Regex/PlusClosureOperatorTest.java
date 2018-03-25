package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;
import org.junit.jupiter.api.Test;

import java.lang.management.PlatformLoggingMXBean;

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

    @Test
    void execute1(){
        RegularDefinition a = new RegularDefinition("a");
        RegularDefinition b = new RegularDefinition("b");
        NFA basis1 = a.getBasis();
        NFA basis2 = b.getBasis();
        UnionOperator union = new UnionOperator(UnionOperator.Mode.MERGE_ACCEPT);
        PlusClosureOperator plus = new PlusClosureOperator();
        NFA temp = union.execute(basis1, basis2);
        System.out.println(plus.execute(temp));
    }

    @Test
    void singleLetterPlus(){
        RegularDefinition b = new RegularDefinition("b");
        NFA basis2 = b.getBasis();
        PlusClosureOperator plus = new PlusClosureOperator();
        System.out.println(plus.execute(basis2));
    }
}