package com.fewstreet;

import java.sql.*;

import static com.fewstreet.MediaFile.VERSION;

public class Main {

    public static void main(String[] args) {
        MediaFileMapper m = new MediaFileMapper();
        try {
            Connection hsqldbc = DriverManager.getConnection("jdbc:hsqldb:file:C:\\Users\\Peter\\Downloads\\db\\subsonic", "SA", "");
            Connection mysqlc = DriverManager.getConnection("jdbc:mysql://192.168.1.221/subsonic_migrate?useJDBCCompliantTimezoneShift=true&serverTimezone=UTC&useSSL=false", "subsonic", "xjXtvllUcRN664pP");
            ResultSet r = hsqldbc.createStatement().executeQuery("SELECT * from MEDIA_FILE ORDER BY id ASC");
            int i = 0;
            mysqlc.setAutoCommit(false);
            while(r.next()){
                MediaFile file = m.mapRow(r);
                //System.out.println(mf.id +" "+mf.artist);
                insert(mysqlc, "insert into media_file (" + MediaFile.INSERT_COLUMNS + ") values (" + questionMarks(MediaFile.INSERT_COLUMNS) + ")",
                        file.getId(), file.getPath(), file.getFolder(), file.getMediaType().name(), file.getFormat(), file.getTitle(), file.getAlbumName(), file.getArtist(),
                        file.getAlbumArtist(), file.getDiscNumber(), file.getTrackNumber(), file.getYear(), file.getGenre(), file.getBitRate(),
                        file.isVariableBitRate(), file.getDurationSeconds(), file.getFileSize(), file.getWidth(), file.getHeight(),
                        file.getCoverArtPath(), file.getParentPath(), file.getPlayCount(), file.getLastPlayed(), file.getComment(),
                        file.getCreated(), file.getChanged(), file.getLastScanned(),
                        file.getChildrenLastUpdated(), file.isPresent(), VERSION);
                i++;
            }
            System.out.println("Inserted all media files");
            mysqlc.commit();
            mysqlc.close();
            hsqldbc.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected static String questionMarks(String columns) {
        int count = columns.split(", ").length;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append('?');
            if (i < count - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    private static int insert(Connection conn, String query, Object ... args) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(query);
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i+1, args[i]);
        }
        int result = stmt.executeUpdate();
        stmt.close();
        return result;
    }
}

