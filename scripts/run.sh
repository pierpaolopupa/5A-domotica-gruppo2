#!/bin/bash

# Termina qualsiasi processo che sta utilizzando la porta 1234
sudo lsof -ti:1234 | xargs sudo kill -9

# Termina eventuali processi di gruppodue.Server e gruppodue.Client
pkill -f 'gruppodue.Server'
pkill -f 'gruppodue.Client'

# Pulisce il terminale
clear

# Percorso della libreria JSON
LIB_PATH=lib/json-java.jar

# Compila i file .java nella cartella src/gruppodue/ includendo la libreria
javac -d bin -cp "$LIB_PATH" src/gruppodue/*.java

# Esegui il file Server.java nel terminale corrente includendo la libreria
java -cp "$LIB_PATH:bin" gruppodue.Server &

# Attendi un secondo per dare tempo al Server di partire
sleep 1

# Esegui il file Client.java in un altro terminale includendo la libreria
kitty --hold -e java -cp "$LIB_PATH:bin" gruppodue.Client
