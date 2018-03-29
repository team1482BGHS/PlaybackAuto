@echo off

:select
set /p mode="(f)etch or (p)ush > "

if /I "%mode%"=="f" goto fetch
if /I "%mode%"=="p" goto push

echo Please type f or p
goto select


:fetch
set /p team="Team number (xxxx) > "
set /p source="Source file (on rio) > "
set /p dest="Destination file (in downloads folder) > "

scp lvuser@172.22.11.2:/home/lvuser/%source% %userprofile%\Downloads\%dest%
if %ERRORLEVEL% EQU 0 goto done
scp lvuser@10.%team:~0,2%.%team:~2,2%.1:/home/lvuser/%source% %userprofile%\Downloads\%dest%
if %ERRORLEVEL% EQU 0 goto done
scp lvuser@robotRIO-%team%-FRC.local:/home/lvuser/%source% %userprofile%\Downloads\%dest%
if %ERRORLEVEL% EQU 0 goto done

goto fail


:push
set /p team="Team number (xxxx) > "
set /p source="Source file (from downloads folder) > "
set /p dest="Destination file (on rio) > "

scp %userprofile%\Downloads\%source% lvuser@172.22.11.2:/home/lvuser/%dest%
if %ERRORLEVEL% EQU 0 goto done
scp %userprofile%\Downloads\%source% lvuser@10.%team~0,2%.%team~2,2%.1:/home/lvuser/%dest%
if %ERRORLEVEL% EQU 0 goto done
scp %userprofile%\Downloads\%source% lvuser@robotRIO-%team%-FRC.local:/home/lvuser/%dest%
if %ERRORLEVEL% EQU 0 goto done

goto fail

:done
echo Transfer finished successfully from %source% to %dest%.
goto end

:fail
echo Transfer failed. Check your team number and the filenames.
goto end

:end
echo Press any key to exit...
pause > nul
