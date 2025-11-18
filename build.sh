#!/bin/bash

SRC_DIR="src"
OUT_DIR="out"
JAR="json.jar"

# Pulizia cartella out
rm -rf $OUT_DIR
mkdir -p $OUT_DIR

# Compilazione
javac -cp "$JAR" -d $OUT_DIR $SRC_DIR/gruppodue/*.java
if [ $? -ne 0 ]; then
    exit 1
fi

# Avvio del client in un terminale separato
kitty -e bash -c "java -cp \"$OUT_DIR:$JAR\" gruppodue.Client; exec bash" &

# Avvio del server nello stesso terminale (foreground)
java -cp "$OUT_DIR:$JAR" gruppodue.Server
