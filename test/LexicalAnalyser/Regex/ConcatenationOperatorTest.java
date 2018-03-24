package LexicalAnalyser.Regex;

import LexicalAnalyser.NFA.NFA;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ConcatenationOperatorTest {

    @Test
    void multipleConcats(){
        List<NFA> bases = IntStream.rangeClosed('a', 'f')
                .mapToObj(c -> new RegularDefinition((char)c+"").getBasis())
                .collect(Collectors.toList());
        ConcatenationOperator op = new ConcatenationOperator();

        while(bases.size()>1){
            List<NFA> nextLevel = new ArrayList<>();
            for (int i = 0; i < bases.size()-1; i+=2) {
                System.out.print("i = " + i);
                System.out.println("bases = " + bases.size());
                NFA left = bases.get(i);
                System.out.println("merged ");
                System.out.println(left);
                NFA right = bases.get(i+1);
                System.out.println("and");
                System.out.println(right);
                NFA result = op.execute(left,right);
                System.out.println("into");
                System.out.println(result);
                nextLevel.add(result);
            }
            bases = nextLevel;
        }
    }

    @Test
    void execute() {
        RegularDefinition left = new RegularDefinition("a*");
        RegularDefinition right = new RegularDefinition("bc");
        NFA basisLeft = left.getBasis();
        NFA basisRight = right.getBasis();
        ConcatenationOperator concatenate = new ConcatenationOperator();
        concatenate.execute(basisLeft, basisRight);
        System.out.println(basisLeft);
    }
}