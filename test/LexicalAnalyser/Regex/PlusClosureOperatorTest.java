package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlusClosureOperatorTest {

    @Test
    void execute() {
        RegularDefinition regDef = new RegularDefinition("a");
        NFA basis = regDef.getBasis();
        PlusClosureOperator plus = new PlusClosureOperator();
        plus.execute(basis);
        System.out.println(basis);
    }
}