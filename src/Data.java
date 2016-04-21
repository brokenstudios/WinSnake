import java.awt.Color;

public class Data {	
	//Graphic ressources-------------------------------------
	//numbers black on color
	public static Drawing D_ZERO = new Drawing("0.bmp");
	public static Drawing D_ONE = new Drawing("1.bmp");
	public static Drawing D_TWO = new Drawing("2.bmp");
	public static Drawing D_THREE = new Drawing("3.bmp");
	public static Drawing D_FOUR = new Drawing("4.bmp");
	public static Drawing D_FIVE = new Drawing("5.bmp");
	public static Drawing D_SIX = new Drawing("6.bmp");
	public static Drawing D_SEVEN = new Drawing("7.bmp");
	public static Drawing D_EIGHT = new Drawing("8.bmp");
	public static Drawing D_NINE = new Drawing("9.bmp");
	//numbers color on black
	public static Drawing D_NZERO = new Drawing("not0.bmp");
	public static Drawing D_NONE = new Drawing("not1.bmp");
	public static Drawing D_NTWO = new Drawing("not2.bmp");
	public static Drawing D_NTHREE = new Drawing("not3.bmp");
	public static Drawing D_NFOUR = new Drawing("not4.bmp");
	public static Drawing D_NFIVE = new Drawing("not5.bmp");
	public static Drawing D_NSIX = new Drawing("not6.bmp");
	public static Drawing D_NSEVEN = new Drawing("not7.bmp");
	public static Drawing D_NEIGHT = new Drawing("not8.bmp");
	public static Drawing D_NNINE = new Drawing("not9.bmp");
	//menu characters (black on color)
	public static Drawing D_M = new Drawing("M.bmp");
	public static Drawing D_E = new Drawing("E.bmp");
	public static Drawing D_N = new Drawing("N.bmp");
	public static Drawing D_U = new Drawing("U.bmp");
	//score characters (color on black)
	public static Drawing D_NS = new Drawing("notS.bmp");
	public static Drawing D_NC = new Drawing("notC.bmp");
	public static Drawing D_NO = new Drawing("notO.bmp");
	public static Drawing D_NR = new Drawing("notR.bmp");
	public static Drawing D_NE = new Drawing("notE.bmp");
	public static Drawing D_NOT = new Drawing("not.bmp");
	//Game objects (black on color)
	public static Drawing D_SNAKE = new Drawing("Snake.bmp");
	public static Drawing D_SADSNAKE = new Drawing("SadSnake.bmp");
	public static Drawing D_SNAKEHEAD = new Drawing("SnakeHead.bmp");
	public static Drawing D_WALL = new Drawing("Wall.bmp");
	public static Drawing D_APPLE = new Drawing("Apple.bmp");
	public static Drawing D_DOOR= new Drawing("Door.bmp");
	public static Drawing D_BOMB = new Drawing("Bomb.bmp");
	public static Drawing D_TUNNEL = new Drawing("Tunnel.bmp");
	//---------------------------------------------------------
	
	//Constant values for case contents------------------------
	public final static int HEAD = 1;
	public final static int EMPTY = 0;
	public final static int APPLE = -1;
	public final static int BIRD = -2;
	public final static int BOMB = -3;
	public final static int INVISIBLEBOMB = -4;
	public final static int LAYOUT = -5;
	public final static int TUNNEL = -6;
	//accesses to levels (doors)
	public final static int DOORS = -10;
	//numbers for lvls 1 to 9 captions
	public final static int ZERO = -20;
	//characters values in table 
	public final static int M = -31;
	public final static int E = -32;
	public final static int N = -33;
	public final static int U = -34;	
	public final static int NS = -35;
	public final static int NC = -36;
	public final static int NO = -37;
	public final static int NR = -38;	
	public final static int NE = -39;
	public final static int NOT = -40;
	//-------------------------------------------------
	
	//Game constants-----------------------------------
	public final static int BLINK_TIME = 5;
	public final static int LOOSER_REPEAT = 10;
	public final static int COUNTDOWN = 100;
	public final static int MENUSPEED = 6;
	//-------------------------------------------------
	
	//Game states--------------------------------------
	public final static int RUN = 1;
	public final static int MENU = 0;
	public final static int LOOSE = -1;
	//-------------------------------------------------
	
	//Display constants--------------------------------
	public final static int RES = 2*14;
	public final static int MENUOFFSET = 2;
	public final static int MARGINNUMBERS = 0; 
	
	//Old display color constant
	public final static Color OLDDISPLAY = new Color(105,150,90);
	public final static Color TEXTCOLOR = Color.BLACK;
	//Display color used, default is olddisplay color
	public static Color display = OLDDISPLAY;
	//-------------------------------------------------
	//2Dimensional objects (directions, positions,...)-------------------------
	//Field size
	public static Object2D area = new Object2D(0,0);
	//Maximum values for field table index (area-1)
	public static Object2D maxindex = new Object2D(0,0);
	//Next case
	public static Object2D next = new Object2D(0,0);
	//Head position, default at x=0 and y = 1/3 of windows height, at beginning
	public static Object2D head = new Object2D (0,0);
	//Keys pressed for directions, default is to the right at beginning
	public static Object2D arrows = new Object2D(0, 0);
	//Window size
	public static Object2D winSize = new Object2D(800, 600);
	//-------------------------------------------------------------------------
	//Game variables-----------------------------------------------------------
	public static int otherKey = 0;
	public static int nbApples = 0;
	public static int nbSteps = 0;
	public static int score = 0;
	public static int level = 0;
	public static int length = 1;
	public static int state  = RUN;
	public static int speed  = MENUSPEED;
	public static int bombCountdown = COUNTDOWN;
	public static boolean bombBlink = true;
	public static boolean playBug = false;
	public static int blinkFrame = 0;
	public static int appearLine = 0;
	public static int frame = 1;
	public static boolean existBomb = false;
	public static boolean existWalls = false;
	public static boolean appear = true;
	//-------------------------------------------------------------------------
	
	//Change color method
	public static void setDisplayColor(Color c){
		display = c;
	}
}
