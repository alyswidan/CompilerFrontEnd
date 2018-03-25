package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by alyswidan on 25/03/18.
 */
class RegexParserTest {
    @Test
    void aOrb() {
        RegexParser parser = new RegexParser();
        NFA result = parser.parse("a|b");
        System.out.println(result);
    }

    @Test
    void aAndBAndDotStar() {
        RegexParser parser = new RegexParser();
        NFA result = parser.parse("(a b .)*");
        System.out.println(result);
    }

    @Test
    void complex() {
        RegexParser parser = new RegexParser();
        NFA result = parser.parse("((a b .)|(a+ b | a*))*");
        System.out.println(result);
    }
}