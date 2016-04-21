import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import hevs.graphics.FunGraphics;

public class Graphics {
	private static int winWidth = Data.winSize.x;
	private static int winHeight = Data.winSize.y;
	public static FunGraphics s;
	
	public static void redraw(){
		synchronized(s.frontBuffer){
		background();
		Do.scoreLayout();
		for (int i = 0; i < Field.area.length; i++) {
			for (int j = 0; j < Field.area[i].length; j++) {
				//Snake body inside
				if(Field.area[i][j] > Data.HEAD) drawObject(i, j, Data.D_SNAKE.img);
				//Snake head inside
				else if(Field.area[i][j] == Data.HEAD ){
					if (Data.state != Data.LOOSE)drawObject(i, j, Data.D_SNAKEHEAD.img);
					else drawObject(i, j, Data.D_SADSNAKE.img);
				}
				//Door inside [-11,-19]
				else if(Field.area[i][j] < Data.DOORS && Field.area[i][j] > Data.DOORS-10) drawObject(i, j, Data.D_DOOR.img);
				//No snake in
				else if (!(i == Data.head.x && j == Data.head.y)){
					//layout
					switch(Field.area[i][j]){
						//-20 is Data.ZERO
						case -20 : 			if(j==0)drawObject(i, j, Data.D_NZERO.img);
											else drawObject(i, j, Data.D_ZERO.img);
											break;
						case -21 : 			if(j==0)drawObject(i, j, Data.D_NONE.img);
											else drawObject(i, j, Data.D_ONE.img);
											break;
						case -22 : 			if(j==0)drawObject(i, j, Data.D_NTWO.img);
											else drawObject(i, j, Data.D_TWO.img);
											break;
						case -23 : 			if(j==0)drawObject(i, j, Data.D_NTHREE.img);
											else drawObject(i, j, Data.D_THREE.img);
											break;
						case -24 : 			if(j==0)drawObject(i, j, Data.D_NFOUR.img);
											else drawObject(i, j, Data.D_FOUR.img);
											break;
						case -25 : 			if(j==0)drawObject(i, j, Data.D_NFIVE.img);
											else drawObject(i, j, Data.D_FIVE.img);
											break;
						case -26 : 			if(j==0)drawObject(i, j, Data.D_NSIX.img);
											else drawObject(i, j, Data.D_SIX.img);
											break;
						case -27 : 			if(j==0)drawObject(i, j, Data.D_NSEVEN.img);
											else drawObject(i, j, Data.D_SEVEN.img);
											break;
						case -28 : 			if(j==0)drawObject(i, j, Data.D_NEIGHT.img);
											else drawObject(i, j, Data.D_EIGHT.img);
											break;
						case -29 : 			if(j==0)drawObject(i, j, Data.D_NNINE.img);
											else drawObject(i, j, Data.D_NINE.img);
											break;
						case Data.M :		drawObject(i, j, Data.D_M.img);
											break;
						case Data.E :		drawObject(i, j, Data.D_E.img);	
											break;
						case Data.N :		drawObject(i, j, Data.D_N.img);
											break;
						case Data.U :		drawObject(i, j, Data.D_U.img);
											break;
						case Data.NS :		drawObject(i, j, Data.D_NS.img);
											break;
						case Data.NC :		drawObject(i, j, Data.D_NC.img);	
											break;
						case Data.NO :		drawObject(i, j, Data.D_NO.img);
											break;
						case Data.NR :		drawObject(i, j, Data.D_NR.img);
											break;
						case Data.NE :		drawObject(i, j, Data.D_NE.img);
											break;
						case Data.NOT :		drawObject(i, j, Data.D_NOT.img);
											break;
						
						//Tunnel inside
						case Data.TUNNEL :	drawObject(i, j, Data.D_TUNNEL.img);
											break;
						//All in all it was just another brick in the wall
						case Data.LAYOUT :	drawObject(i, j, Data.D_WALL.img);
											break;
						//Apple inside
						case Data.APPLE :	drawObject(i, j, Data.D_APPLE.img);
											break;
						//Bomb inside
						case Data.BOMB :	drawObject(i, j, Data.D_BOMB.img);
											break;
						}
					}
				}
			}
		}	
	}
	public static void listener(){
		// Do something when a key has been pressed
	    s.setKeyManager(new KeyAdapter() {
	    	
	        // Will be called when a key has been pressed
	        public void keyPressed(KeyEvent e) {

	            switch(e.getKeyCode()){
	            	case KeyEvent.VK_RIGHT:	if(Data.arrows.x == -1 && Data.arrows.y == 0){
							            		Data.arrows.x = -1;
							            		Data.arrows.y = 0;
							            	}
	            							else{
	            								Data.arrows.x = 1;
							            		Data.arrows.y = 0;
	            							}
	            							break;
	            	case KeyEvent.VK_LEFT:	if(Data.arrows.x == 1 && Data.arrows.y == 0){
							            		Data.arrows.x = 1;
							            		Data.arrows.y = 0;
							            	}
							            	else{
							            		Data.arrows.x = -1;
							            		Data.arrows.y = 0;
							            	}
											break;
	            	case KeyEvent.VK_UP:	if(Data.arrows.x == 0 && Data.arrows.y == 1){
							            		Data.arrows.y = 1;
							            		Data.arrows.x = 0;
							            	}
							            	else{
							            		Data.arrows.y = -1;
							            		Data.arrows.x = 0;
							            	}
											break;
	            	case KeyEvent.VK_DOWN:	if(Data.arrows.x == 0 && Data.arrows.y == -1){
							            		Data.arrows.y = -1;
							            		Data.arrows.x = 0;
							            	}
							            	else{
							            		Data.arrows.y = 1;
							            		Data.arrows.x = 0;
							            	}
											break;
	            }
	        }
	    });
	}
	public static void drawWindow(){
		s = new FunGraphics(winWidth , winHeight , "Test snake by FraGil");
		background();
	}
	public static void background(){
		int posX = 0;
		int posY = 0;
		//draw cases
		for (int i = 0; i < Field.area.length; i++) {
			for (int j = 0; j < Field.area[i].length; j++) {
				posX = i*(Data.RES);
				posY = j*(Data.RES);
				s.setColor(Data.display);
				s.drawFillRect(posX, posY, Data.RES, Data.RES);
			}
		}
		//--draw black lines--
		s.setColor(Color.black);
			//drawing the lines between columns
		for (int i = 1; i < winHeight; i++) {
			posX = i*Data.RES;
			s.drawLine(0, posX, winWidth, posX);
		}
			//draw the lines between lines
		for (int i = 1; i < winWidth; i++) {
			posY = i*Data.RES;
			s.drawLine(posY, 0, posY, winHeight);
		}
	}
	public static void emptyCase(int x, int y){
		//empty a case
		s.setColor(Data.display);
		s.drawFillRect(x*Data.RES, y*Data.RES, Data.RES, Data.RES);
	}
	public static void fillCase(int x, int y){
		//fill a case
		s.setColor(Color.BLACK);
		s.drawFillRect(x*Data.RES, y*Data.RES, Data.RES, Data.RES);
	}
	public static void drawObject(int x, int y, int[][] object){
		//draw an object in a case [x][y]
		s.setColor(Color.BLACK);
		int a = x * Data.RES;
		int b = y * Data.RES;
		//obtain max size of pixel (must preferably be a multiple of object.length)
		int c = Data.RES / object.length;

		for (int i = 0; i < object.length; i++) {
			for (int j = 0; j < object[i].length; j++) {
				if(object[i][j]==1){
					s.drawFillRect(a+i*c, b+j*c, c,c);
				}
			}
		}
	}
	public static int getWinWidth() {
		return winWidth;
	}
	public static void setWinWidth(int winWidth) {
		Graphics.winWidth = winWidth;
	}
	public static int getWinHeight() {
		return winHeight;
	}
	public static void setWinHeight(int winHeight) {
		Graphics.winHeight = winHeight;
	}
}
