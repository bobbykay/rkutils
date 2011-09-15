package rkutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ParseFakturaSpesifikasjonFile {

	static String[] labels = new String[] { "FS-RECORD-TYPE", "FS-SENDER",
			"FS-MOTTAKER", "FS-LAGER-EAN-LOKASJON", "FS-BESTILLINGS-NR",
			"FS-BESTILLINGS-LINJE-NR", "FS-TORR-KJOL-FRYS-KODE",
			"FS-ASG-FAKTURA-NR", "FS-ASG-FAKTURA-DATO", "FS-SAMLE-REF-NR",
			"FS-LEVERINGS-DATO", "FS-LEVERT-KVANTUM", "FS-VEKT-VOLUM-GRENSE",
			"FS-BESTILLINGS-NR2" };

	static int[] pos = new int[] { 2, 2, 2, 13, 7, 3, 1, 8, 6, 8, 6, 7, 5, 10 };

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
			skriv("Feil oppstod p� linje " + linjeNr + ", feiltype: " + e);
			skriv("Linjen med feil: \n" + str);
		}
	}

	private static void skriv(String s) {
		System.out.println(s);
	}
}
