import re
def arrange_stack(stack, input):
  input=input.split(" ")
  i = len(input) - 1
  while i != -1:
    stack.append(input[i])
    i -= 1
  return stack
def gettoken():
  """
  This reads each token from the command line given as <id,x>
  and returns id.
  """
  token = input()
  token = re.findall('<(.+),',token)[0]
  print(token)
  return token
  # try:
  #  x = input_stack.pop()     # here we should get the token from the lexical
  #  return x
  # except IndexError:
  #   return None

# table = {
#   'S': {'ax': ['S->A b S'],
#         'b': ['empty'],
#         'c': ['S->A b S'],
#         'd': ['empty'],
#         'e': ['S->e'],
#         '$': ['S->\L']},
#
#   'A':  {'ax': ['A->ax'],
#         'b': ['sync'],
#         'c': ['A->c A d'],
#         'd': ['sync'],
#         'e': ['empty'],
#         '$': ['empty']},
# }
# input = 'c e ax d b $'
# input_stack =[]
# input_stack = arrange_stack(input_stack, input)

# table = {
#   'S': {'ax': ['S->A b S'],
#         'b': ['empty'],
#         'c': ['S->A b S'],
#         'd': ['empty'],
#         'e': ['S->e'],
#         '$': ['S->\L']},
#
#   'A':  {'ax': ['A->ax'],
#         'b': ['sync'],
#         'c': ['A->c A d'],
#         'd': ['sync'],
#         'e': ['empty'],
#         '$': ['empty']},
# }
# input = 'c e ax d b $'
# input_stack =[]
# input_stack = arrange_stack(input_stack, input)

#print("input:  ",input)
'''
str='a'
temp = table['S']
str1 = (''.join(temp[str]))
print(str1)
new = str1.split(">")[1]
new_stack =  []
s='abc'
new_stack.append("s")
new_stack = arrange_stack(new_stack,s)
print(new_stack)
input = 'abba$'
input_stack = []
stack = []
input_stack = arrange_stack(input_stack, input)
s = next(iter(table))  # first element of the table
stack.append('$')
stack.append(s)
temp = stack.pop()
f_input = input_stack.pop()
input_stack.append(f_input)

'''



def parser_generator(table):
  input_stack1 = []
  stack = []
  input_stack1.append(gettoken())
  s = next(iter(table))  # first element of the table
  stack.append('$')
  stack.append(s)
  #print(stack)
  #print(input_stack)
  flag = 0
  while flag != 1:
    temp = stack.pop()
    try:
      f_input = input_stack1.pop()
    except IndexError:
      f_input = gettoken()
      if f_input is None:
        print("incorrect input ")
        break
    input_stack1.append(f_input)
    if temp==f_input :
      if temp=="$":
        print("accepted")
        break
      #print("macted :",temp)
      input_stack1.pop()
    else:
      try:
        temp2 = table[temp]
        str1 = (''.join(temp2[f_input]))

        if str1 == 'empty':
          print("Error:(illegal" + temp + ") – discard " + f_input)
          input_stack1.pop()
          stack.append(temp)
        elif str1.split(">")[1]=="\L":
          print(str1)
          pass
        else:
          print(str1)
          stack = arrange_stack(stack, str1.split(">")[1])
      except KeyError:
        print("Error: missing " + temp+" , inserted")

table ={'PRIMITIVE_TYPE': {'id': 'sync', ';': 'empty', 'int': 'PRIMITIVE_TYPE->int', 'float': 'PRIMITIVE_TYPE->float', 'if': 'empty', '(': 'empty', ')': 'empty', '{': 'empty', '}': 'empty', 'else': 'empty', 'while': 'empty', '=': 'empty', 'relop': 'empty', 'addop': 'empty', 'mulop': 'empty', 'num': 'empty', '+': 'empty', '-': 'empty', '$': 'empty'}, 'DECLARATION': {'id': 'empty', ';': 'empty', 'int': 'DECLARATION->PRIMITIVE_TYPE id ;', 'float': 'DECLARATION->PRIMITIVE_TYPE id ;', 'if': 'empty', '(': 'empty', ')': 'empty', '{': 'empty', '}': 'sync', 'else': 'empty', 'while': 'empty', '=': 'empty', 'relop': 'empty', 'addop': 'empty', 'mulop': 'empty', 'num': 'empty', '+': 'empty', '-': 'empty', '$': 'sync'}, 'STATEMENT': {'id': 'STATEMENT->ASSIGNMENT', ';': 'empty', 'int': 'STATEMENT->DECLARATION', 'float': 'STATEMENT->DECLARATION', 'if': 'STATEMENT->IF', '(': 'empty', ')': 'empty', '{': 'empty', '}': 'sync', 'else': 'empty', 'while': 'STATEMENT->WHILE', '=': 'empty', 'relop': 'empty', 'addop': 'empty', 'mulop': 'empty', 'num': 'empty', '+': 'empty', '-': 'empty', '$': 'sync'}, 'IF': {'id': 'empty', ';': 'empty', 'int': 'sync', 'float': 'sync', 'if': 'IF->if ( EXPRESSION ) { STATEMENT } else { STATEMENT }', '(': 'empty', ')': 'empty', '{': 'empty', '}': 'sync', 'else': 'empty', 'while': 'empty', '=': 'empty', 'relop': 'empty', 'addop': 'empty', 'mulop': 'empty', 'num': 'empty', '+': 'empty', '-': 'empty', '$': 'sync'}, 'WHILE': {'id': 'empty', ';': 'empty', 'int': 'sync', 'float': 'sync', 'if': 'empty', '(': 'empty', ')': 'empty', '{': 'empty', '}': 'sync', 'else': 'empty', 'while': 'WHILE->while ( EXPRESSION ) { STATEMENT }', '=': 'empty', 'relop': 'empty', 'addop': 'empty', 'mulop': 'empty', 'num': 'empty', '+': 'empty', '-': 'empty', '$': 'sync'}, 'ASSIGNMENT': {'id': 'ASSIGNMENT->id', ';': 'empty', 'int': 'sync', 'float': 'sync', 'if': 'empty', '(': 'empty', ')': 'empty', '{': 'empty', '}': 'sync', 'else': 'empty', 'while': 'empty', '=': 'empty', 'relop': 'empty', 'addop': 'empty', 'mulop': 'empty', 'num': 'empty', '+': 'empty', '-': 'empty', '$': 'sync'}, 'STATEMENT_LIST': {'id': 'empty', ';': 'empty', 'int': 'STATEMENT_LIST->STATEMENT STATEMENT_LIST0', 'float': 'STATEMENT_LIST->STATEMENT STATEMENT_LIST0', 'if': 'empty', '(': 'empty', ')': 'empty', '{': 'empty', '}': 'empty', 'else': 'empty', 'while': 'empty', '=': 'empty', 'relop': 'empty', 'addop': 'empty', 'mulop': 'empty', 'num': 'empty', '+': 'empty', '-': 'empty', '$': 'sync'}, 'METHOD_BODY': {'id': 'empty', ';': 'empty', 'int': 'METHOD_BODY->STATEMENT_LIST', 'float': 'METHOD_BODY->STATEMENT_LIST', 'if': 'empty', '(': 'empty', ')': 'empty', '{': 'empty', '}': 'empty', 'else': 'empty', 'while': 'empty', '=': 'empty', 'relop': 'empty', 'addop': 'empty', 'mulop': 'empty', 'num': 'empty', '+': 'empty', '-': 'empty', '$': 'sync'}, 'STATEMENT_LIST0': {'id': 'empty', ';': 'empty', 'int': 'STATEMENT_LIST0->STATEMENT STATEMENT_LIST0', 'float': 'STATEMENT_LIST0->STATEMENT STATEMENT_LIST0', 'if': 'empty', '(': 'empty', ')': 'empty', '{': 'empty', '}': 'empty', 'else': 'empty', 'while': 'empty', '=': 'empty', 'relop': 'empty', 'addop': 'empty', 'mulop': 'empty', 'num': 'empty', '+': 'empty', '-': 'empty', '$': 'STATEMENT_LIST0->\\L'}, 'FACTOR': {'id': 'FACTOR->id', ';': 'empty', 'int': 'empty', 'float': 'empty', 'if': 'empty', '(': 'FACTOR->( EXPRESSION )', ')': 'sync', '{': 'empty', '}': 'empty', 'else': 'empty', 'while': 'empty', '=': 'empty', 'relop': 'sync', 'addop': 'sync', 'mulop': 'sync', 'num': 'FACTOR->num', '+': 'empty', '-': 'empty', '$': 'empty'}, 'TERM': {'id': 'empty', ';': 'empty', 'int': 'TERM->FACTOR TERM2', 'float': 'TERM->FACTOR TERM2', 'if': 'empty', '(': 'empty', ')': 'sync', '{': 'empty', '}': 'empty', 'else': 'empty', 'while': 'empty', '=': 'empty', 'relop': 'sync', 'addop': 'sync', 'mulop': 'empty', 'num': 'empty', '+': 'empty', '-': 'empty', '$': 'empty'}, 'SIMPLE_EXPRESSION': {'id': 'empty', ';': 'empty', 'int': 'SIMPLE_EXPRESSION->', 'float': 'SIMPLE_EXPRESSION->', 'if': 'empty', '(': 'empty', ')': 'sync', '{': 'empty', '}': 'empty', 'else': 'empty', 'while': 'empty', '=': 'empty', 'relop': 'sync', 'addop': 'empty', 'mulop': 'empty', 'num': 'empty', '+': 'empty', '-': 'empty', '$': 'empty'}, 'SIGN': {'id': 'empty', ';': 'empty', 'int': 'sync', 'float': 'sync', 'if': 'empty', '(': 'empty', ')': 'empty', '{': 'empty', '}': 'empty', 'else': 'empty', 'while': 'empty', '=': 'empty', 'relop': 'empty', 'addop': 'empty', 'mulop': 'empty', 'num': 'empty', '+': 'SIGN->+', '-': 'SIGN->-', '$': 'empty'}, 'EXPRESSION': {'id': 'empty', ';': 'empty', 'int': 'EXPRESSION->SIMPLE_EXPRESSION D', 'float': 'EXPRESSION->SIMPLE_EXPRESSION D', 'if': 'empty', '(': 'empty', ')': 'sync', '{': 'empty', '}': 'empty', 'else': 'empty', 'while': 'empty', '=': 'empty', 'relop': 'empty', 'addop': 'empty', 'mulop': 'empty', 'num': 'empty', '+': 'empty', '-': 'empty', '$': 'empty'}, 'D': {'id': 'empty', ';': 'empty', 'int': 'empty', 'float': 'empty', 'if': 'empty', '(': 'empty', ')': 'D->\\L', '{': 'empty', '}': 'empty', 'else': 'empty', 'while': 'empty', '=': 'empty', 'relop': 'D->relop SIMPLE_EXPRESSION', 'addop': 'empty', 'mulop': 'empty', 'num': 'empty', '+': 'empty', '-': 'empty', '$': 'empty'}, 'SIMPLE_EXPRESSION1': {'id': 'empty', ';': 'empty', 'int': 'empty', 'float': 'empty', 'if': 'empty', '(': 'empty', ')': 'SIMPLE_EXPRESSION1->\\L', '{': 'empty', '}': 'empty', 'else': 'empty', 'while': 'empty', '=': 'empty', 'relop': 'SIMPLE_EXPRESSION1->\\L', 'addop': 'SIMPLE_EXPRESSION1->addop TERM SIMPLE_EXPRESSION1', 'mulop': 'empty', 'num': 'empty', '+': 'empty', '-': 'empty', '$': 'empty'}, 'TERM2': {'id': 'empty', ';': 'empty', 'int': 'empty', 'float': 'empty', 'if': 'empty', '(': 'empty', ')': 'TERM2->\\L', '{': 'empty', '}': 'empty', 'else': 'empty', 'while': 'empty', '=': 'empty', 'relop': 'TERM2->\\L', 'addop': 'TERM2->\\L', 'mulop': 'TERM2->mulop FACTOR TERM2', 'num': 'empty', '+': 'empty', '-': 'empty', '$': 'empty'}}
table_lec = {'S': {'a': 'S->A b S', 'b': 'empty', 'c': 'S->A b S', 'd': 'empty', 'e': 'S->e', '$': 'S->\\L'},
             'A': {'a': 'A->a', 'b': 'sync', 'c': 'A->c A d', 'd': 'sync', 'e': 'empty', '$': 'empty'}}
parser_generator(table)

