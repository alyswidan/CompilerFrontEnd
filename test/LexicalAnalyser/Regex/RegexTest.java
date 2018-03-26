package LexicalAnalyser.Regex;

import LexicalAnalyser.RegularDefinitionsTable;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by alyswidan on 15/03/18.
 */
class RegexTest {
    @org.junit.jupiter.api.Test
    void orPostfix() {
        Regex regex = new Regex("a|b");
        regex.toPostfix();
        System.out.print(regex.rawRegex);
        assert regex.rawRegex.equals( "ab|");
    }
    @org.junit.jupiter.api.Test
    void concatPostfix() {
        Regex regex = new Regex("a+b");
        regex.toPostfix();
        System.out.print(regex.rawRegex);
        assert regex.rawRegex.equals( "a+b.");
    }
    @org.junit.jupiter.api.Test
    void braketPostfix() {
        Regex regex = new Regex("(a|b)*");
        regex.toPostfix();
        System.out.print(regex.rawRegex);
        assert regex.rawRegex.equals( "ab|*");
    }
    @org.junit.jupiter.api.Test
    void checkingPostfix() {
        Regex regex = new Regex("a(a|b .+)*");
        regex.toPostfix();
        System.out.print(regex.rawRegex);
//        assert regex.rawRegex.equals( "aab|*.");
    }
    @org.junit.jupiter.api.Test
    void BigbossPostfix() {
        Regex regex = new Regex("a+b(xb|(ab(ab)*)*)*");
        regex.toPostfix();
        System.out.print(regex.rawRegex);
        assert regex.rawRegex.equals("a+bxbabab.*..*|*..");
    }

    @org.junit.jupiter.api.Test
    void iterator() {
            RegularDefinitionsTable.put("letter", "a-z");
            RegularDefinitionsTable.put("digit", "0-9");

        Regex regex = new Regex("\\. letter digit A|.|");
        for (RegexElement element : regex){
            System.out.print(element.getRawValue() +" " + element.getClass() + "\n");
        }
    }

    @org.junit.jupiter.api.Test
    void iteratorInfix() {
        RegularDefinitionsTable.put("letter", "a-z");
        RegularDefinitionsTable.put("digit", "0-9");

        Regex regex = new Regex("letter+(letter | digit \\. | \\*)*");
        for (RegexElement element : regex) {
            System.out.print(element.getRawValue() + " " + element.getClass().getSimpleName() + "\n");
        }
    }

    @org.junit.jupiter.api.Test
    void iteratorNotSplittable() {
        RegularDefinitionsTable.put("letter", "a-z");
        RegularDefinitionsTable.put("digits", "digit*");
        RegularDefinitionsTable.put("digit", "0-9");

        Regex regex = new Regex("digit(digit+ | letter digits digits digits | digit)*");
        for (RegexElement element : regex) {
            System.out.print(element.getRawValue() + " " + element.getClass().getSimpleName() + "\n");
        }
    }

    @org.junit.jupiter.api.Test
    void iteratorNotSplittableComplexRegDef() {
        RegularDefinitionsTable.put("letter", "a-z");
        RegularDefinitionsTable.put("digits", "digit*|0-6");
        RegularDefinitionsTable.put("digit", "0-9");

        Regex regex = new Regex("digit(digit+ | letter digits digits digits | digit)*");
        for (RegexElement element : regex) {
            System.out.print(element.getRawValue() + " " + element.getClass().getSimpleName() + "\n");
        }
    }

    @org.junit.jupiter.api.Test
    void unaryFollowedByBracket() {
        RegularDefinitionsTable.put("letter","a-z");
        RegularDefinitionsTable.put("digit", "0-9");

        Regex regex = new Regex("letter+(letter | digit)*");
        for (RegexElement element : regex){
            System.out.println(element);
//            System.out.print(element +" " + element.getClass().getSimpleName() + "\n");
        }
    }

    @org.junit.jupiter.api.Test
    void consecutiveRegDefs() {
        RegularDefinitionsTable.put("letter","a-z");
        RegularDefinitionsTable.put("digit", "0-9");

        Regex regex = new Regex("letter letter letter digit A B digit");

        for (RegexElement element : regex){
            System.out.print(element.getRawValue() +" " + element.getClass().getSimpleName() + "\n");
        }
    }

    @org.junit.jupiter.api.Test
    void regDefFollowedByABracket() {
        RegularDefinitionsTable.put("letter", "a-z");
        RegularDefinitionsTable.put("digit", "0-9");

        Regex regex = new Regex("(A|B)*letter A B|\\L|\\* \\+");
        for (RegexElement element : regex){
            System.out.print(element.getRawValue() +" " + element.getClass().getSimpleName() + "\n");
        }
    }


    @org.junit.jupiter.api.Test
    void priority() {
        RegexOperator k = RegexOperatorFactory.getOperator('*');
        RegexOperator p = RegexOperatorFactory.getOperator('+');
        RegexOperator u = RegexOperatorFactory.getOperator('|');
        System.out.println(u.compareTo(k));
        System.out.println(k.compareTo(u));
    }


}

