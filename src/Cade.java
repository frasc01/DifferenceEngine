import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class Difference<T>  {
	enum PLACEMENT {A_ONLY, B_ONLY, BOTH};
	PLACEMENT m_location; // This determines the placement of this segment.
	List<T> m_segment;    // This is a list of the segment for this classes dataType 

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

class CadeException extends Exception {
	private static final long serialVersionUID = -1543931432350783056L;
	CadeException(String msg) {
		super(msg);
	}
	
}

public class Cade<T> {
	private List<T> m_obj1;
	private List<T> m_obj2;

	private boolean m_ignoreWhiteSpace=false;
	private boolean m_sortFirst=false;
	private String m_delimiter="\n";

	List<Difference<T>> diffList;
	
	Cade() {
	}

	Cade(List<T> obj1List, List<T> obj2List) {
		m_obj1 = obj1List;
		m_obj2 = obj2List;
	}
	
	Cade(T obj1, T obj2) throws CadeException {
		setObjects(obj1, obj2);
	}
	
	Cade(T obj1, T obj2, String delimiter) throws CadeException {
		m_delimiter=delimiter;
		setObjects(obj1, obj2);
	}
	
	Cade(T obj1, T obj2, String delimiter, boolean ignoreWhiteSpace) throws CadeException {
		m_ignoreWhiteSpace=ignoreWhiteSpace;
		m_delimiter=delimiter;
		setObjects(obj1, obj2);
	}
	
	Cade(T obj1, T obj2, String delimiter, boolean ignoreWhiteSpace, boolean sortFirst) throws CadeException {
		m_sortFirst=sortFirst;
		m_ignoreWhiteSpace=ignoreWhiteSpace;
		m_delimiter=delimiter;
		setObjects(obj1, obj2);
	}
	
	public void setObjects(T obj1, T obj2) throws CadeException {
		 if (obj1 instanceof String) {
			 // Break this String into its components.
			 String s_obj1 = (String)obj1;
			 String s_obj2 = (String)obj2;

			 // if String and ignore white space, the compress whitespace
			 if (isIgnoreWhiteSpace()) {
				 s_obj1 = s_obj1.replaceAll("[\t ]+", " ");    // Combine white space into a single character
				 s_obj2 = s_obj2.replaceAll("[\t ]+", " ");    // Combine white space into a single character
				 s_obj1 = s_obj1.replaceAll("^[\t ]+", "");    // remove beginning spaces
				 s_obj2 = s_obj2.replaceAll("^[\t ]+", "");    // remove beginning spaces
				 s_obj1 = s_obj1.replaceAll("[\t ]$+", "");    // Remove Trailing spaces
				 s_obj2 = s_obj2.replaceAll("[\t ]$+", "");    // Remove Trailing spaces
				 s_obj1 = s_obj1.replaceAll("[\t ]*"+ m_delimiter + "[\t ]*", m_delimiter);  // Remove Spaces surrounding a newline
				 s_obj2 = s_obj2.replaceAll("[\t ]*" + m_delimiter + "[\t ]*", m_delimiter);  // Remove Spaces surrounding a newline
			 }
			 System.out.println(s_obj1);
			 System.out.println(s_obj2);
			 
			 if (isSortFirst()) {
				 String[] a_obj1=s_obj1.split(m_delimiter);
				 Arrays.sort(a_obj1);
				 String[] a_obj2=s_obj2.split(m_delimiter);
				 Arrays.sort(a_obj2);
				 m_obj1 = (List<T>)Arrays.asList(a_obj1);
				 m_obj2 = (List<T>)Arrays.asList(a_obj2);
			 } else {
				 m_obj1 = (List<T>)Arrays.asList(s_obj1.split(m_delimiter));
				 m_obj2 = (List<T>)Arrays.asList(s_obj2.split(m_delimiter));
			 }
				 
		 } else if (obj1 instanceof List) {
			 // Break this String into its components.
			 List<T> list = (List<T>) obj1;
			 m_obj1 = list;
			 list = (List<T>)obj2;
			 m_obj2 = list;
		 } else {
			 throw new CadeException("Invalid Data Type for Comparible Objects");
		 }

		
	}
	
	private void buildDifferenceTree() {
		
	};
	
	public boolean isIgnoreWhiteSpace() {
		return m_ignoreWhiteSpace;
	}
	
	public void setIgnoreWhiteSpace(boolean ignoreWhiteSpace) {
		m_ignoreWhiteSpace = ignoreWhiteSpace;
	}
	
	public void setDelimiter(String delimiter) {
		m_delimiter = delimiter;
	}
	
	public String getDelimiter() {
		return m_delimiter;
	}
	
	public void setSortFirst(boolean sortFirst) {
		m_sortFirst = sortFirst;
	}
	
	public boolean isSortFirst() {
		return m_sortFirst;
	}
	
	private void constructSuffixTree() {
		int obj1_size = m_obj1.size();
		int obj2_size = m_obj2.size();
		
		int[][] suffixTree = new int[obj1_size][obj2_size];

		int maxlen=0;
		List<T> lcs=new ArrayList<T>();    // Longest Common Substring to both arrays
		for (int i=0; i < obj1_size; i++) {
			for (int j=0; j < obj2_size; j++) {
				if (m_obj1.get(i).equals(m_obj2.get(j))) {
					if (i==0 || j == 0) {							
						suffixTree[i][j] = 1;
					} else {
						suffixTree[i][j] = suffixTree[i-1][j-1] + 1;
					}
					
					if (suffixTree[i][j] > maxlen) {
						maxlen = suffixTree[i][j];
						lcs.clear();
						lcs.addAll(m_obj1.subList(i-maxlen+1, i));
						System.out.println("New Max " + maxlen);
						System.out.println("String : " + lcs.toString());
					}
				} else {						
					suffixTree[i][j]=0;
				}
			}
		}

		buildDifferenceTree();

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
		
		for (int i=0; i < obj1_size; i++) {
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
		
		diff1.setSegment(m_obj1);
		diff1.setLocation(Difference.PLACEMENT.A_ONLY);
		diffs.add(diff1);

		diff2.setSegment(m_obj2);
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
//			String str1 = "xyzzy Hello xyzzy\nWorld Test \nWorld Test application.";
//			String str2 = "This is a Hell of a Hello World application.\nWorld Test application.\nThis is a Hell of a Hello World application.\nThis String has 4 lines in it";
			String str1 = "01\n02\n03\n04\n   05\n06\n07\n08\n09\n10\n11\n12\n13\n14\n15\n16\n17\n18\n19\n20";
			String str2 = "99\n98\n03\n04\n 05\n06\n07\n08\n09\n10\n11\n12\n13\n14\n15\n16\n97\n96\n95\n19\n20\n";
//			String str1 = "01,02,03,04,   05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20";
//			String str2 = "99,98,03,04, 05,06,07,08,09,10,11,12,13,14,15,16,97,96,95,19,20,";

			Cade<String> sde;
			try {
//				sde = new Cade<String>(str1, str2, "\n", true, true);
//				sde = new Cade<String>(str1, str2, ",", true, true);
				sde = new Cade<String>(str1, str2);
				List<Difference<String>> diffs = sde.differences();
				for (Iterator<Difference<String>> itr =diffs.iterator(); itr.hasNext();) {
					Difference<String> diff = itr.next();
					System.out.println(diff.getLocation() + ":");
					for (Iterator<String> diffIterator =diff.getSegment().iterator(); diffIterator.hasNext();) {
						String s = diffIterator.next();
						System.out.println(s);
					}
				}
			} catch (CadeException e) {
				e.printStackTrace();
			}
		}
	}
}
