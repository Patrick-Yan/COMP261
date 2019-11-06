
public class turnAround implements RobotProgramNode{
	
	public turnAround() {}
	
	@Override
	public String toString() {
		return "turnAround;";
	}

	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		robot.turnAround();
	}

}
