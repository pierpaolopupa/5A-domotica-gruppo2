#!/bin/bash

# Percorsi
PROJECT_DIR="$(cd "$(dirname "$0")/.." && pwd)"   # risale dalla cartella script/ alla root del progetto
BIN_DIR="$PROJECT_DIR/bin"
CLASSPATH="$PROJECT_DIR/lib/json.jar"

# 1) Avvia il client nello stesso terminale
cd "$BIN_DIR"
java -cp .:"$CLASSPATH" gruppodue.Client
