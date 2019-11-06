//import GUI.Segements;

public class Roads {


	private int roadid;	
	private int type;
	private String label;
	private String city;
	private boolean oneway;
	private int speed;
	private int roadclass;
	private boolean notforcar;
	private boolean notforpede;
	private boolean	notforbicy;
    //
	public Roads(int roadid,int type,String label,String city,boolean oneway,int speed,
			int roadclass,boolean notforcar,boolean notforpede,boolean	notforbicy){
		this.roadid=roadid;
		this.type=type;
		this.label=label;
		this.city=city;
		this.oneway=oneway;
		this.speed=speed;
		this.roadclass=roadclass;
		this.notforcar=notforcar;
		this.notforpede=notforpede;
		this.notforbicy=notforbicy;

	}
	
	public int getRoadID() {
		return roadid;
	}
	
	public String getLabel() {//get the (String)name of road 
		return label;
	}
	public String toString() {
		return roadid + " "+type+ " "+label+ " "+city+ " "+oneway+ " "+speed+ " "+roadclass+ " "+notforcar+ " "+notforpede+ " "+notforbicy;
	}

}
