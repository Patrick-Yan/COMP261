import java.util.ArrayList;
import java.util.Map;

//import LempelZiv.Tuple;

//import LempelZiv.Tuple;

/**
 * A new instance of LempelZiv is created for every run.
 */
public class LempelZiv {
	/**
	 * Take uncompressed input as a text string, compress it, and return it as a
	 * text string.
	 */
	private int WINDOW_SIZE = 100;//10//1000
	ArrayList<Tuples> tuples = new ArrayList<>();

	public String compress(String input) {//follow the slides
		System.out.println("Window size is "+WINDOW_SIZE);
		// TODO fill this in.
		//String output="";  
		StringBuilder output = new StringBuilder();
		int cursor=0;
		while(cursor<input.length()) {
			int length=0;
			int prevMatch=-1;//=0 is false!
			while(true){
				int start;
				start=(cursor < WINDOW_SIZE)? 0 : cursor-WINDOW_SIZE;
				String pattern = input.substring(cursor, cursor + length);
				String s = input.substring(start, cursor);
				int match = s.indexOf(pattern);

				if(cursor + length >= input.length()){
					match = -1;
				}
			if(match > -1){	//match succeeded
				prevMatch = match;
					length++;
				}else {//else new a tuple
					int offset;
					offset=(prevMatch>-1)? s.length() - prevMatch : 0;
					//cursor =cursor + length + 1;
					char nextChar = input.charAt(cursor+ length - 1);
					Tuples tuple = new Tuples(offset,length - 1,nextChar);//new a tuple
					tuples.add(tuple);
					//output=output+tuple.toString();
					output.append(tuple.toString());
					cursor=cursor+length;
					break;
				}
			}
		}
		return output.toString();
	}

	/**
	 * Take compressed input as a text string, decompress it, and return it as a
	 * text string.
	 */
	public String decompress(String compressed) {
		// TODO fill this in.
		StringBuilder output = new StringBuilder();
		int cursor =0;
		for(Tuples tuple:tuples){     //each tuple in the tuple list
		if(tuple.length ==0 && tuple.offset ==0){//if [0, 0, ch ] : output[cursor++] = ch
			cursor++;
				output.append(tuple.nextCharacter);
			}else{
				output.append(output.substring(cursor - tuple.offset,cursor - tuple.offset + tuple.length));
				cursor = cursor + tuple.length;
				if (tuple.nextCharacter!=null) {//here, I know I should use Character rather than char
					output.append(tuple.nextCharacter);
				}
				cursor++;
			}
		}
		return output.toString();
	}

	/**	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't want to. It is called on every run and its return
	 * value is displayed on-screen. You can use this to print out any relevant
	 * information from your compression.
	 */
	public String getInformation() {

		StringBuilder sb = new StringBuilder();		
		for (Tuples t : tuples) {
			sb.append(t).append("\n");
		}
		return sb.toString();
	}

}

