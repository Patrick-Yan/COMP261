import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * A new instance of HuffmanCoding is created for every run. The constructor is
 * passed the full text to be encoded or decoded, so this is a good place to
 * construct the tree. You should store this tree in a field and then use it in
 * the encode and decode methods.
 */
public class HuffmanCoding {
	Queue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>();
	HashMap<Character, Integer> frequencyMap;
	HashMap<Character, String> huffmanCodeMap;
	HuffmanNode root;
	/**
	 * This would be a good place to compute and store the tree.
	 */
	public HuffmanCoding(String text) {
		// TODO fill this in.
		HashMap<Character, Integer> freq = new HashMap<>();//for storing every character's frequency
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if(!freq.containsKey(c)) {//if isn't contained then new one with frequency 1, 
				freq.put(c, 1);
			}else {//else frequency +1.
				freq.put(c, freq.get(c) + 1);
			}
		}
		this.frequencyMap= freq;
		HuffmanTree tree = new HuffmanTree();
		this.queue = tree.createTree(frequencyMap);//create the huffman tree
		this.root = tree.root;

		//construct the huffman code(huffmanCodeMap)
		HashMap<Character, String> huffmanMap = new HashMap<>();//for storing the huffman code 
		Stack<HuffmanNode> stack = new Stack<>();
		stack.push(this.root);

		while (!stack.isEmpty()) {
			HuffmanNode nodeBePoped = stack.pop();//take out the node which has been transfered to haffman code

			if (nodeBePoped.left != null ) {
				nodeBePoped.left.huffmanCode = (nodeBePoped.huffmanCode + '0');
				stack.push(nodeBePoped.left);//if left child node is not empty then add it in the stack
			}
			if (nodeBePoped.right !=null){
				nodeBePoped.right.huffmanCode=  (nodeBePoped.huffmanCode+ '1');
				stack.push(nodeBePoped.right);//if right child node is not empty then add it in the stack
			} else {//lowest(bottom) level in tree , so it means complete , so add to huffmanmap
				huffmanMap.put(nodeBePoped.letter, nodeBePoped.getCoding());
			}
		}
		//
		this.huffmanCodeMap = huffmanMap;

	}
	/**
	 * Take an input string, text, and encode it with the stored tree. Should
	 * return the encoded text as a binary string, that is, a string containing
	 * only 1 and 0.
	 */
	public String encode(String text) {
		// TODO fill this in.
		//String encoding=""
		StringBuilder encode = new StringBuilder();
		for(int index = 0; index< text.length(); index++){
			char c = text.charAt(index);
			encode.append(huffmanCodeMap.get(c));
			//encode= encode + huffmanCodeMap.get(c);
			//append the corresponding value of char to encode
		}
		return encode.toString();
	}

	/**
	 * Take encoded input as a binary string, decode it using the stored tree,
	 * and return the decoded text as a text string.
	 */
	public String decode(String encoded) {
		// TODO fill this in.
		HuffmanNode root = this.root;
		StringBuilder decoded = new StringBuilder();
		//String s = "";
		char[] charArray = encoded.toCharArray();// separate the string in to an char array
		HuffmanNode pointer = this.root; // create a pointer here and initialise with the root node
		int i=0;
		while(i < charArray.length){
			char Hcode = charArray[i];
			if(Hcode == '0') {
				pointer = pointer.left;
			}else pointer = pointer.right;
			if(pointer.left==null|| pointer.right==null) {
				decoded.append(pointer.letter);
				//s=s+pointer.letter;
				pointer = root;
			}
			i++;
		}
		return decoded.toString();
		//return s;
	}
	//	private HuffmanNode findNode(char c, HuffmanNode node) {
	//		if (node == null) return null;
	//		if (c == node.letter) return node;
	//		
	//		HuffmanNode left = findNode(c, node.left);//recursive
	//		HuffmanNode right = findNode(c, node.right);//recursive
	//		if (left != null) return left;
	//		if (right != null) return right;
	//		return null;//cannot find this node
	//	}
	/**
	 * The getInformation method is here for your convenience, you don't need to
	 * fill it in if you don't want to. It is called on every run and its return
	 * value is displayed on-screen. You could use this, for example, to print
	 * out the encoding tree.
	 */
	public String getInformation() {//for Question 2
		//StringBuilder sb = new StringBuilder("\nThe encoding of each char:\n");
		String sb = "";
		for (Map.Entry<Character, String> character : huffmanCodeMap.entrySet()) {
			sb = sb + character.getKey() + ":" + character.getValue() + "\n";
			//sb.append(character.getKey()).append(": ").append(character.getValue()).append("\n");
		}
		return sb.toString();
	}
}
