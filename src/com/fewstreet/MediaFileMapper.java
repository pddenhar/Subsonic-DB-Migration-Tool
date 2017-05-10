package com.fewstreet;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by peter on 5/10/17.
 */
public class MediaFileMapper {
    public MediaFile mapRow(ResultSet rs) throws SQLException {
        return new MediaFile(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                MediaFile.MediaType.valueOf(rs.getString(4)),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getInt(10) == 0 ? null : rs.getInt(10),
                rs.getInt(11) == 0 ? null : rs.getInt(11),
                rs.getInt(12) == 0 ? null : rs.getInt(12),
                rs.getString(13),
                rs.getInt(14) == 0 ? null : rs.getInt(14),
                rs.getBoolean(15),
                rs.getInt(16) == 0 ? null : rs.getInt(16),
                rs.getLong(17) == 0 ? null : rs.getLong(17),
                rs.getInt(18) == 0 ? null : rs.getInt(18),
                rs.getInt(19) == 0 ? null : rs.getInt(19),
                rs.getString(20),
                rs.getString(21),
                rs.getInt(22),
                rs.getTimestamp(23),
                rs.getString(24),
                rs.getTimestamp(25),
                rs.getTimestamp(26),
                rs.getTimestamp(27),
                rs.getTimestamp(28),
                rs.getBoolean(29),
                rs.getInt(30));
    }
}