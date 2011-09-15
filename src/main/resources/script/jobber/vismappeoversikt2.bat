@echo off
VERIFY OTHER 2>nul
SETLOCAL ENABLEEXTENSIONS
IF ERRORLEVEL 1 echo Kan ikke aktivere utvidelser

@echo *** JOBBEN STARTER ...
@echo .

rem eksempel på kjøring:
rem vismappeoversikt "C:\SPRING\Programutvikling Spring Hibernate in Essence" abs
rem NB! Jobben gir output til c:\a\data.txt

set mapperot=%1

IF EXIST %RKJOBBDIR% (
set jobbdir=%RKJOBBDIR%
) ELSE (
set jobbdir=%RKJOBBDIR2%
set path=%path%;\%jobbdir%\SED\;\%jobbdir%\EXIFutils\
)


rem set config=%jobbdir%java\config\
set workdir=C:\a\
set utfil=%workdir%data3.txt

rem java -cp "%jardir%rkutils-1.0.jar" rkutils.PrintDirectoryNodeNames2 %mapperot% %2 %utfil%

set CLASSPATH=%jobbdir%\java\jarfiler\rkutils-1.0.jar


java rkutils.PrintDirectoryNodeNames2 %mapperot% %2 %utfil%

echo Returkode for programkjøring av PrintDirectoryNodeNames2 er: %ERRORLEVEL%

@echo *** JOBBEN FERDIG ...
@echo .

pause
