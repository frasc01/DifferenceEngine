import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class DifferenceEngine {
	private List<?> m_obj1;
	private List<?> m_obj2;

	private boolean m_ignoreWhiteSpace=false;
	private boolean m_ignoreCase=false;
	private boolean m_sortFirst=false;
	private String m_delimiter="\n";

	List<Difference> diffList;
	
	DifferenceEngine() {
	}

	DifferenceEngine(List<?> obj1List, List<?> obj2List) {
		m_obj1 = (List<?>)obj1List;
		m_obj2 = (List<?>)obj2List;
	}
	

	DifferenceEngine(String obj1, String obj2) {
		this(obj1, obj2, "\n");
	}

	DifferenceEngine(String obj1, String obj2, String delimiter) {
		this(obj1, obj2, delimiter, false);
	}
	
	DifferenceEngine(String obj1, String obj2, String delimiter, boolean ignoreWhiteSpace) {
		this(obj1, obj2, delimiter, ignoreWhiteSpace, false);
	}
	
	DifferenceEngine(String obj1, String obj2, String delimiter, boolean ignoreWhiteSpace, boolean sortFirst) {
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
	
	DifferenceEngine(Array obj1, Array obj2) {
		this(obj1, obj2, false);
	}
	
	DifferenceEngine(Array obj1, Array obj2, boolean sortFirst) {
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
	
	public boolean isIgnoreWhiteSpace() {
		return m_ignoreWhiteSpace;
	}
	
	public boolean isIgnoreCase() {
		return m_ignoreCase;
	}
	
	public void setIgnoreWhiteSpace(boolean ignoreWhiteSpace) {
		m_ignoreWhiteSpace = ignoreWhiteSpace;
	}
	
	public void setIgnoreCase(boolean ignoreCase) {
		m_ignoreCase = ignoreCase;
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
	
	private boolean isSame(Object obj1, Object obj2) {
		boolean rc=false;
		if((obj1 instanceof String) &&
	       (obj2 instanceof String)) {
			if (isIgnoreCase()) {
				rc = ignoreWhiteSpace(obj1.toString()).equalsIgnoreCase(ignoreWhiteSpace(obj2.toString()));
			} else {
		       rc = ignoreWhiteSpace(obj1.toString()).equals(ignoreWhiteSpace(obj2.toString()));
			}
		} else {
			rc = obj1.equals(obj2);			
		}
		return rc;
	}
	
	
	private List<Difference> constructSuffixTree(List<?> objList1, List<?> objList2) {
		List<Difference> allDiffs = new ArrayList<Difference>();
		
		int obj1_size = objList1.size();
		int obj2_size = objList2.size();
		
		if (obj1_size == 0 && obj2_size == 0)
			return allDiffs;
		
		int[][] suffixTree = new int[obj1_size][obj2_size];

		int maxlen=0;
		int maxi=0, maxj=0;
		List lcs=new ArrayList();    // Longest Common Substring to both arrays
		for (int i=0; i < obj1_size; i++) {
			Object obj1=objList1.get(i);
			for (int j=0; j < obj2_size; j++) {
				Object obj2=objList2.get(j);
				if (isSame(obj1, obj2)) {
					if (i==0 || j == 0) {							
						suffixTree[i][j] = 1;
					} else {
						// Use the previous diagonal as your counter and increment
						suffixTree[i][j] = suffixTree[i-1][j-1] + 1;
					}
					
					if (suffixTree[i][j] > maxlen) {
						maxlen = suffixTree[i][j];
						lcs.clear();
						lcs.addAll(objList1.subList(i-maxlen+1, i+1));
						/*
						 * 
						System.out.println("New Max " + maxlen);
						System.out.println("String : " + lcs.toString());
						 */
						
						// Save the Location of the new Max
						maxi = i;
						maxj = j;
					}
				} else {						
					suffixTree[i][j]=0;
				}
			}
		}

		List<Difference> currentDiffs = new ArrayList<Difference>();
		if (maxlen == 0) {
			//  The 2 segments are completely distinct
			// Create a segment for each 
			if (obj1_size > 0) {
				Difference diff=new Difference(); 
				
				diff.setSegment(objList1);
				diff.setLocation(Difference.PLACEMENT.A_ONLY);
				currentDiffs.add(diff);
			}
			if (obj2_size > 0) {
				Difference diff=new Difference(); 
				
				diff.setSegment(objList2);
				diff.setLocation(Difference.PLACEMENT.B_ONLY);
				currentDiffs.add(diff);
			}
			allDiffs = currentDiffs;
		} else {
			Difference diff=new Difference(); 
			
			diff.setSegment(lcs);
			diff.setLocation(Difference.PLACEMENT.BOTH);
			currentDiffs.add(diff);
			
			List<Difference> preDiffs;
			List<Difference> postDiffs;
			// construct the suffix tree from the pre-lcs  subset 
			List<?> pre_lcsobjList1 = new ArrayList();
			List<?> pre_lcsobjList2 = new ArrayList();
			if (maxi-maxlen+1 >= 0)
				pre_lcsobjList1= objList1.subList(0, maxi-maxlen+1);
			if (maxj-maxlen+1 >= 0)
				pre_lcsobjList2= objList2.subList(0, maxj-maxlen+1);
			preDiffs = constructSuffixTree(pre_lcsobjList1, pre_lcsobjList2);
			
			List<?> post_lcsobjList1 = new ArrayList();
			List<?> post_lcsobjList2 = new ArrayList();
			if (maxi+1 <= objList1.size()-1)
				post_lcsobjList1 = objList1.subList(maxi+1, objList1.size());

			if (maxj+1 <= objList2.size()-1) 
				post_lcsobjList2= objList2.subList(maxj+1, objList2.size());
			postDiffs = constructSuffixTree(post_lcsobjList1, post_lcsobjList2);
			// construct the suffix tree from the post-lcs subset
			
			// Join the subsets together, and return the full difference tree.
			currentDiffs.addAll(postDiffs);
			preDiffs.addAll(currentDiffs);
			allDiffs = preDiffs;
			
		}
		
		
		// Print out the suffix tree
		/*
		 * 
		for (int i=0; i < obj1_size; i++) {
			for (int j=0; j < obj2_size; j++) {
				System.out.print(suffixTree[i][j] + (j!=obj2_size-1?", ":""));
			}
			System.out.println();
		}
		 */
		return allDiffs;
	}

	

	/**
	 * 
	 * Break the original arrays into sub arrays of the same type.
	 * Find the Longest Common substring, then break the Files into smaller subcomponents and
	 * recursively process those, until the entire file has completed.
	 * Then reconstruct the difference lists, pointing to the subcomponents. 
	 */
	public List<Difference> differences() {
		List<Difference> diffs = constructSuffixTree(m_obj1, m_obj2);
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
//			String str1 = "01\n02\n03\n04\n   05\n06\n07\n08\n09\n10\n11\n12\n13\n14\n15\n16\n19\n20";
			String str2 = "67\n99\n98\n03\n04\n 05\n06\n07\n08\n09\n10\n11\n12\n13\n14\n15\n16\n97\n96\n95\n19\n20\n";
//			String str2 = "67\n99\n98\n03\n04\n 05\n06\n07\n08\n09\n10\n11\n12\n13\n14\n15\n16\n19\n20\n";
//			String str1 = "01,02,03,04,   05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20";
//			String str2 = "99,98,03,04, 05,06,07,08,09,10,11,12,13,14,15,16,97,96,95,19,20,";

			DifferenceEngine sde;
			
			
			String f1Data="";
			String f2Data="";
			try {
				f1Data = new Scanner(new File(args[0])).useDelimiter("\\Z").next();
				f2Data = new Scanner(new File(args[1])).useDelimiter("\\Z").next();
			} catch (FileNotFoundException e) {
				//  Don't worry about the file not found until you are reading the files
			}
			
//			sde = new DifferenceEngine(str1, str2, "\n", true, true);
//			sde = new DifferenceEngine(str1, str2, ",", true, true);
			sde = new DifferenceEngine(str1, str2);
//			sde = new DifferenceEngine(f1Data, f2Data, "\n", true);
			
			// Loop through the differences and print them out
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
