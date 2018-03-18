package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;

import static org.junit.jupiter.api.Assertions.*;

class KleeneClosureOperatorTest {

    @org.junit.jupiter.api.Test
    void execute() {
        RegularDefinition a = new RegularDefinition("a");
        // System.out.println("regular def is :"+a);
        NFA basis = a.getBasis();
        // System.out.println("Basis is :"+basis);
    }
}