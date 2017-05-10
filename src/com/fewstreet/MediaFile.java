package com.fewstreet;

import java.util.Date;

/**
 * Created by peter on 5/10/17.
 */

public class MediaFile {

    public int id;
    public String path;
    public String folder;
    public MediaType mediaType;
    public String format;
    public String title;
    public String albumName;
    public String artist;
    public String albumArtist;
    public Integer discNumber;
    public Integer trackNumber;
    public Integer year;
    public String genre;
    public Integer bitRate;
    public boolean variableBitRate;
    public Integer durationSeconds;
    public Long fileSize;
    public Integer width;
    public Integer height;
    public String coverArtPath;
    public String parentPath;
    public int playCount;
    public Date lastPlayed;
    public String comment;
    public Date created;
    public Date changed;
    public Date lastScanned;
    public Date starredDate;
    public Date childrenLastUpdated;
    public boolean present;
    public int version;

    public MediaFile(int id, String path, String folder, MediaType mediaType, String format, String title,
                     String albumName, String artist, String albumArtist, Integer discNumber, Integer trackNumber, Integer year, String genre, Integer bitRate,
                     boolean variableBitRate, Integer durationSeconds, Long fileSize, Integer width, Integer height, String coverArtPath,
                     String parentPath, int playCount, Date lastPlayed, String comment, Date created, Date changed, Date lastScanned,
                     Date childrenLastUpdated, boolean present, int version) {
        this.id = id;
        this.path = path;
        this.folder = folder;
        this.mediaType = mediaType;
        this.format = format;
        this.title = title;
        this.albumName = albumName;
        this.artist = artist;
        this.albumArtist = albumArtist;
        this.discNumber = discNumber;
        this.trackNumber = trackNumber;
        this.year = year;
        this.genre = genre;
        this.bitRate = bitRate;
        this.variableBitRate = variableBitRate;
        this.durationSeconds = durationSeconds;
        this.fileSize = fileSize;
        this.width = width;
        this.height = height;
        this.coverArtPath = coverArtPath;
        this.parentPath = parentPath;
        this.playCount = playCount;
        this.lastPlayed = lastPlayed;
        this.comment = comment;
        this.created = created;
        this.changed = changed;
        this.lastScanned = lastScanned;
        this.childrenLastUpdated = childrenLastUpdated;
        this.present = present;
        this.version = version;
    }

    public MediaFile() {
    }

    public static enum MediaType {
        MUSIC,
        PODCAST,
        AUDIOBOOK,
        VIDEO,
        DIRECTORY,
        ALBUM
    }
}
