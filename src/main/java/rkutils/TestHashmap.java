package rkutils;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class TestHashmap {

	public static void main(String args[]) throws IOException {
		Hashtable env = new Hashtable(3);

		env.put("key1", "value1");
		env.put("key2", "value2");
		env.put("key3", "value3");

		Enumeration e = env.keys();

		String s = null, ss = null;
		while (e.hasMoreElements()) {
			Object key = (Object) e.nextElement();
			skriv("KEY: " + key + " /VALUE: " + env.get(key));
		}
	}

	private static void skriv(String string) {
		System.out.println(string);
	}
}
