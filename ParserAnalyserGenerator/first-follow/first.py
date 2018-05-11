from models.production import Production

def first(grammar):
    """
        :param grammar: type=list(Production), this represents the whole grammar
    """
    result = {}
    
    for prod in grammar:
        prod_res = {} # first of parts of this production

        for member in prod:
            # for each part of this production, for E->AT | +S, AT is a member and +S is another
            member_name = Production.get_name(member)

            prod_res[member_name] = []

            for part in member:
                # for the above production, A is a part and T is a part of the member AT
                if isinstance(part, str):
                    prod_res[member_name].append(part)
                elif isinstance(member[0], Production):
                    if member_name in result.keys():
                        prod_res[member_name].extend(combine_firsts(result[member_name]))
                    else:
                        prod_res[member_name].extend(combine_firsts(first([part])))

                # for AT, if A has epsilon in its first then we add the first of T to the first of AT
                # other wise we just go see the other members
                if '\L' not in prod_res[member_name]:
                    break

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


# prod_E = Production('E', [['id'],['\L']])
# prod_S = Production('S', [[prod_E,'+'],['(','id',')']])
#
# print(first([prod_E, prod_S]))