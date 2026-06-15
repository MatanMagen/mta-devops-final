Jenkins Setup Guide - for steps 6-7



Open a standard Windows Command Prompt (CMD) and run: npm install -g selenium-side-runner edgedriver





Step 6: Availability Monitor (SiteMonitor Lite)

1\. Open Jenkins

2\. click on New Item 

3\. select Freestyle project and name it: App-Availability-Monitor.

4\. Under Build Triggers, check the box for Build periodically.

5\. In the Schedule text box (Cron syntax), enter the following to force the job to run automatically every 5 minutes: H/5 \* \* \* \*

6\. Scroll down to Build Steps, click Add build step, and select Execute Windows batch command.

7\. Paste the following automated script:



@echo off

curl -s -o NUL -w "%%{http\_code}" http://localhost:8080/matan-yael-roy-ofri-app/ > temp\_status.txt

set /p HTTP\_CODE=<temp\_status.txt

del temp\_status.txt



echo Current HTTP Status Code is: %HTTP\_CODE%



if "%HTTP\_CODE%"=="200" (

&#x20;   echo \[SUCCESS] SiteMonitor Lite: Application is UP and healthy.

&#x20;   exit 0

) else (

&#x20;   echo \[CRITICAL] SiteMonitor Lite: Application is DOWN!

&#x20;   exit 1

)



8\. Click Save.





Step 7: Automated Testing



1\. Click New Item, create a new Freestyle project, and name it: Selenium-Automation-Suite

2\. Git Integration Setup:Scroll down to Source Code Management and change the selection from None to Git.

In the Repository URL field, paste your team's GitHub repository HTTPS link: https://github.com/MatanMagen/mta-devops-final

3.In the Branches to build field, ensure the branch string matches your main branch name (e.g., change \*/master to \*/main if applicable).

4\. Scroll down to Build Steps, click Add build step, and select Execute Windows batch command.

5\. Paste the following command: call "%AppData%\\npm\\selenium-side-runner.cmd" -c "browserName=MicrosoftEdge ms:edgeOptions.args=\[headless,no-sandbox]" "./mta\_devops\_tests.side"

6\. Click Save.

