import json


class Player:
    def __init__(self, name):
        self.name = name
        self.location = {"zone": [], "location": []}
        self.inventory = {"resource1": 0, "resource2": 0, "resource3": 0}


def inventory_input(json_input, player_name):
    state = json.loads(json_input)
    world = state["world"]
    for y in world:
        for x in y:
            if x == 2:
                pickup = [x, y]
                if state["player"](0) == pickup[0] and state["player"](1) == pickup[1]:
                    player_name.inventory["resource1"] += 1
                    world[y[x]] = 0
    end_state = {"world": world, "player": state["player"]}
    return end_state
