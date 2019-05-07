import unittest
from Demo1 import moving
import json


class UnitTesting(unittest.TestCase):

    def test_upper(self):
        map = [[1, 1, 1, 1], [0, 1, 1, 1], [0, 0, 1, 0], [0, 0, 0, 0]]
        player_location = [0, 1]
        json_world = json.dumps({"map": map, "player": player_location})
        json_world_d = json.dumps({"map": map, "player": player_location})
        self.assertEqual(moving.moving(json_world, "d"), json_world_d)
        self.assertEqual(moving.moving(json_world, "w"), json_world)


if __name__ == '__main__':
    unittest.main()
