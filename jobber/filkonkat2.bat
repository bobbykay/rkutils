@echo off
rem VERIFY OTHER 2>nul
rem SETLOCAL ENABLEEXTENSIONS
rem IF ERRORLEVEL 1 echo Kan ikke aktivere utvidelser


REM *** NB! Her kan søketekst være et regulært uttrykk, og det skilles normalt ikke mellom store og små bokstaver.
rem eksempel på kall av jobben:
rem filkonkat2 <søketekst> <mapperot> <filepattern>
rem filkonkat2 last_testdata_x  Y:\NG_home\ExWorks\database\SQL\*.*
rem findstr "er utfort i IMI Station" C:\SVNwork\psl\ *.java
REM *** NB! Her kan søketekst være et regulært uttrykk
REM set soeketekst=[hH]ibernate
REM set soeketekst=BeholdningsmodulenDS
REM set mapperot=C:\SVNwork\beholdningsmodulen\
REM set filepattern=*.*

@echo *** JOBBEN STARTER ...

set soeketekst=%1
set mapperot=%2
set filepattern=%3    
set currdir=%cd%


set jobbdrive=C:
set workdir=c:\a\
set tempfil=%workdir%robert2.txt
set utfil=%workdir%robert.txt
set utfil2=%workdir%data.txt

set dependencies=c:\jobber

set sed_home=%dependencies%\sed

set kolovskr=******************************************  O V E R S K R I F T  ******************************************


type NUL > "%tempfil%"
type NUL > "%utfil%"
type NUL > "%utfil2%"

rem pause

%jobbdrive%

echo 'Jobben søker etter teksten:'
echo "-->%soeketekst%<-- "
echo "i rotmappe:"
echo "-->%mapperot%%filepattern%<--"
findstr /S /M /R /I /C:%soeketekst% %mapperot%%filepattern% >> "%tempfil%"

@echo "teksten kalles i følgende filer: "
@echo (...

type "%tempfil%"
@echo ...)
rem pause

rem for /F "eol=; tokens=* delims=, " %%i in (%tempfil%) do @echo %%i


for /F "eol=; tokens=* delims=, " %%i in (%tempfil%) do call filkonkatp.bat "%%i" %utfil%

rem pause

echo %kolovskr% > %utfil2%

rem sed -f seds_pomxml.txt -n < %utfil% >> %utfil2%
%sed_home%\sed "" < %utfil% >> %utfil2%

@echo output for jobben ligger i filen %utfil%
@echo ... JOBBEN FERDIG ***
@echo .
     
cd %curdir%     
pause