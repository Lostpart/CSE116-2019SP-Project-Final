var socket = io.connect({transports: ['websocket']});

socket.on('joingame', joingameRecive);
socket.on('heartbeat', heartbeatRecive);
socket.on('leavegame', leavegameRecive);

var canvas = document.getElementById("canvas");
var context = canvas.getContext("2d");
context.globalCompositeOperation = 'source-over';
var clientWidth = document.documentElement.clientWidth*0.95;
var clientHeight = document.documentElement.clientHeight*0.95;
var shiluo_timer = {
    sto: [],
    siv: []
};
var gameworld = {
    username : "",
    Boundary : [0,0],
    user: [], //[x, y, resource, size, color, speed]
    endtime_Timestamp: 0,
    GUIlastupdatetime: 0,
    loginsuccess: false,
    resourceball:[], // [x,y,color]
    player:[],
    playerdie: false,
    token: ""
};

const colormap = {
    0: "#6b8fff",
    1: "#FF0000",
    2: "#FFAA00",
    3: "#FFFF00",
    4: "#00ff66",
    5: "#0000FF",
    6: "#9400ff",
    7: "#ff00c6",
    8: "#009c38",
};

function color_decode(colorint){
    if (colorint <0){
        colorint = colorint * -1
    }
    while(colorint > 8){
        colorint = colorint - 8
    }
    return colormap[colorint]
}


function randomUsername(range){
    var str = "";
    arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
    for(var ia=0; ia<range; ia++){
        pos = Math.round(Math.random() * (arr.length-1));
        str =str + arr[pos];
    }
    return str;
}
// function onload_Strttgame() {
//     console.log("页面加载完毕")
// }

function onload_Strttgame(){
    gameworld["username"] = randomUsername(8);
    socket.emit('joingame', JSON.stringify({'msgtype': "joingame", "username": gameworld["username"] , "token": ""}));
}

function heartbeatRecive(event){
    /**
     * /Heartbeat
     * Parameter	    Type	                        Description & e.g
     * msgtype	      String	                      “heartbeat”(for Demo2 only)
     * username	    String	                      Username e.g:Shiluo
     * token	        String	                      40 character token generate when user login
     *                                              E.g. 573e693fcdb2ba85bdfcb461ecfb40efbc3d7417
     * time	        Int	10 digit                  Timestamp
     * location	    List of List with two Double	The new location for each player_sub
     *                                             [[1.0,5.2],[4.1,3.3],[2.1,1.1]]
     */
    var data = JSON.parse(event);
    var state = data["state"];
    //      200- Alive(Good)
    //      501- Server has more player_sub then Client, pls check
    //      503- Server has less player_sub then client, pls check
    //      301- Player die
    //      302- Whole Game are ended
    //      403- Invalid token
    if(state == 200){
        heartbeat_update_user_sizeonly(data)
        heartbeat_update_otherplayer(data)
        heartbeat_update_resourceball(data)
    }else if (state == 501){
        heartbeat_update_user(data)
        heartbeat_update_otherplayer(data)
        heartbeat_update_resourceball(data)
    }else if (state == 503){
        heartbeat_update_user(data)
        heartbeat_update_otherplayer(data)
        heartbeat_update_resourceball(data)
    }else if (state == 301){
        console.log("Reminder 301 Player die")
        //发送结束游戏数据包，提醒玩家已死亡
        socket.emit('leavegame', JSON.stringify({'msgtype': "leavegame", "username": gameworld["username"] , "token": gameworld["token"]}));
        gameworld["endtime_Timestamp"] = Math.round(new Date().getTime()/1000)
    }else if (state == 302){
        console.log("Reminder 302 Whole Game are ended")
    }else if (state == 403){
        console.log("Error 403 Invalid token")
    }
}

function heartbeat_update_user(data){
    //[x, y, resource, size, color, speed]
    gameworld["user"] = data["player"]
}
function heartbeat_update_user_sizeonly(data) {
    //[x, y, resource, size, color, speed]
    gameworld["user"][1][0][3] =  data["player"][1][0][3]
}
function heartbeat_update_otherplayer(data){
    //[x, y, resource, size, color, speed]
    gameworld["player"] = data["otherplayer"]
}
function heartbeat_update_resourceball(data) {
    gameworld["resourceball"] = data["resourceball"]
}



function joingameRecive(event) {
    /**
     Parameter         Type          Description & e.g
     msgtype           String        “joingame”(for Demo2 only)
     username          String        Username e.g:Shiluo
     token             String        40 character token generate when user login
     E.g. 573e693fcdb2ba85bdfcb461ecfb40efbc3d7417
     */
    var data = JSON.parse(event);
    var state = data["state"];

    if(state == 200){
        if (data["username"] == gameworld["username"]){
            gameworld["Boundary"] = data["boundary"];
            gameworld["user"] = data["player"];
            gameworld["endtime_Timestamp"] = data["endtime"];
            gameworld["loginsuccess"] = true;
            gameworld["token"] = data["token"];
            shiluo_timer.siv.push(window.setInterval(heartbeatsend, 50));
            shiluo_timer.siv.push(window.setInterval(updateworld, 25));

        } else {
            console.log("Error 000 username/msgtype doesn't match with sending username/msgtype")
        }
    }else if(state == 301) {
        console.log("Error 301 empty username")
    }else if(state == 401) {
        console.log("Error 401 this name has already been used")
    }
}

function leavegameRecive(event) {
    /**
     Parameter         Type          Description & e.g
     msgtype           String        “leavegame”(for Demo2 only)
     username          String        Username e.g:Shiluo
     token             String        40 character token generate when user login
     E.g. 573e693fcdb2ba85bdfcb461ecfb40efbc3d7417
     */
        //{"state":200,"username":"Shiluo","msgtype":"leavegame","player":["Shiluo",[[763,769,12,2,3,51]]]}
    var data = JSON.parse(event);
    var state = data["state"];

    if(state == 200){
        console.log("200 the player die");
        shiluo_timer.siv.forEach(function(siv) {clearInterval(siv)});
        alert("You die! The page will close");
        closeWebPage()
        gameworld["playerdie"] = true
    }else if(state == 301) {
        console.log("Error 301 empty username")
    }else if(state == 401) {
        console.log("Error 401 this name has already been used")
    }
}


function closeWebPage(){
    if (navigator.userAgent.indexOf("MSIE") > 0) {//close IE
        if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
            window.opener = null;
            window.close();
        } else {
            window.open('', '_top');
            window.top.close();
        }
    }
    else if (navigator.userAgent.indexOf("Firefox") > 0) {//close firefox
        window.location.href = 'about:blank ';
    } else {//close chrome;It is effective when it is only one.
        window.opener = null;
        window.open('', '_self');
        window.close();
    }
}

function placeCircle(x, y, color, size,topLeft,scalingrate) {
    context.fillStyle = color_decode(color);
    context.beginPath();

    context.arc((x- topLeft[0]) / scalingrate,
        (y - topLeft[1]) / scalingrate,
        size / scalingrate,
        0,
        2 * Math.PI);
    context.fill();
    // context.strokeStyle = 'black';
    // context.stroke();
}

function clearBoard() {
    context.clearRect(0, 0, clientWidth , clientHeight);
    canvas.setAttribute("width", String(clientWidth) );
    canvas.setAttribute("height", String(clientHeight) );
}

function calCenter(){
    var x = gameworld["user"][1][0][0];
    var y = gameworld["user"][1][0][1];
    var size = gameworld["user"][1][0][3];
    //console.log([(size*4)/ clientWidth, (size*4)/clientHeight,x,y])
    return [(size*16)/ clientWidth, (size*16)/clientHeight,x,y]
}

function heartbeatsend(){
    /**
     * /Heartbeat
     * Parameter	    Type	                        Description & e.g
     * msgtype	      String	                      “heartbeat”(for Demo2 only)
     * username	    String	                      Username e.g:Shiluo
     * token	        String	                      40 character token generate when user login
     *                                              E.g. 573e693fcdb2ba85bdfcb461ecfb40efbc3d7417
     * time	        Int	10 digit                  Timestamp
     * location	    List of List with two Double	The new location for each player_sub
     *                                             [[1.0,5.2],[4.1,3.3],[2.1,1.1]]
     */
    var resultjsmap = {
        "username" : gameworld["user"][0],
        "msgtype" : "heartbeat",
        "time" : Math.round(new Date().getTime()/1000),
        "location" : [[gameworld["user"][1][0][0],gameworld["user"][1][0][1]]],
        "action" : {},
        "token": gameworld["token"]
    };
    socket.emit('heartbeat', JSON.stringify(resultjsmap));
}

function updateuserlocation(delta){
    var speed_vector = [0,0];
    if(keyStates["moveX"] != 0 && keyStates["moveY"] !=0){
        speed_vector = [keyStates["moveX"] *0.7071, keyStates["moveY"] *0.7071]
    } else {
        speed_vector = [keyStates["moveX"], keyStates["moveY"]]
    }

    gameworld["user"][1][0][0] = gameworld["user"][1][0][0] + speed_vector[0]*gameworld["user"][1][0][5]*(delta/1000);
    gameworld["user"][1][0][1] = gameworld["user"][1][0][1] + speed_vector[1]*gameworld["user"][1][0][5]*(delta/1000);


    if(gameworld["user"][1][0][0] > gameworld["Boundary"][0]){
        gameworld["user"][1][0][0] = gameworld["Boundary"][0]
    } else if(gameworld["user"][1][0][0] < 0){
        gameworld["user"][1][0][0] = 0
    }

    if(gameworld["user"][1][0][1] > gameworld["Boundary"][1]){
        gameworld["user"][1][0][1] = gameworld["Boundary"][1]
    } else if(gameworld["user"][1][1] < 0){
        gameworld["user"][1][0][1] = 0
    }



}

function updateworld() {
    if(gameworld["GUIlastupdatetime"] === 0){
        gameworld["GUIlastupdatetime"] = Date.now() - 1
    }
    var delta = (gameworld["GUIlastupdatetime"]-Date.now());
    gameworld["GUIlastupdatetime"] = Date.now();


    updateuserlocation(delta);

    if(gameworld["playerdie"]){
        shiluo_timer.siv.forEach(function(siv) {clearInterval(siv)});
        alert("You die! The page will close");
        closeWebPage()
    }
    if(Math.round(new Date().getTime()/1000) > gameworld["endtime_Timestamp"]){
        shiluo_timer.siv.forEach(function(siv) {clearInterval(siv)});
        alert("This round is ended! The page will close");
        closeWebPage()
    }

    clearBoard();
    var guidata = calCenter();
    var scalingrate = Math.max(0.05, guidata[0], guidata[1]);
    var topLeft_Coordinate = [guidata[2]-(clientWidth/2) * scalingrate, guidata[3]-(clientHeight/2) * scalingrate];
    console.log("缩放度scalingrate:"+String(scalingrate)+"\t左上角坐标："+String(topLeft_Coordinate));
    // console.log(topLeft_Coordinate)
    for(r of gameworld["resourceball"]){
        placeCircle(r[0],r[1],r[2],0.5,topLeft_Coordinate,scalingrate)
    }
    for(p of gameworld["player"]){
        for(r of p[1]){
            placeCircle(r[0],r[1],r[4],r[3],topLeft_Coordinate,scalingrate)
        }
    }
    for(r of gameworld["user"][1]){
        placeCircle(r[0],r[1],r[4],r[3],topLeft_Coordinate,scalingrate)
    }


}













