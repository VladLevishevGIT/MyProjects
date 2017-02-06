package tests;

import core.JSONReader;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;


public class Test {

	public static void main(String[] args) throws MalformedURLException, IOException, JSONException {
		
		String url = "https://itunes.apple.com/search?term=jack+johnson&limit=1";
		
		JSONReader jr = new JSONReader();
		
		JSONObject json = jr.readJsonFromURL(url);
		
		System.out.println(json.toString());
		System.out.println(json.getJSONArray("results").getJSONObject(0).getString("artistName"));
	}

}