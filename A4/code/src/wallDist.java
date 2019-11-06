
public class wallDist implements Sensor{

	
	@Override
	public int compute(Robot robot) {
		return robot.getDistanceToWall();//distance to wall
	}
	
	@Override
	public String toString(){
		return "wallDist";
	}
}
