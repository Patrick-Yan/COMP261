
public class shieldOn implements RobotProgramNode {

	@Override
	public void execute(Robot robot) {
		robot.setShield(true);//take on the shield
	}
	
	@Override
	public String toString(){
		return "shieldOn;";
	}

}
