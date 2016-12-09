#!/usr/bin/env python
# -*- coding: utf-8 -*-

import re
import sys
import urllib
import string
import json
import copy
import traceback
import os

baseUrl = "http://h.nimingban.com/Api"
commitUrl = "http://h.nimingban.com/t/6610926"
forumList = [];
picAvailable = False
bossFileName = "thefile.txt"


def getJsonFromUrl(url):
    resp = urllib.urlopen(url)
    return json.loads(resp.read())


def convert(orig):
    uniStr = (re.sub(r'\\u\w{4}',
                     lambda e: unichr(int(e.group(0)[2:], 16)).encode('utf-8'),
                     orig))
    brTag = re.compile('<br />')
    # return brTag.sub(os.linesep, uniStr)
    return brTag.sub('', uniStr)


def cPrint(str):
    print(convert(str))


def printHelp():
    print('''q:退出'
    [h]:帮助
    [F]:显示所有板块
    [F {id} {page}]:显示指定板块的指定页
    [t {id} {page}]:显示指定串指定页
    [f]:前进历史记录
    [b]:后退历史记录
    [p]:前一页 [n]:后一页 [T]:切换为有/无图模式（屏蔽有图Po主串)
    [c]艹！老板来了(伪装输出在同目录《theFile》内''')
    if picAvailable:
        have = '有'
    else:
        have = "无"
    print('当前为' + have + '图模式')


def printForum():
    forumListStr = ""
    for fName in forumList:
        forumListStr += (" [" + fName[0] + ", " + fName[1] + "]")
    cPrint(forumListStr)


def showForum(id, page):
    forumPageJson = getJsonFromUrl(baseUrl + "/showf?id=" + id + "&page=" + page)
    for thread in forumPageJson:
        if ~picAvailable and thread["img"] != "":
            continue
        str = "["
        str += (thread["id"] + " " + thread["replyCount"] + "]" + thread["content"])
        cPrint(str);


def showThread(id, page):
    threadPageJson = getJsonFromUrl(baseUrl + "/thread?id=" + id + "&page=" + page)
    printSingleReply(threadPageJson)
    for reply in threadPageJson["replys"]:
        printSingleReply(reply)


threadProperties = [
    ["now", u"时间", u""],
    ["title", u"标题", u"无标题"],
    ["img", u"图", u""],
    ["replyCount", u"回复", u""],
    ["id", u"id", u""],
    ["admin", u"admin", u"0"]
]

opHisQueue = [["h"]]
opHisPos = 0


def printSingleReply(sJson):
    str = "["
    for prop in threadProperties:
        if sJson.has_key(prop[0]) and sJson[prop[0]] != prop[2]:
            str += (prop[1] + ":" + sJson[prop[0]] + " ")
    str += "]" + sJson[u"content"]
    cPrint(str)


def forumOp(param="all", page="1"):
    print
    u"查看板块id:" + param + u" 第" + page + u"页"
    if param == "all":
        printForum()
    else:
        showForum(param, page)


def threadOp(param="null", page="1"):
    print
    u"查看串:" + param + u" 第" + page + u"页"
    if param == "null":
        print
        u"请在参数输入串号"
    else:
        showThread(param, page)


def fowardOp():
    global opHisQueue
    global opHisPos
    print
    u"前进"
    if opHisPos < len(opHisQueue) - 1:
        opHisPos += 1
    return opHisQueue[opHisPos]


def backOp():
    global opHisQueue
    global opHisPos
    print u"后退"
    if opHisPos > 0:
        opHisPos += -1
    return opHisQueue[opHisPos]


def prevOp():
    global opHisQueue
    global opHisPos
    print(u"前一页")

    inputList = opHisQueue[opHisPos]
    if inputList[0] not in ["F", "t"] or len(inputList) < 2:
        return inputList
    tInputList = copy.copy(inputList)
    if len(tInputList) > 2:
        page = tInputList[2]
        del tInputList[2]
    else:
        page = 1
    if page > 1:
        tInputList.insert(2, str(int(page) - 1))
    return tInputList


def nextOp():
    global opHisQueue
    global opHisPos
    print(u"后一页")
    inputList = opHisQueue[opHisPos]
    if inputList[0] not in ["F", "t"] or len(inputList) < 2:
        return inputList
    tInputList = copy.copy(inputList)
    if len(tInputList) > 2:
        page = tInputList[2]
        del tInputList[2]
    else:
        page = 1
    # 向后翻页不做限制
    tInputList.insert(2, str(int(page) + 1))
    return tInputList


def tuOp():
    global picAvailable
    picAvailable = ~picAvailable
    print(u"切换为" + (u"有" if picAvailable else u"无") + u"图模式")


def caoOp():
    global bossFileName
    bossFile = open(bossFileName)
    bossFileContent = bossFile.read()
    bossFile.close()
    print
    bossFileContent


def quitOp():
    print(u"退出")


operationDict = {
    "F": [forumOp, 2],
    "t": [threadOp, 2],
    "q": [quitOp, 0],
    "T": [tuOp, 0],
    "c": [caoOp, 0]
}

shortOpDict = {
    "f": fowardOp,
    "b": backOp,
    "p": prevOp,
    "n": nextOp
}


def queueAdd(opList):
    global opHisQueue
    global opHisPos
    opHisQueue = opHisQueue[:opHisPos + 1]
    opHisQueue.append(opList)
    opHisPos += 1


def mainLoop():
    input = raw_input()
    while (input != "q"):
        isNewOp = 1
        try:
            inputList = input.split(" ")
            if shortOpDict.has_key(inputList[0]):
                if inputList[0] in ["f", "b"]:
                    isNewOp = 0
                inputList = shortOpDict[inputList[0]]()
            if not operationDict.has_key(inputList[0]):
                printHelp()
                isNewOp = 0
            elif operationDict[inputList[0]][1] == 2 and len(inputList) == 3:
                operationDict[inputList[0]][0](inputList[1], inputList[2])
            elif operationDict[inputList[0]][1] > 0 and len(inputList) == 2:
                operationDict[inputList[0]][0](inputList[1])
            else:
                operationDict[inputList[0]][0]()
        except Exception as e:
            isNewOp = 0
            print(u"出现异常啦!" + os.linesep)
            print(traceback.format_exc())
            printHelp()
            print(u"请仔细看帮助，或者反应到此处" + commitUrl)
        if isNewOp:
            queueAdd(inputList)

        input = raw_input()


def main():
    print(u"正在初始化，请稍等")
    forumBigList = getJsonFromUrl(baseUrl + "/getForumList")
    # forumBigList = [{"forums":[{"name":"test", "id":"1"},{"name":"test1", "id":"2"}]}]
    for forums in forumBigList:
        for forum in forums['forums']:
            forumList.append((forum["name"], forum["id"]))
    print("初始化成功!")
    printHelp()
    mainLoop()


if __name__ == '__main__':
    main()
