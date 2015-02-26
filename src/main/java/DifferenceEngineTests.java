package main.java;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.DifferenceEngine;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.fail;




public class DifferenceEngineTests {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDifferenceEngineListOfQListOfQ() {
		Integer a1[] = {1,2,3};
		Integer a2[] = {3,4,5,6};
		
		DifferenceEngine de = new DifferenceEngine(Arrays.asList(a1), Arrays.asList(a2));
		Assert.assertNotNull(de);
		if (de != null) {
			Assert.assertEquals(3, de.getList1().size());
			Assert.assertEquals(1, de.getList1().get(0));
			Assert.assertEquals(2, de.getList1().get(1));
			Assert.assertEquals(3, de.getList1().get(2));
			Assert.assertEquals(4, de.getList2().size());
			Assert.assertEquals(3, de.getList2().get(0));
			Assert.assertEquals(4, de.getList2().get(1));
			Assert.assertEquals(5, de.getList2().get(2));
			Assert.assertEquals(6, de.getList2().get(3));
			Assert.assertEquals("Dilimiter miss match", de.getDelimiter(), "\n");
			Assert.assertFalse(de.isIgnoreWhiteSpace());
			Assert.assertFalse(de.isSortFirst());
			Assert.assertFalse(de.isIgnoreCase());
		}
	}

	@Test
	public void testDifferenceEngineStringString() {
		DifferenceEngine de = new DifferenceEngine("String1.0\nString1.1", "String2.0\nString2.1");
		Assert.assertNotNull(de);
		if (de != null) {
			Assert.assertEquals(2, de.getList1().size());
			Assert.assertEquals("String1.0", de.getList1().get(0));
			Assert.assertEquals("String1.1", de.getList1().get(1));
			Assert.assertEquals(2, de.getList2().size());
			Assert.assertEquals("String2.0", de.getList2().get(0));
			Assert.assertEquals("String2.1", de.getList2().get(1));
			Assert.assertEquals("Dilimiter miss match", de.getDelimiter(), "\n");
			Assert.assertFalse(de.isIgnoreWhiteSpace());
			Assert.assertFalse(de.isSortFirst());
			Assert.assertFalse(de.isIgnoreCase());
		}
	}

	@Test
	public void testDifferenceEngineStringStringString() {
		DifferenceEngine de = new DifferenceEngine("String1.0\nString1.1", "String2.0\nString2.1", "\n");
		Assert.assertNotNull(de);
		if (de != null) {
			Assert.assertEquals(2, de.getList1().size());
			Assert.assertEquals("String1.0", de.getList1().get(0));
			Assert.assertEquals("String1.1", de.getList1().get(1));
			Assert.assertEquals(2, de.getList2().size());
			Assert.assertEquals("String2.0", de.getList2().get(0));
			Assert.assertEquals("String2.1", de.getList2().get(1));
			Assert.assertEquals("Dilimiter miss match", de.getDelimiter(), "\n");
			Assert.assertFalse(de.isIgnoreWhiteSpace());
			Assert.assertFalse(de.isSortFirst());
			Assert.assertFalse(de.isIgnoreCase());
		}
	}

	@Test
	public void testDifferenceEngineStringStringStringBoolean() {
		DifferenceEngine de = new DifferenceEngine("String1.0\nString1.1", "String2.0\nString2.1", "\n", true);
		Assert.assertNotNull(de);
		if (de != null) {
			Assert.assertEquals(2, de.getList1().size());
			Assert.assertEquals("String1.0", de.getList1().get(0));
			Assert.assertEquals("String1.1", de.getList1().get(1));
			Assert.assertEquals(2, de.getList2().size());
			Assert.assertEquals("String2.0", de.getList2().get(0));
			Assert.assertEquals("String2.1", de.getList2().get(1));
			Assert.assertEquals("Dilimiter miss match", de.getDelimiter(), "\n");
			Assert.assertTrue(de.isIgnoreWhiteSpace());
			Assert.assertFalse(de.isSortFirst());
			Assert.assertFalse(de.isIgnoreCase());
		}
	}

	@Test
	public void testDifferenceEngineStringStringStringBooleanBoolean() {
		// Test constructor with 5 parameters
		// String, String, String,  Boolean, Boolean
		DifferenceEngine de = new DifferenceEngine("String1.0\nString1.1", "String2.0\nString2.1", "\n", true, true);
		Assert.assertNotNull(de);
		if (de != null) {
			Assert.assertEquals(2, de.getList1().size());
			Assert.assertEquals("String1.0", de.getList1().get(0));
			Assert.assertEquals("String1.1", de.getList1().get(1));
			Assert.assertEquals(2, de.getList2().size());
			Assert.assertEquals("String2.0", de.getList2().get(0));
			Assert.assertEquals("String2.1", de.getList2().get(1));
			Assert.assertEquals("Dilimiter miss match", de.getDelimiter(), "\n");
			Assert.assertTrue(de.isIgnoreWhiteSpace());
			Assert.assertTrue(de.isSortFirst());
			Assert.assertFalse(de.isIgnoreCase());
		}
	}
		
	@Test
	public void testDifferenceEngineNoDelimiterStringStringStringBooleanBoolean() {
		// Test constructor with 5 parameters
		// String, String, String,  Boolean, Boolean
		
		DifferenceEngine de = new DifferenceEngine("String1.0\nString1.1", "String2.0\nString2.1", "no_delimiter", true, true);
		Assert.assertNotNull(de);
		if (de != null) {
			Assert.assertEquals(1, de.getList1().size());
			Assert.assertEquals("String1.0\nString1.1", de.getList1().get(0));
			Assert.assertEquals(1, de.getList2().size());
			Assert.assertEquals("String2.0\nString2.1", de.getList2().get(0));
			Assert.assertEquals("Dilimiter miss match", de.getDelimiter(), "no_delimiter");
			Assert.assertTrue(de.isIgnoreWhiteSpace());
			Assert.assertTrue(de.isSortFirst());
			Assert.assertFalse(de.isIgnoreCase());
		}
	}

	@Test
	public void testDifferenceEngineArrayArray() {
		Integer a1[] = {1,2,3};
		Integer a2[] = {3,4,5,6};
		
		DifferenceEngine de = new DifferenceEngine(a1, a2);
		Assert.assertNotNull(de);
		if (de != null) {
			Assert.assertEquals(3, de.getList1().size());
			Assert.assertEquals(1, de.getList1().get(0));
			Assert.assertEquals(2, de.getList1().get(1));
			Assert.assertEquals(3, de.getList1().get(2));
			Assert.assertEquals(4, de.getList2().size());
			Assert.assertEquals(3, de.getList2().get(0));
			Assert.assertEquals(4, de.getList2().get(1));
			Assert.assertEquals(5, de.getList2().get(2));
			Assert.assertEquals(6, de.getList2().get(3));
			Assert.assertEquals("Dilimiter miss match", de.getDelimiter(), "\n");
			Assert.assertFalse(de.isIgnoreWhiteSpace());
			Assert.assertFalse(de.isSortFirst());
			Assert.assertFalse(de.isIgnoreCase());
		}
	}
	
	@Test
	public void testDifferenceEngineIntArrayIntArray() {
		int a1[] = {1,2,3};
		int a2[] = {3,4,5,6};
		
		DifferenceEngine de = new DifferenceEngine(a1, a2);
		Assert.assertNotNull(de);
		if (de != null) {
			Assert.assertEquals(3, de.getList1().size());
			Assert.assertEquals(1, de.getList1().get(0));
			Assert.assertEquals(2, de.getList1().get(1));
			Assert.assertEquals(3, de.getList1().get(2));
			Assert.assertEquals(4, de.getList2().size());
			Assert.assertEquals(3, de.getList2().get(0));
			Assert.assertEquals(4, de.getList2().get(1));
			Assert.assertEquals(5, de.getList2().get(2));
			Assert.assertEquals(6, de.getList2().get(3));
			Assert.assertEquals("Dilimiter miss match", de.getDelimiter(), "\n");
			Assert.assertFalse(de.isIgnoreWhiteSpace());
			Assert.assertFalse(de.isSortFirst());
			Assert.assertFalse(de.isIgnoreCase());
		}
	}
	
	@Test
	public void testDifferenceEngineDoubleArrayDoubleArray() {
		double a1[] = {2.718281828, 3.1415926, 1.414};
		double a2[] = {5.2, 2.718281828, 4.91, 3.1415926, 6.0221413e+23, 186000.0};
		
		DifferenceEngine de = new DifferenceEngine(a1, a2);
		Assert.assertNotNull(de);
		if (de != null) {
			Assert.assertEquals(3, de.getList1().size());
			Assert.assertEquals(2.718281828, de.getList1().get(0));
			Assert.assertEquals(3.1415926, de.getList1().get(1));
			Assert.assertEquals(1.414, de.getList1().get(2));
			Assert.assertEquals(6, de.getList2().size());
			Assert.assertEquals(5.2, de.getList2().get(0));
			Assert.assertEquals(2.718281828, de.getList2().get(1));
			Assert.assertEquals(4.91, de.getList2().get(2));
			Assert.assertEquals(3.1415926, de.getList2().get(3));
			Assert.assertEquals(6.0221413e+23, de.getList2().get(4));
			Assert.assertEquals(186000.0, de.getList2().get(5));
			Assert.assertEquals("Dilimiter miss match", de.getDelimiter(), "\n");
			Assert.assertFalse(de.isIgnoreWhiteSpace());
			Assert.assertFalse(de.isSortFirst());
			Assert.assertFalse(de.isIgnoreCase());
		}
	}

	@Test
	public void testDifferenceEngineArrayArrayBoolean() {
		double a1[] = {2.718281828, 3.1415926, 1.414};
		double a2[] = {5.2, 2.718281828, 4.91, 3.1415926, 6.0221413e+23, 186000.0};
	
		DifferenceEngine de = new DifferenceEngine(a1, a2, true);
		Assert.assertNotNull(de);
		if (de != null) {
			Assert.assertEquals(3, de.getList1().size());
			Assert.assertEquals(1.414, de.getList1().get(0));
			Assert.assertEquals(2.718281828, de.getList1().get(1));
			Assert.assertEquals(3.1415926, de.getList1().get(2));
			Assert.assertEquals(6, de.getList2().size());
			Assert.assertEquals(2.718281828, de.getList2().get(0));
			Assert.assertEquals(3.1415926, de.getList2().get(1));
			Assert.assertEquals(4.91, de.getList2().get(2));
			Assert.assertEquals(5.2, de.getList2().get(3));
			Assert.assertEquals(186000.0, de.getList2().get(4));
			Assert.assertEquals(6.0221413e+23, de.getList2().get(5));
			Assert.assertEquals("Dilimiter miss match", de.getDelimiter(), "\n");
			Assert.assertFalse(de.isIgnoreWhiteSpace());
			Assert.assertTrue(de.isSortFirst());
			Assert.assertFalse(de.isIgnoreCase());
		}	}

	@Test
	public void testIgnoreWhiteSpace() {
		String a1 = "String :  2";
		String a2 = "String :     2";
		DifferenceEngine de = new DifferenceEngine(a1, a2, ":", true);
		Assert.assertTrue(de.isIgnoreWhiteSpace());

		List<Difference> diffs = de.differences();
		Assert.assertEquals(1, diffs.size());
		Assert.assertEquals(Difference.PLACEMENT.BOTH, diffs.get(0).getLocation());
	}

	@Test
	public void testDontIgnoreWhiteSpace() {
		String a1 = "String :  2";
		String a2 = "String :     2";
		DifferenceEngine de = new DifferenceEngine(a1, a2, ":", false);
		Assert.assertFalse(de.isIgnoreWhiteSpace());
		
		List<Difference> diffs = de.differences();
		Assert.assertEquals(3, diffs.size());
		Assert.assertEquals(Difference.PLACEMENT.BOTH, diffs.get(0).getLocation());
		Assert.assertEquals("String ", diffs.get(0).getSegment().get(0));
		Assert.assertEquals(Difference.PLACEMENT.A_ONLY, diffs.get(1).getLocation());
		Assert.assertEquals("  2", diffs.get(1).getSegment().get(0));
		Assert.assertEquals(Difference.PLACEMENT.B_ONLY, diffs.get(2).getLocation());
		Assert.assertEquals("     2", diffs.get(2).getSegment().get(0));
	}

	@Test
	public void testIsIgnoreCase() {
		DifferenceEngine de = new DifferenceEngine("String1.0\nString1.1", "String2.0\nString2.1", "\n");
		Assert.assertNotNull(de);
		if (de != null) {
			Assert.assertFalse(de.isIgnoreCase());
			de.setIgnoreCase(true);
			Assert.assertTrue(de.isIgnoreCase());
		}	
	}

	@Test
	public void testSetIgnoreWhiteSpace() {
		String a1 = "String :  2";
		String a2 = "String :     2";
		DifferenceEngine de = new DifferenceEngine(a1, a2, ":", false);
		Assert.assertFalse(de.isIgnoreWhiteSpace());
		de.setIgnoreWhiteSpace(true);
		Assert.assertTrue(de.isIgnoreWhiteSpace());
		
		List<Difference> diffs = de.differences();
		Assert.assertEquals(1, diffs.size());
		Assert.assertEquals(Difference.PLACEMENT.BOTH, diffs.get(0).getLocation());
	}

	@Test
	public void testSetIgnoreCase() {
		String a1 = "STRING :  CaseTest";
		String a2 = "string :  casetest";
		DifferenceEngine de = new DifferenceEngine(a1, a2, ":");
		Assert.assertFalse(de.isIgnoreWhiteSpace());
		Assert.assertFalse(de.isIgnoreCase());
		de.setIgnoreCase(true);
		Assert.assertTrue(de.isIgnoreCase());
		
		List<Difference> diffs = de.differences();
		Assert.assertEquals(1, diffs.size());
		Assert.assertEquals(Difference.PLACEMENT.BOTH, diffs.get(0).getLocation());
	}

	@Test
	public void testGetDelimiter() {
		String a1 = "STRING :  CaseTest";
		String a2 = "string :  casetest";
		DifferenceEngine de = new DifferenceEngine(a1, a2, ":");	
		Assert.assertEquals(":", de.getDelimiter());
	}

	@Test
	public void testIsSortFirst() {
		String a1 = "String :  2";
		String a2 = "String :     2";
		DifferenceEngine de = new DifferenceEngine(a1, a2, ":", false, false);	
		Assert.assertFalse(de.isSortFirst());
		Assert.assertEquals("String ", de.getList1().get(0));
		
		de = new DifferenceEngine(a1, a2, ":", false, true);	
		Assert.assertTrue(de.isSortFirst());
		Assert.assertEquals("  2", de.getList1().get(0));
	}

	@Test
	public void testDifferences() {
		String str1 = "xyzzy Hello xyzzy\nWorld Test \nWorld Test application.";
		String str2 = "This is a Hell of a Hello World application.\nWorld Test application.\nThis is a Hell of a Hello World application.\nThis String has 4 lines in it";
		DifferenceEngine de = new DifferenceEngine(str1, str2);	
		de.differences();

		Assert.assertEquals("xyzzy Hello xyzzy", de.getList1().get(0));

	}

	@Test
	public void testUsage() {
		System.out.println("Display Usage Below");
		System.out.println("===========================================");
		DifferenceEngine.usage();
		System.out.println("===========================================");
	}

}
