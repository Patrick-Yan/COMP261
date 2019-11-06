
public class takeFuel implements RobotProgramNode{
    
	
	public takeFuel() {
		//super();//???
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		robot.takeFuel();//take fuel from barrel or robot
	}
	
	@Override
	public String toString() {
		return "takeFuel;";
	}

}
