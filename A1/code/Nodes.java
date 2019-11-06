
import java.util.ArrayList;
import java.util.List;

public class Nodes {
	private String NodeId;
	private double Lat;
	private double Lon;
	private List<Segments> connected = new ArrayList<Segments>();//Store the segments connected to node

	public Nodes(String NodeId, double Lat, double Lon) {
		this.NodeId = NodeId;
		this.Lat = Lat;
		this.Lon = Lon;
        
	}
	public List<Segments> getConnected() {//get the list of segments connected to its node
		return connected;
	}
    public void addSeg(Segments s) {//method for adding Segment to node
    	connected.add(s);
    }
	public String toString() {//help to test by print node out
		return NodeId + " " + Lat + " " + Lon;
	}

	public String getNodeId() {
		return NodeId;
	}

	public double getLat() {
		return Lat;
	}

	public double getLon() {
		return Lon;
	}

    

}
