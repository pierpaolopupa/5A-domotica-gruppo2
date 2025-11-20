@echo off
setlocal

REM ------------------------------------------------------
REM Pulizia: uccide eventuali server Java in esecuzione
REM ------------------------------------------------------
for /f "tokens=2" %%i in ('tasklist ^| findstr java.exe') do (
    taskkill /PID %%i /F >nul 2>&1
)

REM ------------------------------------------------------
REM Percorsi
REM ------------------------------------------------------
set "PROJECT_DIR=%~dp0..\"
set "SRC_DIR=%PROJECT_DIR%src\gruppodue"
set "BIN_DIR=%PROJECT_DIR%bin"
set "LIB_DIR=%PROJECT_DIR%lib"
set "CLASSPATH=%LIB_DIR%\json.jar"

REM ------------------------------------------------------
REM 1) Elimina le classi vecchie e ricrea la cartella bin
REM ------------------------------------------------------
if exist "%BIN_DIR%" rmdir /s /q "%BIN_DIR%"
mkdir "%BIN_DIR%"

REM ------------------------------------------------------
REM 2) Compila tutti i file Java
REM ------------------------------------------------------
dir /b /s "%SRC_DIR%\*.java" > "%PROJECT_DIR%sources.txt"
javac -cp "%CLASSPATH%" -d "%BIN_DIR%" @"%PROJECT_DIR%sources.txt"
if errorlevel 1 (
    del "%PROJECT_DIR%sources.txt"
    exit /b 1
)
del "%PROJECT_DIR%sources.txt"

REM ------------------------------------------------------
REM 3) Avvia il server nel terminale corrente
REM ------------------------------------------------------
cd /d "%BIN_DIR%"
start "" cmd /k "java -cp .;%CLASSPATH% gruppodue.Server"

REM ------------------------------------------------------
REM 4) Aspetta 1 secondo
REM ------------------------------------------------------
timeout /t 1 >nul

REM ------------------------------------------------------
REM 5) Avvia il client in un nuovo terminale (qui con cmd)
REM ------------------------------------------------------
start "" cmd /k "cd /d %BIN_DIR% && java -cp .;%CLASSPATH% gruppodue.Client"

endlocal
