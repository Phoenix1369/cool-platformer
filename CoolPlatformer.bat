@echo off
cd %~dp0
del *.class
@echo on
REM "cd"s into current Disk and Path
REM Deletes Existing '.class' files
javac CoolPlatformer.java
pause
java CoolPlatformer
pause
