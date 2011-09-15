package rkutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ParseTransportorFakturaLinjeFile {

	static String[] labels = new String[] { "F2-RECORD-TYPE", "F2-SENDER",
			"F2-MOTTAKER", "F2-LAGER-EAN-LOKASJON", "F2-BESTILLINGS-NR",
			"F2-TORR-KJOL-FRYS-KODE", "F2-ASG-KUNDE-NR",
			"F2-LEV-LAGER-EAN-LOK", "F2-LEVERT-FRA-POST-NR",
			"F2-LEVERT-FRA-LAND", "F2-LEVERT-TIL-POST-NR",
			"F2-FAKTURA-BEREGN-KODE", "F2-FRAKT-VEKT(7.3)", "F2-FRAKT-RATE(6.2)",
			"F2-FRAKT-SUM(7.2)", "F2-SAMLE-REF-NR", "F2-ASG-FAKTURA-NR",
			"F2-ASG-FAKTURA-DATO", "F2-MINSTE-FRAKT-VEKT", "F2-MOTTAT-DATO", "F2-LEVERANDOR-GRUPPE-LNR",
			"F2-FULLBIL-PALLANTALL", "F2-BESTILLINGS-NR2"};

	static int[] pos = new int[] { 2, 2, 2, 13, 7, 1, 8, 13, 5, 2, 5, 1, 10, 8,
			9, 8, 8, 6, 5, 6, 9, 3, 10};
	
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
