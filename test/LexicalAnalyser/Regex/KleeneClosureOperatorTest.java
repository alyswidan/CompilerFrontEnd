package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;

import java.awt.event.KeyListener;

import static org.junit.jupiter.api.Assertions.*;

class KleeneClosureOperatorTest {

    @org.junit.jupiter.api.Test
    void execute() {
        RegularDefinition a = new RegularDefinition("ab");
        NFA basis = a.getBasis();
        KleeneClosureOperator kc = new KleeneClosureOperator();
        System.out.println(kc.execute(basis));
    }
}