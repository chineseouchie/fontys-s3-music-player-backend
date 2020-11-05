package com.joey.musicplayer.controller;

import com.joey.musicplayer.helper.SongHelper;
import com.joey.musicplayer.model.Account;
import com.joey.musicplayer.model.Song;
import com.joey.musicplayer.repository.AccountRepository;
import com.joey.musicplayer.repository.SongRepository;
import com.joey.musicplayer.util.JwtUtil;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("song")
@CrossOrigin(origins = {"http://localhost", "http://localhost:3000"})
public class SongController {
    @Autowired
    SongRepository songRepository;
    @Autowired
    AccountRepository accountRepository;
    SongHelper songHelper = new SongHelper();

    String path = System.getProperty("user.dir");

    @GetMapping()
    public String getAllSongs(@RequestParam int userId) {
        List<Song> songs = songRepository.findSongsByAccount_AccountId(userId);
        List<HashMap<String, Object>> SongData = new ArrayList<>();

        // Create API data
        for (Song item : songs) {
            SongData.add(songHelper.CreateSongData(item));
        }

        // Create API response data
        HashMap<String, Object> responseMessage = new HashMap<>();
        responseMessage.put("message", "Songs Loaded");
        responseMessage.put("songs", SongData);

        return new JSONObject(responseMessage).toJSONString();
    }

    @GetMapping("/play")
    public String playSong(@RequestParam int songId/*, @RequestHeader(name="Authorization") String token*/) throws Exception {
        Song song = songRepository.findSongBySongId(songId);
        String fileName = song.getFileName();
        String folder = song.getPath();

        // Check if user is valid
        JwtUtil jwtUtil = new JwtUtil();
//        String jwt = token.substring(7);

//        String username = jwtUtil.extractUsername(jwt);
//        String accountId = jwtUtil.extractAccountId(jwt);


        File file = new File(this.path + folder + fileName);

        // Create API response data
        HashMap<String, Object> responseMessage = new HashMap<>();
        responseMessage.put("message", "Playing song");
        responseMessage.put("songData", Arrays.toString(FileUtils.readFileToByteArray(file)));
        return new JSONObject(responseMessage).toJSONString();
    }

    @PostMapping("/upload")
    public String  UploadSong(@RequestParam("files") MultipartFile files, int userId, String name, String artistName) throws IOException, UnsupportedAudioFileException {
        String fileName = songHelper.GenerateFileName(files, userId);
        String folder = "\\upload\\";
        Account account = accountRepository.findAccountByAccountId(userId);
        Date date = Calendar.getInstance().getTime();

        // Save audio to the upload folder
        File file = songHelper.SaveAudioFile(files, this.path, folder, fileName);

        // Get audio duration
        String duration = songHelper.GetDurationFromAudio(file);

        Song song = new Song(name, artistName, fileName, folder, account, duration, date);

        songRepository.save(song);

        // Create API response data
        HashMap<String, Object> responseMessage = new HashMap<>();
        responseMessage.put("message", "Song saved");
        return new JSONObject(responseMessage).toJSONString();
    }

    private String ApiMessage(Arrays data) {
        HashMap<String, Object> responseMessage = new HashMap<>();

        return new JSONObject(responseMessage).toJSONString();
    }
}
