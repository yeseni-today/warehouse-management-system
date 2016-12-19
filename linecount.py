# coding=utf-8
import os


# 用于判断文件名是否以某个字符结尾，有则判断行数
def lines(path, filename, endwith):
    # name = filename.split(".")[0]
    if filename == "jquery-3.1.1.js" or filename == "three.min.js":
        return 0
    reslut = 0
    if filename.endswith(endwith):
        # 判断行数
        with open(path + "/" + filename, 'r') as f:
            for i in f:
                reslut += 1
        print(filename, "扫描完成", "总共行数：", reslut)
    return reslut


# 这是遍历方法
def foreachdir(path, endwith):
    totalline = 0
    for n in os.listdir(path):
        # print('for in', path + "/" + n)
        if os.path.isdir(path + "/" + n):
            # print('foreach', path + "/" + n)
            totalline = totalline + foreachdir(path + "/" + n, endwith)
        else:
            totalline = totalline + lines(path, n, endwith)
    return totalline


mainpath = os.getcwd()
endwiths = ("py", "java", "js", "html")
print('start', "行数计数开始")
result = {}
count = 0
for endwith in endwiths:
    count += foreachdir(mainpath, endwith)
    result[endwith] = count

for key in result.keys():
    print(key, result[key])

print("计数完成", 'count:', count)
# java 7898
# js 1689
# html 6553
# py 68
# total 12-18  23:20 16208
