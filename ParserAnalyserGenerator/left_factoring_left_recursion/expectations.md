*The expected inputs and outputs of both left factoring and left recursion elemenation are the same*

for the following grammar
    E => AT | B | id
    S => B | (E)
## Expected Input
list of productions
```python
    [E, S]
```
## Expected output
list of productions after removing left recursion or after left factoring the grammar
```python
    [E, A, S, B]
```
Where A and B are new production objects added to the grammar after the process
