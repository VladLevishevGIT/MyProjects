package core;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JavaRoundBean {
	
	private String artistName;
	private String firstSong;
	private String secondSong;
	private String thirdSong;
	
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getFirstSong() {
		return firstSong;
	}
	public void setFirstSong(String firstSong) {
		this.firstSong = firstSong;
	}
	public String getSecondSong() {
		return secondSong;
	}
	public void setSecondSong(String secondSong) {
		this.secondSong = secondSong;
	}
	public String getThirdSong() {
		return thirdSong;
	}
	public void setThirdSong(String thirdSong) {
		this.thirdSong = thirdSong;
	}

}
