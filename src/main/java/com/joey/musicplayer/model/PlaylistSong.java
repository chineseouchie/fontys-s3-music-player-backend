package com.joey.musicplayer.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "playlistSong")
public @Data class PlaylistSong {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int playlistSongId;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="accountId")

    private Account account;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="playlistId")
    private Playlist playlist;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="songId")
    private Song song;


}
