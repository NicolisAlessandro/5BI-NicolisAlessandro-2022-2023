# -*- coding: utf-8 -*-
__author__ = "deMartini Francesco"
__version__ = "0101 2021/11/30"

import os
from genericpath import isdir, isfile

exist = False
percorso, tail = os.path.split(__file__)
os.chdir(percorso)

boold = True
if __name__ == '__main__':
    if boold:
        print("Start")

    projectListDir = os.listdir()

    for directory in projectListDir:
        if isdir(directory) and directory.startswith("nicolis"):
            package = "package " + directory + ".bin;\n"
            directory += "/bin/"
            if isdir(directory):
                for javaFilePath in os.listdir(directory):
                    javaFilePath = directory + javaFilePath
                    if isfile(javaFilePath) and javaFilePath.endswith(".java"):

                        javaFile = open(javaFilePath, "r", )
                        listLines = javaFile.readlines()
                        javaFile.close()

                        if package not in listLines:
                            print(javaFilePath)
                            listLines.insert(0, package + "\n")
                            javaFile = open(javaFilePath, "w", )
                            listLines = javaFile.writelines(listLines)
                            javaFile.close()

    if boold:
        print("End")
