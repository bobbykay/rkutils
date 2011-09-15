@echo off
VERIFY OTHER 2>nul
SETLOCAL ENABLEEXTENSIONS
IF ERRORLEVEL 1 echo Kan ikke aktivere utvidelser

rem cmd /v /K for /F "eol=; tokens=* delims=, " %i in (c:\a\data.txt) do  @echo %i
rem for /F "eol=; tokens=* delims=, " %%i in (%tempfil%) do call filkonkatp.bat "%%i" %utfil%
rem FOR /F "eol=; tokens=1,2,3* delims=, " %i in (data.txt) do @echo %i %j %k

@echo *** JOBBEN STARTER ...
@echo .


IF EXIST %RKJOBBDIR% (
set jobbdir=%RKJOBBDIR%
) ELSE (
set jobbdir=%RKJOBBDIR2%
set EXIFUTILS_HOME=%jobbdir%\EXIFutils
set path=%path%;%EXIFUTILS_HOME%

)


IF EXIST %RKJOBBDIR% (
set jobbdir=%RKJOBBDIR%
) ELSE (
set jobbdir=%RKJOBBDIR2%
set path=%path%;\%jobbdir%\SED\;\%jobbdir%\EXIFutils\
)

set workfil=c:\a\data.txt
set workfil2=c:\a\data2.txt


echo %path% > %workfil%
sed "s/;/\n/g" < %workfil% > %workfil2%

rem pause

echo Oversikt over alle path-mapper som kaller %1 > %workfil%

for /F "eol=; tokens=* delims=;" %%i in (%workfil2%) do  call findxpath_sub.bat "%%i" %1 %workfil%

echo on

@echo .
@echo Innholdet i %workfil%
@echo ===^>
@type %workfil%
@echo ^<===

@echo ...
@echo *** JOBBEN FERDIG ...

@pause

