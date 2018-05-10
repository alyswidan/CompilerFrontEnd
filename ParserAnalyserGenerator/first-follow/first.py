from models.production import Production

def first(grammar):
    """
        :param grammar: type=list(Production), this represents the whole grammar
    """
    print('entering first with ', grammar)
    result = {}
    
    for prod in grammar:
        prod_res = {} # first of parts of this production
        
        for member in prod:
            print('exploring ', member)
            member_name = Production.get_name(member)
            if isinstance(member, str):
                prod_res[member_name] = member
            elif isinstance(member, Production):
                if member_name in result.keys():
                    prod_res[member_name] = combine_firsts(result[member_name])
                else:
                    prod_res[member_name] = combine_firsts(first([member]))

        result[prod.name] = prod_res

    return result


def combine_firsts(firsts):
    """
        :param first: type=dict(string,list(string))
        a list dict representing the firsts parts of a production
        example:
        E->+AT | id
        {
            '+AT':['+']
            'id':['id']
        }
    """
    result = []
    for val in firsts.values():
        for inner in val:
            result.append(inner)
    
    return result


prod_E = Production('E', [['id'],['\L']])
prod_S = Production('S', [[prod_E,'+'],['(','id',')']])

print(first([prod_E, prod_S]))