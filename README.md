## usage
To only use the parser:
```bash
   # to generate the parsing table
   python3 ParserAnalyserGenerator generate -f java_parser_grammar  
   # to lex and parse a program
   java -jar CompilerFrontEnd.jar simulate java_lex_grammar java_prog | python3 ParserAnalyserGenerator/ parse  
   
```
