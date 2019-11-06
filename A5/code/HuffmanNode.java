
public class HuffmanNode implements Comparable<HuffmanNode> {

	char letter;
	int frequency;
	HuffmanNode left;
	HuffmanNode right;
	HuffmanNode parent;
    String huffmanCode= "";//for saving HuffmanCode
    
	public HuffmanNode(char letter, int frequency) {
		super();
		this.letter = letter;
		this.frequency = frequency;
	}
	public HuffmanNode(HuffmanNode left, HuffmanNode right) {
		super();
		this.left = left;
		this.right = right;
		this.left.parent = parent;
		this.right.parent = parent;
	}
	@Override
	public int compareTo(HuffmanNode o) {
		// TODO Auto-generated method stub
		if (this.frequency < o.getFrequency()) {
            return -1;
        } else if (this.frequency > o.getFrequency()) {
            return 1;
        } else {
            return 0;
        }
	}
	//Automatic generated getter and setter
	public char getLetter() {
		return letter;
	}
	public void setLetter(char letter) {
		this.letter = letter;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public HuffmanNode getLeft() {
		return left;
	}
	public void setLeft(HuffmanNode left) {
		this.left = left;
	}
	public HuffmanNode getRight() {
		return right;
	}
	public void setRight(HuffmanNode right) {
		this.right = right;
	}
	public HuffmanNode getParent() {
		return parent;
	}
	public void setParent(HuffmanNode parent) {
		this.parent = parent;
	}
	public String getCoding() {

		return this.huffmanCode;
	}
   
}
