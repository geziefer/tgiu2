# tgiu2 - wildfly branch
The Game Is Up v2
https://github.com/geziefer/tgiu2.git

# Software
Git
Maven 3
JDK 1.8
WildFly 10.0.0.Final
PostgresQL 9.3
Eclipse Neon Java EE edition

# Config Wildfly
copy modules and configuration folder from wildfly to WildFly installation

# Config PostgresQL
create schema "tgiu"
create user "tgiu" with password "tgiu" as owner of schema "tgiu"
run src/main/resources/db/createDB.sql within PostgresQL
run src/main/resources/import.sql within PostgresQL

# Project
add JDK 1.8 as installed JRE
add WildFly as Server
import existing Maven project
	
# Build on command line
mvn clean install

# Deploy on command line
copy to WildFly's standalone/deployments

# Run WebApp
localhost:8080/tgiu2/
