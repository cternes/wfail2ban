wfail2ban
=========================================

wfail2ban is an attempt to provide a nearly as possible 1:1 fail2ban clone for windows. It's not yet feature complete and currently does not provide all functionality known from fail2ban. 
However, it is already possible to parse log files, define filters and ban hosts with the windows firewall. 

Limitations:
* Currently only tested with Windows 7
* Please note that you need to run a command line with __administrator privileges__ in order to let wfail2ban configure the windows firewall

Compile from source with maven
-------------------------

To compile the project from source and create a runnable jar file from it, simply type the following on the command line:  

    mvn clean compile package
	
This will given you a jar file called __wfail2ban.jar__ in the target folder.

Run the jar file
------------------------

You can now run the jar file by typing the following on a command line:

    java -jar wfail2ban.jar
	
Please note that you will need a __jail.conf__ in the same directory as the jar file and a filter folder with filter configurations. The configuration files are basically the same as 
the original fail2ban configuration files.

If you want to test wfail2ban without really banning hosts, just use the -test option:

    java -jar wfail2ban.jar -test
