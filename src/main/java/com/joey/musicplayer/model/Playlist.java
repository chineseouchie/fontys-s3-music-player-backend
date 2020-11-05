package com.joey.musicplayer.model;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "playlist")
public @Data class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int playlistId;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accountId")
    private Account account;


    public boolean AddSong(Song song) {
        throw new UnsupportedOperationException();
    }

    public boolean RemoveSong(Song song) {
        throw new UnsupportedOperationException();
    }
}
