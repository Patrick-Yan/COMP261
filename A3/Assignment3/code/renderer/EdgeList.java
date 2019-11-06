package renderer;

/**
 * EdgeList should store the data for the edge list of a single polygon in your
 * scene. A few method stubs have been provided so that it can be tested, but
 * you'll need to fill in all the details.
 *
 * You'll probably want to add some setters as well as getters or, for example,
 * an addRow(y, xLeft, xRight, zLeft, zRight) method.
 */
public class EdgeList {
	float[][] edgelist;
	private int starty;
	private int endy;
	
	public EdgeList(int startY, int endY) {
		// TODO fill this in.
		this.starty=startY;
		this.endy=endY;
		edgelist=new float[4][endY-startY + 1];
	}
	public void addLeftRow(int y, float xLeft, float zLeft){
		if(!(y< 0) && !(y > endy)) {
			edgelist[0][y - starty] = xLeft;
			edgelist[1][y - starty] = zLeft;
		}
	}

	public void addRightRow(int y, float xRight, float zRight){
		if(!(y< 0) && !(y > endy)) {
			edgelist[2][y - starty] = xRight;
			edgelist[3][y - starty] = zRight;
		}
}

	public int getStartY() {
		// TODO fill this in.
		return starty;
	}

	public int getEndY() {
		// TODO fill this in.
		return endy;
	}

	public float getLeftX(int y) {
		// TODO fill this in.
		 return edgelist[0][y - starty];
	}

	public float getRightX(int y) {
		// TODO fill this in.
		return edgelist[2][y - starty];
	}

	public float getLeftZ(int y) {
		// TODO fill this in.
		return edgelist[1][y - starty];
	}

	public float getRightZ(int y) {
		// TODO fill this in.
		return edgelist[3][y - starty];
	}
}

// code for comp261 assignments
