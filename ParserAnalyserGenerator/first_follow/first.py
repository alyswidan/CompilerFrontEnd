from models.production import Production
result = {}
def compute_first(grammar):
    """
        :param grammar: type=list(Production), this represents the whole grammar
    """

    # print('grammar=',grammar)
    for prod in grammar:
        prod_res = {} # first of parts of this production
        # print(f'exploring prod {prod}')
        for member in prod:
            # for each part of this production, for E->AT | +S, AT is a member and +S is another
            member_name = Production.get_name(member)
            prod_res[member_name] = []
            # print(f'exploring member {member} of prod {prod}')
            for part in member:
                # for the above production, A is a part and T is a part of the member AT
                # print(f'exploring part {part} of member {member} of prod {prod}')
                if isinstance(part, str):
                    # print(f'{part} is a string')
                    prod_res[member_name].append(part)
                    break
                elif isinstance(part, Production):
                    if member_name in result.keys():
                        prod_res[member_name].extend(combine_firsts(result[part.name]))
                    else:
                        # print(f'recursing with {part}')
                        compute_first([part])
                        y=combine_firsts(result[part.name])
                        # print(f'came back from {part} to {prod.name}with {x}')
                        # print(f'adding {y} to {prod.name}')
                        prod_res[member_name].extend(y)

                # for AT, if A has epsilon in its first then we add the first of T to the first of AT
                # other wise we just go see the other members
                if '\L' not in prod_res[member_name]:
                    break

            result[prod.name] = prod_res

    return result


def combine_firsts(production_firsts):
    """
        :param first: type=dict(string,list(string))
        :returns list of strings
        if the following is the first of E+ as computed
        by function above
        {'E+': ['id', '\\L', '+'], '(id)': ['(']}
        this function combines it into ['id', '\\L', '+', '(']
    """
    result = []
    for val in production_firsts.values():
        for inner in val:
            result.append(inner)
    
    return result

def flatten_grammar_firsts(grammar_firsts):
    """
    :param grammar: a list of firsts in the format below
    {'E': {'id': ['id'], '\\L': ['\\L']}, 'S': {'E+': ['id', '\\L', '+'], '(id)': ['(']}}
    :returns: a similar dict where each production is flattenned
    """

    return {k:combine_firsts(v) for k,v in grammar_firsts.items()}




# prod_E = Production('XYZ', [['id'],['\L']])
# prod_S = Production('ABC', [[prod_E,'+'],['(','id',')']])
