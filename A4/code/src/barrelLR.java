
public class barrelLR implements Sensor {
	
	private ExprNode exp;
	
	public barrelLR(ExprNode e){
		exp = e;
	}
	
	@Override
	public int compute(Robot robot) {
		return robot.getBarrelLR(exp.compute(robot));
	}

	@Override
	public String toString(){
		return "barrelLR";
	}

}
