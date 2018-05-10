class Production:
    def __init__(self, name, members):
        """
            :param name: type=string, name of the production 
            :param members: type=list(list(Production for non terminals and string for terminals)) 
            this is the list of Productions 
            this production resolves to, if the production is
            E-> AT|+T|\L
            the expected list is [[prod_A, prod_T], ['+', prod_T],['\L]]
            where terminals are presented as strings and prod_X indicates
            a production object with the name X. 
        """

        self.name = name
        self.members_dict = {}
        self.ordered_member_names = []
        for member in members:
            self.add_member(member)
            self.ordered_member_names.append(Production.get_name(member))

    def get_member(self, name):
        try:
            member = self.members_dict[name]
            return member
        except KeyError:
            raise ValueError(f'production {self.name} has no member {name}')

        

    def remove_member(self, name):
        try:
            del self.members_dict[name]
        except KeyError:
            raise ValueError(f'production {self.name} has no member {name}') 
    
    def add_member(self, member):
        """
            add a member list to the production
            :param member: type=list(Production for non terminals and string for terminals)
        """
        self.members_dict[Production.get_name(member)] = member

    @staticmethod
    def get_name(member):
        member_name = ''
        for part in member:
            if isinstance(part, str):
                member_name += part
            elif isinstance(part, Production):
                member_name += part.name
            else:
                raise ValueError(f'object of type {type(part)}'
                                    f' is not a prodution nor a string')

        return member_name

    def __iter__(self):
        for name in self.ordered_member_names:
            yield self.get_member(name)

    def __str__(self):
        return ' | '.join(self.members_dict.keys())

    __repr__ = __str__
    

# prod_E = Production('E', [['id'],['\L']])
# prod_S = Production('S', [['+',prod_E],['(','id',')']])
# for i in prod_S:
#     print(i)