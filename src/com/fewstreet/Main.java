package com.fewstreet;

import java.sql.*;


public class Main {

    public static void main(String[] args) {
        try {
            Connection hsqldbc = DriverManager.getConnection("jdbc:hsqldb:file:C:\\Users\\Peter\\Downloads\\db\\subsonic", "SA", "");
            Connection mysqlc = DriverManager.getConnection("jdbc:mysql://192.168.1.221/subsonic_migrate?useJDBCCompliantTimezoneShift=true&serverTimezone=UTC&useSSL=false", "subsonic", "xjXtvllUcRN664pP");
            mysqlc.setAutoCommit(false);

            try { migrateVersion(hsqldbc, mysqlc); } catch (SQLException e) {System.out.println("Versions Failed");}
            try { migrateRoles(hsqldbc, mysqlc); } catch (SQLException e) {System.out.println("Roles Failed");}
            try { migrateUsers(hsqldbc, mysqlc); } catch (SQLException e) {System.out.println("Users Failed");}
            try { migrateUserRole(hsqldbc, mysqlc); } catch (SQLException e) {System.out.println("User roles Failed");}
            try { migrateMediaFiles(hsqldbc, mysqlc); } catch (SQLException e) {System.out.println("Media files Failed");}
            try { migrateAlbums(hsqldbc, mysqlc); } catch (SQLException e) {System.out.println("Albums Failed");}
            try { migrateArtists(hsqldbc, mysqlc); } catch (SQLException e) {System.out.println("Artists roles Failed");}
            try { migrateMusicFolders(hsqldbc, mysqlc); } catch (SQLException e) {System.out.println("Music Folders Failed");}
            try { migrateMusicFolderUsers(hsqldbc, mysqlc); } catch (SQLException e) {System.out.println("Music Folder Users Failed");}

            mysqlc.commit();
            mysqlc.close();
            hsqldbc.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void migrateVersion(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT version from VERSION");
        while(r.next()){
            insert(new_db_conn, "insert into VERSION ( version ) values (?)",
                    r.getInt(1));
        }
        System.out.println("Inserted all versions");
    }

    private static void migrateRoles(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT id, name from ROLE");
        while(r.next()){
            insert(new_db_conn, "insert into ROLE ( id, name ) values (?,?)",
                    r.getInt(1), r.getString(2));
        }
        System.out.println("Inserted all roles");
    }


    private static void migrateUsers(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT "+User.COLUMNS +" from USER");
        while(r.next()){
            User user = User.mapRow(r);
            insert(new_db_conn, "insert into USER (" + User.COLUMNS + ") values (" + questionMarks(User.COLUMNS) + ")",
                    user.getUsername(), user.getPassword(), user.getEmail(), user.isLdapAuthenticated(),
                    user.getBytesStreamed(), user.getBytesDownloaded(), user.getBytesUploaded());
        }
        System.out.println("Inserted all users");
    }

    private static void migrateUserRole(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT username, role_id from USER_ROLE");
        while(r.next()){
            insert(new_db_conn, "insert into USER_ROLE ( username, role_id ) values (?,?)",
                    r.getString(1), r.getInt(2));
        }
        System.out.println("Inserted all user roles");
    }

    private static void migrateMusicFolders(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT "+MusicFolder.COLUMNS +" from MUSIC_FOLDER");
        while(r.next()){
            MusicFolder musicFolder = MusicFolder.mapRow(r);
            insert(new_db_conn, "insert into MUSIC_FOLDER (" + MusicFolder.COLUMNS + ") values (" + questionMarks(MusicFolder.COLUMNS) + ")",
                    musicFolder.getId(), musicFolder.getPath(), musicFolder.getName(), musicFolder.isEnabled(), musicFolder.getChanged());
        }
        System.out.println("Inserted all music folders");
    }

    private static void migrateMusicFolderUsers(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT music_folder_id, username from MUSIC_FOLDER_USER");
        while(r.next()){
            insert(new_db_conn, "insert into MUSIC_FOLDER_USER ( music_folder_id, username ) values (?,?)",
                    r.getInt(1), r.getString(2));
        }
        System.out.println("Inserted all music folder users");
    }


    private static void migrateAlbums(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT "+Album.COLUMNS +" from ALBUM ORDER BY id ASC");
        while(r.next()){
            Album album = Album.mapRow(r);
            insert(new_db_conn, "insert into ALBUM (" + Album.COLUMNS + ") values (" + questionMarks(Album.COLUMNS) + ")",
                    album.getId(), album.getPath(),
                    album.getName(), album.getArtist(), album.getSongCount(), album.getDurationSeconds(),
                    album.getCoverArtPath(), album.getYear(), album.getGenre(), album.getPlayCount(), album.getLastPlayed(),
                    album.getComment(), album.getCreated(), album.getLastScanned(), album.isPresent(), album.getFolderId());
        }
        System.out.println("Inserted all albums");
    }

    private static void migrateArtists(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT "+Artist.COLUMNS +" from ARTIST ORDER BY id ASC");
        while(r.next()){
            Artist artist = Artist.mapRow(r);
            insert(new_db_conn, "insert into ARTIST (" + Artist.COLUMNS + ") values (" + questionMarks(Artist.COLUMNS) + ")",
                    artist.getId(), artist.getName(), artist.getCoverArtPath(), artist.getAlbumCount(),
                    artist.getLastScanned(), artist.isPresent(), artist.getFolderId());
        }
        System.out.println("Inserted all artists");
    }

    private static void migrateMediaFiles(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT "+MediaFile.INSERT_COLUMNS+" from MEDIA_FILE ORDER BY id ASC");
        while(r.next()){
            MediaFile file = MediaFile.mapRow(r);
            insert(new_db_conn, "insert into media_file (" + MediaFile.INSERT_COLUMNS + ") values (" + questionMarks(MediaFile.INSERT_COLUMNS) + ")",
                    file.getId(), file.getPath(), file.getFolder(), file.getMediaType().name(), file.getFormat(), file.getTitle(), file.getAlbumName(), file.getArtist(),
                    file.getAlbumArtist(), file.getDiscNumber(), file.getTrackNumber(), file.getYear(), file.getGenre(), file.getBitRate(),
                    file.isVariableBitRate(), file.getDurationSeconds(), file.getFileSize(), file.getWidth(), file.getHeight(),
                    file.getCoverArtPath(), file.getParentPath(), file.getPlayCount(), file.getLastPlayed(), file.getComment(),
                    file.getCreated(), file.getChanged(), file.getLastScanned(),
                    file.getChildrenLastUpdated(), file.isPresent(), MediaFile.VERSION);
        }
        System.out.println("Inserted all media files");
    }

    private static String questionMarks(String columns) {
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

