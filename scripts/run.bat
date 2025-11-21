@echo off
REM Libera la porta usata dal Server (termina processi che usano la porta 1234)
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :1234') do (
    taskkill /PID %%a /F >nul 2>&1
)

REM Termina eventuali processi di gruppodue.Server e gruppodue.Client
taskkill /F /IM java.exe >nul 2>&1

REM Pulisce il terminale
cls

REM Percorso della libreria JSON
set LIB_PATH=lib\json-java.jar

REM Compila i file .java nella cartella src\gruppodue\ includendo la libreria
javac -d bin -cp "%LIB_PATH%" src\gruppodue\*.java

REM Esegui il file Server.java nel terminale corrente includendo la libreria
start "Server" cmd /c java -cp "%LIB_PATH%;bin" gruppodue.Server

REM Attendi un secondo per dare tempo al Server di partire
timeout /t 1 >nul

REM Esegui il file Client.java in un altro terminale includendo la libreria
start "Client" cmd /c java -cp "%LIB_PATH%;bin" gruppodue.Client
