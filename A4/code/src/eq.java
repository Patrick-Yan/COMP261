
public class eq implements COND {
	private ExprNode left;//sensor
	private ExprNode right;//expression
	
	public eq(ExprNode l, ExprNode r){
		left = l;
		right = r;
	}
	
	@Override
	public boolean compute(Robot robot) {
		if(left.compute(robot) == right.compute(robot)){//if oil is equals
			return true;
		}
		return false;
	}

	@Override
	public String toString(){
		return "eq("+left+"---"+right+")";
	}
}
