@echo off
chcp 65001 > nul

cd /d C:\ticket_prj\crawler

C:\Users\user\AppData\Local\Python\pythoncore-3.14-64\python.exe "C:\ticket_prj\crawler\python kbo_record_job.py" >> kbo_record_log.txt 2>&1

exit /b %ERRORLEVEL%