package com.joey.musicplayer.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "song")
public @Data class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int songId;
    private String name;
    private String artistName;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private Account account;
    private String fileName;
    private String path;
    private String duration;
    private Date date;

    public Song(){}

    public Song(String name, String artistName) {
        this.name = name;
        this.artistName = artistName;
    }

    public Song(String name, String artistName, String fileName, String path, Account account, String duration, Date date) {
        this.name = name;
        this.artistName = artistName;
        this.fileName = fileName;
        this.path = path;
        this.account = account;
        this.duration = duration;
        this.date = date;
    }
}
