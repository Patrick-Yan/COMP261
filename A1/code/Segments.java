
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class Segments {

	private String roadId;
	private double Length;
	private int nodeID1;
	private int nodeID2;
	private List<Location> coordinations;
    public boolean highlight = false;
    
	public  Segments(String roadId, double Length , int nodeID1,int nodeID2, List<Location> coordinations) {
	     this.roadId=roadId;
	     this.Length=Length;
	     this.nodeID1=nodeID1;
	     this.nodeID2=nodeID2;
	     this.coordinations=coordinations;
	}
	public String getRoadId() {
		return roadId;
	}
	public double getLength() {
		return Length;
	}
	public int getNodeID1(){
		return nodeID1;
	}
	public int getNodeID2() {
		return nodeID2;
	}

	
	
	public void drawLine(Graphics g, Location origin, double scale) {//method for drawing Segments 
		g.setColor(Color.black);
		for (int i = 0; i < coordinations.size()-1; i++) {//traverse the list of coordinations
			Point p = coordinations.get(i).asPoint(origin, scale);//get the point
			Point q = coordinations.get(i+1).asPoint(origin, scale);
			//System.out.println(p+" "+q);
			if(highlight==true)g.setColor(Color.GREEN);//Highlight
			g.drawLine(p.x, p.y, q.x, q.y);
			
			
		}
}
	
public String toString() {
	return roadId+" "+Length+" "+nodeID1+" "+nodeID2+" "+coordinations;
	
}

}
