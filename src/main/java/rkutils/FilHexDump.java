package rkutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class FilHexDump {
	static final String[] hexdef = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};


	public static void main(String args[]) throws IOException {
		skriv("--- Program starts ---", false);


		File fil = new File("c:/a/klient.log");
		File nyFil = new File("c:/a/robert.txt");

		copyFile(fil, nyFil);
		
		skriv("Programmet avslutter...", false);
	} // main slutt

	private static void skriv(String string) {
		skriv(string, false);
	}

	private static void skriv(String string, boolean isDebugMessage) {
		System.out.println(string);
	}

	public static void copyFile(File src, File dst) throws IOException {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len, result = 0;
		
		boolean ascii0D = false;
		boolean ascii0A = false;
		
		while ((len = in.read(buf)) > 0) {
			for (int i = 0; i < len; i++) {
				result = 0;
				for (int j = 0; j < 8; j++) {
					long maske = (long)Math.pow(2, j);
//					System.out.println("maske: " + maske);
				    if ((maske & buf[i]) > 0) {
				    	result+= (int)maske;
				    }
				}
//				System.out.println((buf[i]) + " " + result);
				
				int sif1 = result/16;
				int sif2 = result - (sif1 * 16);
				
				String hexverdi = hexdef[sif1] + hexdef[sif2];
				
				System.out.print(hexverdi + " ") ;
				
				if (hexverdi.equalsIgnoreCase("0D")) ascii0D = true;
				if (hexverdi.equalsIgnoreCase("0A")) ascii0A = true;
				
				if (ascii0A && ascii0D) {
					System.out.println("");
					ascii0D = false;
					ascii0A = false;					
				}
			}
			// out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}
}
