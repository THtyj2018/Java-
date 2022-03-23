@echo off

set javalist=tempjavalist.txt
set mainclass=game.GameMain

set curdir=%~dp0

setlocal ENABLEDELAYEDEXPANSION

FOR /R .\ %%f IN (*.java) DO (
    set file=%%f
    echo !file:%curdir%=.\! >> .\%javalist%
)

endlocal

javac @.\%javalist%
del .\%javalist%
java %mainclass%
del /s .\*.class