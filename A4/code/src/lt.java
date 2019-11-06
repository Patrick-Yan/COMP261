
 public class lt implements COND {
	private ExprNode left;//sensor
	private ExprNode right;//expression
	
	public lt(ExprNode l, ExprNode r){
		left = l;
		right = r;
	}

	@Override
	public boolean compute(Robot robot) {
		if(left.compute(robot) < right.compute(robot)){// if less than
			return true;
		}
		return false;
	}
	@Override
	public String toString(){
		return "lt("+left+", "+right+")";
	}
}
