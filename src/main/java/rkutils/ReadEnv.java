package rkutils;

import java.io.*;
import java.util.*;

public class ReadEnv {
	public static String getEnvVar(String name) throws Throwable {
		return System.getenv(name);
	}

	public static Map getEnvVars() throws Throwable {
		Map env = System.getenv();
		return env;
	}
}
