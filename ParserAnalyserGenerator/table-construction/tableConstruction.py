first = { 'E':{'AT': ['a'],
               'id': ['x']},
          'S':{'B':['b'],
               '(E)':['a', 'x']}
}

follow = {'E':['a'],
          'S':['x']}

terminals = ['a', 'b', 'x']

def constructParsingTable(first, follow, terminals):
    
    terminals.append('$') # append $ to the list of terminals
    table = {} # the dict of dicts that will be returned at the end
    
    for non_terminal_key in first.keys():
        
        entry_in_table = {} 
        for terminal_key in terminals:
            
            # check if in first of the non terminal
            if terminal_key in [item for sublist in first[non_terminal_key].values() for item in sublist]:
                for val in first[non_terminal_key].values():
                    if terminal_key in val:
                        entry_in_table[terminal_key] = non_terminal_key + '->' + \
                                                ''.join([k for k,v in first[non_terminal_key].items() if terminal_key in v]) 
            
            # check if in the follow of the non terminal
            elif terminal_key in follow[non_terminal_key]:
                entry_in_table[terminal_key] = 'synh'
            
            # if not in the first nor the follow of the terminal the error
            else:
                entry_in_table[terminal_key] = 'empty'
        
        table[non_terminal_key] = entry_in_table
    
    return table
            