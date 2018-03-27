package LexicalAnalyser.Regex;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by alyswidan on 26/03/18.
 */
class MultiUnionOperatorTest {
    @Test
    void execute() {
        List<RegularDefinition> regs = IntStream.rangeClosed('a','d')
                                                .mapToObj(i->new RegularDefinition((char)i+""))
                                                .collect(Collectors.toList());

        MultiUnionOperator union = new MultiUnionOperator();
        System.out.println(union.execute(regs.stream()
                                .map(RegularDefinition::getBasis)
                                .collect(Collectors.toList())));
    }

}