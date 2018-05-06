
# First
## Expected Input

List of production objects
```python
    [Production_1, Production_2, Production_3, ..., Production_n]
```
## Expected Output
map of map of constituent productions to list of first
for the following grammar
    E => AT | B | id
    S => B | (E)
The output should be the following
```python
    {
        E:{
            'AT': [f1, f2, ..., fn],
            'B' : [f1, f2, fn],
            "id": ["id"]
        }
        S:{
            'B':[f1, f2, ..., fn]
            ['(E)']:[f1, f2, ..., fn]
        }
    }
```
# Follow
## Expected Input
List of production objects
```python
    [Production_1, Production_2, Production_3, ..., Production_n]
```
## Expected Input
map of production objects to list of follows
for the following grammar
    E => AT | B | id
    S => B | (E)
The output should be the following
```python
    {
        E:[f1, f2, f3, ..., fn]
        S:[f1, f2, f3, ..., fn]
    }
```


