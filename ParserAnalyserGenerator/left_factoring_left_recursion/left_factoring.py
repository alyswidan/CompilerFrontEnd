class production():
    def __init__(self, non_terminal, productions):
        self.non_terminal = non_terminal
        self.productions = productions


def leftFactoring(splits):
    # splits = lines.split("#")
    # print(splits)
    grammar = []
    for prod in splits:
        if prod:
            splittingProd = prod.split(" -> ")
            non_terminal = splittingProd[0]
            productions = splittingProd[1].split(" | ")
            for i in range(len(productions)):
                productions[i]= productions[i].rstrip('\n')

            g = production(non_terminal,productions)
            # print(g.non_terminal)
            # print(g.productions)
            grammar.append(g)
    newGrammar = []
    for g in grammar:
        thisnonTerminal = g.non_terminal
        listOfProductions = g.productions
        count = 0
        commondict = {}
        if len(listOfProductions)== 1:
            samep= production(thisnonTerminal,listOfProductions)
            newGrammar.append(samep)
        else:
            flag = False
            for i in range(0,len(listOfProductions)-1):

                for j in range (i+1, len(listOfProductions)):

                    remaining = []
                    thisproduction = listOfProductions[i]
                    nextproduction = listOfProductions[j]
                    # print(thisproduction)
                    # print(nextproduction)
                    # print("~~~~~~~~~~~~~")
                    k=0
                    commonhere = ''
                    # commonhere = list(set([c for c in thisproduction if c in nextproduction]))
                    # s = set(thisproduction)
                    # t = set (nextproduction)
                    # commonhere = s & t
                    # commonhere = str(''.join(commonhere))
                    # print(commonhere)
                    # k = len(commonhere)
                    # print(k)
                    # print(thisproduction)
                    # print(nextproduction)
                    while thisproduction[k] == nextproduction[k]:
                        commonhere += thisproduction[k]
                        k +=1
                        if (k == len(thisproduction)) | (k == len(nextproduction)):
                            break
                        # print(commonhere)
                        # print(k)
                    # print("common here ", commonhere)
                    if commonhere:
                        flag = True
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
                        # print(remaining)
                        # print(commondict)
                    elif not flag:
                        remaining.append(thisproduction)
                        remaining.append(nextproduction)
                        newgrammerProdList = []
                        for g in newGrammar:
                            newgrammerProdList.append(g.productions)
                        if listOfProductions not in newgrammerProdList :
                            samep = production(thisnonTerminal,listOfProductions)
                            newGrammar.append(samep)
                            # print(thisnonTerminal)
                            # print(listOfProductions)
                            # print(remaining)
                            # print("=================")
            newlist = []
            newlistp = []
            newp = []
            for key, value in commondict.items():
                newlist.append(key+' '+thisnonTerminal+str(count))
                # print(newlist)
                newlistp.append(value)
                # print(newlistp)
                newp.append(thisnonTerminal+str(count))
                # print(newp)
                count +=1
            if newlist:
                ng= production(thisnonTerminal,newlist)
                newGrammar.append(ng)
            for i in range(len(newp)):
                # print(newp[i])
                # print(newlistp[i])
                ng= production(newp[i],newlistp[i])

                newGrammar.append(ng)
    # print(">>>>>>>>>>>>>>>>>>>>>")
    outputList = []
    for p in newGrammar:
        # print(p.non_terminal)
        # print(p.productions)
        outputList.append(p.non_terminal+' -> '+(' | '.join(p.productions)))
    # for x in outputList:
    #     print(x)
    return outputList