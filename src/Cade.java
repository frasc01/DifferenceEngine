import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Cade {
	private List<?> m_obj1;
	private List<?> m_obj2;

	private boolean m_ignoreWhiteSpace=false;
	private boolean m_sortFirst=false;
	private String m_delimiter="\n";

	List<Difference> diffList;
	
	Cade() {
	}

	Cade(List<?> obj1List, List<?> obj2List) {
		m_obj1 = (List<?>)obj1List;
		m_obj2 = (List<?>)obj2List;
	}
	

	Cade(String obj1, String obj2) {
		this(obj1, obj2, "\n");
	}

	Cade(String obj1, String obj2, String delimiter) {
		this(obj1, obj2, delimiter, false);
	}
	
	Cade(String obj1, String obj2, String delimiter, boolean ignoreWhiteSpace) {
		this(obj1, obj2, delimiter, ignoreWhiteSpace, false);
	}
	
	Cade(String obj1, String obj2, String delimiter, boolean ignoreWhiteSpace, boolean sortFirst) {
		m_delimiter = delimiter;
		m_ignoreWhiteSpace = ignoreWhiteSpace;
		m_sortFirst = sortFirst;
		
		if (isSortFirst()) {
			String[] a_obj1=obj1.split(m_delimiter);
			Arrays.sort(a_obj1);
			String[] a_obj2=obj2.split(m_delimiter);
			Arrays.sort(a_obj2);
			m_obj1 = (List<?>)Arrays.asList(a_obj1);
			m_obj2 = (List<?>)Arrays.asList(a_obj2);
		} else {
			m_obj1 = (List<?>)Arrays.asList(obj1.split(m_delimiter));
			m_obj2 = (List<?>)Arrays.asList(obj2.split(m_delimiter));
		}
	}
	
	Cade(Array obj1, Array obj2) {
		this(obj1, obj2, false);
	}
	
	Cade(Array obj1, Array obj2, boolean sortFirst) {
		m_sortFirst = sortFirst;	

		if (isSortFirst()) {
//			Arrays.sort(obj1);
//			Arrays.sort(obj2);
			m_obj1 = (List<?>)Arrays.asList(obj1);
			m_obj2 = (List<?>)Arrays.asList(obj2);
		} else {
			m_obj1 = (List<?>)Arrays.asList(obj1);
			m_obj2 = (List<?>)Arrays.asList(obj2);
		}
	}
	

	
	public String ignoreWhiteSpace(String inputString) {
		 // if String and ignore white space, the compress whitespace
		String outputString = inputString;
		 if (isIgnoreWhiteSpace()) {
			 outputString = outputString.replaceAll("[\t ]+", " ");    // Combine white space into a single character
			 outputString = outputString.replaceAll("^[\t ]+", "");    // remove beginning spaces
			 outputString = outputString.replaceAll("[\t ]$+", "");    // Remove Trailing spaces
			 outputString = outputString.replaceAll("[\t ]*"+ m_delimiter + "[\t ]*", m_delimiter);  // Remove Spaces surrounding a newline
		 }
		 return outputString;
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
		List lcs=new ArrayList();    // Longest Common Substring to both arrays
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

	
	public List<Difference> differences() {
		// Break the original arrays into sub arrays of the same type.
		// Find the Longest Common substring, then break the Files into smaller subcomponents and
		// recursively process those, until the entire file has completed.
		/// Then reconstruct the difference lists, pointing to the subcomponents. 
		
		constructSuffixTree();
		
		List<Difference> diffs = new ArrayList<Difference>();
		Difference diff1=new Difference(); 
		Difference diff2=new Difference(); 
		
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

			Cade sde;
//			sde = new Cade(str1, str2, "\n", true, true);
//			sde = new Cade(str1, str2, ",", true, true);
			sde = new Cade(str1, str2);
			List<Difference> diffs = sde.differences();
			for (Iterator<Difference> itr =diffs.iterator(); itr.hasNext();) {
				Difference diff = itr.next();
				System.out.println(diff.getLocation() + ":");
				for (Iterator<?> diffIterator = diff.getSegment().iterator(); diffIterator.hasNext();) {
					Object s = diffIterator.next();
					System.out.println(s.toString());
				}
			}
		}
	}
}
