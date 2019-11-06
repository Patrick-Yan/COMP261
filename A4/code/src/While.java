
public class While implements RobotProgramNode{

	private COND condition;
	private RobotProgramNode block;
	
	public While(COND c,RobotProgramNode block) {
		this.condition=c;
		this.block = block;
	}
	@Override
	public void execute(Robot robot) {
		
		while(condition.compute(robot)){//when condition now working then block working
			block.execute(robot);
		}
		
	}
	@Override
	public String toString(){
		return "while("+condition.toString()+")" +block;
	}
	
	
}
