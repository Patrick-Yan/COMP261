
public class STMT implements RobotProgramNode {//for statement

	RobotProgramNode node;

	public STMT(RobotProgramNode Node) {
		this.node = Node;
	}
	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		node.execute(robot);
	}
	@Override
	public String toString() {
		return node.toString();
	}
}
