biometry-lock-facial-and-vocal
==============================

Presentation
--------------------------

To use the vocal-recognition, you have to use the two scripts :
enrolementBis.sh
identification.sh

Theses script will call function from the LIUM Library to make the differents models and the identification

enrolementBis.sh
--------------------------
This script takes in paramater a wav file, which has to contains the different data of the user's panel.
A file .seg have to be present. It represents the segmentation of the audio data and the different id of the users.
The wav paramater has to be in the /wav file, and be written without the extension '.wav'.
The seg file has to be in the /seg file, and be written without the extension '.seg'.
Example : sh enrolementBis.sh sousMonde

The script will produce a file X.init.gmm, which contains the models gmm of the different users.

identification.sh :
--------------------------
This script takes in paramater a wav file, which has to contains the record of the person who is trying to identificate.
This file has to be in the /wav file, and be written without the extension '.wav'.
Example : sh identification.sh Nicolas

The resultat is readable in the log at the line which begins by ADDIDENT

Example :
ADDIDENT name: s0#_#nicolas score:-32.343197088389566 gmm:S0#_#Nicolas cluster:S0      {addScore() / 1}

