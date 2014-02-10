#!/bin/bash

#Transform le premier fichier en paramètre (de type en wave) en $1
java -Xmx2024m -jar ./LIUM_SpkDiarization.jar --fInputMask=./wav/$1.wav --sOutputMask=./seg/$1.seg --doCEClustering  showName
