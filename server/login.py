# -*- coding: utf-8 -*-
import pymysql
import hashlib
import os
import time
import socket
import json
import datetime


def login(username, orig_token):
    setting_exptime = 28800

    ##CREATE TABLE `cse116proj1`.`user` ( `uid` INT NOT NULL AUTO_INCREMENT , `username` VARCHAR(16) NOT NULL , `token` VARCHAR(64) NOT NULL , `validity` INT NOT NULL , PRIMARY KEY (`uid`)) ENGINE = InnoDB;
    db = pymysql.connect(host='***',user='cse116proj1',passwd='***',db='cse116proj1',port=***,charset='utf8' )
    cursor = db.cursor()

    # 库名 cse116proj1
    # 表名 user

    # 查询是否有对应玩家
    sql = """SELECT * FROM user WHERE username = '%s' """ % username

    cursor.execute(sql)

    results = cursor.fetchall()
    print("第一次查询|First lookup result:" + str(results))

    #判断是否存在
    if results == ():
        #create an account
        print(username + " "+"无法查询到对应数据 can't find any data")
        token = hashlib.sha1(os.urandom(128)).hexdigest()
        exptime = int(time.time()) + setting_exptime

        print("生成token： "+ token)
        print("token有效期： "+ str(exptime))
        sql = """INSERT INTO user(uid, username, token, validity) VALUES (null, '%s', '%s', '%s')""" % (username, token, str(exptime))
        cursor.execute(sql)
        db.commit()

        print(username + " "+"新建对应数据 creating profile")

        sql = """SELECT * FROM user WHERE username = '%s' """ % username
        cursor.execute(sql)
        results = cursor.fetchall()

        ##print("第二次查询" + str(results))

        db.commit()
        db.close()
        if results == ():
            return {"state": 501} # unable to create user data
        else:
            with open("tokencache.txt", "a") as f:
                f.write(str(token)+"\n")
            return {"username": username, "token": token, "state": 200}

    if len(results) != 1:
        print(" 发现多条同名数据，正在删除")
        # an error happened delete all information about this account
        sql = """DELETE FROM user WHERE username = '%s'""" % (username)
        try:
            cursor.execute(sql)
            db.commit()
        except:
            db.rollback()
        db.close()
        return {"state": 502} # an error happened during login, please try again
    else:
        d_uid = results[0][0]
        d_username = results[0][1]
        d_token = results[0][2]
        d_exptime = results[0][3]

        if orig_token == d_token:
            print("The input token is same as database. Renewing 输入token与数据库一致，正在renew")
            exptime = int(time.time()) + (setting_exptime*2)
            sql = """UPDATE user SET validity = '%s' WHERE uid = '%s'""" % (str(exptime), d_uid)
            cursor.execute(sql)
            db.commit()
        else:
            print("the input token is different than database 输入token与数据库不一致")
            if d_exptime < int(time.time()):
                print("regenerating token. 重新生成token")
                token = hashlib.sha1(os.urandom(128)).hexdigest()
                d_token = token
                exptime = int(time.time()) + setting_exptime
                sql = """UPDATE user SET token = '%s',validity = '%s' WHERE uid = '%s'""" % (token, str(exptime), d_uid)
                cursor.execute(sql)
                db.commit()
            else:
                print("the token in database still valid")
                db.close()
                return {"state": 401} #this name has already been used
        db.close()
        with open("tokencache.txt", "a") as f:
            f.write(str(d_token)+"\n")
        return {"username": username, "token": d_token, "state": 200}

def MySQLtokencheck(username,token):
    db = pymysql.connect(host='cdb-m0myq4i3.gz.tencentcdb.com',user='cse116proj1',passwd='W75ECPtJmT8a4cTs',db='cse116proj1',port=10015,charset='utf8' )
    cursor = db.cursor()
    sql = """SELECT * FROM user WHERE token = '%s' """ % token
    cursor.execute(sql)
    results = cursor.fetchall()
    if len(results) != 1:
        return False
    else:
        d_username = results[0][1]
        d_token = results[0][2]
        d_exptime = results[0][3]
        if(d_username == username and d_token == token):
            if(d_exptime > int(time.time())):
                return True
            else:
                return False
        else:
            return False



def tokencheck(username,token,msgtype):
    tokenExist = False
    with open("tokencache.txt") as f:
        for line in f:
            if username == line.replace("\n",""):
                tokenExist = True
                break
    if msgtype == "heartbeat" or msgtype == "leavegame":
        tokenExist = True
    if tokenExist:
        return True
    else:
        return MySQLtokencheck(username,token)
