@echo off
VERIFY OTHER 2>nul
SETLOCAL ENABLEEXTENSIONS
IF ERRORLEVEL 1 echo Kan ikke aktivere utvidelser

@echo *** JOBBEN STARTER ...
@echo .


IF EXIST %RKJOBBDIR% (
set jobbdir=%RKJOBBDIR%
) ELSE (
set jobbdir=%RKJOBBDIR2%
set path=%path%;\%jobbdir%\SED\;\%jobbdir%\EXIFutils\
)

set config=%jobbdir%java\config\
set workdir=C:\a\
set jardir=%jobbdir%java\jarfiler\
set utfil=%workdir%data.txt


copy %config%app.properties.mal %config%app.properties
copy %config%db.properties.WT.prod %config%db.properties

copy "%jobbdir%WT\SQL\WT daglig resultat korrigert resultat personlig korrigert dagssaldo.txt" %workdir%sqlin.txt

rem java JdbcSql J

java -cp %jardir%rkutils-1.0.jar;%jardir%classes12.jar;%config%; rkutils.JdbcSql "J"

echo Returkode for programkjøring av JdbcSql er: %ERRORLEVEL%

rem utfil er %workdir%sqlut.txt

@echo *** JOBBEN FERDIG ...
@echo .

pause
exit

