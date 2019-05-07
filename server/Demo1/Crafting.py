import json


def craft_wall(world_map_json, build_location_json, player_name):
    mappy = json.loads(world_map_json)
    world_map = mappy["map"]
    build_location = json.loads(build_location_json)
    new_world_map = {"map": {}, "player": world_map["player"]}
    if player_name.inventory["resource1"] >= 2:
        y = build_location[0]
        x = build_location[1]
        world_map[y[x]] = 3
        new_world_map["map"] = world_map
        return json.dumps(new_world_map)
    else:
        return json.dumps(world_map)
