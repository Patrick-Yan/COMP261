import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//import GUI.Roads;

public class map extends GUI {
	private Location origin;
	private double scale=100;
	private double north = -90 ;
	private double west = 180;
	private List<Roads>road = new ArrayList<Roads>();
	private List <Segments> Segment =new ArrayList<Segments>();
	public List<Location>loc = new ArrayList<>();
	public Map<String,Nodes>node = new HashMap<>();//build a node map
	public static final double MoveDistance = 10;
	public static final double ZoomNumber = 1.3;
	private TrieNode Root = new TrieNode(); 


	@Override

	protected void redraw(Graphics g) {
		// TODO Auto-generated method stub
		for(Nodes n : node.values()) {
			Location l1 = Location.newFromLatLon(n.getLat(), n.getLon());
			Point p = l1.asPoint(origin,scale);
			g.setColor(Color.black);
			g.fillOval(p.x-2,p.y-2, 4,4);//if don't -2 the line won't connect with node
		}
		for (Segments s : Segment) {
			s.drawLine(g, origin, scale);
			s.highlight=false;//unhighlight the Segment highlighted before 
		}

	}

	@Override
	protected void onClick(MouseEvent e) {
		// TODO Auto-generated method stub
		Location clicked = Location.newFromPoint(e.getPoint(), origin, scale);
		List<Roads> R = new ArrayList<Roads>();
		for(Nodes n : node.values()) {
			Location L = Location.newFromLatLon(n.getLat(), n.getLon());
			if(L.isClose(clicked, 0.04)) { //if location I clicked is close to a node , I get the road 
				//connected to this node and highlight it
				for (Segments s : Segment) {
					String NID =  n.getNodeId();
					if((NID.equals(Integer.toString(s.getNodeID1())))||(NID.equals(Integer.toString(s.getNodeID2())))) {
						for(Segments seg:Segment) {
							if(seg.getRoadId().equals(s.getRoadId())) {
								seg.highlight=true;

								for(Roads road1 : road) {
									if(Integer.toString(road1.getRoadID()).equals(seg.getRoadId())) {//Prepare for getOutputArea
										R.add(road1);

									}
								}
							}
						}
					}
				}
			}
			String print = null;
			for(Roads r : R) {
				print = print+r;
			}
			getTextOutputArea().setText(print);//print the information of roads connected to the node
		}
	}

	@Override
	protected void onSearch() {//The first part is for searching exact name of road
		// TODO Auto-generated method stub
		
		String RoadName = getSearchBox().getText();
		boolean fullname = false;
		for (Roads r: road) {
			if(r.getLabel().equals(RoadName)) {
				for(Segments s : Segment) {
					if(Integer.toString(r.getRoadID()).equals(s.getRoadId())) {
						s.highlight=true;
						getTextOutputArea().setText(r.toString());
						fullname=true;
					}
				}
			}
		}

		if(!fullname) {//This is the second part,and is for searching prefix of road
			char[] roadname = RoadName.toCharArray();
			List<Roads>Roadname=getAll(roadname);
			//System.out.println(roadname);
			List<Segments>highlight= new ArrayList<Segments>();
			List<Roads> roadPrinted = new ArrayList<Roads>(); 
			if(Roadname!=null) {
				for(Roads r : Roadname) {
					for(Segments s : Segment) {
						s.highlight=false;
						if((s.getRoadId()).equals(Integer.toString(r.getRoadID()))) {
							highlight.add(s);
							//System.out.println(highlight.size());
							if(!roadPrinted.contains(r)) {
								roadPrinted.add(r);
							}
						}
					}
				}
				String print = null;
				for(Roads r : roadPrinted) {
					print = print+r;
				}
				getTextOutputArea().setText(print);
			}
			for(Segments s : highlight) {
				s.highlight=true;//highlight the road I may want to search
			}
		}

	}

	@Override
	protected void onMove(Move m) {
		// TODO Auto-generated method stub
		if (m.equals(Move.NORTH)) {
			origin = origin.moveBy(0, MoveDistance / scale);
		} else if (m == GUI.Move.SOUTH) {
			origin = origin.moveBy(0, -MoveDistance / scale);
		} else if (m == GUI.Move.EAST) {
			origin = origin.moveBy(MoveDistance / scale, 0);
		} else if (m == GUI.Move.WEST) {
			origin = origin.moveBy(-MoveDistance / scale, 0);
		} else if (m == GUI.Move.ZOOM_IN) {
			
			if (scale < 200) {
				
				scaleOrigin(true);//ZoomIn
				scale *= ZoomNumber;
			}
		} else if (m == GUI.Move.ZOOM_OUT) {
			if (scale > 1) {
				
				scaleOrigin(false);//ZoomOut
				scale /= ZoomNumber;
			}
		}
	}


	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {
		// TODO Auto-generated method stub
		//Read nodes file///////////////////////////////////////////////////////////////////
		BufferedReader reader1 = null;
		try {

			reader1 = new BufferedReader(new FileReader(nodes));

			String line;
			while ((line = reader1.readLine()) != null) {
				String[] tokens = line.split("\t");

				String s=tokens[0];
				double x= Double.valueOf(tokens[1]);
				double y= Double.valueOf(tokens[2]);

				Nodes n = new Nodes(s,x,y);
				Location l =Location.newFromLatLon(x, y);
				loc.add(l);//prepare for finding the origin
				node.put(s, n);
				//System.out.println(n);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for(Location Loc : loc) {//I want to find the most north value and the most west value and combine them to a new location as origin
			if(Loc.x < west) {
				west = Loc.x;
			}
			if(Loc.y>north) {
				north = Loc.y;
			}
		}	
		origin = new Location(west,north);
		//Read roads file/////////////////////////////////////////////////////////////////////
		BufferedReader reader2 = null;
		try {

			reader2 = new BufferedReader(new FileReader(roads));

			String line;
			line = reader2.readLine();
			while ((line = reader2.readLine()) != null) {
				String[] tokens = line.split("\t");

				int roadid=Integer.valueOf(tokens[0]);
				int type=Integer.valueOf(tokens[1]);
				String label=tokens[2];
				String city=tokens[3];
				boolean oneway=Boolean.valueOf(tokens[4]);
				int speed=Integer.valueOf(tokens[5]);
				int roadclass=Integer.valueOf(tokens[6]);
				boolean notforcar=Boolean.valueOf(tokens[7]);
				boolean notforpede=Boolean.valueOf(tokens[8]);
				boolean	notforbicy=Boolean.valueOf(tokens[9]);

				Roads r = new Roads(roadid,type,label,city,oneway,speed,roadclass,	
						notforcar,notforpede,notforbicy);
				road.add(r);//prepare for onsearch

				char charName[]=r.getLabel().toCharArray();
				//System.out.println(charName);

				addTrieNode(charName,r);
				//System.out.println(r);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//Read segments file///////////////////////////////////////////////////////////////////////
		BufferedReader reader3 = null;

		try {
			reader3 = new BufferedReader(new FileReader(segments));

			String line;
			line = reader3.readLine();

			while (true) {
				line = reader3.readLine();
				if(line==null) {break;}
				String[] tokens = line.split("\t");

				String roadId= tokens[0];
				double Length= Double.valueOf(tokens[1]);
				int nodeID1=Integer.valueOf(tokens[2]);
				int nodeID2 = Integer.valueOf(tokens[3]);

				List<Location>coordination = new ArrayList<Location>();
				for(int i = 4 ;i<tokens.length;i+=2) {
					double c = Double.valueOf(tokens[i]);
					double d = Double.valueOf(tokens[i+1]);
					Location l = Location.newFromLatLon(c,d);
					//System.out.println(l);
					coordination.add(l);
				}
				Segments s =new Segments(roadId,Length,nodeID1,nodeID2,coordination);

				for(Nodes n : node.values()) {
					if(n.getNodeId().equals(Integer.toString(nodeID1))||(n.getNodeId().equals(Integer.toString(nodeID2)))) {
						n.addSeg(s);
					}
				}
				Segment.add(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader3.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void scaleOrigin(boolean zoomIn) {//whether zoom in or zoom out
		Dimension area = getDrawingAreaDimension();
		double zoom = zoomIn ? 1 / ZoomNumber : ZoomNumber;//if zoomIn is true then return the left part of colon:
		                                                   //else zoomIn is false then return the right part of colon:

		int dx =  (int) ((area.width - (area.width * zoom)) / 2);
		int dy = (int) ((area.height - (area.height * zoom)) / 2);
		//System.out.println(dx+" "+dy);
		origin = Location.newFromPoint(new Point(dx, dy), origin, scale);
		//System.out.println(origin);
	}
	public void addTrieNode(char[]word ,Roads R) {//add trie node into TrieNode object
		TrieNode root = Root;//set node to the root of the trie
		for(char c: word) {
			if(!root.getTrie().containsKey(c)) {
				root.add(c);
				root=root.getTrie().get(c);//move node to the child corresponding to c
			}else root=root.getTrie().get(c);
		}root.getList().add(R); //add obj into node.objects
	}
	public List<Roads>getAll(char[]prefix){//get all roads similar to prefix you input
		List<Roads>results = new ArrayList<Roads>();
		TrieNode root = Root;
		for(char c : prefix) {
			if(!root.getTrie().containsKey(c)) {
				return null;
			}else root=root.getTrie().get(c);
		}
		getAllFrom(root,results);
		return results;
	}

	private void getAllFrom(TrieNode node, List<Roads> results) {//find and get all the roads that meet the criteria
		// TODO Auto-generated method stub
		for(Roads R : node.getList()) {
			results.add(R);
		}
		for(TrieNode T : node.getTrie().values()) {
			getAllFrom(T,results);
		}
	}

	public static void main(String[] args){
		new map();
	} 
}


