
public class gt implements COND {
	private ExprNode left;//sensor
	private ExprNode right;//expreesion
	
	public gt(ExprNode l, ExprNode r){
		left = l;
		right = r;
	}
	
	@Override
	public boolean compute(Robot robot) {
		if(left.compute(robot) > right.compute(robot)){// if greater than 
			return true;
		}
		return false;
	}
	
	@Override
	public String toString(){
		return "gt("+left+", "+right+")";
	}
}
