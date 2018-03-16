package LexicalAnalyser.Regex;

/**
 * Created by alyswidan on 16/03/18.
 */
public class EpsilonRegularDefinition extends RegularDefinition {

    EpsilonRegularDefinition(String rawRegex) {
        super(rawRegex);
    }

    @Override
    public String getRawValue() {
        return "\\L";
    }
}
