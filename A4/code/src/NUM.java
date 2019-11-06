
public class NUM implements ExprNode {

private int value;//the value of number
	
	public NUM(int v){
		value = v;
	}
	
	@Override
	public int compute(Robot robot) {
		return value;
	}

	@Override
	public String toString(){
		return ""+value;
	}

}
