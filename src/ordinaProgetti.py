# -*- coding: utf-8 -*-
__autor__ = "Francesco"
__version__ = "0101 2020/10/12"

import glob
import shutil

from inizializza import *


def rotate(lista: list, n):
    return lista[n:] + lista[:n]


boold = True
if __name__ == "__main__":
    if boold:
        print("Start")

    head, tail = os.path.split(__file__)
    os.chdir(head)
    percorso = os.getcwd()

    vettFile = glob.glob('*.java')
    for f in vettFile:

        projectName = (f.rsplit(".", 1))[0].capitalize()
        newFileName = shutil.move(f, projectName + ".java")

        headerMd = "# Program name: " + newFileName + "\n" + \
                   "---\n" + \
                   "\n" + \
                   "## Consegna\n _________________"

        exist, perBin, perDoc, perFile, cartella = cartelle(projectName)

        if (not exist == False):
            print(exist)
            break

        filejava = open(perDoc + pathSeparation + readmeName, "w")
        filejava.write(headerMd)
        filejava.close()

        fjava = open(newFileName, "r")
        vRighe = fjava.readlines()
        fjava.close()

        vRighe.append("package " + nomeCartella +
                      projectName + "." + binName + ";\n\n")
        vRighe = rotate(vRighe, -1)

        fjava = open(newFileName, "w")
        fjava.writelines(vRighe)
        fjava.close()

        shutil.move(percorso + "\\" + newFileName, perBin)

    if boold:
        print("End")
