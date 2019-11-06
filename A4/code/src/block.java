
import java.util.List;

public class block implements RobotProgramNode{
	public List<RobotProgramNode> actions;//store the statements

	public block(List<RobotProgramNode> Actions) {
	
		this.actions = Actions;
	}

	public void execute (Robot robot) {
		for (int i = 0; i < actions.size(); i++) {//for every valid step robot should do the action correspongding to it
			actions.get(i).execute(robot);
		}
	}

	public void addNode(RobotProgramNode node) {//add a action
		actions.add(node);
	}

	public int Size(){
		return this.actions.size();
	}

	public String toString() {
		String str = "";
		for (RobotProgramNode r : actions) {
			str += "\t" + r.toString() + "\n";
		}
		return str;
	}
}
