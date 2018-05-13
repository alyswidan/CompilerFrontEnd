from left_factoring_left_recursion.left_factoring import leftFactoring
import re



class production():
    def __init__(self, non_terminal, productions):
        self.non_terminal = non_terminal
        self.productions = productions

def leftRecursion(Filename):
    filein = open(Filename,'r')
    lines = filein.read()

    splits = lines.split("#")
    # print(splits)
    grammar = []
    for prod in splits:
        if prod:
            splittingProd = prod.split(" = ")
            non_terminal = splittingProd[0]
            # print(splittingProd)
            productions = splittingProd[1].split(" | ")
            for i in range(len(productions)):
                productions[i] = re.sub('[\n ]', ' ', productions[i])
                # productions[i]= productions[i].rstrip('\n')
                # productions[i] = productions[i].rstrip('\'')

            g = production(non_terminal.strip(),productions)
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
                # print("this production",p)
                # print(p[0:len(grammar[j].non_terminal)])
                # print(grammar[j].non_terminal)
                if (p[0:len(grammar[j].non_terminal)] == grammar[j].non_terminal):
                    print("LR",p[0:len(grammar[j].non_terminal)])

                    for pp in (grammar[j].productions):
                        print("replacing ",pp)
                        newlist.append(pp +p[len(grammar[j].non_terminal):])
                else:
                    newlist.append(p)
            # print('_____________________')
            grammar[i].productions = newlist
            # print(grammar[i].productions)
            # print("--------------------")
        # start removing imm LR
        listOfprod = grammar[i].productions
        recursivelist = []
        remaininglist = []
        flag = False
        for p in listOfprod:
            # print(p[0:len(grammar[i].non_terminal)])
            # print(grammar[i].non_terminal)
            if p[0:len(grammar[i].non_terminal)] == grammar[i].non_terminal:

                recursivelist.append(p[len(grammar[i].non_terminal):]+' '+grammar[i].non_terminal+str(count))
                flag = True

            else:
                remaininglist.append(p+' '+grammar[i].non_terminal+str(count))
        # print(".......................")

        if flag:
            recursivelist.append('\L')
            # print(recursivelist)
            # print(remaininglist)
            g = production(grammar[i].non_terminal,remaininglist)
            ng = production(grammar[i].non_terminal+str(count),recursivelist)
            newgrammar.append(g)
            newgrammar.append(ng)
            count += 1
        else:
            newgrammar.append(grammar[i])

    outputList = []
    for p in newgrammar:
        print(p.non_terminal)
        print(p.productions)
        outputList.append(p.non_terminal + ' -> ' + (' | '.join(p.productions)))
    for x in outputList:
        print(x)
    return outputList

# first_output = leftRecursion("grammar.txt")
# print("first")
# print(first_output)
#
# output = leftFactoring(first_output)
# print("second")
# print(output)