package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;

import static org.junit.jupiter.api.Assertions.*;

class KleeneClosureOperatorTest {

    @org.junit.jupiter.api.Test
    void execute() {
        RegularDefinition a = new RegularDefinition("a");
        // System.out.println("regular def is :"+a);
        NFA basis = a.getBasis();
        KleeneClosureOperator kc = new KleeneClosureOperator();
        kc.execute(basis);
        System.out.println(basis);

        // System.out.println("Basis is :"+basis);
    }
}