//Code realised by Loïc FraGil(TM) -- all rights reserved -- 2015 #we are super propre #mrPropre

public class SnakeZecute {
	//Creates a new table of integers (game area)
	
	static Field f ;
	static Song bug;
	static Song welcome;
	static Song advert;
	static Song ding;
	static Song sessionstart;
	static Song sessionstop;
	
	public static void main(String[] args) {
		// forces JavaFX init (audio play requires it)
		new javafx.embed.swing.JFXPanel(); 
		bug = new Song("winxpcritical.mp3");
		welcome = new Song("winxpwelcome.mp3");
		advert = new Song("winxpadvert.mp3");
		ding = new Song("winxpding.mp3");
		sessionstart = new Song("winxpsessionstart.mp3");
		sessionstop = new Song("winxpsessionstop.mp3");
		
		//Get user working directory to save images into
		System.out.println("Please copy medias in this directory : "  + System.getProperty("user.dir"));

		//Ask user for parameters and get xSize and ySize
		Do.askParameters();		
		f = new Field(Data.area.x, Data.area.y);
		//Opens a new window with FunGraphics library
		Graphics.drawWindow();
		//and call a new keyboard listener
		Graphics.listener();

		//Start in the menu
		Data.state = Data.MENU;
		Data.appearLine = (int)Data.maxindex.y/3;
		Data.length = 1;
		menuInit();

		while(true){
			
			//displays game in console mode
			if(Data.playBug)bug.play();
			else bug.stop();
			
			//Window launched
			if(Data.state != Data.LOOSE){
				//Menu
				if(Data.state == Data.MENU){
					Data.score = 0;
					//erase decor
					Do.eraseMulti(Data.APPLE, false);
					//Display menu when in pause mode (menu mode)
					Data.playBug = false;
					Do.menuLayout();
					if(Data.appear){
						//plays win xp welcome song 
						welcome.play();
						//generates first snake
						Do.appear();
					}
				}
				//Game 
				else {
					//random walls creation
					if(!Data.existWalls){
						Do.randomWalls();
						Data.existWalls = true;
					}
					//random bomb spawn, countdown process
					if(Data.existBomb){
						Data.bombCountdown -- ;
						//last 1/3 of time : bomb will be blinking
						if(Data.bombCountdown < Data.COUNTDOWN/3){
							if(Data.bombBlink){
								Do.switchValues(Data.BOMB, Data.INVISIBLEBOMB);;
							}
							else{
								Do.switchValues(Data.INVISIBLEBOMB, Data.BOMB);
							}
							Data.bombBlink = !Data.bombBlink ;
						}
						//bomb exploded : speed up snake
						if(Data.bombCountdown <= 0){
							Data.speed += 3;
							Do.erase(Data.BOMB);
							Do.newBomb();
						}
					}
					else Do.newBomb();
				}
				//process move
				Do.move();
				Data.score = Data.nbSteps * Data.nbApples * Data.level / 20;
				Graphics.redraw();
			}
			//game loose
			if(Data.state == Data.LOOSE){
				
				//immobilizes the snake and clear all except snake
				Data.arrows.set2D(0, 0);
				Data.existWalls = false;
				looseAnimToMenu();
			}
			Graphics.s.syncGameLogic(Data.speed);
		}
	}
	private static void looseAnimToMenu(){
		Data.speed = Data.BLINK_TIME;
		//Data.playbug is for song maintain (only one time, correct duration)
		if(Data.blinkFrame == 0) {
			Data.playBug = true ;
		}
		if(Data.blinkFrame < Data.LOOSER_REPEAT){
			if(Data.blinkFrame % 2 == 0){
				//fill area with background color
				Graphics.background();
			}
			else{
				//fill area with decor
				Graphics.redraw();
			}
			Data.blinkFrame ++;
		}
		if(Data.blinkFrame == Data.LOOSER_REPEAT){
			//Other key pressed
			Graphics.redraw();
			//next step is menu
			//default direction in menu
			SnakeZecute.sessionstop.stop();
			SnakeZecute.sessionstop.play();
			menuInit();
			Do.eraseMulti(Data.HEAD, true);
		}
	}
	private static void menuInit(){
		Data.arrows.set2D(1, 0);
		Data.state = Data.MENU;
		Data.head.set2D(0, (int)Data.maxindex.y/3);
		Data.length = 1;
		Data.speed = Data.MENUSPEED;
		Data.appear = true;
		Data.frame = 1;
		if(Data.score != 0){
			System.out.println("Your last score : " +Data.score);
			Data.score = 0;
		}
		Data.nbApples = 0;
		Data.nbSteps = 0;
		Data.blinkFrame = 0 ;
	}
}
