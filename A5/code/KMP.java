/**
 * A new KMP instance is created for every substring search performed. Both the
 * pattern and the text are passed to the constructor and the search method. You
 * could, for example, use the constructor to create the match table and the
 * search method to perform the search itself.
 */
public class KMP {//just follow the pseudocode in slides
	int[] table;
	//building the table
	public KMP(String pattern, String text) {
		// TODO maybe fill this in.
		//long start = System.currentTimeMillis();
		table =new int[pattern.length()];
		table[0]=-1;
		//table[1]=0;
		int j = 0 ;
		int pos = 1;

		while(pos<pattern.length()-1) {
			if(pattern.charAt(pos)==pattern.charAt(j)) {
				table[pos]=j+1;
				pos++;
				j++;
			}else if(j>0){
				j=table[j];
			}else {//j=0
				table[pos]=0;
				pos++;
			}
		}
		//long end = System.currentTimeMillis();
		//System.out.println("constructor : "+(end-start));
	}

	/**
	 * Perform KMP substring search on the given text with the given pattern.
	 * 
	 * This should return the starting index of the first substring match if it
	 * exists, or -1 if it doesn't.
	 */

	public int search(String pattern, String text) {
		//System.out.println("KMPsearch : ");
		int k = 0;
		int i = 0;
		long start = System.currentTimeMillis();
		while(k+i<text.length()) {
			if(pattern.charAt(i)==text.charAt(i+k)){
				i++;
				if(i==pattern.length()) {
					long end = System.currentTimeMillis();
					System.out.println("KMPsearsh : "+Math.abs(end-start));
					return k;
				}
			}else if(table[i]==-1) {
				i=0;
				k=k+i+1;

			}else {
				k=k+i-table[i];
				i=table[i];
			}
			//long end = System.currentTimeMillis();
		}
		// TODO fill this in.
//		long end = System.currentTimeMillis();
//		System.out.println("KMPsearsh : "+(end-start));
		return -1;
	}

	/**
	 * Perform Brute force algorithm search substring search on the given text with the given pattern.
	 *
	 * This should return the starting index of the first substring match if it
	 * exists, or -1 if it doesn't.
	 */

	public int bruteSearch(String pattern, String text) {
		
		long start = System.currentTimeMillis();
		int p = pattern.length();
		int t = text.length();
		boolean found;
		
		if(p <1)return -1;
		//if pattern doesn't exist or searching text length shorter than pattern then return -1
		for(int i = 0; i < t-p+1;i++){
			found = true;
			for(int j = 0; j < p;j++){
				if(pattern.charAt(j) != text.charAt(i+j)){
					found = false;
					break;
				}
			}
			if(found == true) {
				long end = System.currentTimeMillis();
				System.out.println("bruteSearsh : "+Math.abs(end-start));
				return i;
			}
		}
//		long end = System.currentTimeMillis();
//		System.out.println("bruteSearsh : "+(end-start));
		return -1;
	}
}


