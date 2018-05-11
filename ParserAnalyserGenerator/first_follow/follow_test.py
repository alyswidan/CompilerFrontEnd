import unittest

from models.production import Production


class Follow_test(unittest.TestCase):
    def lec_follow_example(self):
        prod_E = Production('E',[[prod_T,prod_E1]])
        prod_E1 = Production('E1', [['T', prod_E1],['\L']])
        prod_T = Production('T', [[]])

if __name__ == '__main__':
    unittest.main()
