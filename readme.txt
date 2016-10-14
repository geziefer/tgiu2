# tgiu2
The Game Is Up v2
https://github.com/geziefer/tgiu2.git

# Software
Git
Maven 3
JDK 1.8
Tomcat 7
optional: MySQL 5.3
optional: Eclipse Neon Java EE edition

# Config Tomcat
optional: if maven should be able to deploy to Tomcat, edit tomcat's conf/tomcat-users.xml:
	<role rolename="manager-gui"/>
	<role rolename="manager-script"/>
	<user username="admin" password="admin" roles="manager-gui,manager-script" />

# Config MySQL
optional: if MySQL should be used instead of In-Memory-DB:
	create schema "tgiu"
	create user "tgiu" with password "tgiu" with all rights on schema "tgiu"
	run src/main/resources/db/createDB.sql within MySQL
	run src/main/resources/import.sql within MySQL

# Project
optional: if Eclipse should be used:
	add JDK 1.8 as installed JRE
	add Tomcat as Server
	import existing Maven project
	
# Build on command line
mvn clean install

# Deploy on command line
mvn clean tomcat7:deploy

# Profiles
append "-P prod" (default) to mvn command to build with MySQL DB 
append "-P dev" to mvn command to build with In-Memory-DB 

# Run WebApp
localhost:8080/tgiu2/
