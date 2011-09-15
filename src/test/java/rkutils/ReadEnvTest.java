package rkutils;

import java.io.*;
import java.util.*;

import org.junit.Ignore;

public class ReadEnvTest extends junit.framework.TestCase {

	public void testGetEnvVar() {
		try {
			String envName = "path";

			String envValue = ReadEnv.getEnvVar(envName);

			System.out.println("the current value of " + envName + " is : "
					+ envValue);
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	public void testGetEnvVars() {
		try {

			Map map = ReadEnv.getEnvVars();

			System.out.println("List of environment variables: \n");

			for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();

				System.out.println(entry.getKey() + " = " + entry.getValue());
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
