import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Song {
	String uri ;
	MediaPlayer mp ;
	 
	Song(String uri){
			this.mp = new MediaPlayer(new Media(Song.class.getResource("/resources/"+uri).toString()));
	}
	public void play(){
		mp.play();
	}
	public void stop() {
		mp.stop();
	}
}
