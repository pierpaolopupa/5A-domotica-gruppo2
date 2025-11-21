@echo off
REM Pulisce il terminale
cls

REM Percorso della libreria JSON
set LIB_PATH=lib\json-java.jar

REM Compila i file .java nella cartella src\gruppodue\ includendo la libreria
javac -d bin -cp "%LIB_PATH%" src\gruppodue\*.java

REM Avvia il Client nello stesso terminale includendo la libreria
java -cp "%LIB_PATH%;bin" gruppodue.Client
