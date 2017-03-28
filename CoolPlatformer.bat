@echo off
cd %~dp0
del *.class
REM "cd"s into current Disk and Path
REM Deletes Existing '.class' files
@echo on
javac CoolPlatformer.java
pause
java CoolPlatformer
pause
