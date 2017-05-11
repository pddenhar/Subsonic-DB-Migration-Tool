package com.fewstreet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Peter on 5/10/2017.
 */
public class Album {
    public static final String COLUMNS = "id, path, name, artist, song_count, duration_seconds, cover_art_path, " +
            "year, genre, play_count, last_played, comment, created, last_scanned, present, folder_id";

    private int id;
    private String path;
    private String name;
    private String artist;
    private int songCount;
    private int durationSeconds;
    private String coverArtPath;
    private Integer year;
    private String genre;
    private int playCount;
    private Date lastPlayed;
    private String comment;
    private Date created;
    private Date lastScanned;
    private boolean present;
    private Integer folderId;

    public Album() {
    }

    public Album(int id, String path, String name, String artist, int songCount, int durationSeconds, String coverArtPath,
                 Integer year, String genre, int playCount, Date lastPlayed, String comment, Date created, Date lastScanned,
                 boolean present, Integer folderId) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.artist = artist;
        this.songCount = songCount;
        this.durationSeconds = durationSeconds;
        this.coverArtPath = coverArtPath;
        this.year = year;
        this.genre = genre;
        this.playCount = playCount;
        this.lastPlayed = lastPlayed;
        this.comment = comment;
        this.created = created;
        this.lastScanned = lastScanned;
        this.folderId = folderId;
        this.present = present;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getSongCount() {
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public String getCoverArtPath() {
        return coverArtPath;
    }

    public void setCoverArtPath(String coverArtPath) {
        this.coverArtPath = coverArtPath;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public Date getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(Date lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastScanned() {
        return lastScanned;
    }

    public void setLastScanned(Date lastScanned) {
        this.lastScanned = lastScanned;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public Integer getFolderId() {
        return folderId;
    }
    public static Album mapRow(ResultSet rs) throws SQLException {
        return new Album(
            rs.getInt(1),
            rs.getString(2),
            rs.getString(3),
            rs.getString(4),
            rs.getInt(5),
            rs.getInt(6),
            rs.getString(7),
            rs.getInt(8) == 0 ? null : rs.getInt(8),
            rs.getString(9),
            rs.getInt(10),
            rs.getTimestamp(11),
            rs.getString(12),
            rs.getTimestamp(13),
            rs.getTimestamp(14),
            rs.getBoolean(15),
            rs.getInt(16)
        );
    }

}
