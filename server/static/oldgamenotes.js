//document.addEventListener('keydown', pressKey);
//document.addEventListener('keyup', releaseKey);

function ajaxPostRequest(path, data, callback){ //POST function
    var request = new XMLHttpRequest();
    request.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200){
            callback(this.response);
        }
    };
    request.open("POST", path);
    request.send(data);
}

function ajaxGetRequest(path, callback){ //Get function
    var request = new XMLHttpRequest();
    request.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200){
        callback(this.response);
        }
    };
    request.open("GET", path);
    request.send();
}

/*function getMap(newPlayerPosition){
  ajaxPostRequest("map",newPlayerPosition,create);
}

function joinGame(){
  val postMap = {"msgtype": "joingame", "username": "qmt", "token": ""}
  ajaxPostRequest("joingame", JSON.stringify(postMap))
}*/

var gameWorld = {"map":[10000,10000], "player":[1,3], "health":100, "speed": 1};


//var config = {"type":Phaser.AUTO, "width":512, "height":512, "scene":{"preload":preload,"create":create,"update":update}};

//var game = new Phaser.Game(800, 600, Phaser.AUTO, '116-project', { preload: preload, create: create, update: update, render: render });

var config = {
        type: Phaser.AUTO,
        width: 800,
        height: 600,
        physics: {
            default: 'arcade',
            arcade: {
                gravity: { y: 200 }
            }
        },
        scene: {
            preload: preload,
            create: create
        }
    };

    var game = new Phaser.Game(config);

var moveMap = {"up": false, "right": false, "down": false, "left": false}

/*function pressKey(key){
  console.log("pressed")
  if (key.keyCode === 87){
    moveMap["up"] = true
  }
  if (key.keyCode === 83){
    moveMap["down"] = true
  }
  if (key.keyCode === 65){
    moveMap["left"] = true
  }
  if (key.keyCode === 68){
    moveMap["right"] = true
  }
  console.log(world["player"])
}

function releaseKey(key){
  if (key.keyCode === 87){
    moveMap["up"] = false
  }
  if (key.keyCode === 83){
    moveMap["down"] = false
  }
  if (key.keyCode === 65){
    moveMap["left"] = false
  }
  if (key.keyCode === 68){
    moveMap["right"] = false
  }
  console.log(world["player"])
}*/

function updateStats(){
  document.getElementById("stats").innerHTML = "Health:" + String(gameWorld["health"]) + "\nWood:" + String(gameWorld["wood"]) + "\nStone:" + String(gameWorld["stone"]) + "\nGold:" + String(gameWorld["gold"]);
}

function startGame() {
    //new Phaser.Game(config);
    //updateStats();
    //getLogin();
    console.log("started");
  }

function preload(){
    console.log("Loading images...")
    this.load.image("floor", "floor.png");
    this.load.image("grass", "grass.png");
    this.load.image("player", "player.png");
    this.load.image("stone", "stone.png");
    console.log("Images loaded")
}


/*function create(){
    level = world.map
    console.log(level)
    var y=16;
    for (i = 0; i < level[0]; i++){
        var x=16;
        //console.log(i)
        for (a = 0; a < level[1]; a++){
          //console.log(a)
          this.add.image(x, y, "floor");
          x = x + 32;
        }
        y = y + 32;
    }
    console.log("Map loaded")
}


function update(){
  if (moveMap["up"]){
    world["player"][1] = world["player"][1] - world["speed"]
  };
  if (moveMap["right"]){
    world["player"][0] = world["player"][0] + world["speed"]
  };
  if (moveMap["down"]){
    world["player"][1] = world["player"][1] + world["speed"]
  };
  if (moveMap["left"]){
    world["player"][0] = world["player"][0] - world["speed"]
  };
  var postMap = {"msgtype": "heartbeat", "username": "qmt", "token": "", "time": "", "distance_moved": 0, "action": 0};
  window.requestAnimationFrame(update)
}
window.requestAnimationFrame(update)*/

var player;
var cursors;

function create() {
    console.log("Creating game...")
    var level = gameWorld.map
    this.add.tileSprite(0, 0, 32, 32, 'floor');
    game.world.setBounds(0, 0, level[0], level[1]);
    game.physics.startSystem(Phaser.Physics.P2JS);
    player = game.add.sprite(game.world.centerX, game.world.centerY, 'player');
    game.physics.p2.enable(player);
    cursors = game.input.keyboard.createCursorKeys();
    game.camera.follow(player);
    console.log("Game created")
}

function update() {
    player.body.setZeroVelocity();
    if (cursors.up.isDown) {
        player.body.moveUp(300)
    }
    else if (cursors.down.isDown) {
        player.body.moveDown(300);
    }
    if (cursors.left.isDown) {
        player.body.velocity.x = -300;
    }
    else if (cursors.right.isDown) {
        player.body.moveRight(300);
    }
}

function render() {
    console.log("Rendering...")
    game.debug.cameraInfo(game.camera, 32, 32);
    game.debug.spriteCoords(player, 32, 500);
    console.log("Rendered")
}

/*function sendMessage(){
  message = document.getElementById("chat").innerHTML;
}*/

console.log("loaded");
