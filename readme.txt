# tgiu2
The Game Is Up v2

# Software
- Maven 3
- JDK 1.8
- Tomcat 7
- MySQL 5.3

# Config Tomcat
- copy DB driver mysql-connector to tomcat's lib
- edit context.xml:
	<Resource name="jdbc/tgiuDS" auth="Container" type="javax.sql.DataSource"
		driverClassName="org.hsqldb.jdbcDriver" url="jdbc:hsqldb:mem:testapp"
		username="sa" password="" maxActive="20" maxIdle="10" maxWait="-1" />

# Config MySQL
- create schema / user tgiu
- run createDB.sql
