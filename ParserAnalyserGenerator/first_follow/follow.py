from first_follow.first import compute_first,flatten_grammar_firsts
from models.grammar import Grammar
from models.production import Production


def compute_follow(grammar, grammar_firsts):
    """
    :param grammar: type=list(Production) representing the grammar
    :param firsts: the value returned by passing this grammar to the first function
    :returns a dict of strings to list of strings where the key is a non-terminal
    and the list is a list containing the follow of this non-terminal.
    """

    first = flatten_grammar_firsts(grammar_firsts)
    follow = {prod_name:[] for prod_name in first.keys()}
    children = {prod_name:[] for prod_name in first.keys()}

    def _update_children(parent, follow_list):
        visited = set()
        def _do_update(parent_inner, follow_list_inner):
            if parent_inner in visited:
                return
            visited.add(parent_inner)

            for child in children[parent_inner]:
                follow[child].extend(follow_list_inner)
                _do_update(child, follow[child])

        return _do_update(parent, follow_list)

    def _add_to_follow(key, val):
        follow[key].extend(val)
        follow[key] = list(set(follow[key]))


    for production in grammar:
        if production.is_start:
            follow[production.name] = ['$']
        for member in production:
            num_parts = len(member)
            for part_idx in range(num_parts):
                current_part = member[part_idx]
                next_part = member[part_idx + 1] if part_idx < num_parts-1 else None
                if isinstance(current_part, str):
                    continue

                if part_idx < num_parts - 1:
                    # this is the case when A->xBS, here the follow of B contains the first
                    # of S.
                    if isinstance(current_part, Production):
                        # if the next part in this member is a terminal, add it to  the follow
                        # of the current part, else if it is a non terminal, add its first to this
                        if isinstance(next_part, str):
                            _add_to_follow(current_part.name, [next_part])
                           # inform our children of this addition to our follow
                            _update_children(current_part.name, [next_part])
                        else:
                            req_first = list(set(first[next_part.name]) - {'\L'})
                            _add_to_follow(current_part.name, req_first)
                            # again inform our children
                            _update_children(current_part.name, req_first)


                if part_idx == num_parts - 1 or \
                    (part_idx == num_parts - 2 and isinstance(next_part, Production) and'\L' in first[next_part.name]):
                    # this is the case when A->xB or A->xBD and D has an epsilon in
                    # its first, we call B a child of A because it needs the follow
                    # of A to compute its own follow

                    # add what we know about the follow of the parent to
                    # the follow of this part

                    _add_to_follow(current_part.name, follow[production.name])
                    # inform the children of current_part of this change
                    _update_children(current_part.name, follow[production.name])

                    # make the current part a child of the non terminal
                    # we are currently exploring
                    children[production.name].append(current_part.name)

        _update_children(production.name, follow[production.name])

    follow = {k:list(set(v)) for k,v in follow.items()}

    return follow



g = Grammar()
g.add_production('E->T E1')
g.add_production('E1->+ T E1 | \L')
g.add_production('T->F T1')
g.add_production('T1->* F T1 | \L')
g.add_production('F->( E ) | id')
g.set_start('E')
print(g)
first = compute_first(g.as_list())
print(compute_follow(g.as_list(), first))