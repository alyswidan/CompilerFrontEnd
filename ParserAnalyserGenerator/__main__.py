import argparse
from left_factoring_left_recursion.left_factoring import leftFactoring
from left_factoring_left_recursion.left_recursion import leftRecursion
from first_follow.follow import compute_follow
from first_follow.first import compute_first
from table_construction.tableConstruction import constructParsingTable


from models.grammar import Grammar

arg_parser = argparse.ArgumentParser('a parser generator')
arg_parser.add_argument('mode', choices=['generate', 'parse'],
                        default='generate', help='what mode are we in')

arg_parser.add_argument('-f', '--grammar_file', help='the grammar file used to build the table')

arg_parser.add_argument('-p','--program', help='the program we want to pars')

args = arg_parser.parse_args()

if args.mode == 'generate':
    cleaned_grammar = leftRecursion(args.grammar_file)
    grammar_obj = Grammar()
    for production in cleaned_grammar:
        grammar_obj.add_production(production)

    start_symbol = cleaned_grammar[0].split('->')[0].strip()
    grammar_obj.set_start(start_symbol)

    print('cleaned_grammar=',cleaned_grammar)
    first = compute_first(grammar_obj.as_list())
    print('first=',first)
    follow = compute_follow(grammar_obj.as_list(), first)
    print('follow=',follow)
    table = constructParsingTable(first, follow)

    print(table)