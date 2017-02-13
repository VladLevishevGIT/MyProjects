package tests;

import core.*;


import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;


public class Test {

	public static void main(String[] args) throws MalformedURLException, IOException, JSONException {
		
		/*QuizListCreator chooser = new QuizListCreator();
		
		String url = chooser.artistRandomChooser();
		
		JSONReader jr = new JSONReader();
		
		JSONObject json = jr.readJsonFromURL(url);
		
		System.out.println(json.toString());
		System.out.println(json.getJSONArray("results").getJSONObject(0).getString("artistName"));*/
		
		QuizListCreator qc = new QuizListCreator();
		System.out.println(qc.JSONsongsCreator(qc.artistRandomChooser()).toString());
	}

}
