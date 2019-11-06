
public class turnR implements RobotProgramNode{
	
	//private String s;
	
	public turnR() {
		//this.s = index;
	}
	
	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		//if(this.s=="turnR;")
		robot.turnRight();
	}
	
	@Override
	public String toString() {
		return "turnL;";
	}
}
