@echo off
setlocal

set PROJECT_DIR=%~dp0
set SRC_DIR=%PROJECT_DIR%src\gruppodue
set BIN_DIR=%PROJECT_DIR%bin
set CLASSPATH=%PROJECT_DIR%json.jar

if not exist "%BIN_DIR%" mkdir "%BIN_DIR%"

REM Creo un file che contiene la lista di tutti i .java
dir /b /s "%SRC_DIR%\*.java" > "%PROJECT_DIR%sources.txt"

REM Compilo in bin
javac -cp "%CLASSPATH%" -d "%BIN_DIR%" @"%PROJECT_DIR%sources.txt"
if errorlevel 1 (
    del "%PROJECT_DIR%sources.txt"
    pause
    exit /b
)

del "%PROJECT_DIR%sources.txt"

REM Avvio server
start "" cmd /k "cd /d %BIN_DIR% && java -cp .;%CLASSPATH% gruppodue.Server"

REM Aspetto un secondo per far partire il server
timeout /t 1 >nul

REM Avvio client
start "" cmd /k "cd /d %BIN_DIR% && java -cp .;%CLASSPATH% gruppodue.Client"

endlocal
