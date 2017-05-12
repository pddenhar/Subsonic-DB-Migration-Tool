Subsonic Database Migration Tool
=================================

Java tool to migrate a Subsonic database from HSQLDB to MySQL.

This tool is currently updated to migrate database version 6.1b1, and may not work on other versions.

Warnings
--------

It is expected that your are experienced enough with databases to go in
and tweak any minor missed items after using this tool.

THERE IS NO GUARANTEE THAT THIS TOOL WILL WORK WITH ANY OTHER VERSION / WILL NOT DESTROY YOUR DATABASE.

**Back up your original HSQLDB subsonic database before attempting migration.**

**Any data in the NEW database will be deleted when the tool copies data from the old database.**

Usage
-----

The absolute first step is to set Subsonic up to connect to the new database and run it at least once, to
build the table structure. 

**THIS TOOL DOES NOT CREATE DATABASES OR TABLES.** It only copies data from the old HSQLDB (or any other JDBC subsonic database) 
to an empty new one (testing with MySQL). It merely populates the empty tables in the new database.

The easiest way to run this tool is to open the project in IntelliJ, open Main.java and change the `OLD_DB_URI`, `NEW_DB_URI`
and the two passwords and run it. OLD_DB_URI should point to your existing HSQLDB database (/var/subsonic/db/subsonic on Linux). 
Make sure your mysql server is accessible to the machine you run this tool from
(either by running this tool on your Subsonic server localhost or setting your MySQL bind port to 0.0.0.0 instead of 127.0.0.1)