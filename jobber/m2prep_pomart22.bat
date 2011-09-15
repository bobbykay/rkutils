VERIFY OTHER 2>nul
SETLOCAL ENABLEEXTENSIONS
IF ERRORLEVEL 1 echo Kan ikke aktivere utvidelser

echo  kopierer fil: %1 til %2
echo filename: %1 > %2
type %1 >> %2

sed -f %sedprogram%\seds_pomxml3.txt -n < %2 >> %3
rem pause
