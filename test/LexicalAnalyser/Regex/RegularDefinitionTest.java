package LexicalAnalyser.Regex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by alyswidan on 22/03/18.
 */
class RegularDefinitionTest {
    @Test
    void getParts() {
        RegularDefinition reg = new RegularDefinition("\\L");
        System.out.println(reg);
        System.out.println(reg.getParts());
    }

}