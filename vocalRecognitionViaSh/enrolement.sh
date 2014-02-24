#!/bin/bash

# Permet de générer un modèle à partir .wav.

#Wave2seg
#java -Xmx2024m -jar ./LIUM_SpkDiarization.jar --fInputMask=./wav/$1.wav --sOutputMask=./seg/$1.seg --doCEClustering showName

subWorld="sousMonde"

#copy the ubm for each speaker
java -Xmx2024m -cp LIUM_SpkDiarization.jar fr.lium.spkDiarization.programs.MTrainInit --help --sInputMask=./seg/$subWorld.seg --fInputMask=./wav/$1.wav --fInputDesc="audio16kHz2sphinx,1:3:2:0:0:0,13,1:1:300:4"  --emInitMethod=copy --tInputMask=./ubm.gmm --tOutputMask=$1.init.gmm speakers


#train (MAP adaptation, mean only) of each speaker, the diarization file describes the training data of each speaker.
java -Xmx2024m -cp LIUM_SpkDiarization.jar fr.lium.spkDiarization.programs.MTrainMAP --help --sInputMask=./seg/$1.seg --fInputMask=./wav/$1.wav --fInputDesc="audio16kHz2sphinx,1:3:2:0:0:0,13,1:1:300:4"  --tInputMask=$1.init.gmm  --emCtrl=1,5,0.01 --varCtrl=0.01,10.0 --tOutputMask=./modele/modelede$1.gmm speakers
