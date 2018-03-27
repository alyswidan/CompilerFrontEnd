package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;

import java.awt.event.KeyListener;

import static org.junit.jupiter.api.Assertions.*;

class KleeneClosureOperatorTest {

    @org.junit.jupiter.api.Test
    void execute() {
        RegularDefinition a = new RegularDefinition("a");
        RegularDefinition b = new RegularDefinition("b");
        NFA basis1 = a.getBasis();
        NFA basis2 = b.getBasis();
        ConcatenationOperator conc = new ConcatenationOperator();
        KleeneClosureOperator kc = new KleeneClosureOperator();
        System.out.println(conc.execute(kc.execute(basis1), basis2));;
    }
}