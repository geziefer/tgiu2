# tgiu2
The Game Is Up v2
https://github.com/geziefer/tgiu2.git

# Software
Git
Maven 3
JDK 11
WildFly 21
MariaDB 10.5.x (with HeidiSQL)
Eclipse Oxygen Java EE edition or Netbeans 12

# Config Wildfly
copy standalone folder in folder wildfly to WildFly installation

# Config MariaDB
run src/main/resources/db/createUser.sql as root in HeidiSQL
run src/main/resources/db/createDB.sql as tgiu in HeidiSQL
run src/main/resources/import.sql as tgiu in HeidiSQL

# Config Eclipse
add JDK 11 as installed JRE
add WildFly as Server (may require JBoss AS tools from marketplace)
import existing Maven project
	
# Build on command line
mvn clean install

# Maven Profiles
append "-P prod" (default) to mvn command to build with MariaDB 
append "-P dev" to mvn command to build with In-Memory-DB 

# Deploy on command line
copy to WildFly's standalone/deployments

# Run WebApp
localhost:8080/tgiu2/
