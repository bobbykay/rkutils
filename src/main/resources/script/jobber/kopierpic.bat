@echo on
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



set jobbdrive=C:
set workdir=c:\a\
set tempfil=%workdir%robert4.txt
set utfil=%workdir%robert.txt
set utfil2=%workdir%data.txt

IF EXIST %RKJOBBDIR% (
set jobbdir=%RKJOBBDIR%
) ELSE (
set jobbdir=%RKJOBBDIR2%
set SED_HOME=%jobbdir%\SED
set path=%path%;%SED_HOME%
)

set tempcd = %cd%

rem pause

%jobbdrive%


@echo kopierer bildene til mappen cd "C:\Documents and Settings\rokind\Mine dokumenter\Mine bilder\presentasjon"

          
rmdir /s "C:\Documents and Settings\rokind\Mine dokumenter\Mine bilder\presentasjon"

mkdir "C:\Documents and Settings\rokind\Mine dokumenter\Mine bilder\presentasjon"

cd "C:\Documents and Settings\rokind\Mine dokumenter\Mine bilder\presentasjon"
          
for /F "eol=; tokens=* delims=, " %%i in (%tempfil%) do call kopierpicp.bat %%i


@echo ... JOBBEN FERDIG ***
@echo .
                    
cd %tempcd%
                    
pause