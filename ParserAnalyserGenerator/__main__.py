import argparse
from left_factoring_left_recursion.left_factoring import leftFactoring
from left_factoring_left_recursion.left_recursion import leftRecursion
from first_follow.follow import compute_follow
from first_follow.first import compute_first
from parsing.parser import parser_generator
from table_construction.tableConstruction import constructParsingTable
import pickle
from models.grammar import Grammar
# from parsing.parser import parser_generator

arg_parser = argparse.ArgumentParser('a parser generator')
arg_parser.add_argument('mode', choices=['generate', 'parse'],
                        default='generate', help='what mode are we in')

arg_parser.add_argument('-f', '--grammar_file', help='the grammar file used to build the table')

arg_parser.add_argument('-p','--program', help='the program we want to parse')

args = arg_parser.parse_args()

if args.mode == 'generate':
    start_symbol, terminals, cleaned_grammar = leftRecursion(args.grammar_file)
    cleaned_grammar = leftFactoring(cleaned_grammar)
    grammar_obj = Grammar()
    for production in cleaned_grammar:
        grammar_obj.add_production(production)
    print(start_symbol)
    grammar_obj.set_start(start_symbol)

    print('cleaned_grammar=',cleaned_grammar)
    print('-------------------------------------------------------------------')
    first = compute_first(grammar_obj.as_list())
    print('first=',first)
    print('-------------------------------------------------------------------')
    follow = compute_follow(grammar_obj.as_list(), first)
    print('follow=',follow)
    table = constructParsingTable(first, follow, terminals)
    print('-------------------------------------------------------------------')
    print(table)
    with open('table.pkl', 'wb+') as file:
        pickle.dump((start_symbol, table), file)

elif args.mode == 'parse':
    with open('table.pkl', 'rb') as file:
        start_symbol, table = pickle.load(file)

    parser_generator(table, start_symbol)
