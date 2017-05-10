package com.fewstreet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        MediaFileMapper m = new MediaFileMapper();
        try {
            Connection hsqldbc = DriverManager.getConnection("jdbc:hsqldb:file:/tmp/db/subsonic", "SA", "");
            //Connection mysqlc = DriverManager.getConnection("jdbc:mysql://localhost/subsonic", "subsonic", "");
            ResultSet r = hsqldbc.createStatement().executeQuery("SELECT * from MEDIA_FILE ORDER BY id ASC LIMIT 2000");
            int i = 0;
            while(r.next() && i < 700){
                MediaFile mf = m.mapRow(r);
                System.out.println(mf.id +" "+mf.artist);
                i++;
            }
            hsqldbc.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

