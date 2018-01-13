<<<<<<< HEAD
create database tgiu;
create user 'tgiu' identified by 'tgiu';
grant all privileges on tgiu.* to 'tgiu';
=======
create user tgiu with login unencrypted password 'tgiu';
create database tgiu owner tgiu;
>>>>>>> wildfly
