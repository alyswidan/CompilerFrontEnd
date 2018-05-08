def arrange_stack(stack, input):
  input=input.split(" ")
  i = len(input) - 1
  while i != -1:
    stack.append(input[i])
    i -= 1
  return stack
table = {
  'S': {'ax': ['S->A b S'],
        'b': ['empty'],
        'c': ['S->A b S'],
        'd': ['empty'],
        'e': ['S->e'],
        '$': ['S->\L']},

  'A':  {'ax': ['A->ax'],
        'b': ['sync'],
        'c': ['A->c A d'],
        'd': ['sync'],
        'e': ['empty'],
        '$': ['empty']},
}
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
input = 'c e ax d b $'
print("input:  ",input)
input_stack = []
stack = []
input_stack = arrange_stack(input_stack, input)
s = next(iter(table))  # first element of the table
stack.append('$')
stack.append(s)
#print(stack)
#print(input_stack)
flag = 0
while flag != 1:
  #print(stack)
  temp = stack.pop()
  f_input = input_stack.pop()
  input_stack.append(f_input)
  if temp==f_input :
    if temp=="$":
      print("accepted")
      break
    #print("macted :",temp)
    input_stack.pop()
  else:
    try:
      temp2 = table[temp]
      str1 = (''.join(temp2[f_input]))

      if str1 == 'empty':
        print("Error:(illegal" + temp + ") – discard " + f_input)
        input_stack.pop()
        stack.append(temp)
      elif str1.split(">")[1]=="\L":
        print(str1)
        pass
      else:
        print(str1)
        stack = arrange_stack(stack, str1.split(">")[1])
    except KeyError:
      print("Error: missing " + temp+" , inserted")






