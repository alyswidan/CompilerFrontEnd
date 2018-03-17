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
        Regex regex = new Regex("a(a|b)*");
        regex.toPostfix();
        System.out.print(regex.rawRegex);
        assert regex.rawRegex.equals( "aab|*.");
    }
    @org.junit.jupiter.api.Test
    void BigbossPostfix() {
        Regex regex = new Regex("a+b(xb|(ab(ab)*)*)*");
        regex.toPostfix();
        System.out.print(regex.rawRegex);
        assert regex.rawRegex.equals("a+bxb.abab.*..*|*..");
    }

    @org.junit.jupiter.api.Test
    void iterator() {
        RegularDefinitionsTable.put("letter", new RegularDefinition("letter"));
        RegularDefinitionsTable.put("digit", new RegularDefinition("digit"));
        Regex regex = new Regex("letterdigit|*");
        Iterator<RegexElement> iter = regex.iterator();
        assert iter.next().getRawValue().equals("letter");
        assert iter.next().getRawValue().equals("digit");
        assert iter.next().getRawValue().equals("|");
        assert iter.next().getRawValue().equals("*");
        assertFalse(iter.hasNext());
    }

}