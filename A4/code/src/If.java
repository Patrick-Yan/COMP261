import java.util.List;

public class If implements RobotProgramNode{
	private List<COND> conditions;
	private List<RobotProgramNode> blocks;

	public If(List<COND> c, List<RobotProgramNode> bl){
		conditions = c;
		blocks = bl;
	}

	@Override
	public void execute(Robot robot) {
		int size = conditions.size();//list of conditions
		for(int i = 0; i <= size; i++){//every condition
			if(i == size){//when finish all action 
				break;//break
			}
			else if(conditions.get(i).compute(robot)){//compute every conditions
				blocks.get(i).execute(robot);// make robot do every action in block
				break;
			}
		}
	}

	@Override
	public String toString(){
		return  ("if(" + conditions.get(0).toString() + ")" + blocks.get(0).toString());
	}
}
