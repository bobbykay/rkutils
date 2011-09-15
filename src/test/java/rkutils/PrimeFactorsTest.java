package rkutils;

import junit.framework.TestCase;
import java.util.*;
import rkutils.PrimeFactors;


public class PrimeFactorsTest extends junit.framework.TestCase  {

	public void testOne(){
		PrimeFactors Pf = new PrimeFactors();
		
		assertEquals(Pf.generate(1).size(), new ArrayList<Integer>().size());
	}
	
	public void testTwo() {
		PrimeFactors Pf = new PrimeFactors();
		
		List<Integer> twoList = new ArrayList<Integer>();
		twoList.add(new Integer(2));

		
		assertEquals(Pf.generate(2), twoList);
	}

	public void testThree() {
		PrimeFactors Pf = new PrimeFactors();
		
		List<Integer> twoList = new ArrayList<Integer>();
		twoList.add(new Integer(2));

		assertEquals(Pf.generate(2), twoList);
	}
	
	public void testFour() {
		PrimeFactors Pf = new PrimeFactors();
		
		List<Integer> twoList = new ArrayList<Integer>();
		twoList.add(new Integer(2));
		twoList.add(new Integer(2));
		
		assertEquals(Pf.generate(4), twoList);
	}

	public void testFive() {
		PrimeFactors pf = new PrimeFactors();
		
		List<Integer> fiveList = new ArrayList<Integer>();
		fiveList.add(5);
		
		assertEquals(pf.generate(5), fiveList);
		
	}
	
	public void testEight() {
		PrimeFactors pf = new PrimeFactors();
		
		List<Integer> fiveList = new ArrayList<Integer>();
		fiveList.add(2);
		fiveList.add(2);
		fiveList.add(2);
		
		assertEquals(pf.generate(8), fiveList);
	}
	
	public void testNine() {
		PrimeFactors pf = new PrimeFactors();
		
		List<Integer> fiveList = new ArrayList<Integer>();
		fiveList.add(3);
		fiveList.add(3);
		
		assertEquals(pf.generate(9), fiveList);
	}	
}