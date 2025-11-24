@echo off

set PORT=1234
set LIB_PATH=lib\json-java.jar
set SRC_PATH=src\gruppodue
set BIN_PATH=bin

for /f "tokens=5" %%a in ('netstat -ano ^| findstr :%PORT%') do (
    taskkill /PID %%a /F >nul 2>&1
)

cls

javac -d "%BIN_PATH%" -cp "%LIB_PATH%" "%SRC_PATH%\*.java"
if errorlevel 1 exit /b

start /b "" java -cp "%LIB_PATH%;%BIN_PATH%" gruppodue.Server

timeout /t 1 >nul

start "" cmd /c java -cp "%LIB_PATH%;%BIN_PATH%" gruppodue.Client