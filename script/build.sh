#!/bin/bash

# Trova eventuali processi Java sul server e uccidili
pkill -f "gruppodue.Server"

# Libera la porta usata dal Server
fuser -k 1234/tcp

clear

# Percorsi
PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)/.."
SRC_DIR="$PROJECT_DIR/src"
BIN_DIR="$PROJECT_DIR/bin"
LIB_DIR="$PROJECT_DIR/lib"
CLASSPATH="$LIB_DIR/json.jar"

# 1) Elimina completamente le classi vecchie
rm -rf "$BIN_DIR"
mkdir -p "$BIN_DIR"

# 2) Compila TUTTI i file Java
find "$SRC_DIR" -name "*.java" > "$PROJECT_DIR/sources.txt"
javac -cp "$CLASSPATH" -d "$BIN_DIR" @"$PROJECT_DIR/sources.txt" || exit 1
rm "$PROJECT_DIR/sources.txt"

# 3) Avvia il server nel terminale corrente
cd "$BIN_DIR"
java -cp ".:$CLASSPATH" gruppodue.Server &

# 4) Aspetta 1 secondo
sleep 1

# 5) Avvia il client in un nuovo terminale kitty
kitty bash -c "cd \"$BIN_DIR\" && java -cp .:\"$CLASSPATH\" gruppodue.Client"
