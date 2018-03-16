package LexicalAnalyser.Regex;

import LexicalAnalyser.RegularDefinitionsTable;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by alyswidan on 15/03/18.
 */
class RegexTest {
    @org.junit.jupiter.api.Test
    void toPostfix() {


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