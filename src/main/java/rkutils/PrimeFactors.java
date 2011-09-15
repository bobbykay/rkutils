package rkutils;

import java.util.List;
import java.util.ArrayList;

public class PrimeFactors {
	
	public List<Integer> generate(int n) {
		List l = new ArrayList<Integer>();
		int r = 0;
		int div = 0;

		for (int i = 2; i <= n; i++) {
			r = n % i;
			if (r == 0) {
				l.add(new Integer(i));
				div = n / i;
				l.addAll(generate(div));
				break;
			}
		}
		return l;
	}
}
