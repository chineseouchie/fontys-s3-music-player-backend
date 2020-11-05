package com.joey.musicplayer.repository;

import com.joey.musicplayer.model.Song;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, Integer> {
    Song findSongBySongId(int id);
    List<Song> findSongsByAccount_AccountId(int accountId);
}
