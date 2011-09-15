@echo off
@echo ***

sed -n "s/\(.*\)/and t\.\1\ =\ ss1\.\1/ p" < c:\a\t.txt > robert.txt

sed -n "s/[,]*//g p" < c:\a\t.txt < robert.txt 

@echo ***

pause
