package LexicalAnalyser;

/**
 * Created by alyswidan on 16/03/18.
 */
public class BareGrammarPair {

    /*
    * represents a pair of sting grammar pairs as they were in the grammar file
    *
    * */
    public enum Types{REGEX,REGDEF,KEYWORD,PUNCTUATION}


    private String name,value;
    private Types type;

    public String getValue() {
        return value;
    }

    public String getName() {

        return name;
    }

    public BareGrammarPair(String name, String value, Types type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public Types getType() {
        return type;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}
