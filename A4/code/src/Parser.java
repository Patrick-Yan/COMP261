
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;
import javax.swing.JFileChooser;


/**
 * The parser and interpreter. The top level parse function, a main method for
 * testing, and several utility methods are provided. You need to implement
 * parseProgram and all the rest of the parser.
 */
public class Parser {

	/**
	 * Top level parse method, called by the World
	 */
	static RobotProgramNode parseFile(File code) {
		Scanner scan = null;
		try {
			scan = new Scanner(code);

			// the only time tokens can be next to each other is
			// when one of them is one of (){},;
			scan.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");

			RobotProgramNode n = parseProgram(scan); // You need to implement this!!!

			scan.close();
			return n;
		} catch (FileNotFoundException e) {
			System.out.println("Robot program source file not found");
		} catch (ParserFailureException e) {
			System.out.println("Parser error:");
			System.out.println(e.getMessage());
			scan.close();
		}
		return null;
	}

	/** For testing the parser without requiring the world */

	public static void main(String[] args) {
		if (args.length > 0) {
			for (String arg : args) {
				File f = new File(arg);
				if (f.exists()) {
					System.out.println("Parsing '" + f + "'");
					RobotProgramNode prog = parseFile(f);
					System.out.println("Parsing completed ");
					if (prog != null) {
						System.out.println("================\nProgram:");
						System.out.println(prog);
					}
					System.out.println("=================");
				} else {
					System.out.println("Can't find file '" + f + "'");
				}
			}
		} else {
			while (true) {
				JFileChooser chooser = new JFileChooser(".");// System.getProperty("user.dir"));
				int res = chooser.showOpenDialog(null);
				if (res != JFileChooser.APPROVE_OPTION) {
					break;
				}
				RobotProgramNode prog = parseFile(chooser.getSelectedFile());
				System.out.println("Parsing completed");
				if (prog != null) {
					System.out.println("Program: \n" + prog);
				}
				System.out.println("=================");
			}
		}
		System.out.println("Done");
	}

	// Useful Patterns

	static Pattern NUMPAT = Pattern.compile("-?\\d+"); // ("-?(0|[1-9][0-9]*)");
	static Pattern OPENPAREN = Pattern.compile("\\(");
	static Pattern CLOSEPAREN = Pattern.compile("\\)");
	static Pattern OPENBRACE = Pattern.compile("\\{");
	static Pattern CLOSEBRACE = Pattern.compile("\\}");
	static Pattern ACTIONS = Pattern.compile("move|wait|turnAround|turnL|turnR|shieldOn|shieldOff|takeFuel");
	static Pattern SENSORS = Pattern.compile("barrelFB|barrelLR|fuelLeft|numBarrels|oppFB|oppLR|wallDist");

	/**
	 * PROG ::= STMT+
	 */

	static RobotProgramNode parseProgram(Scanner s){
		//THE PARSER GOES HERE!
		List<RobotProgramNode> nodes = new ArrayList<RobotProgramNode>();
		while(s.hasNext()) {
			nodes.add(parseStmt(s));
		}
		RobotProgramNode PROG = new PROG(nodes);
		//System.out.print(PROG);
		return PROG;
	}
/////////////////////////////////////////if while loop block statement///////////////////////////////////////////////////////////////////
	static RobotProgramNode parseLoop(Scanner s) {
		if(!checkFor("loop", s)){ fail("missing loop", s); }
		return new loop(parseBlock(s));
	}
	static RobotProgramNode parseWhile(Scanner s) {
		if(!checkFor("while", s)){ fail("missing while", s); }
		if(!checkFor(OPENPAREN, s)){ fail("missing ( ", s); }
		COND c = (COND) parseCondition(s);
		if(!checkFor(CLOSEPAREN, s)){ fail("missing ) ", s); }
		block b = (block) parseBlock(s);
		return new While(c,b);
	}

	static RobotProgramNode parseBlock(Scanner s) {
		if(!checkFor(OPENBRACE, s)){ fail("missing {", s); }//System.out.print(s);
		List<RobotProgramNode> nodes = new ArrayList<RobotProgramNode>();
		while(!s.hasNext(CLOSEBRACE)) {
			nodes.add(parseStmt(s));
		}
		if(!checkFor(CLOSEBRACE,s)){ fail("missing }", s); }//check for whether there is a closebrace or not
		return new block(nodes);
	}
	static RobotProgramNode parseStmt(Scanner s) {
		RobotProgramNode node;
		if(s.hasNext("loop")){
			return parseLoop(s);//if loop then loop
		}
		else if(s.hasNext("if")){
			node = parseIf(s);//if if then do if parse
			return node;
		}
		else if(s.hasNext("while")){
			node = parseWhile(s);
			return node;
		}
		else if(s.hasNext(ACTIONS)){
			node = parseAct(s);
			return node;
		}
		fail("missing valid satement", s);
		return null;
	}
	static RobotProgramNode parseIf(Scanner s) {
		ArrayList<COND> conditions = new ArrayList<COND>();//condition list
		ArrayList<RobotProgramNode> blocks = new ArrayList<RobotProgramNode>();//block list

		if(!checkFor("if", s)){ fail("missing if", s); }
		if(!checkFor(OPENPAREN, s)){ fail("missing ( ", s); }
		conditions.add(parseCondition(s));
		if(!checkFor(CLOSEPAREN, s)){ fail("missing ) ", s); }//check ( )
		blocks.add(parseBlock(s));

		If n = new If(conditions, blocks);
		return n;
	}
	
	////////////////////////////////////////action node here/////////////////////////////////////////////////////////

	static RobotProgramNode parseAct(Scanner s){
		RobotProgramNode Node;
		if(s.hasNext("move")){
			Node = parseMove(s);
			if(!checkFor(";", s)){ fail("missing ; ", s); }
			return Node;
		}else if(s.hasNext("turnL")){
			Node = parseTurnL(s);
			if(!checkFor(";", s)){ fail("missing ; ", s); }
			return Node;
		}else if(s.hasNext("wait")){
			Node = parseWait(s);
			if(!checkFor(";", s)){ fail("missing ;", s); }
			return Node;
		}else if(s.hasNext("turnR")){
			Node = parseTurnR(s);
			if(!checkFor(";", s)){ fail("missing ;", s); }
			return Node;
		}else if(s.hasNext("takeFuel")){
			Node = parseTakeFuel(s);
			if(!checkFor(";", s)){ fail("missing ;", s); }
			return Node;
		}else if(s.hasNext("shieldOn")){
			Node = parseShieldOn(s);
			if(!checkFor(";", s)){ fail("missing ;", s); }
			return Node;
		}else if(s.hasNext("shieldOff")){
			Node = parseShieldOff(s);
			if(!checkFor(";", s)){ fail("missing ;", s); }
			return Node;
		}else if(s.hasNext("turnAround")){
			Node = parseTurnAround(s);
			if(!checkFor(";", s)){ fail("missing ;", s); }
			return Node;
		}
		fail("need valid action", s);
		return null;
	}
	static RobotProgramNode parseMove(Scanner s){
		ExprNode n = null;
		if(!checkFor("move", s)){ fail("missing move", s); }
		if(s.hasNext(OPENPAREN)){//stage 2 check () but haven't done move with parameter
			if(!checkFor(OPENPAREN, s)){ fail("missing a ( ",s); }
			n = parseExpr(s);
			if(!checkFor(CLOSEPAREN, s)){ fail("missing a ) ",s); }
		}
		return new ACT(n);
	}
	static RobotProgramNode parseTurnAround(Scanner s) {//TurnAround
		if(!checkFor("turnAround", s)){ fail("missing turnAround", s); }
		return new turnAround();
	}
	static RobotProgramNode parseTurnL(Scanner s) {//TurnF
		if(!checkFor("turnL", s)){ fail("missing turnL", s); }
		return new turnL();
	}

	static RobotProgramNode parseTurnR(Scanner s) {//TurnR
		if(!checkFor("turnR", s)){ fail("missing turnR", s); }
		return new turnR();
	}

	static RobotProgramNode parseTakeFuel(Scanner s) {//TakeFuel
		if(!checkFor("takeFuel", s)){ fail("missing takeFuel", s); }
		return new takeFuel();
	}
	static RobotProgramNode parseShieldOn(Scanner s){//ShieldOn
		if(!checkFor("shieldOn", s)){ fail("missing shieldOn", s); }
		return new shieldOn();
	}

	static RobotProgramNode parseShieldOff(Scanner s){//ShieldOff
		if(!checkFor("shieldOff", s)){ fail("missing shieldOff", s); }
		return new shieldOff();
	}
	static RobotProgramNode parseWait(Scanner s) {
		ExprNode n = null;
		if(!checkFor("wait", s)){ fail("missing wait", s); }
		if(s.hasNext(OPENPAREN)){
			if(!checkFor(OPENPAREN, s)){ fail("missing a ( ",s); }
			n = parseExpr(s);
			if(!checkFor(CLOSEPAREN, s)){ fail("missing a ) ",s); }
		}
		return new wait();//////no parameter at stage 0 and 1
	}
	static ExprNode parseExpr(Scanner s){
		if(s.hasNext(SENSORS)){ return parseSensor(s); }
		else if(s.hasNext(NUMPAT)){ return parseNum(s); }
		fail("missing valid Expression (Sensor or Number)", s);
		return null;
	}
	static ExprNode parseNum(Scanner s) {
		if(s.hasNext(NUMPAT)){
			return new NUM(s.nextInt());
		}fail("missing numbers", s);
		return null;
	}
//////////////////////////////////////////// Sensor nodes here/////////////////////////////////////////////////////////
	static Sensor parseSensor(Scanner s) {//parse Sensors : fuelLeft  oppLR  oppFB  numBarrels  barrelLR  barrelFB  wallDist
		if(s.hasNext("fuelLeft")){ 
			if(!checkFor("fuelLeft", s)){ fail("missing fuelLeft", s); }
			return new fuelLeft(); 
		}
		else if(s.hasNext("oppLR")){ 
			if(!checkFor("oppLR", s)){ fail("missing oppLR", s); }
			return new oppLR(); 
		}
		else if(s.hasNext("oppFB")){ 
			if(!checkFor("oppFB", s)){ fail("missing oppFB", s); }
			return new oppFB(); 
		}
		else if(s.hasNext("numBarrels")){ 
			if(!checkFor("numBarrels", s)){ fail("missing numBarrels", s); }
			return new numBarrels(); 
		}
		else if(s.hasNext("barrelLR")){ 
			return parseBarrelLR(s); 
		}
		else if(s.hasNext("barrelFB")){
			return parseBarrelFB(s); 
		}
		else if(s.hasNext("wallDist")){ //
			if(!checkFor("wallDist", s)){ fail("missing wallDist", s); }
			return new wallDist(); 
		}
		fail("need a valid sensor", s);
		return null;
	}
	static Sensor parseBarrelLR(Scanner s) {
		if(!checkFor("barrelLR", s)){ fail("missing barrelLR", s); }
		ExprNode n = new NUM(0);
		if(s.hasNext(OPENPAREN)){
			if(!checkFor(OPENPAREN, s)){ fail("missing ( ", s); }
			n = parseExpr(s);
			if(!checkFor(CLOSEPAREN, s)){ fail("missing ) ", s); }
		}
		return new barrelLR(n);
	}

	static Sensor parseBarrelFB(Scanner s) {
		if(!checkFor("barrelFB", s)){ fail("missing barrelFB", s); }
		ExprNode n = new NUM(0);
		if(s.hasNext(OPENPAREN)){
			if(!checkFor(OPENPAREN, s)){ fail("missing ( s", s); }
			n = parseExpr(s);
			if(!checkFor(CLOSEPAREN, s)){ fail("missing )", s); }
		}
		return new barrelFB(n);
	}

	/////////////////////////////////////////Conditions/////////////////////////////////////////////////////////////
	static COND parseCondition(Scanner s) {//parse Conditions lt gt eq
		if(s.hasNext("lt")){ return parseLT(s); }
		else if(s.hasNext("gt")){ return parseGT(s); }
		else if(s.hasNext("eq")){ return parseEQ(s); }
		fail("expected a valid COND", s);
		return null;
	}
	static COND parseLT(Scanner s) {//less than 
		if(!checkFor("lt", s)){ fail("expected lt", s); }
		if(!checkFor(OPENPAREN, s)){ fail("missing ( ", s); }
		ExprNode lhs = parseExpr(s);
		if(!checkFor(",", s)){ fail("missing , ", s); }
		ExprNode rhs = parseExpr(s);
		if(!checkFor(CLOSEPAREN, s)){ fail("missing ) ", s); }
		return new lt(lhs, rhs);
	}

	static COND parseGT(Scanner s) {//greater than
		if(!checkFor("gt", s)){ fail("missing gt", s); }
		if(!checkFor(OPENPAREN, s)){ fail("missing ( ", s); }
		ExprNode lhs = parseExpr(s);
		if(!checkFor(",", s)){ fail("missing ,", s); }
		ExprNode rhs = parseExpr(s);
		if(!checkFor(CLOSEPAREN, s)){ fail("missing ) ", s); }
		return new gt(lhs, rhs);
	}

	static COND parseEQ(Scanner s) {// equals
		if(!checkFor("eq", s)){ fail("missing eq", s); }
		if(!checkFor(OPENPAREN, s)){ fail("missing ( ", s); }
		ExprNode lhs = parseExpr(s);
		if(!checkFor(",", s)){ fail("missing , ", s); }
		ExprNode rhs = parseExpr(s);
		if(!checkFor(CLOSEPAREN, s)){ fail("missing ) ", s); }
		return new eq(lhs, rhs);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// utility methods for the parser

	/**
	 * Report a failure in the parser.
	 */
	static void fail(String message, Scanner s) {
		String msg = message + "\n   @ ...";
		for (int i = 0; i < 5 && s.hasNext(); i++) {
			msg += " " + s.next();
		}
		throw new ParserFailureException(msg + "...");
	}

	/**
	 * Requires that the next token matches a pattern if it matches, it consumes
	 * and returns the token, if not, it throws an exception with an error
	 * message
	 */
	static String require(String p, String message, Scanner s) {
		if (s.hasNext(p)) {
			return s.next();
		}
		fail(message, s);
		return null;
	}

	static String require(Pattern p, String message, Scanner s) {
		if (s.hasNext(p)) {
			return s.next();
		}
		fail(message, s);
		return null;
	}

	/**
	 * Requires that the next token matches a pattern (which should only match a
	 * number) if it matches, it consumes and returns the token as an integer if
	 * not, it throws an exception with an error message
	 */
	static int requireInt(String p, String message, Scanner s) {
		if (s.hasNext(p) && s.hasNextInt()) {
			return s.nextInt();
		}
		fail(message, s);
		return -1;
	}

	static int requireInt(Pattern p, String message, Scanner s) {
		if (s.hasNext(p) && s.hasNextInt()) {
			return s.nextInt();
		}
		fail(message, s);
		return -1;
	}

	/**
	 * Checks whether the next token in the scanner matches the specified
	 * pattern, if so, consumes the token and return true. Otherwise returns
	 * false without consuming anything.
	 */
	static boolean checkFor(String p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

	static boolean checkFor(Pattern p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

}

// You could add the node classes here, as long as they are not declared public (or private)
