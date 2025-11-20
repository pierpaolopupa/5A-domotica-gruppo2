@echo off

:: Percorsi
set PROJECT_DIR=%~dp0..\  :: Torna alla root del progetto
set BIN_DIR=%PROJECT_DIR%bin
set CLASSPATH=%PROJECT_DIR%lib\json.jar

:: 1) Avvia il client nello stesso terminale
cd /d %BIN_DIR%
java -cp .;%CLASSPATH% gruppodue.Client
