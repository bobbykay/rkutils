@echo off
@echo mapperot: %mapperot%
VERIFY OTHER 2>nul
SETLOCAL ENABLEEXTENSIONS
IF ERRORLEVEL 1 echo Kan ikke aktivere utvidelser

@echo *** Jobb som produserer en sdv-fil som inneholder en oversikt over filer og mapper gitt en bestemt mapperot som input.
@echo *** kjørekommando: vismappeoversikt2 mapperot [filsti-format]
@echo *** parameter [filsti-format] er valgfri. 
@echo ***   Med verdien "abs" vil kolonnen i utfilen for mappesti angis som absolutt filsti, ellers angis en relativ filsti i forhold til mapperot.
@echo *** eksempler på kjøring:
@echo *** vismappeoversikt2 C:\jobber
@echo *** vismappeoversikt2 "C:\Documents and Settings"
@echo *** vismappeoversikt2 "C:\jobber" abs 
@echo .
@echo *** NB! Jobben gir output til c:\a\data.txt

@echo *** JOBBEN STARTER ...
@echo .

set dep=c:\jobber

set mapperot=%1

rem @echo dep: %dep%

set workdir=C:\a\
set utfil=%workdir%data3.txt

rem java -cp "%jardir%rkutils-1.0.jar" rkutils.PrintDirectoryNodeNames2 %mapperot% %2 %utfil%

rem @echo classpath: %dep%\java\jarfiler\rkutils-1.0.jar

set CLASSPATH=%dep%\java\jarfiler\rkutils-1.0.jar


IF (%1)==() (
@echo !!! Parameter for mapperot mangler !!!
@echo *** Jobben avslutter ***
) ELSE (
java.exe rkutils.PrintDirectoryNodeNames2 %mapperot% %2 %utfil%

echo Returkode for programkjøring av PrintDirectoryNodeNames2 er: %ERRORLEVEL%

@echo *** JOBBEN FERDIG ...
@echo .
)

