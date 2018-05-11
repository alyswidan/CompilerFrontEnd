class production():
    def __init__(self, non_terminal, productions):
        self.non_terminal = non_terminal
        self.productions = productions


filein = open('leftrecursionGrammar.txt','r')
lines = filein.read()

splits = lines.split("#")
print(splits)
grammar = []
for prod in splits:
    if prod:
        splittingProd = prod.split(" -> ")
        non_terminal = splittingProd[0]
        productions = splittingProd[1].split(" | ")
        for i in range(len(productions)):
            productions[i]= productions[i].rstrip('\n')

        g = production(non_terminal,productions)
        print(g.non_terminal)
        print(g.productions)

        grammar.append(g)
print("~~~~~~~~~~~~")
count = 0
newgrammar = []
for i in range (0,len(grammar)):
    listOfprod = grammar[i].productions
    for j in range (0,i):
        newlist = []
        for p in listOfprod:

            if (p[0:len(grammar[j].non_terminal)] == grammar[j].non_terminal):
                for pp in (grammar[j].prodictions):
                    newlist.append(pp +p[len(grammar[j].non_terminal):])
            else:
                newlist.append(p)

        grammar[i].prodictions = newlist
        print(grammar[i].prodictions)
        print("--------------------")
    listOfprod = grammar[i].productions
    recursivelist = []
    remaininglist = []
    flag = False
    for p in listOfprod:
        if p[0:len(grammar[i].non_terminal)] == grammar[i].non_terminal:
            recursivelist.append(p[len(grammar[i].non_terminal):]+' '+grammar[i].non_terminal+str(count))
            flag = True
        else:
            remaininglist.append(p+' '+grammar[i].non_terminal+str(count))
    if flag:
        recursivelist.append('\L')
        print(recursivelist)
        print(remaininglist)
        g = production(grammar[i].non_terminal,remaininglist)
        ng = production(grammar[i].non_terminal+str(count),recursivelist)
        newgrammar.append(g)
        newgrammar.append(ng)
        count += 1
    else:
        newgrammar.append(grammar[i])



for p in newgrammar:
    print(p.non_terminal)
    print(p.productions)
