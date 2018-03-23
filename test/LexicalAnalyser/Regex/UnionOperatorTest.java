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
        List<NFA> operands = new ArrayList<>();
        operands.add(basis1);
        operands.add(basis2);
        operands.add(basis3);
        UnionOperator union = new UnionOperator();
        ConcatenationOperator concatenate = new ConcatenationOperator();
        NFA test = union.execute(basis1, basis2);
        System.out.println(concatenate.execute(test, basis3));
    }
}