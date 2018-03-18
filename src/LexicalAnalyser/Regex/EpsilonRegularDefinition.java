package LexicalAnalyser.Regex;

/**
 * Created by alyswidan on 16/03/18.
 */
public class EpsilonRegularDefinition extends RegularDefinition {

    public EpsilonRegularDefinition(){
        super("\\L");
    }


    @Override
    public String getRawValue() {
        return "\\L";
    }
}
