package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConcatenationOperatorTest {

    @Test
    void execute() {
        RegularDefinition left = new RegularDefinition("a");
        RegularDefinition right = new RegularDefinition("b");
        NFA basisLeft = left.getBasis();
        NFA basisRight = right.getBasis();
        ConcatenationOperator concatenate = new ConcatenationOperator();
        concatenate.execute(basisLeft, basisRight);
        System.out.println(basisLeft);
    }
}