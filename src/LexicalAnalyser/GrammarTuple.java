package LexicalAnalyser;

/**
 * Created by alyswidan on 16/03/18.
 */
public class GrammarTuple {

    /*
    * represents a pair of sting grammar pairs as they were in the grammar file
    *
    * */
    public enum Types{REGEX,REGDEF,KEYWORD,PUNCTUATION}


    private String name,value;
    private Types type;
    private int order;

    public String getValue() {
        return value;
    }

    public String getName() {

        return name;
    }

    public int getOrder() {
        return order;
    }

    public GrammarTuple(String name, String value, Types type, int order) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.order = order;
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
