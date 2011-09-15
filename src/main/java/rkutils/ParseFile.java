package rkutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ParseFile {

	static String[] labels = new String[] { "selskap", "kunde", "mdato",
			"mtid", "mlinge", "msending", "stat", "mtype", "antalltegn",
			"vare1", "varen", "tid" };
	static int[] pos = new int[] { 2, 6, 6, 4, 2, 6, 2, 2, 5, 8, 8, 4 };
	static int linjeNr = 0;

	public static void main(String args[]) throws Exception {

		BufferedWriter out = null;
		BufferedReader in = null;
		try {
			out = new BufferedWriter(new FileWriter("c://a//data.txt"));

			in = new BufferedReader(new FileReader("c://a//innfil.txt"));
			String str;
			String utlinje = "";

			for (int i = 0; i < labels.length; i++) {
				utlinje += labels[i] + ";";
			}

			skriv(utlinje);
			out.write(utlinje + "\n");

			linjeNr = 0;

			while ((str = in.readLine()) != null) {
				linjeNr++;

				process(str, out);
			}
			in.close();
		} catch (IOException e) {
		} finally {
			in.close();
			out.close();
		}

	}

	private static void process(String str, BufferedWriter out)
			throws Exception {

		String utlinje = "";
		int startPos = 0, sluttPos = 0, antallTegn;

		try {
			for (int i = 0; i < labels.length; i++) {
				antallTegn = pos[i];
				sluttPos = startPos + antallTegn;
				if (!(i == 0))
					utlinje += ";";

				utlinje += str.substring(startPos, sluttPos);

				startPos += antallTegn;
			}
			skriv(utlinje);

			out.write(utlinje + "\n");
		} catch (Exception e) {
			skriv("Feil oppstod på linje " + linjeNr + ", feiltype: " + e);
			skriv("Linjen med feil: \n" + str);
		}
	}

	private static void skriv(String s) {
		System.out.println(s);
	}
}
