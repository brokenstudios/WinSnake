//For Bi-dimensional elements
public class Object2D {
	int x = 0;
	int y = 0;
	Object2D(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void set2D(int x, int y){
		this.x = x;
		this.y = y;
	}
	public boolean compareTo(Object2D toCompare){
		if(this.x == toCompare.x && this.y == toCompare.y){
			return true;
		}
		else {
			return false;
		}
	}
}
