VERIFY OTHER 2>nul
SETLOCAL ENABLEEXTENSIONS
IF ERRORLEVEL 1 echo Kan ikke aktivere utvidelser

rem eksempel på bruk: 
rem > slettdir C:\c\soa .svn
rem sletter alle filer som heter .svn i inneværende mappe og alle undermapper
rem eksempel2:
rem slettdir C:\SVNwork\psl\db *cvs
rem sletter alle CVS mapper i inneværende mappe og alle undermapper

echo on
@echo *** JOBBEN STARTER ...
@echo .

set mapperot=%1
set dirpattern=%2

set jobbdrive=C:
set workdir=c:\a\
set tempfil=%workdir%robert2.txt


type NUL > "%tempfil%"


%jobbdrive%
cd %mapperot%

pause

rem dir /A D /B /S "%dirpattern%" >> "%tempfil%"

dir /ADH /S /P /B "%dirpattern%" >> "%tempfil%"

@echo "filpattern matcher med følgende filer:"
type "%tempfil%"

@echo Avbryt jobben hvis du ikke vil at disse filene skal slettes!!

pause

rem for /F "eol=; tokens=* delims=, " %%i in (%tempfil%) do @echo %%i

for /F "eol=; tokens=* delims=, " %%i in (%tempfil%) do rmdir /S /Q "%%i"

@echo *** JOBBEN FERDIG ...
@echo .

pause
