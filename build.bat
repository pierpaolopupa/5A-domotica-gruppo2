@echo off
setlocal

set PROJECT_DIR=%~dp0
set CLASSPATH=%PROJECT_DIR%json.jar

if not exist "%PROJECT_DIR%bin" mkdir "%PROJECT_DIR%bin"

javac -cp "%CLASSPATH%" -d "%PROJECT_DIR%bin" "%PROJECT_DIR%gruppodue\*.java"

start cmd /k "cd /d %PROJECT_DIR%bin && java -cp .;%CLASSPATH% gruppodue.Server"
start cmd /k "cd /d %PROJECT_DIR%bin && java -cp .;%CLASSPATH% gruppodue.Client"

endlocal
