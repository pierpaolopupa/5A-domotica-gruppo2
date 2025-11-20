#!/bin/bash

# Percorsi
PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
BIN_DIR="$PROJECT_DIR/bin"
CLASSPATH="$PROJECT_DIR/json.jar"

# 1) Avvia il client nello stesso terminale
cd "$BIN_DIR"
java -cp .:"$CLASSPATH" gruppodue.Client
