rem @echo off
VERIFY OTHER 2>nul
SETLOCAL ENABLEEXTENSIONS
IF ERRORLEVEL 1 echo Kan ikke aktivere utvidelser

@echo *** JOBBEN STARTER ...
@echo .

set dependencies=c:\jobber


set config=%dependencies%\java\config\
set workdir=C:\a
set jardir=%dependencies%\java\jarfiler\
set utfil=%workdir%\data.txt


copy %config%app.properties.mal %config%app.properties

rem copy %config%db.properties.WT.prod %config%db.properties     
copy %config%db.properties.XW.test %config%db.properties     


rem copy "%jobbdir%WT\SQL\WT daglig resultat korrigert resultat personlig korrigert dagssaldo.txt" %workdir%sqlin.txt   
copy "C:\svnwork\xw\trunk\10_1_3_server\db\rapporter\XW avvik per sending gitt fakturanummer.sql" %workdir%sqlin.txt   

rem java JdbcSql J

--java -cp %jardir%rkutils-1.0.jar;%jardir%classes12.jar;%config%; rkutils.JdbcSql "J"
java -cp %jardir%rkutils-1.0.jar;%jardir%classes12.jar;%config%; rkutils.JdbcSql "N"

echo Returkode for programkj�ring av JdbcSql er: %ERRORLEVEL%

rem utfil er %workdir%\sqlut.txt

@echo *** JOBBEN FERDIG ...
@echo .

pause
exit
