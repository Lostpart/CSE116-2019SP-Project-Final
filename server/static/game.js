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

var send = {"msgtype": "heartbeat", "username": "Qmt", "token": 123456789012345689012345678901234567890, "time": 1234567890, "location": [[1.0,5.2],[4.1,3.3],[2.1,1.1]], "action": {"split": [0,0], "bobble": [0,0]}}

function heartbeat(){
    ajaxPostRequest("heartbeat", JSON.stringify(send), doHeartbeat)
}

function doHeartbeat(data){
  var map = JSON.parse(data)
  //gameWorld["player"] = map[]
}

var config = {
    type: Phaser.AUTO,
    width: 800,
    height: 600,
    parent: 'cse116',
    physics: {
        default: 'arcade',
    },
    scene: {
        preload: preload,
        create: create,
        update: update
    }
};

var gameWorld = {"map":[100,100], "player":[1,3], "health":100, "speed": 100};
var player;
var cursors;
var dataMap;

var game = new Phaser.Game(config);

function preload ()
{
    this.load.image('floor', 'floor.png');
    this.load.image('player', 'player.png');
}

function createPlayer(color, radius) {
  var graphics = this.add.graphics(0,0);
  graphics.radius = radius;
  graphics.beginFill(color);
  graphics.lineStyle(2, 0xffd900, 1);
  graphics.drawCircle(0, 0, graphics.radius * 2);
  graphics.endFill();
  graphics.generateTexture("player");
  graphics.destroy();﻿

  sprite = this.add.sprite(radius * 2, radius * 2, "player");
}

var post = 0

function create(){
  var map = {'msgtype': "joingame", "username": "Qmt", "token": 123456789012345689012345678901234567890}
  ajaxPostRequest('joingame', JSON.stringify(map), getData)
  console.log("Joingame recieved")
  var map = JSON.parse(dataMap);
  var worldSize = map["boundary"][0]

  this.cameras.main.setBounds(0, 0, 32 * worldSize, 32 * worldSize);
  this.physics.world.setBounds(0, 0, 32 * worldSize, 32 * worldSize);


  var y=16;
  console.log("Loading map...")
  for (i = 0; i < worldSize; i++){
      var x=16;
      //console.log(i)
      for (a = 0; a < worldSize; a++){
        //console.log(a)
        this.add.image(x, y, "floor");
        x = x + 32;
      }
      y = y + 32;
      //console.log(i + "/" + worldSize)
  }
  console.log("Map loaded!")

  cursors = this.input.keyboard.createCursorKeys();

  //createPlayer(0xffd900,10)

  player = this.physics.add.sprite(map["player"][1][0][0], map["player"][1][0][1], 'player');

  player.setCollideWorldBounds(true);

  this.cameras.main.startFollow(player, true, 0.05, 0.05);
}

function getData(data){
  dataMap = data;
}

function update (){

    heartbeat();

    player.setVelocity(0);
    var speed = gameWorld["speed"]

    if (cursors.left.isDown)
    {
        player.setVelocityX(-speed);
    }
    else if (cursors.right.isDown)
    {
        player.setVelocityX(speed);
    }

    if (cursors.up.isDown)
    {
        player.setVelocityY(-speed);
    }
    else if (cursors.down.isDown)
    {
        player.setVelocityY(speed);
    }
}
