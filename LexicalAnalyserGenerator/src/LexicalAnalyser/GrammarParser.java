package LexicalAnalyser;

import LexicalAnalyser.Regex.Regex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alyswidan on 16/03/18.
 */
public class GrammarParser {

    private int lineNumber ;
    List<GrammarTuple> parseFile(String grammarPath){
        lineNumber = 0;
        String fileName = grammarPath;
        String line = null;
        List<GrammarTuple> PairList = new ArrayList<GrammarTuple>();

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
                    String[] splited = line.trim().split(" ");
                    List<String> names = Arrays.stream(splited).collect(Collectors.toList());
                    List<String> values = Arrays.stream(splited)
                                                 .map(this::getSeparatedWord)
                                                 .collect(Collectors.toList());
                    fromArrayToPair(values,names, GrammarTuple.Types.KEYWORD,PairList);
                    lineNumber++;
                    continue;
                }
                if(line.startsWith("["))
                {
                    line=line.substring(1,line.length()-1);
                    List<String> splited = Arrays.stream(line.split(" ")).collect(Collectors.toList());
                    fromArrayToPair(splited,null, GrammarTuple.Types.PUNCTUATION,PairList);
                    lineNumber++;
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

                        if(name.equals("relop")){
                            String []splitted = value.split("|");
                            StringBuilder builder = new StringBuilder();
                            for(String s : splitted){
                                if(s.equals("|"))
                                    builder.append("| ");
                                else
                                    builder.append(getSeparatedWord(s)).append(" ");
                            }
                            value = builder.toString().trim();
                        }
                        PairList.add(new GrammarTuple(name,value, GrammarTuple.Types.REGEX,lineNumber));

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
                        PairList.add(new GrammarTuple(name,value, GrammarTuple.Types.REGDEF,lineNumber));
                        break;
                    }
                }
            lineNumber++;
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

    List<Regex> parseBareGrammar(List<GrammarTuple> PairList){
        List<Regex> REGEXList = new ArrayList<>();

        for (GrammarTuple pair : PairList) {

            if (pair.getType().equals(GrammarTuple.Types.REGDEF)) {
                RegularDefinitionsTable.put(pair.getName(), pair.getValue());
            } else {
                REGEXList.add(new Regex(pair.getValue(),pair.getName(),pair.getOrder()));
            }

        }

        return REGEXList;

    }
    private void fromArrayToPair(List<String> values, List<String> names, GrammarTuple.Types type, List<GrammarTuple> PairList) {

        for (int i = 0; i < values.size(); i++) {
            if(type == GrammarTuple.Types.PUNCTUATION)
            {
                PairList.add(new GrammarTuple("PUNCTUATION"+values.get(i),values.get(i),type,lineNumber));
            }
            else
            {
                PairList.add(new GrammarTuple(names.get(i),values.get(i),type,lineNumber));
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
