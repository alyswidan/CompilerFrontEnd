class production():
    def __init__(self, non_terminal, productions):
        self.non_terminal = non_terminal
        self.productions = productions


def leftFactoring(Filename):
    filein = open(Filename,'r')
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
    newGrammar = []
    for g in grammar:
        thisnonTerminal = g.non_terminal
        listOfProductions = g.productions
        count = 0
        commondict = {}
        for i in range(0,len(listOfProductions)-1):
            for j in range (i+1, len(listOfProductions)):
                remaining = []
                thisproduction = listOfProductions[i]
                nextproduction = listOfProductions[j]
                print(thisproduction)
                print(nextproduction)
                print("~~~~~~~~~~~~~")
                k=0
                commonhere = ''
                while thisproduction[k] == nextproduction[k]:
                    commonhere += thisproduction[k]
                    k +=1
                print("common here ", commonhere)
                if commonhere :
                    remaining.append(thisproduction[k:])
                    remaining.append(nextproduction[k:])
                    if commonhere in commondict:
                        x = commondict.get(commonhere)
                        if thisproduction[k:] not in x:
                            commondict[commonhere].append(thisproduction[k:])
                        if nextproduction[k:] not in x:
                            commondict[commonhere].append(nextproduction[k:])
                    else:
                        commondict[commonhere]= remaining
                    print(remaining)
                    print(commondict)
        print(">>>>>>>>>>>>>>>>>>>>>")
        print(">>>>>>>>>>>>>>>>>>>>>")
        print(">>>>>>>>>>>>>>>>>>>>>")
        newlist = []
        newlistp = []
        newp = []
        for key, value in commondict.items():
            newlist.append(key+' '+thisnonTerminal+str(count))
            print(newlist)
            newlistp.append(value)
            # print(newlistp)
            newp.append(thisnonTerminal+str(count))
            # print(newp)
            count +=1
        ng= production(thisnonTerminal,newlist)
        newGrammar.append(ng)
        print(">>>>>>>>>>>>>>>>>>>>>")
        print(">>>>>>>>>>>>>>>>>>>>>")

        for i in range(len(newp)):
            print(newp[i])
            print(newlistp[i])
            ng= production(newp[i],newlistp[i])
            newGrammar.append(ng)
    print(">>>>>>>>>>>>>>>>>>>>>")
    outputList = []
    for p in newGrammar:
        print(p.non_terminal)
        print(p.productions)
        outputList.append(p.non_terminal+' -> '+(' | '.join(p.productions)))
    for x in outputList:
        print(x)
    return outputList

leftFactoring('leftfactoringGrammar.txt')