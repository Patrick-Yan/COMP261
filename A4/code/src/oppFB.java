
public class oppFB implements Sensor {

	@Override
	public int compute(Robot robot) {
		return robot.getOpponentFB(); //get another robot position on front and back
	}

	@Override
	public String toString(){
		return "oppFB";
	}

}
