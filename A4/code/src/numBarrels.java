
public class numBarrels implements Sensor {

	@Override
	public int compute(Robot robot) {
		return robot.numBarrels();//return the barrels in board
	}

	@Override
	public String toString(){
		return "numBarrels";
	}

}
