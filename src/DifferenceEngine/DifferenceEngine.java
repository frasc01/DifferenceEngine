package DifferenceEngine;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Difference<T>  {
	enum PLACEMENT {A_ONLY, B_ONLY, BOTH};
	PLACEMENT m_location;
	List<T> m_segment;

	Difference(PLACEMENT location, List<T> segment){
		m_segment = segment;
		m_location = location;
	}
	
	Difference(){
	}
	
	public void setSegment(List<T> segment) {
		m_segment = segment;
	}

	public List<T> getSegment() {
		return m_segment;
	}
	
	public void setLocation(PLACEMENT  location) {
		m_location = location;
	}

	public PLACEMENT getLocation() {
		return m_location;
	}
}

class DifferenceEngineException extends Exception {
	private static final long serialVersionUID = -1543931432350783056L;
	DifferenceEngineException(String msg) {
		super(msg);
	}
	
}

public class DifferenceEngine<T> {
	private T m_obj1;
	private T m_obj2;
	
	List<Difference<T>> diffList;
	
	DifferenceEngine(T obj1, T obj2) {
		m_obj1 = obj1;
		m_obj2 = obj2;
	}
	
	private void buildDifferenceTree() {
		
	};
	
	private void constructSuffixTree() {
		int obj1_size = Array.getLength(m_obj1);
		int obj2_size = Array.getLength(m_obj2);
		
		int[][] suffixTree = new int[obj1_size][obj2_size];

		try {
			if (!m_obj1.getClass().isArray()) {
				System.out.println("Object 1 is not an Array");
				throw new DifferenceEngineException("Object 1 is not an Array");
			}
			if (!m_obj2.getClass().isArray()) {
				System.out.println("Object 2 is not an Array");
					throw new DifferenceEngineException("Object 2 is not an Array");
			}
			int maxlen=0;
			List<Object> lcs=new ArrayList<Object>();    // Longest Common Substring to both arrays
			for (int i=0; i < obj1_size; i++) {
				for (int j=0; j < obj2_size; j++) {
					if (Array.get(m_obj1,i).equals(Array.get(m_obj2,j))) {
						if (i==0 || j == 0) {							
							suffixTree[i][j] = 1;
						} else {
							suffixTree[i][j] = suffixTree[i-1][j-1] + 1;
						}
						
						if (suffixTree[i][j] > maxlen) {
							maxlen = suffixTree[i][j];
							lcs.clear();
							for (int z = i-maxlen+1; z <= i; z++) {
								lcs.add(Array.get(m_obj1, z));
							}
							System.out.println("New Max " + maxlen);
							System.out.println("String : " + lcs.toString());
						}
					} else {						
						suffixTree[i][j]=0;
					}
				}
			}

			buildDifferenceTree();
			
			               
		} catch (DifferenceEngineException e) {
			System.out.println(e.getMessage());
		}
		
		/*
		function LCSubstr(S[1..m], T[1..n])
	    L := array(1..m, 1..n)
	    z := 0
	    ret := {}
	    for i := 1..m
	        for j := 1..n
	            if S[i] == T[j]
	                if i == 1 or j == 1
	                    L[i,j] := 1
	                else
	                    L[i,j] := L[i-1,j-1] + 1
	                if L[i,j] > z
	                    z := L[i,j]
	                    ret := {S[i-z+1..i]}
	                else
	                if L[i,j] == z
	                    ret := ret ∪ {S[i-z+1..i]}
	            else 
	                L[i,j] := 0
	    return ret
	    		
	 */
		// Print header 
		System.out.print("   ");
		for (int i=0; i < obj2_size; i++) {
			System.out.print(Array.get(m_obj2,i) + "  ");
		}
		System.out.println();
		
		for (int i=0; i < obj1_size; i++) {
			System.out.print(Array.get(m_obj1, i) + "  ");
			for (int j=0; j < obj2_size; j++) {
				System.out.print(suffixTree[i][j] + (j!=obj2_size-1?", ":""));
			}
			System.out.println();
		}
	}

	
	public List<Difference<T>> differences() {
		// Break the original arrays into sub arrays of the same type.
		// Find the Longest Common substring, then break the Files into smaller subcomponents and
		// recursively process those, until the entire file has completed.
		/// Then reconstruct the difference lists, pointing to the subcomponents. 
		
		constructSuffixTree();
		
		List<Difference<T>> diffs = new ArrayList<Difference<T>>();
		Difference<T> diff1=new Difference<T>(); 
		Difference<T> diff2=new Difference<T>(); 
		
		diff2.setSegment(new ArrayList<T>());
		diff1.getSegment().add(m_obj1);
		diff1.setLocation(Difference.PLACEMENT.A_ONLY);
		diffs.add(diff1);

		diff2.setSegment(new ArrayList<T>());
		diff2.getSegment().add(m_obj2);
		diff2.setLocation(Difference.PLACEMENT.B_ONLY);
		diffs.add(diff2);
		return diffs;
	}
	
	
	public static void usage() {
		System.out.println("java DifferenceEngine <file1> <file2>");
		System.out.println(" --- this command takes 2 parameters.");
		System.out.println(" 	 These are the 2 files we will be comparing.");
	}
	
	public static void main(String[] args) {
		if (args.length != 2)
			usage();
		else {
			String str1 = "xyzzy Hello xyzzy World Test application.";
			String str2 = "This is a Hell of an Hello World application.";

			DifferenceEngine<char[]> sde = new DifferenceEngine<char[]>(str1.toCharArray(), str2.toCharArray());
			List<Difference<char[]>> sdiffs = sde.differences();
			for (Difference diff : sdiffs) {
				System.out.println(diff.getLocation() + ":");
//				for (char[] c : diff.getSegment())
//					System.out.print(c);
				System.out.println();
			}
			
			
			int[] a1={1,2,3,4,5};
			int[] a2={10,12,2,3,4, 15, 18};
			
			DifferenceEngine<int[]> ide = new DifferenceEngine<int[]>(a1, a2);
//			List<int[]> idiffs = ide.differences();
//			for (int[] diff : idiffs) {
//				for (int i : diff)
//					System.out.print(i + ", ");
				System.out.println();
//			}
		}
	}
}
