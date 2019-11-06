
public class loop implements RobotProgramNode{
	
	private RobotProgramNode block;
	
	public loop(RobotProgramNode Block) {
		this.block = Block;
	}
	
	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		while(true) {
		block.execute(robot);
		}
	}
	
	@Override
	public String toString() {
		return "loop" + block ;
	}

}
