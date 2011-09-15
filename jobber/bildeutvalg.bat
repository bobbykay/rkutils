@echo off
VERIFY OTHER 2>nul
SETLOCAL ENABLEEXTENSIONS
IF ERRORLEVEL 1 echo Kan ikke aktivere utvidelser

rem Denne jobben kopierer over alle jpg bilder som ligger i mapper som ligger under en angitt rotmappe
rem Parametre:                                             
rem alle parametre blir n� hentet fra bildeutvalg.properties filen som m� ligge i classpath for � bli fanget opp av programmet.
rem    kilderotmappe: rotmappen for alle undermapper
rem    startmappe og sluttmappe: start- og sluttmappen for utvalget det kopieres for. 
rem        Alle mapper fom. startmappen og tom. sluttmappen i alfabetisk rekkef�lge inng�r i utvalget av mapper det kopieres for
rem    antallfiler: maks begrensening for antall filer i en utmappe. Nye utmapper opprettes dynamisk etterhvert som de blir fulle (f�r maks antall filer) 
rem        Bildene kopieres til utmapperot: c:\tmp\tmpdir\ og det opprettes undermapper (med maks begrensening angitt av parameteren antallfiler) c:\tmp\tmpdir\DIR1 DIR2, ... 
rem        inntil alle filer i utvalget er kopiert.
rem    debugmodus: hvis denne har verdien "debug" vil debugmeldinger fra java-programmet vises, ellers ikke.
rem    copymodus: hvis denne har "nocopy" vil ikke filene kopieres, men det blir rapportert p� utvalgsfiler og tilsvarende filsti og navn hvis filene skulle kopieres.
rem Jobben kaller EXIFUtils programmet exiflist.exe via DOS shellkommando (fra java-programmet) normalt i mappen C:\Programfiler\EXIFutils for � f� tak i "bilde tatt dato".

rem eksempel p� kj�ring: parameter settes f�rst i bildeutvalg.properties
rem s� kj�re fra command prompt kommandoen: 
rem bildeutvalg

@echo *** JOBBEN STARTER ...
@echo .


IF EXIST %RKJOBBDIR% (
set jobbdir=%RKJOBBDIR%
) ELSE (
set jobbdir=%RKJOBBDIR2%
set EXIFUTILS_HOME=%jobbdir%\EXIFutils
set path=%path%;%EXIFUTILS_HOME%
)

set CLASSPATH=%jobbdir%\java\jarfiler\rkutils-1.0.jar;%jobbdir%;CLASSPATH

rem eksempel p� bruk: java rkutils.BildeUtvalg "C:\bilder" 2008_03_05 2009_03_01 100



pause

@echo on                         

type nul > c:\a\data.txt

java rkutils.BildeUtvalg

echo Returkode for programkj�ring av BildeUtvalg er: %ERRORLEVEL%

type c:\a\robert.txt

sed -n "/Rapport: / s/Rapport: \(.*\)\1// p" < c:\a\robert.txt > c:\a\DATA.TXT


@echo *** JOBBEN FERDIG ...
@echo .


pause
