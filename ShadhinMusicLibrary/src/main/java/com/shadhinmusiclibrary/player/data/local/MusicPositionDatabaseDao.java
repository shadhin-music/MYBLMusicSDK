package com.shadhinmusiclibrary.player.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.gm.shadhin.player.data.model.MusicPosition;

import java.util.List;

@Dao
public interface MusicPositionDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MusicPosition item);

    @Query("UPDATE musicposition SET position  = (CASE WHEN duration <= :position THEN 0 ELSE :position END) WHERE mediaId = :mediaId")
    void update(String mediaId, long position);

    @Query("SELECT * FROM musicposition WHERE mediaId = :mediaId LIMIT 1")
    MusicPosition findMyMediaId(String mediaId);

}
