package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnionOperatorTest {

    @Test
    void execute() {
        RegularDefinition a = new RegularDefinition("a");
        RegularDefinition b = new RegularDefinition("b");
        RegularDefinition c = new RegularDefinition("c");
        NFA basis1 = a.getBasis();
        NFA basis2 = b.getBasis();
        NFA basis3 = c.getBasis();
        UnionOperator union = new UnionOperator();
        NFA temp = union.execute(basis1, basis2);
        temp = union.execute(temp, basis3);
        KleeneClosureOperator kc = new KleeneClosureOperator();
        System.out.println(kc.execute(temp));
    }

    @Test
    void execute1(){
        RegularDefinition a = new RegularDefinition("a");
        RegularDefinition b = new RegularDefinition("b");
        NFA basis1 = a.getBasis();
        NFA basis2 = b.getBasis();
        UnionOperator union = new UnionOperator();
        System.out.println(union.execute(basis1, basis2));
    }
}