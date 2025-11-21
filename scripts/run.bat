@echo off

:: Termina eventuali processi che occupano la porta 1234
for /f "tokens=5" %%i in ('netstat -ano ^| findstr :1234') do taskkill /f /pid %%i

:: Termina eventuali processi di gruppodue.Server e gruppodue.Client
taskkill /f /im java.exe /t

:: Pulisce la finestra del terminale
cls

:: Percorso della libreria JSON
set LIB_PATH=lib\json-java.jar

:: Compila i file .java nella cartella src\gruppodue\ includendo la libreria
javac -d bin -cp "%LIB_PATH%" src\gruppodue\*.java

:: Esegui il file Server.java nel terminale corrente includendo la libreria
start /b java -cp "%LIB_PATH%;bin" gruppodue.Server

:: Attendi 1 secondo per dare tempo al Server di partire
timeout /t 1 /nobreak

:: Esegui il file Client.java in un altro terminale includendo la libreria
start cmd /k java -cp "%LIB_PATH%;bin" gruppodue.Client
