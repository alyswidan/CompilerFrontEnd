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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alyswidan on 16/03/18.
 */
public class GrammarParser {


    List<BareGrammarPair> parseFile(String grammarPath){
        String fileName = grammarPath;
        String line = null;
        List<BareGrammarPair> PairList = new ArrayList<BareGrammarPair>();

        try {

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                int idx;
                String name;
                String value;
                boolean f= false;
                if(line.startsWith("{"))
                {
                    line=line.substring(1,line.length()-1);
                    List<String> splited = Arrays.stream(line.split(" "))
                                                 .map(this::getSeparatedWord)
                                                 .collect(Collectors.toList());
                    fromArrayToPair(splited,BareGrammarPair.Types.KEYWORD,PairList);
                    continue;
                }
                if(line.startsWith("["))
                {
                    line=line.substring(1,line.length()-1);
                    List<String> splited = Arrays.stream(line.split(" ")).collect(Collectors.toList());
                    fromArrayToPair(splited,BareGrammarPair.Types.PUNCTUATION,PairList);

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

                        PairList.add(new BareGrammarPair(name,value,BareGrammarPair.Types.REGEX));

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
                        PairList.add(new BareGrammarPair(name,value,BareGrammarPair.Types.REGDEF));
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

    List<Regex> parseBareGrammar(List<BareGrammarPair> PairList){
        List<Regex> REGEXList = new ArrayList<>();

        for (BareGrammarPair pair : PairList) {

            if (pair.getType().equals(BareGrammarPair.Types.REGDEF)) {
                RegularDefinitionsTable.put(pair.getName(), pair.getValue());
            } else {
                REGEXList.add(new Regex(pair.getValue(),pair.getName()));
            }

        }

        return REGEXList;

    }
    private void fromArrayToPair(List<String> array,BareGrammarPair.Types type,List<BareGrammarPair> PairList) {
        for (String s : array){
            if(type == BareGrammarPair.Types.PUNCTUATION)
            {
                PairList.add(new BareGrammarPair("PUNCTUATION"+s,s,type));
            }
            else
            {
                PairList.add(new BareGrammarPair(s,s,type));
            }
        }
    }

    private String getSeparatedWord(String s) {
        StringBuilder builder = new StringBuilder();
        for(char c : s.toCharArray()){
            builder.append(c).append(" ");
        }
        return builder.toString();
    }





}
