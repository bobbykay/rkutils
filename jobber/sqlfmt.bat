echo off

rem programmet formaterer en sql til standard sql redigeringsformat
rem eksempel på kjøring:
rem sqlfmt.bat c:\a\sql.txt    
rem pause   

IF EXIST %RKJOBBDIR% (
set jobbdir=%RKJOBBDIR%
) ELSE (
set jobbdir=%RKJOBBDIR2%
set SED_HOME=%jobbdir%\SED
set path=%path%;%SED_HOME%
)
rem pause

set utfil=c:\a\data.txt   
set tempfil1=c:\a\temp1.txt
set tempfil2=c:\a\temp2.txt
set sedprogram=%jobbdir%\SED_program

echo > %tempfil1%
echo > %tempfil2%

rem pause
@echo *** Programmet konverterer en sql til standard redigeringsformat. ***

@echo ...          jobbdir: %jobbdir%
@echo ...            utfil: %utfil%  
@echo ... sedprogram mappe: %sedprogram%                                        


pause          



sed -n -f %sedprogram%\seds_sqlfmt1.txt < %1 > %tempfil1%

sed -f %sedprogram%\seds_sqlfmt2.txt < %tempfil1% > %tempfil2%

sed -n -f %sedprogram%\seds_sqlfmt3.txt < %tempfil2% > %utfil%

echo ... programkjøring ferdig.

pause

exit
