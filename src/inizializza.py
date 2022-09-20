# -*- coding: utf-8 -*-
__autor__ = "Francesco"
__version__ = "0101 2020/10/12"

# TODO modificare scrittura README encoding sbagliata


import importlib
import os
import sys
from pathlib import Path

exist = False
percorso, tail = os.path.split(__file__)
os.chdir(percorso)

# CONSTANTI
pathSeparation = "//"
nomeCartella = "nicolis_A_"
readmeName = "README.md"
binName = "bin"
docName = "doc"
fileName = "file"


def import_parents(level):
    global __package__
    file = Path(__file__).resolve()
    parent, top = file.parent, file.parents[level]

    sys.path.append(str(top))
    try:
        sys.path.remove(str(parent))
    except ValueError:  # giá rimosso
        pass

    __package__ = '.'.join(parent.parts[len(top.parts):])
    importlib.import_module(__package__)  # won't be needed after that


def cartelle(nomeProgetto):
    cartella = percorso + pathSeparation + nomeCartella + nomeProgetto
    esiste = os.path.isdir(cartella)
    perBin = ""
    perDoc = ""
    perFile = ""

    if not esiste:
        os.makedirs(cartella)

        perBin = cartella + pathSeparation + binName
        os.makedirs(perBin)

        perDoc = cartella + pathSeparation + docName
        os.makedirs(perDoc)

        perFile = cartella + pathSeparation + fileName
        os.makedirs(perFile)

    else:
        esiste = "Progetto già esistente"
    return esiste, perBin, perDoc, perFile, cartella


def creaFile(perBin, perDoc, intJava, intRead):
    try:
        filejava = open(perBin + pathSeparation +
                        projectName + ".java", "w")
        filejava.write(intJava)
        filejava.close()

        filejava = open(perDoc + pathSeparation + readmeName, "w")
        filejava.write(intRead)
        filejava.close()
    except IOError:
        return False
    return True


boold = True
if __name__ == '__main__' and __package__ is None:
    import_parents(level=2)
    from .. import AutoReadme

    if boold:
        print("Start")

    # INPUT
    projectName = input("inserire nome progetto: ").capitalize()
    consegna = ""
    counterCapolinea = 1

    print("inserire consegna progetto: ")
    while counterCapolinea < 3:

        inputConsegna = input()
        consegna += inputConsegna + "\n"

        if inputConsegna == "":
            counterCapolinea += 1
        else:
            counterCapolinea = 1

    # Java
    headerJava = \
        'package ' + nomeCartella + projectName + '.' + binName + ';\n' + \
        '\n' + \
        'import java.io.File;\n' + \
        '\n' + \
        'class ' + projectName + ' {\n' + \
        '\n' + \
        '\tpublic ' + projectName + '() {\n' + \
        '\n' + \
        '\t}\n' + \
        '}\n' + \
        '\n' + \
        'class ' + projectName + 'Test {\n' + \
        '\tpublic static void main(String[] args) {\n' + \
        '\n' + \
        '\t\tSystem.out.println("Start");\n' + \
        '\n' + \
        '\t\t//\t\tCALCOLO PATH RELATIVO UNIVERSALE\n' + \
        '\t\t//----------------------------------------------------------------------\n' + \
        '\t\tString tempPath = new File(\n' + \
        '\t\t\t\tString.valueOf(' + projectName + '.class.getPackage()).replace("package ", "").replace(".", "/")\n' + \
        '\t\t).getParent();\n' + \
        '\t\tFile uesrPath = new File(System.getProperty("user.dir"));\n' + \
        '\t\tString projectPath = uesrPath.getName().equals(tempPath) ?\n' + \
        '\t\t\t\tuesrPath.getPath() :\n' + \
        '\t\t\t\tnew File(uesrPath.getPath() + "/src").exists() ?\n' + \
        '\t\t\t\t\t\tuesrPath.getPath() + "/src/" + tempPath :\n' + \
        '\t\t\t\t\t\tuesrPath.getPath() + tempPath;\n' + \
        '\t\t//----------------------------------------------------------------------\n' + \
        '\n' + \
        '\t\t// COSTANTI\n' + \
        '\t\tString resursesPath = "/' + fileName + '/";\n' + \
        '\n' + \
        '\t\tSystem.out.println("Hello, World");\n' + \
        '\n' + \
        '\t\tSystem.out.println("End");\n' + \
        '\n' + \
        '\t}\n' + \
        '}'

    # Readme
    headerMd = \
        "# Program name: " + projectName + ".java\n" + \
        "\n" + \
        "---\n" + \
        "\n" + \
        "## Consegna\n\n" + consegna.rstrip() + "\n"

    # OUTPUT
    exist, perBin, perDoc, perFile, dirUpdated = cartelle(projectName)

    if not exist:
        creation = creaFile(perBin, perDoc, headerJava, headerMd)
        if creation:
            AutoReadme.updateMD(percorso, nomeCartella, dirUpdated)
        else:
            print("Erroe on write files")
    else:
        print(exist)

    if boold:
        print("End")
