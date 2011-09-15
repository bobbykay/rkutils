VERIFY OTHER 2>nul
SETLOCAL ENABLEEXTENSIONS
IF ERRORLEVEL 1 echo Kan ikke aktivere utvidelser

echo on
echo programmet starter ...

rem eksempel på bruk
rem		m2rep_pomart21.bat S:\org\hibernate\
set mavenrot=%1
rem set mavenrot=C:\xw\
set workdrive=C:
set workdir=C:\a\
set tempfil=robert2.txt
set utfil=robert.txt
set utfil2=robert3.txt
set utfil3=c:\a\data.txt
set kolovskr=kilde;groupId;artifactId;version;scope 


IF EXIST %RKJOBBDIR% (
set jobbdir=%RKJOBBDIR%
) ELSE (
set jobbdir=%RKJOBBDIR2%
set SED_HOME=%jobbdir%\SED
set path=%path%;%SED_HOME%
)

set sedprogram=%jobbdir%\SED_program


%workdrive%

cd %workdir%

@echo utfil =  %utfil%

@echo '' > %utfil%

rem pause

dir /S /B %mavenrot%*pom.xml > "%tempfil%"


pause

rem for /F "eol=; tokens=* delims=, " %%i in (%tempfil%) do @echo %%i

set param="< robert.txt >> c:\a\data.txt"

echo %kolovskr% > %utfil2%
for /F "eol=; tokens=* delims=, " %%i in (%tempfil%) do call m2prep_pomart22.bat "%%i" %utfil% %utfil2% %sedprogram%

copy %utfil2% %utfil3%


pause