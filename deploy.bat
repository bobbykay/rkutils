@echo on

set jobbdir=%RKJOBBDIR%\
set config=%jobbdir%java\config\
set workdir=C:\a\
set jardir=%jobbdir%java\jarfiler\
set utfil=%workdir%data3.txt

pause

copy target\rkutils-1.0.jar "%jardir%"

pause
