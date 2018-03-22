package LexicalAnalyser.Regex;

import java.util.Set;

/**
 * Created by alyswidan on 16/03/18.
 */
public class EpsilonRegularDefinition extends RegularDefinition {

    public EpsilonRegularDefinition(){
        super("\\L");
    }

    @Override
    public Set<RegularDefinition> getParts() {
        if (parts.size() == 0){
            this.parts.add(new EpsilonRegularDefinition());
        }
        return this.parts;
    }

    @Override
    public String getRawValue() {
        return "\\L";
    }
}
