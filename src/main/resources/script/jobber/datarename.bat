rem denne jobben renamer data3.txt til data.txt og sletter opprinnelige data.txt

c:
cd \a
del data.txt
rename data3.txt data.txt
echo returkode fra jobb: 
@echo %ERRORLEVEL%
pause

exit
