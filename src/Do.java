import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import hevs.utils.Input;

public class Do {
 	public static void askParameters(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double height = screenSize.getHeight();
		System.out.println("Please enter the width : ");
		Data.area.x = Input.readInt();
		//maybe use Dialogs Class to ask in window
		if(Data.area.x > 9 * 4 + 1 ){
			Data.area.x = 9 * 4 + 1;
			System.out.println("The width you had entered was too big. We reduced it.");
		}
		else if(Data.area.x < 8){
			Data.area.x = 8;
			System.out.println("The width you had entered was too small. We adapted it.");
		}
		System.out.println("Please enter the height : ");
		Data.area.y = Input.readInt();
		if(Data.area.y > height *3/4 / Data.RES){
			Data.area.y = (int)height *3/4 / Data.RES;
			System.out.println("The height you had entered was too big. We reduced it for your screen.");
		}
		else if(Data.area.y < 8){
			Data.area.y = 8;
			System.out.println("The width you had entered was too small. We adapted it.");
		}
//		//add a row for scores
//		Data.area.x = 9 * 4 + 1;
//		Data.area.y = (int)height *3/4 / Data.RES +1;
		
		Data.maxindex.x = Data.area.x-1;
		Data.maxindex.y = Data.area.y-1;
		System.out.println("Windows will be opened. Please wait.");
		Graphics.setWinWidth(Data.RES * Data.area.x);
		Graphics.setWinHeight(Data.RES * Data.area.y);
	}
	public static void move(){
		nextPosition();
		isAnybodyThere();
		if(Data.state != Data.LOOSE){
			if(Data.state != Data.MENU){
				Data.nbSteps ++;
			}
	 		//No directions, no move
	 		if(!Data.arrows.compareTo(new Object2D(0,0))){
				//moves the snake in table
				for (int i = 0; i < Field.area.length; i++) {
					for (int j = 0; j < Field.area[i].length; j++) {
						if(Field.area[i][j]>Data.length){
							//Erases last cell
							Field.area[i][j]=Data.EMPTY;
						}
						if(Field.area[i][j]>Data.EMPTY){
							//Snake inside
							Field.area[i][j]++;
						}
					}
				}
				//assign next position as the next snake head
				//actualizes new head position
				Data.head.x = Data.next.x;
				Data.head.y = Data.next.y;
				setCase(Data.HEAD, Data.head.x, Data.head.y);
	 		}
 		}
	}
	private static void nextPosition (){
		//UP LIMIT (exclude first line reserved for score)
		if(Data.head.y==1 && Data.arrows.y == -1){
			Data.next.x = Data.head.x;
			Data.next.y = Data.maxindex.y;
		}
		//DOWN LIMIT
		else if(Data.head.y==Data.maxindex.y && Data.arrows.y == 1){
			Data.next.x = Data.head.x;
			Data.next.y = 1;
		}
		//LEFT LIMIT
		else if(Data.head.x==0 && Data.arrows.x == -1){
			Data.next.x = Data.maxindex.x;
			Data.next.y = Data.head.y;
		}
		//RIGHT LIMIT
		else if(Data.head.x==Data.maxindex.x && Data.arrows.x == 1){
			Data.next.x = 0;
			Data.next.y = Data.head.y;
		}
		//MOVE WHEN NO LIMIT
		else{
			Data.next.y = Data.head.y+Data.arrows.y;
			Data.next.x = Data.head.x+Data.arrows.x;
		}
	}
	private static void isAnybodyThere(){
		int nextCell = Field.area[Data.next.x][Data.next.y];
		if(nextCell == Data.APPLE){
			//Add a length unit to snake
			Data.length++;
			setCase(Data.HEAD, Data.next.x, Data.next.y);
			newApple(); 
			Data.nbApples ++;
			SnakeZecute.advert.stop();
			SnakeZecute.advert.play();
		}
		//next cell contains a bomb
		else if(nextCell == Data.BOMB){
			//snake goes through bomb (replaces it)
			setCase(Data.HEAD, Data.next.x, Data.next.y);
			//renew bomb randomly and play song
			newBomb(); 
			SnakeZecute.ding.stop();
			SnakeZecute.ding.play();
			randomColor();
		}
		//can't loose in menu
		else if ((nextCell == Data.LAYOUT || nextCell >= Data.HEAD)&& Data.state == Data.MENU){
			//set move to 0
			Data.arrows.set2D(0, 0);
		}
		//next cell contains a level access 
		else if (nextCell < Data.DOORS && nextCell > Data.DOORS-10){
			//game start
			Data.state = Data.RUN;
			//process difficulty
			Data.level = -(10+nextCell);
			Data.speed = (int) 3+Data.level*2;
			SnakeZecute.sessionstart.stop();
			SnakeZecute.sessionstart.play();
			//clear layout
			eraseMulti(Data.LAYOUT,false);
			newApple(); 
			newBomb();
		}
		//LOOSE
		else if (nextCell > Data.HEAD && nextCell < Data.length || (nextCell == Data.LAYOUT && Data.state != Data.MENU )){
			Data.state = Data.LOOSE;
		}
	}
	public static void setCase(int content, int x, int y) {
		if(verify(x,y)){
			Field.area[x][y] = content;
		}
	}
	public static int getCase(int x, int y) {
		if(verify(x,y)){
			return Field.area[x][y];
		}
		else return 1000;
	}
	private static boolean verify(int x, int y){
		if (Data.maxindex.x >= x && Data.maxindex.y >= y ){
			return true;
		}
		else{
			return false;
		}
	}
	private static boolean isFree(int x, int y, int exception){
		//no objects in score line
		if(y == 0){
			return false;
		}
		if(getCase(x,y) == Data.EMPTY ){
			 return true;
		}
		if(getCase(x,y) == exception) {
			return true;
		}
		else return false;
	}
	private static boolean isAreaFree(int x, int y, int clearance, int tolerance, int exception){
		//count all cells containing any value except EXCEPTION, in an area of CLEARANCE by CLEARANCE around cell in (X,Y).
		//this value is compared to TOLERANCE, then if is bigger, return false (area is not enough free) 
		int tempx;
		int tempy;
		int nbCellsFilled = 0;
		//iterate table around given cell
		for(int i = -clearance; i<clearance ; i++){
			for(int j = -clearance; j<clearance ; j++){
				//cast cell
				if(x+i < 0){
					tempx = x+i+Data.maxindex.x;
				}
				else tempx = x+i;
				//cast cell
				if(y+j < 0){
					tempy = y+j+Data.maxindex.y;
				}
				else tempy = y+j;
				//check if is free
				if(!isFree(tempx,tempy,exception))nbCellsFilled ++ ;
			}
		}
		if(nbCellsFilled > tolerance || !isFree(x,y,Data.EMPTY)){
			return false;
		}
		else return true;
	}
	public static void newApple(){
		int x = (int)(Math.random()*Data.maxindex.x);
		int y = (int)(Math.random()*Data.maxindex.y);
		//apple can appear near other objects, but not more than 2. exception : bombs are not considered
		while(!isAreaFree(x,y,1,2,Data.BOMB)){
			x = (int)(Math.random()*Data.maxindex.x);
			y = (int)(Math.random()*Data.maxindex.y);
		}
		setCase(Data.APPLE,x,y);
	}
	public static void newBomb(){
		int x = (int)(Math.random()*Data.maxindex.x);
		int y = (int)(Math.random()*Data.maxindex.y);
		//3 chance of 10 that a bomb appear
		if(Math.random() > (0.95 - 0.02 * Data.level)){
			while(!isAreaFree(x,y,1,2,Data.APPLE)){
				x = (int)(Math.random()*Data.maxindex.x);
				y = (int)(Math.random()*Data.maxindex.y);
			}
			setCase(Data.BOMB,x,y);
			Data.bombCountdown = Data.COUNTDOWN;
			Data.existBomb = true;
		}
		else Data.existBomb = false;
	}
	public static void erase(int value){
		for (int i = 0; i < Field.area.length; i++) {
			for (int j = 0; j < Field.area[i].length; j++) {
				if(Field.area[i][j] == value){
					//Erases last cell
					Field.area[i][j]=Data.EMPTY;
				}
			}
		}
	}
	public static void eraseMulti(int value, boolean toPositive){
		for (int i = 0; i < Field.area.length; i++) {
			for (int j = 0; j < Field.area[i].length; j++) {
				if(Field.area[i][j] >= value && toPositive){
					//Erases last cell
					Field.area[i][j]=Data.EMPTY;
				}
				else if(Field.area[i][j] <= value && !toPositive){
					//Erases last cell
					Field.area[i][j]=Data.EMPTY;
				}
			}
		}
	}
	public static void menuLayout(){
		scoreLayout();
		//draw menu title
		int posX = Data.area.x / 2 - 2;
		int posY = Data.MENUOFFSET;
		if(Data.appearLine == posY){
			Data.appearLine++;
		}
		for(int i = 0; i < 4; i++){
			Field.area[posX + i][posY] = Data.M-i;
		}
		
		//draw tunnel
		Do.setCase(Data.TUNNEL, 0, Data.appearLine);
		
		//draw levels layout (columns)
		int height = Data.area.y*2/3;
		int lastColumn = Data.area.x / 4;
		int centerMargin = (Data.area.x - lastColumn*4)/2;
		
		for (int i = 0; i < Field.area.length; i++) {
			for (int j = 0; j < Field.area[i].length; j++) {
				
				if(j == Data.maxindex.y){
					//last line to draw walls
					setCase(Data.LAYOUT, i, j);
				}
				
				if(j == Data.maxindex.y - 1){
					//line containing doors to select level (levels are -11,-12,-13,... for levels 1,2,3,...)
					int value = Data.DOORS - ( i/4 + 1 );
					if(value == Data.ZERO)value = -19;
					if(getCase(i,j) != Data.LAYOUT){
						setCase(value, i, j);
					}
					
				}
				
				//test if columns is a 4 multiple and is contained between 2/3 height and full height.
				//add a centering margin in first and last columns
				if(j >= height && (i+centerMargin)%4 == 0 && i>centerMargin && i<Data.maxindex.x){
					setCase(Data.LAYOUT, i, j);
				}
				//display numbers only when no snake on it
				else if(j == height+Data.MARGINNUMBERS && (i+centerMargin)%4 == 2){
					if(getCase(i,j) < Data.HEAD){
						setCase(Data.ZERO - (i/4 + 1), i, j);
					}
				}
				
			}
		}
	}
	public static void scoreLayout(){
		//draw score line
		int posX = 0;
		int posY = 0;
		for(int i = 0; i < 5; i++){
			Field.area[posX + i][posY] = Data.NS-i;
		}
		for(int i = 5 ; i<=Data.maxindex.x ; i++){
			Field.area[posX + i][posY] = Data.NOT;
		}
		int nbDigits = 0;
		if(Data.score == 0){
			nbDigits = 1;
		}
		else{
			nbDigits = (int)Math.ceil(Math.log10(Data.score));
			if(nbDigits == 0){
				nbDigits = 1;
			}
		}
		int value;
		int x;
		int tempScore = Data.score;
		int[] digitScore = new int[nbDigits];
		for(int i = 0; i<nbDigits; i++){
			//process most significant digit
			digitScore[i] = tempScore / (int) Math.pow(10, nbDigits-i-1);
			//for next operation, remove most significant digit
			tempScore = tempScore - digitScore[i]*(int)Math.pow(10, nbDigits-i-1);
			//convert to class Data correct values
			digitScore[i] = Data.ZERO - digitScore[i];
		}
		for (int i = 0; i < nbDigits; i++){
			value = digitScore[nbDigits-i-1];
			x = Data.maxindex.x - i;
			setCase(value,x,0);
		}
	}
	public static void appear(){
		//Set default direction and position for snake
		//Appears from a tunnel
		//-----------------------
		//draw only decor at first time
		//draw snake going out of the tunnel the 3 next times
		if (Data.frame >= 1 && Data.frame < 4){
			if(Data.frame == 1){
				//initialize head position in the tunnel
				Do.setCase(Data.HEAD, 0, Data.appearLine);
				//actualizes new head position
				Data.head.set2D(0, Data.appearLine);
			}
			//process normal move and add a length (gives illusion that snake goes out from his tunnel)
			Data.length++;
		}
		else{
			//end of apparition process
			Data.appear = false;
		}
		Data.frame++;
	}
	public static void randomWalls(){
		//declare variables
		//x, y are used for wall placement
		int x = 0;
		int y = 0;
		int nbWalls, sizeWall;
		//2 2D objects for directions in x and y and position in x and y
		Object2D dirWall = new Object2D(0,0);
		Object2D posWall = new Object2D(0,0);
		
		//this is a parameter of the random function, for each level
		int maxNb = Data.level * 5 ; 
		int maxSize = Data.level; 
		int minNB = Data.level;
		
		//from 1 to maxNb walls
		nbWalls = (int)(Math.random()*maxNb + minNB);
		
		//go through table to set walls
		for(int j = 0; j < nbWalls ; j++){
			//for each wall, determines randomly the size of this wall
			sizeWall = (int)(Math.random()*maxSize+2);
			
			//set direction randomly, cast between -1 (down or left) 0(nothing) and 1(up or right)
			//two variables between 0 and 3
			double temp1 = Math.random()*3;
			double temp2 = Math.random()*3;
			//set directions in function of temp values (sort of cast)
			if(temp1  < 1.0) dirWall.x = -1;
			else if(temp1  < 2.0) dirWall.x = 0;
			else if(temp1  < 3.0) dirWall.x = 1;
			if(temp2 < 1.0) dirWall.y = -1;
			else if(temp2 < 2.0) dirWall.y = 0;
			else if(temp2 < 3.0) dirWall.y = 1;
			
			//random for the two coordinates
			posWall.set2D((int)(Math.random()*Data.maxindex.x),(int)(Math.random()*Data.maxindex.y));
			int nbWallsDone = 0;
			for (int i = 0 ; i < sizeWall ; i++){
				//indexes for each wall cell
				x = (posWall.x + i*dirWall.x) % Data.maxindex.x;
				y = (posWall.y + i*dirWall.y) % Data.maxindex.y;
				
				//if index negative, then is reported to the end of the table
				x = Math.abs(x);
				y = Math.abs(y);
				
				//check if area around cell is completely free (3x3)
				if(isAreaFree(x, y, 3, 0, Data.LAYOUT)){
					nbWallsDone ++;
					setCase(Data.LAYOUT, x , y);
				}
				//area is not free, so this cell will be reported to a new cell away. (one left -> one more) une de perdue, une de retrouvée !
				else sizeWall ++;
				if(nbWallsDone >= maxNb){
					break;
				}
				if(sizeWall > 30)break;
			}
		}
	}
	private static void randomColor(){
		float r = (float) Math.random();
		float g = (float) Math.random();
		float b = (float) Math.random();
		Data.setDisplayColor(new Color(r,g,b));
	}
	public static void switchValues(int previous, int next){
		//replace a value with another
		for (int i = 0; i < Field.area.length; i++) {
			for (int j = 0; j < Field.area[i].length; j++) {
				if(Field.area[i][j] == previous){
					Field.area[i][j] = next;
				}
			}
		}	
	}
}
