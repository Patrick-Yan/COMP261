
public class turnL implements RobotProgramNode{
	
	//private String s;
	
	public turnL() {
		//super();
		//this.s = S;
	}
	
	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		//if(this.s=="turnL;")//   Line 332 in Robot class
		robot.turnLeft();
	}
	
	@Override
	public String toString() {
		return "turnL;";
	}
}

