
public class ACT implements RobotProgramNode{
	private ExprNode exp;

	public ACT(ExprNode e){
		exp = e;
	}

	@Override
	public void execute(Robot robot) {
		if(exp == null){ robot.move(); }
		else{
			int stop = exp.compute(robot);//compute the total oil left
			for(int i = 0; i < stop; i++){
				robot.move();//how many steps can robot go
			}
		}
	}

	@Override
	public String toString(){
		if( exp != null){
			return "move("+exp+");";
		}
		return "move;";
	}
}
