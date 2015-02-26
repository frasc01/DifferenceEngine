package main.java;
import java.util.ArrayList;
import java.util.Arrays;

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
			Assert.assertTrue(de.isIgnoreWhiteSpace());
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
		
		
		de = new DifferenceEngine("String1.0\nString1.1", "String2.0\nString2.1", "no_delimiter", true, true);
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
	public void testDifferenceEngineArrayArrayBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testIgnoreWhiteSpace() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsIgnoreWhiteSpace() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsIgnoreCase() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetIgnoreWhiteSpace() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetIgnoreCase() {
		fail("Not yet implemented");
	}


	@Test
	public void testGetDelimiter() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetSortFirst() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsSortFirst() {
		fail("Not yet implemented");
	}

	@Test
	public void testDifferences() {
		fail("Not yet implemented");
	}

	@Test
	public void testUsage() {
		fail("Not yet implemented");
	}

}
