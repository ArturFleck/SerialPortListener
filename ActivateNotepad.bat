@echo off
setlocal

set "programName=notepad++.exe"  REM Change this to the desired program's executable name

for /f "tokens=2 delims=," %%H in ('tasklist /V /FO csv ^| find /I "%programName%"') do (
    set "windowTitle=%%H"
    if %%H EQU N/A (
        set "isRunning=false"
    ) else (
        set "isRunning=true"
    )
)

if "%isRunning%"=="true" (
    echo Program is already running.
) else (
    echo Program is not running. Starting it...
    start "" %programName%
)

if "%isRunning%"=="false" (
    echo Waiting for the program to start...
    timeout /t 2 > nul
)

cscript //nologo //e:jscript "%~f0" "%windowTitle%"

exit /b

@if (@CodeSection == @Batch) @then

@end

var shell = new ActiveXObject("WScript.Shell");
var windowTitle = WScript.Arguments(0);
shell.AppActivate(windowTitle);
