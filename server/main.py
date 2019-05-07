from flask import Flask, request, send_from_directory
from flask_socketio import SocketIO, emit
import json
import login
import socket
from threading import Thread
import eventlet
eventlet.monkey_patch()

app = Flask(__name__)
socket_server = SocketIO(app)


scala_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
scala_socket.connect(('localhost', 19199))
delimiter = "~"
usernameToSid = {}
usernameTotoken = {}


with open("tokencache.txt", "w") as f:
    f.write("EMPTY-CACHE\n")


def listen_to_scala(the_socket):
    buffer = ""
    while True:
        buffer += the_socket.recv(1024).decode()
        while delimiter in buffer:
            message = buffer[:buffer.find(delimiter)]
            buffer = buffer[buffer.find(delimiter) + 1:]
            get_from_scala(message)


def get_from_scala(rawmessage):
    global usernameToSid
    data = json.loads(rawmessage)
    username = data.get("username", "")
    msgtype = data.get("msgtype", "heartbeat")
    user_socket = usernameToSid.get(username, "")
    # print("调试信息：")
    # print("用户名"+username)
    # print(usernameToSid)
    # print("用户sid"+user_socket)
    if user_socket != "":
        if msgtype == "joingame":
            data["token"] = usernameTotoken[username]
            rawmessage = json.dumps(data)
        print("Scala →Client|"+msgtype+"："+rawmessage)
        socket_server.emit(msgtype, rawmessage, room=user_socket)
        #print(user_socket)

    return 0


def send_to_scala(data):
    scala_socket.sendall((json.dumps(data) + delimiter).encode(encoding='UTF-8')) ## , errors='ignore'


def sprint(X):
    with open("print.txt", 'a') as f:
        f.write(str(X))
    return 0


def login_main(rawdata):
    # rawdata = str(request.data)
    #    with open("rid.txt", "a") as f:
    #        f.write(rawdata+"\n")
    data = json.loads(rawdata)
    username = data.get("username", "")
    token = data.get("token", "")

    if username == "":
        result = {"state" : 301} # empty username
    else:
        result = login.login(username, token)

    return json.dumps(result)


@app.route('/')
def index():
    return send_from_directory('.', 'game.html')


@app.route('/<path:filename>')
def static_files(filename):
    return send_from_directory('.', filename)


@socket_server.on('joingame')
def joingame(rawdata):
    # rawdata = str(request.data)
    print("Client→Scala |joingame ："+rawdata)
    data = json.loads(rawdata)
    username = data.get("username", "")
    token = data.get("token", "")

    # 确认是否有Token
    if login.tokencheck(username, token,"joingame"):
        # 有就直接加入游戏
        usernameTotoken[username] = token
        usernameToSid[username] = request.sid
        send_to_scala(rawdata)

        # s = socket.socket()
        # host = socket.gethostname()
        # port = 19199
        # s.connect((host, port))
        # s.send(json.dump({"job":"joingame", "username":username}).encode())
        # data = s.recv(1024)
        # s.close()

        # using socket send data to scala
    else:
        # 如无合法Token则执行login操作
        loginReturnRawData = login_main(rawdata)
        loginReturnData = json.loads(loginReturnRawData)
        print(loginReturnRawData)
        if loginReturnData["state"] == 200:
            usernameTotoken[username] = loginReturnData.get("token","")
            usernameToSid[username] = request.sid
            send_to_scala(rawdata)

        else:
            socket_server.emit('joingame', loginReturnRawData, room=request.sid)
            # 403- Invalid token
        # return json.dumps({"error": 401})
    pass


@socket_server.on('leavegame')
def leavegame(rawdata):
    # rawdata = str(request.data)
    print("Client→Scala |leavegame："+rawdata)
    data = json.loads(rawdata)
    username = data.get("username", "")
    token = data.get("token", "")

    # 确认是否有Token
    if login.tokencheck(username,token,"leavegame"):
        # 有就直接传输数据
        # del usernameToSid[request.sid]
        # usernameToSid[username] = request.sid
        usernameToSid[username] = request.sid
        send_to_scala(rawdata)
    else:
        socket_server.emit('leavegame', json.dumps({"msgtype":"leavegame","error": 403}), room=request.sid)
        # 403- Invalid token
    pass


@socket_server.on('heartbeat')
def heartbeat(rawdata):
    # rawdata = str(request.data)
    print("Client→Scala |heartbeat："+rawdata)
    data = json.loads(rawdata)
    username = data.get("username", "")
    token = data.get("token", "")

    # 确认是否有Token
    if login.tokencheck(username,token,"heartbeat"):
        # 有就直接传输数据
        usernameToSid[username] = request.sid
        send_to_scala(rawdata)
    else:
        print("验证失败")
        socket_server.emit('heartbeat', json.dumps({"msgtype":"heartbeat","error": 403}), room=request.sid)
        # 403- Invalid token
    pass


@socket_server.on('testmsg')
def testmsg(rawdata):
    # rawdata = str(request.data)
    print("Client→Python："+rawdata)
    socket_server.emit('testmsg', json.dumps({"msgtype":"heartbeat","msg":rawdata}), room=request.sid)
    pass


# if __name__ == "__main__":
#     app.run(debug=True, host="0.0.0.0", port=8080)


Thread(target=listen_to_scala, args=(scala_socket,)).start()


if __name__ == '__main__':
    print("Server Start on 9199")
    socket_server.run(app, host='0.0.0.0', port=9199,debug = True)

