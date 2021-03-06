package core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class QuizListCreator {

	public static ArrayList<String> artistsList = new ArrayList<String>();
	static {
		artistsList.add("Madonna");
		artistsList.add("Rihanna");
		artistsList.add("Sia");
		artistsList.add("Adele");
		artistsList.add("Beyonce");
	}

	public String artistRandomChooser() {
		String URL;
		String artist = artistsList.get((int) (Math.random() * 5));
		URL = "https://itunes.apple.com/search?term=" + artist + "&limit=10";
		return URL;
	}

	public String songRandomChooser(JSONObject jo) throws JSONException {
		String song = jo.getJSONArray("results").getJSONObject((int) (Math.random() * 9)).getString("trackName");
		return song;
	}

	public JavaRoundBean RoundCreator(String URL) throws MalformedURLException, IOException, JSONException {

		JSONReader jr = new JSONReader();
		JSONObject JSONsongs = jr.readJsonFromURL(URL);

		String artist = JSONsongs.getJSONArray("results").getJSONObject(0).getString("artistName");
		String firstSong = songRandomChooser(JSONsongs);
		String secondSong = songRandomChooser(JSONsongs);
		if (firstSong.equals(secondSong)) {
			secondSong = songRandomChooser(JSONsongs);
		}
		String thirdSong = songRandomChooser(JSONsongs);
		if (firstSong.equals(thirdSong) || (secondSong.equals(thirdSong))) {
			thirdSong = songRandomChooser(JSONsongs);
		}

		JavaRoundBean round = new JavaRoundBean();

		round.setArtistName(artist);
		round.setFirstSong(firstSong);
		round.setSecondSong(secondSong);
		round.setThirdSong(thirdSong);

		/*
		 * round.setArtistName("artist"); round.setFirstSong("firstSong");
		 * round.setSecondSong("secondSong"); round.setThirdSong("thirdSong");
		 */

		return round;
	}

}
