@echo off

rem pause

rem svn up

set loggfil=c:\a\bygg.log
set loggfil2=c:\a\bygg2.log

@echo 'Resultatet av build' > %loggfil%
@echo 'Resultatet av build' > %loggfil%2

IF (%1)==(test) (
@echo kjører med junit tester
call mvn clean >> %loggfil%

@echo resultatet av mvn clean kommando: %ERRORLEVEL%
 
call mvn -Dskip.assembly=true install >> %loggfil%

@echo resultatet av mvn -Dskip.assembly=true install kommando: %ERRORLEVEL%
) ELSE (
@echo kjører uten junit tester
call mvn clean > %loggfil%

@echo resultatet av mvn clean kommando: %ERRORLEVEL%

call mvn -Dmaven.test.skip=true -Dskip.assembly=true install >> %loggfil%

@echo resultatet av mvn -Dmaven.test.skip=true -Dskip.assembly=true install kommando: %ERRORLEVEL%
)

sed -n "/BUILD SUCCESS\|BUILD FAILURE/p"  < %loggfil% >> %loggfil%2


call mvn org.apache.maven.plugins:maven-eclipse-plugin:2.6:clean >> %loggfil%

@echo resultatet av mvn eclipse:clean kommando: %ERRORLEVEL%

call mvn org.apache.maven.plugins:maven-eclipse-plugin:2.6:eclipse >> %loggfil%

@echo resultatet av mvn eclipse:eclipse kommando: %ERRORLEVEL% 

sed -n "/BUILD SUCCESS\|BUILD FAILURE/p"  < %loggfil% >> %loggfil%2