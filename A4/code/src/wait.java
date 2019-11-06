

public class wait implements RobotProgramNode{

	public wait(){
		
	}
	
	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		 robot.idleWait(); 
		
	}
	
	@Override
	public String toString(){
		
		return "wait;";
	}
//	ExprNode exp;
//	public wait(ExprNode e){
//		exp = e;
//	}
//	
//	@Override
//	public void execute(Robot robot) {
//		// TODO Auto-generated method stub
//		if(exp == null){ robot.idleWait(); }
//		else{
//			int stop = exp.compute(robot);
//			for(int i = 0; i > stop; i++){
//				robot.idleWait();
//			}
//		}	
//	}
//	
//	@Override
//	public String toString(){
//		if( exp != null){
//			return "wait("+exp+");";
//		}
//		return "wait;";
//	}
//	
}
