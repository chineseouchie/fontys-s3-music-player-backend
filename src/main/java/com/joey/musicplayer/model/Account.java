package com.joey.musicplayer.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "account")
public @Data class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int accountId;
    @Column(unique = true)
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String salt;
//
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "songId")
//    private List<Song> songs;
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "playlistId")
//    private List<Playlist> playlists;

    public Account() {
    }

    public Account(String username, String password, String salt) {
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    public Account(String username, String firstname, String lastname, String password, String email, String salt) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.salt = salt;
    }
}
