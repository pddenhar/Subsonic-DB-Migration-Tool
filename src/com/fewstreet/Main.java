package com.fewstreet;

import java.sql.*;


public class Main {
    private static String OLD_DB_URI = "jdbc:hsqldb:file:C:\\Users\\Peter\\Downloads\\db\\subsonic";
    private static String NEW_DB_URI = "jdbc:mysql://192.168.1.221/subsonic";
    private static String NEW_DB_USER = "subsonic";
    private static String NEW_DB_PASS = "xjXtvllUcRN664pP";

    public static void main(String[] args) {
        try {
            Connection old_db_connection = DriverManager.getConnection(OLD_DB_URI, "SA", "");
            Connection new_db_connection = DriverManager.getConnection(NEW_DB_URI+"?useJDBCCompliantTimezoneShift=true&serverTimezone=UTC&useSSL=false&allowMultiQueries=true", NEW_DB_USER, NEW_DB_PASS);
            new_db_connection.setAutoCommit(false);

            truncateDatShit(new_db_connection);

            try { migrateVersion(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("Versions Failed: " + e);}
            try { migrateTableID(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("Table IDs Failed: " + e);}
            try { migrateSystemAvatars(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("System avatars failed: " + e);}
            try { migrateRoles(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("Roles Failed: " + e);}
            try { migrateUsers(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("Users Failed: " + e);}
            try { migrateUserRole(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("User roles Failed: " + e);}
            try { migrateUserSettings(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("User settings Failed: " + e);}
            try { migrateCustomAvatars(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("System avatars failed: " + e);}
            try { migrateMediaFiles(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("Media files Failed: " + e);}
            try { migrateGenres(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("Genres Failed: " + e);}
            try { migrateAlbums(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("Albums Failed: " + e);}
            try { migrateArtists(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("Artists roles Failed: " + e);}
            try { migrateMusicFolders(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("Music Folders Failed: " + e);}
            try { migrateMusicFolderUsers(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("Music Folder Users Failed: " + e);}
            try { migrateStarredMediaFile(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("Starred media file Failed: " + e);}
            try { migrateStarredAlbum(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("Starred album Failed: " + e);}
            try { migrateStarredArtist(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("Starred artist Failed: " + e);}
            try { migrateUserRating(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("User rating Failed: " + e);}
            try { migrateTranscoding(old_db_connection, new_db_connection); } catch (SQLException e) {System.out.println("Transcodings Failed: " + e);}

            new_db_connection.commit();
            new_db_connection.close();
            old_db_connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void migrateVersion(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        String TABLE_NAME = "VERSION";
        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT version from "+TABLE_NAME);
        while(r.next()){
            insert(new_db_conn, "insert into "+TABLE_NAME+" ( version ) values (?)",
                    r.getInt(1));
        }
        System.out.println("Inserted all versions");
    }

    private static void migrateTableID(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        String TABLE_NAME = "TABLE_ID";
        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT table_name,max_id from "+TABLE_NAME);
        while(r.next()){
            insert(new_db_conn, "insert into "+TABLE_NAME+" ( table_name,max_id ) values (?,?)",
                    r.getString(1), r.getInt(2));
        }
        System.out.println("Inserted all table ids");
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

    private static void migrateSystemAvatars(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        String TABLE_NAME = "SYSTEM_AVATAR";
        String columns = "id, name, created_date, mime_type, width, height, data";

        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT "+columns+" from "+TABLE_NAME);
        while(r.next()){
            insert(new_db_conn, "insert into "+TABLE_NAME+" ( "+columns+" ) values ("+questionMarks(columns)+")",
                    r.getInt(1), r.getString(2), r.getTimestamp(3), r.getString(4),
                    r.getInt(5), r.getInt(6), r.getBlob(7));
        }
        System.out.println("Inserted all system avatars");
    }

    private static void migrateCustomAvatars(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        String TABLE_NAME = "CUSTOM_AVATAR";
        String columns = "id, name, created_date, mime_type, width, height, data, username";

        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT "+columns+" from "+TABLE_NAME);
        while(r.next()){
            insert(new_db_conn, "insert into "+TABLE_NAME+" ( "+columns+" ) values ("+questionMarks(columns)+")",
                    r.getInt(1), r.getString(2), r.getTimestamp(3), r.getString(4),
                    r.getInt(5), r.getInt(6), r.getBlob(7), r.getString(8));
        }
        System.out.println("Inserted all custom avatars");
    }


    private static void migrateUserSettings(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        String TABLE_NAME = "USER_SETTINGS";
        String COLUMNS = "username, locale, theme_id, final_version_notification, beta_version_notification, "+
                "main_caption_cutoff, main_track_number, main_artist, main_album, main_genre, main_year, "+
                "main_bit_rate, main_duration, main_format, main_file_size, playlist_caption_cutoff, "+
                "playlist_track_number, playlist_artist, playlist_album, playlist_genre, playlist_year, " +
                "playlist_bit_rate, playlist_duration, playlist_format, playlist_file_size, last_fm_enabled, " +
                "last_fm_username, last_fm_password, transcode_scheme, show_now_playing, selected_music_folder_id, " +
                "party_mode_enabled, now_playing_allowed, web_player_default, avatar_scheme, system_avatar_id, " +
                "changed, show_chat, song_notification, show_artist_info, auto_hide_play_queue, view_as_list, " +
                "default_album_list, queue_following_songs, show_side_bar, " +
                "show_index_in_side_bar, preferred_video_bitrate";
        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT "+COLUMNS +" from "+TABLE_NAME);
        while(r.next()){
            insert(new_db_conn, "insert into " + TABLE_NAME + " (" + COLUMNS + ") values (" + questionMarks(COLUMNS) + ")",
                    r.getString(1), r.getString(2), r.getString(3),
                    r.getInt(4), r.getInt(5), r.getInt(6),
                    r.getInt(7), r.getInt(8), r.getInt(9),
                    r.getInt(10), r.getInt(11), r.getInt(12),
                    r.getInt(13), r.getInt(14), r.getInt(15),
                    r.getInt(16), r.getInt(17), r.getInt(18),
                    r.getInt(19), r.getInt(20), r.getInt(21),
                    r.getInt(22), r.getInt(23), r.getInt(24),
                    r.getInt(25), r.getInt(26), r.getString(27),
                    r.getString(28), r.getString(29), r.getInt(30),
                    r.getInt(31), r.getInt(32), r.getInt(33),
                    r.getInt(34), r.getString(35), r.getInt(36),
                    r.getTimestamp(37), r.getInt(38), r.getInt(39),
                    r.getInt(40), r.getInt(41), r.getInt(42),
                    r.getString(43), r.getInt(44), r.getInt(45),
                    r.getInt(46), r.getInt(47));
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

    private static void migrateGenres(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        String TABLE_NAME = "GENRE";
        String columns = "name, song_count, album_count";

        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT "+columns+" from "+TABLE_NAME);
        while(r.next()){
            insert(new_db_conn, "insert into "+TABLE_NAME+" ( "+columns+" ) values ("+questionMarks(columns)+")",
                    r.getString(1), r.getInt(2), r.getInt(3));
        }
        System.out.println("Inserted all genres");
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

    private static void migrateStarredMediaFile(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        String TABLE_NAME = "STARRED_MEDIA_FILE";
        String columns = "id, media_file_id, username, created";

        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT "+columns+" from "+TABLE_NAME);
        while(r.next()){
            insert(new_db_conn, "insert into "+TABLE_NAME+" ( "+columns+" ) values ("+questionMarks(columns)+")",
                    r.getInt(1), r.getInt(2), r.getString(3), r.getTimestamp(4));
        }
        System.out.println("Inserted all starred media files");
    }

    private static void migrateStarredAlbum(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        String TABLE_NAME = "STARRED_ALBUM";
        String columns = "id, album_id, username, created";

        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT "+columns+" from "+TABLE_NAME);
        while(r.next()){
            insert(new_db_conn, "insert into "+TABLE_NAME+" ( "+columns+" ) values ("+questionMarks(columns)+")",
                    r.getInt(1), r.getInt(2), r.getString(3), r.getTimestamp(4));
        }
        System.out.println("Inserted all starred albums");
    }

    private static void migrateStarredArtist(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        String TABLE_NAME = "STARRED_ARTIST";
        String columns = "id, artist_id, username, created";

        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT "+columns+" from "+TABLE_NAME);
        while(r.next()){
            insert(new_db_conn, "insert into "+TABLE_NAME+" ( "+columns+" ) values ("+questionMarks(columns)+")",
                    r.getInt(1), r.getInt(2), r.getString(3), r.getTimestamp(4));
        }
        System.out.println("Inserted all starred artists");
    }

    private static void migrateUserRating(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        String TABLE_NAME = "USER_RATING";
        String columns = "username, path, rating";

        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT "+columns+" from "+TABLE_NAME);
        while(r.next()){
            insert(new_db_conn, "insert into "+TABLE_NAME+" ( "+columns+" ) values ("+questionMarks(columns)+")",
                    r.getString(1), r.getString(2), r.getInt(3));
        }
        System.out.println("Inserted all user ratings");
    }

    private static void migrateTranscoding(Connection old_db_conn, Connection new_db_conn) throws SQLException {
        String TABLE_NAME = "TRANSCODING2";
        String columns = "id, name, source_formats, target_format, step1, step2, step3, default_active";

        ResultSet r = old_db_conn.createStatement().executeQuery("SELECT "+columns+" from "+TABLE_NAME);
        while(r.next()){
            insert(new_db_conn, "insert into "+TABLE_NAME+" ( "+columns+" ) values ("+questionMarks(columns)+")",
                    r.getInt(1), r.getString(2), r.getString(3), r.getString(4),
                    r.getString(5), r.getString(6), r.getString(7), r.getInt(8));
        }
        System.out.println("Inserted all transcodings");
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
    
    private static boolean truncateDatShit(Connection newconn) throws SQLException {
        String query = "SET FOREIGN_KEY_CHECKS=0; " +
                "TRUNCATE TABLE album; " +
                "TRUNCATE TABLE artist; " +
                "TRUNCATE TABLE bookmark; " +
                "TRUNCATE TABLE custom_avatar; " +
                "TRUNCATE TABLE genre; " +
                "TRUNCATE TABLE internet_radio; " +
                "TRUNCATE TABLE media_file; " +
                "TRUNCATE TABLE music_file_info; " +
                "TRUNCATE TABLE music_folder; " +
                "TRUNCATE TABLE music_folder_user; " +
                "TRUNCATE TABLE play_queue; " +
                "TRUNCATE TABLE play_queue_file; " +
                "TRUNCATE TABLE player; " +
                "TRUNCATE TABLE player_transcoding2; " +
                "TRUNCATE TABLE playlist; " +
                "TRUNCATE TABLE playlist_file; " +
                "TRUNCATE TABLE playlist_user; " +
                "TRUNCATE TABLE podcast_channel; " +
                "TRUNCATE TABLE podcast_episode; " +
                "TRUNCATE TABLE role; " +
                "TRUNCATE TABLE share; " +
                "TRUNCATE TABLE share_file; " +
                "TRUNCATE TABLE starred_album; " +
                "TRUNCATE TABLE starred_artist; " +
                "TRUNCATE TABLE starred_media_file; " +
                "TRUNCATE TABLE system_avatar; " +
                "TRUNCATE TABLE table_id; " +
                "TRUNCATE TABLE transcoding2; " +
                "TRUNCATE TABLE user; " +
                "TRUNCATE TABLE user_rating; " +
                "TRUNCATE TABLE user_role; " +
                "TRUNCATE TABLE user_settings; " +
                "TRUNCATE TABLE version; " +
                "TRUNCATE TABLE video_conversion; " +
                "SET FOREIGN_KEY_CHECKS=1; ";
        boolean result = newconn.createStatement().execute(query);
        System.out.println("Truncated all tables");
        return result;
    }
}

