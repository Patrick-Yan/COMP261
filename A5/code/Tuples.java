
public class Tuples {
	
	int offset;
	int length;
	Character nextCharacter;//we canoot use char here 
	//because we want to know whether it is null or not later

	public Tuples(int offset, int length, Character nextCharacter){
		this.offset = offset;
		this.length = length;
		this.nextCharacter = nextCharacter;
	}

//	public boolean haveNextCharacter() {
//		if(this.nextCharacter) {}
//		
//	}
	
	@Override
	public String toString() {
		return "[" + offset + ", " + length + ", " + nextCharacter + "]";
	}

}
