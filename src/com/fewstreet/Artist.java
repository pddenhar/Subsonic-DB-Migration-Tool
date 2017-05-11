package com.fewstreet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Peter on 5/10/2017.
 */
public class Artist {

    public static final String COLUMNS = "id, name, cover_art_path, album_count, last_scanned, present, folder_id";

    private int id;
    private String name;
    private String coverArtPath;
    private int albumCount;
    private Date lastScanned;
    private boolean present;
    private Integer folderId;

    public Artist() {
    }

    public Artist(int id, String name, String coverArtPath, int albumCount, Date lastScanned, boolean present, Integer folderId) {
        this.id = id;
        this.name = name;
        this.coverArtPath = coverArtPath;
        this.albumCount = albumCount;
        this.lastScanned = lastScanned;
        this.present = present;
        this.folderId = folderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverArtPath() {
        return coverArtPath;
    }

    public void setCoverArtPath(String coverArtPath) {
        this.coverArtPath = coverArtPath;
    }

    public int getAlbumCount() {
        return albumCount;
    }

    public void setAlbumCount(int albumCount) {
        this.albumCount = albumCount;
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

    public static Artist mapRow(ResultSet rs) throws SQLException {
        return new Artist(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getTimestamp(5),
                rs.getBoolean(6),
                rs.getInt(7));
    }
}
