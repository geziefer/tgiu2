# tgiu2 - wildfly branch
The Game Is Up v2
https://github.com/geziefer/tgiu2.git

# Software
Git
Maven 3
JDK 1.8
WildFly 10
PostgresQL 9.5 (with pgAdmin 1.X)
Eclipse Neon Java EE edition

# Config Wildfly
copy modules and configuration folder from wildfly to WildFly installation

# Config PostgreSQL
run src/main/resources/db/createUser.sql as postgres user
run src/main/resources/db/createDB.sql as tgiu user
run src/main/resources/import.sql as tgiu user

# Project
add JDK 1.8 as installed JRE
add WildFly as Server (may require JBoss AS tools from marketplace)
import existing Maven project
	
# Build on command line
mvn clean install

# Deploy on command line
copy to WildFly's standalone/deployments

# Run WebApp
localhost:8080/tgiu2/
