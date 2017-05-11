Java tool to migrate a subsonic database (currently 6.1b1) from HSQLDB to MySQL.

Warnings
--------

It is expected that your are experienced enough with databases to go in
and tweak any minor missed items after using this tool.

THERE IS NO GUARANTEE THAT THIS TOOL WILL WORK WITH ANY OTHER VERSION / WILL NOT DESTROY YOUR DATABASE.

Usage
-----

The absolute first step is to set Subsonic up to connect to the new database and run it at least once, to
build the table structure. THIS TOOL DOES NOT CREATE DATABASES OR TABLES.
It merely populates the empty tables in the new database.