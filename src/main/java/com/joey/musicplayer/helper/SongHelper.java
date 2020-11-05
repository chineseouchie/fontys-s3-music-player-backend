package com.joey.musicplayer.helper;

import com.joey.musicplayer.model.Song;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SongHelper {
    // Generate unique string for audio file
    public String GenerateFileName(MultipartFile files, int userId) {
        UUID uuid = UUID.randomUUID();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        String extensionType = FilenameUtils.getExtension(files.getOriginalFilename());

        return String.format("%s-%s-%s.%s", userId, currentDate, uuid,extensionType);
    }

    public String GetDurationFromAudio(File file) throws IOException, UnsupportedAudioFileException {
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
        if (fileFormat instanceof TAudioFileFormat) {
            Map<?, ?> properties = (fileFormat).properties();
            String key = "duration";
            Long microseconds = (Long) properties.get(key);
            int milli = (int) (microseconds / 1000);
            int sec = (milli / 1000) % 60;
            int min = (milli / 1000) / 60;
            return min + ":" + sec;
        } else {
            throw new UnsupportedAudioFileException();
        }
    }

    public File SaveAudioFile(MultipartFile files, String path, String folder, String fileName) throws IOException {
        File file = new File(path + folder + fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(files.getBytes());
        fileOutputStream.close();

        return file;
    }

    public HashMap<String, Object> CreateSongData(Song song) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("songId", song.getSongId());
        data.put("name", song.getName());
        data.put("artistName", song.getArtistName());
        data.put("accountId", song.getAccount().getAccountId());
        data.put("dateAdded", song.getDate().toString());
        data.put("duration", song.getDuration());

        return data;
    }
}
