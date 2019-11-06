import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieNode {
	private List<Roads>roadsList = new ArrayList<Roads>();
	private Map<Character, TrieNode> Trie = new HashMap<Character, TrieNode>();

	public void add(char word) {//method for creating a new TrieNode ,add a TrieNode to a trie
		TrieNode trienode =new TrieNode();
		Trie.put(word, trienode);
	}
	public List<Roads>getList(){
		return roadsList;
	}
	public Map<Character, TrieNode> getTrie(){
		return Trie;
	}
	public String toString() {
		return roadsList + " " + Trie;
	}
}