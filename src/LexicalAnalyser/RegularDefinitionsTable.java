package LexicalAnalyser;

import LexicalAnalyser.Regex.RegularDefinition;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 * Created by alyswidan on 15/03/18.
 */
public class RegularDefinitionsTable {

    static Map<String, RegularDefinition> regularDefinitions = new HashMap<>();


    public static int size() {
        return regularDefinitions.size();
    }

    public static boolean isEmpty() {
        return regularDefinitions.isEmpty();
    }

    public static boolean containsKey(Object o) {
        return regularDefinitions.containsKey(o);
    }

    public static RegularDefinition get(Object o) {
        return regularDefinitions.get(o);
    }

    public static RegularDefinition put(String s, String regdef) {

        RegularDefinition regularDefinition=new RegularDefinition(regdef);

        return regularDefinitions.put(s, regularDefinition);
    }

    public static RegularDefinition remove(Object o) {
        return regularDefinitions.remove(o);
    }

    public static Collection<RegularDefinition> values() {
        return regularDefinitions.values();
    }

    public static void forEach(BiConsumer<? super String, ? super RegularDefinition> biConsumer) {
        regularDefinitions.forEach(biConsumer);
    }
}
