public class Field {
	public static int[][] area;
	
	//Constructor
	Field(int x, int y){
		area = new int[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				//Initializes table "area"
				area[i][j] = Data.EMPTY;
			}
		}
	}
	
	// Special String default function give a text array with array
		public String toString() {
			String stringToReturn = "";
			stringToReturn += "        ";
			//write column headers
			for (int i = 0; i < Data.area.x; i++) {
				if (i <= 9) {
					stringToReturn += "0";
				}
				stringToReturn += i + " ";
			}
			stringToReturn += "\n\n";
			//write rows
			for (int j = 0; j < Data.area.y; j++) {
				stringToReturn += "row ";
				if (j <= 9) {
					stringToReturn += "0";
				}
				stringToReturn += j + " "; 
				//write columns in rows
				for (int k = 0; k < Data.area.x; k++) {
					// write blank space for column alignment
					stringToReturn += "  "; 
					if (area[k][j] > Data.HEAD) {
						// this case contains a snake part
						stringToReturn += "◙";
					} else if (area[k][j] == Data.APPLE) {
						// this case contains an apple
						stringToReturn += "A";
					} 
					else if (area[k][j] == Data.BOMB) {
							// this case contains an apple
							stringToReturn += "B";
					} else if (area[k][j] == Data.LAYOUT) {
						// this case contains an apple
						stringToReturn += "#";
					}
					else if (area[k][j] == Data.LAYOUT) {
						// this case contains an apple
						stringToReturn += "#";
					}else if (area[k][j] == Data.TUNNEL) {
						// this case contains an apple
						stringToReturn += "C";
					}else if (area[k][j] == Data.HEAD) {
						// this case contains an apple
						stringToReturn += "☻";
					}else if (area[k][j] <= Data.ZERO && area[k][j] > Data.M) {
						// this case contains an apple
						stringToReturn += -(area[k][j] + 20);
					}
					else if (area[k][j] == Data.M) {
						// this case contains an apple
						stringToReturn += "M";
					}
					else if (area[k][j] == Data.E) {
						// this case contains an apple
						stringToReturn += "E";
					}
					else if (area[k][j] == Data.N) {
						// this case contains an apple
						stringToReturn += "N";
					}else if (area[k][j] == Data.U) {
						// this case contains an apple
						stringToReturn += "U";
					}else if (area[k][j] == Data.DOORS){
						// this case contains a door
						stringToReturn += "H";
					}else {
						// this case contains nothing
						stringToReturn += "O";
					}
				}
				stringToReturn += "\n";
			}
			return stringToReturn;
		}
}
