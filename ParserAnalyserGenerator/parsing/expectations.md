# Parser
## Expected Input

The parser table in the form of diict of dicts and the input that will be consumed, the table looks like this (it may also have sync for panic mode recovery):
```python
# the Parsing Table
    {
        S:{
            'a': ['S->aBa'],
            'b' : ['empty'],
            '$' : ['empty']
        }
        B:{
            'a' : ['B->\L'],
            'b' : ['B->bB'],
            '$' : ['empty']
        }
    }
```
## Expected Output
List of strings of the productions used to consume the the input 
The output should be the following
```python
    [
         'S -> aBa',
         'B -> bB',
         'B -> bB'
    ]
```