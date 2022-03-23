@echo off

set resfolder=res
set mainclass=game.GameMain
set packname=MyBnb.jar

del %packname%

set curdir=%~dp0
set javalist=tempjavafiles.txt
set packlist=temppacklist.txt

setlocal ENABLEDELAYEDEXPANSION

FOR /R .\ %%f IN (*.java) DO (
    set file=%%f
    echo !file:%curdir%=.\! >> .\%javalist%
)

javac @.\%javalist%
del .\%javalist%

FOR /R .\ %%f IN (*.class) DO (
    set file=%%f
    echo !file:%curdir%=.\! >> .\%packlist%
)

FOR /R .\%resfolder%\ %%f IN (*.*) DO (
    set file=%%f
    echo !file:%curdir%=.\! >> .\%packlist%
)

endlocal

jar -cvfe %packname% %mainclass% @.\%packlist%

del .\%packlist%
del /s .\*.class