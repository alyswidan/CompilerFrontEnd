package LexicalAnalyser;

/**
 * Created by alyswidan on 23/03/18.
 */
public class Utils {

    public static boolean isInteger(String s){

        try {
            Integer.parseInt(s);
        }catch (NumberFormatException ex){
            return false;
        }
        return true;
    }
}
