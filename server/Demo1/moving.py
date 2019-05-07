import json


def moving(json_level, move_string):
    level = json.loads(json_level)
    game_map = level["map"]
    player_location = level["player"]
    potential_location = []
    end_map = {"map": game_map, "player": potential_location}
    if move_string == 'w':
        potential_location = [player_location[0], player_location[1] - 1]
    if move_string == 'a':
        potential_location = [player_location[0] - 1, player_location[1]]
    if move_string == 's':
        potential_location = [player_location[0], player_location[1] + 1]
    if move_string == 'd':
        potential_location = [player_location[0] + 1, player_location[1]]
    if potential_location[0] > len(game_map):
        return json.dumps(level)
    if potential_location[0] < 0:
        return json.dumps(level)
    if potential_location[1] > len(game_map):
        return json.dumps(level)
    if potential_location[1] < 0:
        return json.dumps(level)
    endpoint = game_map[potential_location[1]], [potential_location[0]]
    if endpoint == 0:
        return json.dumps(end_map)
    if endpoint == 1:
        return json.dumps(end_map)
    else:
        return json.dumps(level)
