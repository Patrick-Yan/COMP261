import java.util.List;

public class PROG implements RobotProgramNode{
	public List<RobotProgramNode> movements;//list of actions

	public PROG(List<RobotProgramNode> nodes) {
		this.movements = nodes;
	}

	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		for (int i = 0; i < this.movements.size(); i++) {//for every action
			movements.get(i).execute(robot);
		}
	}

	public void add(RobotProgramNode node) {
		this.movements.add(node);
	}
	public int size() {
		return movements.size();
	}

	@Override
	public String toString() {
		String str ="";
		for(RobotProgramNode m :movements) {
			str += m.toString() + "\n";
		}
		return str;
	}

}
