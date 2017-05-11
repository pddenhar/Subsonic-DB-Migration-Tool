package com.fewstreet;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Peter on 5/10/2017.
 */
public class MusicFolder {
    public static final String COLUMNS = "id, path, name, enabled, changed";


    private Integer id;
    private String path;
    private String name;
    private boolean isEnabled;
    private Date changed;

    /**
     * Creates a new music folder.
     *
     * @param id      The system-generated ID.
     * @param path    The path of the music folder.
     * @param name    The user-defined name.
     * @param enabled Whether the folder is enabled.
     * @param changed When the corresponding database entry was last changed.
     */
    public MusicFolder(Integer id, String path, String name, boolean enabled, Date changed) {
        this.id = id;
        this.path = path;
        this.name = name;
        isEnabled = enabled;
        this.changed = changed;
    }

    /**
     * Creates a new music folder.
     *
     * @param path    The path of the music folder.
     * @param name    The user-defined name.
     * @param enabled Whether the folder is enabled.
     * @param changed When the corresponding database entry was last changed.
     */
    public MusicFolder(String path, String name, boolean enabled, Date changed) {
        this(null, path, name, enabled, changed);
    }

    /**
     * Returns the system-generated ID.
     *
     * @return The system-generated ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Returns the path of the music folder.
     *
     * @return The path of the music folder.
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the path of the music folder.
     *
     * @param path The path of the music folder.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Returns the user-defined name.
     *
     * @return The user-defined name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user-defined name.
     *
     * @param name The user-defined name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns whether the folder is enabled.
     *
     * @return Whether the folder is enabled.
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * Sets whether the folder is enabled.
     *
     * @param enabled Whether the folder is enabled.
     */
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    /**
     * Returns when the corresponding database entry was last changed.
     *
     * @return When the corresponding database entry was last changed.
     */
    public Date getChanged() {
        return changed;
    }

    /**
     * Sets when the corresponding database entry was last changed.
     *
     * @param changed When the corresponding database entry was last changed.
     */
    public void setChanged(Date changed) {
        this.changed = changed;
    }

    public static MusicFolder mapRow(ResultSet rs) throws SQLException {
        return new MusicFolder(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getTimestamp(5));
    }
}
