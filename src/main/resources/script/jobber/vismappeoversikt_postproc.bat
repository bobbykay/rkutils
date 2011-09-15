rem VERIFY OTHER 2>nul
rem SETLOCAL ENABLEEXTENSIONS
rem IF ERRORLEVEL 1 echo Kan ikke aktivere utvidelser

echo on
echo programmet starter ...

rem eksempel på kjøring:
rem vismappeoversikt "C:\SPRING\Programutvikling Spring Hibernate in Essence" abs
rem NB! Jobben gir to output filer:
rem Mappeinfo skrives til data.txt og Filinfo skives til data2.txt
rem data3.txt brukes til mellomlagring

set mapperot=%1
rem set mapperot="C:\SPRING\Programutvikling Spring Hibernate in Essence"
set jobbdir=%RKJOBBDIR%\
set config=%jobbdir%java\config\
set workdir=C:\a\
set jardir=%jobbdir%java\jarfiler\
set utfil=%workdir%data3.txt

rem java -cp "%jardir%rkutils-1.0.jar" rkutils.PrintDirectoryNodeNames %mapperot% %2

sed -n "/Mappe\|radtype/ p" < %utfil% > %workdir%data.txt

sed -n "/Fil\|radtype/ p" < %utfil% > %workdir%data2.txt

REM  Xabsoluttfilsti

echo %ERRORLEVEL%

pause