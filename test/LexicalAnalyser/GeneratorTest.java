package LexicalAnalyser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by alyswidan on 26/03/18.
 */
class GeneratorTest {
    @Test
    void generate() {
        Generator g = new Generator();
        System.out.println(g.generate("temp.txt"));
    }

}