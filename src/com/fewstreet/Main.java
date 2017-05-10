package com.fewstreet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        MediaFileMapper m = new MediaFileMapper();
        try {
            Connection c = DriverManager.getConnection("jdbc:hsqldb:file:/tmp/db/subsonic", "SA", "");
            //c.createStatement().execute("CREATE TABLE MEDIA_FILE (name varchar(45))");
            //c.createStatement().execute("INSERT INTO MEDIA_FILE VALUES ('peter')");
            ResultSet r = c.createStatement().executeQuery("SELECT * from MEDIA_FILE LIMIT 50");
            while(r.next()){
                MediaFile mf = m.mapRow(r);
                System.out.println(mf.id +" "+mf.artist);
            }
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

