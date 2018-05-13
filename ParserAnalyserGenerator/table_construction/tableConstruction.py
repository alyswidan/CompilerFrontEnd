from first_follow.first import compute_first
from first_follow.follow import compute_follow
from models.grammar import Grammar

first = { 'S':{
                'AbS': ['a', 'c'],
                'e': ['e'],
                '\L':['\L']
               },
          'A':{
              'a':['a'],
              'cAd':['c']
              }
        }

follow = {'S':['$'],
          'A':['b', 'd']
         }

terminals = ['a', 'b', 'c', 'd', 'e']

# first = {"e":{"te'": ['(', 'x', 'y']},
#          "e'":{"+te' | \L":['+', '\L']},
#          "t":{"ft'":['(', 'x', 'y']},
#          "t'":{"*ft' | \L":['*', '\L']},
#          "f":{"(e) | x | y":['(', 'x', 'y']}
#         }
#
# follow = {'e':['$', ')'],
#           "e'":['$', ')'],
#           't':['+', '$', ')'],
#           "t'":['+', '$', ')'],
#           'f':['*','+', '$', ')']
#          }
#
# terminals = ['x', 'y', '+', '*', '(', ')','$']

def constructParsingTable(first, follow, terminals):

    terminals = terminals + ['$']

    table = {} # the dict of dicts that will be returned at the end as the parsing table
    
    for non_terminal_key in first.keys(): # iterate over the rows
        entry_in_table = {} 
        for terminal_key in terminals: # iterate over the columns 
            
            # check if in the first of the non terminal
            if terminal_key in [item for sublist in first[non_terminal_key].values() for item in sublist]:
                for val in first[non_terminal_key].values():
                    if terminal_key in val and terminal_key:
                        set_of_pathes = [k for k,v in first[non_terminal_key].items() if terminal_key in v]
                        # print([k for k,v in first[non_terminal_key].items() if terminal_key in v])
                        req_path=''
                        for path in set_of_pathes:
                            req_path = ''
                            if len(set_of_pathes) == 1: # if one path this is the required path
                                req_path = path
                                break
                            elif terminal_key in path: # if more than one path check terminal in that path
                                req_path = path
                                break
                            else:
                                pass
                        # print(f'the required path {req_path}')
                        entry_in_table[terminal_key] = non_terminal_key + '->' + req_path
            
            # check if in the follow of the non terminal
            elif terminal_key in follow[non_terminal_key]:
                if '\L' not in [item for sublist in first[non_terminal_key].values() for item in sublist]:
                    entry_in_table[terminal_key] = 'sync'
                else:
                    entry_in_table[terminal_key] = non_terminal_key + "->"+"\L"
                
            # if not in the first nor the follow of the terminal the error
            else:
                entry_in_table[terminal_key] = 'empty'
        
        table[non_terminal_key] = entry_in_table
    
    return table

# {'A': {'a': 'A->a', 'b': 'sync', 'c': 'A->c A d', 'd': 'sync', 'e': 'empty', '$': 'empty'},
#  'S': {'a': 'S->A b S', 'b': 'empty', 'c': 'S->A b S', 'd': 'empty', 'e': 'S->e', '$': 'empty'}}