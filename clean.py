# coding=utf-8
import os


# 用于判断文件名是否以某个字符结尾，有则删除
def check(path, filename, endwith):
    name = filename.split(".")[0]
    if name.endswith(endwith):
        os.remove(path + "\\" + filename)
    return


# 用于删除以某个字符串结尾的文件，不包括后缀名
def foreachdir(path, endwith):
    for n in os.listdir(path):
        if os.path.isdir(path + "\\" + n):
            print('foreach', path + "\\" + n)
            foreachdir(path + "\\" + n, endwith)
        else:
            check(path, n, endwith)


mainpath = os.getcwd()
endwith = "-FINDERLO"
foreachdir(mainpath, endwith)
print(mainpath, '目录下以', endwith, '结尾的所有文件（不包括后缀）已经删除')
