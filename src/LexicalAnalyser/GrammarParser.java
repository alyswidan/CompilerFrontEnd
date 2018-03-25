package LexicalAnalyser;

import LexicalAnalyser.Regex.Regex;
import LexicalAnalyser.Regex.RegexElement;
import LexicalAnalyser.Regex.RegularDefinition;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by alyswidan on 16/03/18.
 */
public class GrammarParser {


    List<BareGrammarPair> parseFile(String grammarPath){
        /*
        * http://tutorials.jenkov.com/java-nio/path.html
        * please use the nio api to manipulate files
        * the above link has some good information
        * -----------------------------------------
        *
        *
        * */

        String fileName = grammarPath;
        String line = null;
        List<BareGrammarPair> PairList = new ArrayList<BareGrammarPair>();

        try {

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                int idx;
                String name;
                String value;
                boolean f= false;
                if(line.startsWith("{"))
                {
                    line=line.substring(1,line.length()-1);
                    String[] splited = line.split(" ");
                    fromArrayToPair(splited,BareGrammarPair.Types.KEYWORD,PairList);
//                    KW.name.add("keyword");
//                    KW.value.add(line);
//                    System.out.println("keyword");
//                    System.out.println(line);
                    continue;
                }
                if(line.startsWith("["))
                {
                    line=line.substring(1,line.length()-1);
                    String[] splited = line.split(" ");
                    fromArrayToPair(splited,BareGrammarPair.Types.PUNCTUATION,PairList);
//                    PN.name.add("punctuations");
//                    PN.value.add(line);
//                    System.out.println("punctuations");
//                    System.out.println(line);
                    continue;
                }

                for (int i = 0; i < line.length(); i++){
                    char c = line.charAt(i);
                    if(c==':')
                    {
                        idx = i;
                        f = true;
                        name = line.substring(0,idx);
                        value = line.substring(idx+1);
                        System.out.println(name);
                        System.out.println(value);
                        PairList.add(new BareGrammarPair(name,value,BareGrammarPair.Types.REGEX));
//                        RE.name.add(name);
//                        RE.value.add(value);
                        break;
                    }
                }
                for (int j = 1; j < line.length(); j++){
                    char c = line.charAt(j);
                    char p = line.charAt(j-1);
                    if(c=='='&& p!='\\' && !f)
                    {
                        idx = j;
                        name = line.substring(0,idx);
                        value = line.substring(idx+1);
                        System.out.println(name);
                        System.out.println(value);
                        PairList.add(new BareGrammarPair(name,value,BareGrammarPair.Types.REGDEF));
//                        RD.name.add(name);
//                        RD.value.add(value);
                        break;
                    }
                }

            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println( "Error reading file '" + fileName + "'");
        }
        return PairList;
    }

    Collection<Regex> parseBareGrammar(List<BareGrammarPair> PairList){
        Collection<Regex> REGEXList = new ArrayList<Regex>();

        for (int i = 0; i < PairList.size(); i++) {
            if(PairList.get(i).getType().equals(BareGrammarPair.Types.REGEX))
            {
                REGEXList.add(new Regex(PairList.get(i).getValue()));
                System.out.println("REGEX: "+PairList.get(i).getValue());
            }
            else if(PairList.get(i).getType().equals(BareGrammarPair.Types.REGDEF))
            {
                RegularDefinitionsTable.put(PairList.get(i).getName(),PairList.get(i).getValue());
                System.out.println("REGDEF name: "+PairList.get(i).getName());
                System.out.println("REGDEF value: "+PairList.get(i).getValue());
            }
        }

        return REGEXList;

    }
    public void fromArrayToPair(String[] array,BareGrammarPair.Types type,List<BareGrammarPair> PairList) {
        for (int i = 0; i < array.length; i++){
            if(type == BareGrammarPair.Types.PUNCTUATION)
            {
                System.out.println("PUNCTUATION"+i);
                System.out.println(array[i]);
                PairList.add(new BareGrammarPair("PUNCTUATION"+i,array[i],type));
            }
            else
            {
                System.out.println(array[i]);
                System.out.println(array[i]);
                PairList.add(new BareGrammarPair(array[i],array[i],type));
            }
        }
    }




}
