from flask import Flask, request, render_template, send_file, Response, jsonify, send_from_directory
import json
import login
import socket
import urllib.request
import clientDebug.heartbeat
app = Flask(__name__)


def sprint(X):
    with open("print.txt", 'a') as f:
        f.write(str(X))
    return 0


@app.route('/')
def hello_world():
    print("Loaded Main")
    return render_template("/index.html")

@app.route('/<path:filename>')
def static_files(filename):
    print("Loaded" + filename)
    return send_from_directory('static', filename)


@app.route('/login', methods = ['POST'])
def login():
    rawdata = str(request.data)
    #    with open("rid.txt", "a") as f:
    #        f.write(rawdata+"\n")
    data = json.loads(rawdata[2:-1])
    username = data.get("username", "")
    token = data.get("token", "")

    if username == "":
        result = {"error" : 301} # empty username
    else:
        result = login.login(username, token)

    return json.dumps(result)


@app.route('/joingame', methods = ['POST'])
def joingame():
    rawdata = str(request.data)
    data = json.loads(rawdata[2:-1])
    username = data.get("username", "")
    token = data.get("token", "")
    return json.dumps({"state":200,"username":username,"msgtype":"joingame","token":token,"boundary":[500,500],"endtime":1555188079,"player":[username,[[11,31,12,2,0,51]]]})

@app.route('/heartbeat', methods = ['POST'])
def herateat():
    rawdata = str(request.data)
    data = json.loads(rawdata[2:-1])
    username = data.get("username", "")
    token = data.get("token", "")
    return json.dumps(clientDebug.heartbeat.heartbeat_Json_generater(username,token))

@app.route('/leavegame', methods = ['POST'])
def leavegame():
    rawdata = str(request.data)
    data = json.loads(rawdata[2:-1])
    username = data.get("username", "")
    token = data.get("token", "")
    return json.dumps({"state":200,"username":username,"msgtype":"leavegame","token":token})




if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port=8080)
