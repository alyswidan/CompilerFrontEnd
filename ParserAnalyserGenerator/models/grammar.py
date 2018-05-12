from models.production import Production


class Grammar:
    def __init__(self, separator='->'):
        self.productions = {}
        self.dependants = {}
        self.separator = separator

    def add_production(self, production_string):
        """

        :param production_string: a string of the form A->B | (A)
        :return:
        """

        name, value = production_string.split(self.separator)
        name = name.strip()
        production_obj = Production(name=name)
        members = [member.strip() for member in value.split('|')]
        for member_idx, member in enumerate(members):
            parts = [part.strip() for part in member.split(' ')]
            member_lst = []
            for part_idx, part in enumerate(parts):
                if self._is_non_terminal(part):
                    if part in self.productions:
                        member_lst.append(self.productions[part])
                    elif part == name:
                        member_lst.append(production_obj)
                    else:
                        if part not in self.dependants:
                            self.dependants[part] = []

                        member_lst.append(part)
                        self.dependants[part].append((name, member, part_idx))
                else:
                    member_lst.append(part)


            production_obj.add_member(member_lst)

        if name in self.dependants:
            for dependant in self.dependants[name]:
                self.productions[dependant[0]].members_dict[dependant[1]][dependant[2]] = production_obj

        self.productions[name] = production_obj

    def as_list(self):
        return list(self.productions.values())

    def _is_non_terminal(self, candidate):

        res = sum([int(c.isupper() or c =='_' or c.isnumeric()) for c in candidate]) == len(candidate)
        # candidate[0] != "'" and candidate != '\L'
        return res

    def set_start(self, production_name):
        self.productions[production_name].is_start = True

    def __str__(self):
        return str(self.as_list())


