package LexicalAnalyser;

import LexicalAnalyser.BaseModels.Entry;
import LexicalAnalyser.DFA.DFA;
import LexicalAnalyser.DFA.DFASimulator;

import java.io.*;

/**
 * Created by alyswidan on 28/03/18.
 */
public class Main {


    public static void main(String[] args) {
        String mode = args[0];
        String fileName = args[1];
        if(mode.equalsIgnoreCase("generate")){
            DFA dfa = new Generator().generate(fileName);
            System.out.println(dfa);
            try (ObjectOutputStream serializer = new ObjectOutputStream(new FileOutputStream(fileName+".ser"))){
                serializer.writeObject(dfa);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(mode.equalsIgnoreCase("simulate")){
            DFA dfa=null;
            try(ObjectInputStream deserializer = new ObjectInputStream(new FileInputStream(fileName+".ser")))
            {
                dfa = (DFA)deserializer.readObject();
            }
            catch(IOException ex)
            {
                System.out.println("IOException is caught");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            String program = args[2];
            StringBuilder builder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(program))) {

                String line;
                while((line = reader.readLine()) != null){
                    builder.append(line);
                }
            }
            catch (IOException ex){
                ex.printStackTrace();
            }

            DFASimulator simulator = new DFASimulator(dfa,builder.toString());
            while (simulator.hasNext()){
                Entry<String,String> token = simulator.next();
                token.setKey(token.getKey().replaceAll("PUNCTUATION","").replaceAll("\\\\",""));
                System.out.println("<"+token.getKey()+", "+token.getValue()+">");
            }
            System.out.println("<$,$>");


        }else{
            System.out.println("invalid mode "+mode);
        }

    }



}
