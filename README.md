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

1. Make sure your new database server is up and running
2. Follow the instructions on http://www.subsonic.org/pages/database.jsp to connect your existing 
Subsonic installation to the empty database. 
3. Run Subsonic at least once to initialize the new database with empty tables.
4. Open the project in IntelliJ, open Main.java and change the `OLD_DB_URI`, `NEW_DB_URI`
   and the two passwords.
   * OLD_DB_URI should point to your existing HSQLDB database (/var/subsonic/db/subsonic on Linux).
5. Run the project in IntelliJ and the data will be migrated from your old database to the new one,
leaving your old database unchanged.

**THIS TOOL DOES NOT CREATE DATABASES OR TABLES.** It only copies data from the old HSQLDB (or any other JDBC subsonic database) 
to an empty new one (testing with MySQL). It merely populates the empty tables in the new database.

Make sure your mysql server is accessible to the machine you run this tool from
(either by running this tool on your Subsonic server localhost or setting your MySQL bind port to 0.0.0.0 instead of 127.0.0.1)

