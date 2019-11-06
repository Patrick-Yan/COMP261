
public class barrelFB implements Sensor {

	private ExprNode exp;
	
	public barrelFB(ExprNode e){
		exp = e;
	}
	
	@Override
	public int compute(Robot robot) {
		return robot.getBarrelLR(exp.compute(robot));//compute the oil left in robot and add new oil from barrel
	}
	
	@Override
	public String toString(){
		return "barrelFB";
	}

}
