#!/bin/bash

# Pulisce il terminale
clear

# Percorso della libreria JSON
LIB_PATH=lib/json-java.jar

# Compila i file .java nella cartella src/gruppodue/ includendo la libreria
javac -d bin -cp "$LIB_PATH" src/gruppodue/*.java

# Avvia il Client nello stesso terminale includendo la libreria
java -cp "$LIB_PATH:bin" gruppodue.Client
