import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class HuffmanTree {
	HuffmanNode root;//root node of tree
	public HuffmanTree() {}//constructor
	public PriorityQueue createTree(HashMap<Character, Integer> freqencyMap) {
		//for create a frequency map 
		
		PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();

		for(Entry<Character, Integer> c : freqencyMap.entrySet()) {//Character c : freqMap.entrySet()
			HuffmanNode n = new HuffmanNode(c.getKey(), c.getValue());
			queue.add(n);
		}

		while(queue.size()>1) {
			HuffmanNode left = queue.poll();
			HuffmanNode right = queue.poll();
			HuffmanNode parent = new HuffmanNode(left, right);
			int parentFrequency = left.frequency + right.frequency;
			parent.setFrequency(parentFrequency);//set frequency of parent
			left.parent=parent;//set left and right 's parent
			right.parent=parent;
			queue.add(parent);//add it to queue and find parent's parent
			// System.out.println(parent.frequency);
		}
		//after finishing the whole tree then get the first value of tree(queue)
		this.root = queue.peek();//set the root of the tree!!
		return queue;
	}
}
