import unittest
import login


class UnitTesting(unittest.TestCase):

    def test_upper(self):
        self.assertEqual(login.login("tianyi","97b32b66c9545096727805052a650e131dbc3d75"),{'state': 200,'token': '97b32b66c9545096727805052a650e131dbc3d75','username': 'tianyi'})
        self.assertNotEqual(login.login("shiluo","97b32b66c9545096727805052a650e131dbc3d75"), {'token': '97b32b66c9545096727805052a650e131dbc3d75', 'username': 'shiluo'})


if __name__ == '__main__':
    unittest.main()