@echo off
VERIFY OTHER 2>nul
SETLOCAL ENABLEEXTENSIONS
IF ERRORLEVEL 1 echo Kan ikke aktivere utvidelser


@echo *** JOBBEN STARTER ...
@echo .

rem eksempel på kjøring:
rem vismappeoversikt "C:\SPRING\Programutvikling Spring Hibernate in Essence" abs
rem NB! Jobben gir to output filer:
rem Mappeinfo skrives til data.txt og Filinfo skives til data2.txt
rem data3.txt brukes til mellomlagring

set mapperot=%1
rem set mapperot="C:\SPRING\Programutvikling Spring Hibernate in Essence"

IF EXIST %RKJOBBDIR% (
set jobbdir=%RKJOBBDIR%
) ELSE (
set jobbdir=%RKJOBBDIR2%
set SED_HOME=%jobbdir%\SED
set path=%path%;%SED_HOME%
)

set config=%jobbdir%java\config\
set workdir=C:\a\
set jardir=%jobbdir%java\jarfiler\
set utfil=%workdir%data3.txt

java -cp "%jardir%rkutils-1.0.jar" rkutils.PrintDirectoryNodeNames %mapperot% %2

sed -n "/Mappe\|radtype/ p" < %utfil% > %workdir%data.txt

echo radtype;nivå;filstørr(MB);filstørr(Byte);filsti lengde;filnavn lengde;filsti;filnavn;ext > %workdir%data2.txt
sed -n "/Fil;/ p" < %utfil% >> %workdir%data2.txt

REM  Xabsoluttfilsti

echo Returkode for programkjøring av PrintDirectoryNodeNames er: %ERRORLEVEL%


@echo *** JOBBEN FERDIG ...
@echo .

pause
