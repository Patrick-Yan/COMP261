
public class move implements RobotProgramNode{
	
	public move() {
	}

	@Override
	public String toString() {
		return "move;";
	}

	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		robot.move();

	}

}
