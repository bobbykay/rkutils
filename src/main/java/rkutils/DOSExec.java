package rkutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class DOSExec {
	public static void main(String[] args) {
		System.out.println("Programmet starter");
		File fil = new File("c://a//bilde.JPG");
		System.out.println("Output: " + getOwner(fil));

		System.out.println("Programmet avsluttet.");
	}

	public static String getOwner(File f) {

		String toRet = "";

		// form command string
		String cmd[] = new String[2];
		cmd[0] = "exiflist";
		cmd[1] = f.getAbsolutePath();
		//cmd[2] = " > c://a//robert.txt";

		try {

			// setup the Process and buffer
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader buffr = new BufferedReader(new InputStreamReader(p
					.getInputStream()));

			String line = null;
			while ((line = buffr.readLine()) != null) {
				toRet = toRet.concat(line);
			}

			// skriv(toRet);

		} catch (IOException ioe) {
			System.err.print("Unknown IO Error in function getOwner(): ");
			System.err.println(ioe.getMessage());
		}
		int pos = toRet.indexOf("Date/Time Taken");
		return toRet.substring(pos + 27, pos + 27 + 20);


	}

	static void skriv(String s) {
		System.out.println(s);
	}
}
