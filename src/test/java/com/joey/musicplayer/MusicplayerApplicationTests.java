package com.joey.musicplayer;

import com.joey.musicplayer.helper.SongHelper;
import com.joey.musicplayer.model.Account;
import com.joey.musicplayer.model.Song;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration()
class MusicplayerApplicationTests {
	String testDir = System.getProperty("user.dir") + "\\src\\test\\java\\com\\joey\\musicplayer\\testFiles\\";

	@Test
	public void testGetDurationFromAudio() throws IOException, UnsupportedAudioFileException {
		File file = new File(testDir + "testAudio.mp3");

		SongHelper songHelper = new SongHelper();
		String duration = songHelper.GetDurationFromAudio(file);

		Assert.state(duration.equals("4:32"), "Audio duration must be 4:32");
	}
	@Test
	public void CreateSongData() {
		Account account = new Account();
		Song song = new Song(
				"songNameTest",
				"songArtistNameTest",
				"testAudio.mp3",
				testDir,
				account,
				"4:32",
				new Date()
		);

		HashMap<String, Object> data = new HashMap<>();
		data.put("songId", song.getSongId());
		data.put("name", song.getName());
		data.put("artistName", song.getArtistName());
		data.put("accountId", song.getAccount().getAccountId());
		data.put("dateAdded", song.getDate().toString());
		data.put("duration", song.getDuration());

		SongHelper songHelper = new SongHelper();
		HashMap<String, Object> songData = songHelper.CreateSongData(song);
		Assert.state(songData.equals(data), "Song data must be the same");
	}

}
