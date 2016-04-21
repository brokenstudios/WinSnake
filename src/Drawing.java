import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Drawing {
	//pixel array
	public int[][] img ;
	String url = "";
	//Constructor
	Drawing (String url){
		this.toArray(url);
	}
	//BMP-1 to pixel array
	public void toArray(String url){
		BufferedImage image = null;
		try {
			image = ImageIO.read(Drawing.class.getResource("/resources/"+url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Bad url for image : " + "/resources/"+url +" Image not loaded correctly.");;
		}
		//declares table with image width
		img = new int[image.getWidth()][];
	
		for (int x = 0; x < image.getWidth(); x++) {
			//adjusts this line size
		    img[x] = new int[image.getHeight()];
		    //fill array with image data
		    for (int y = 0; y < image.getHeight(); y++) {
		        img[x][y] = (int) (image.getRGB(x, y) == 0xFFFFFFFF ? 0 : 1);
		    }
		}
	}
}