
public class oppLR implements Sensor{

	@Override
	public int compute(Robot robot) {
		return robot.getOpponentLR(); //get another robot position on left and right
	}

	@Override
	public String toString(){
		return "oppLR";
	}

}
