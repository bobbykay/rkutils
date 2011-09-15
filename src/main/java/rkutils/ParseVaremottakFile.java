package rkutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ParseVaremottakFile {

	static String[] labels = new String[] {"V3-RECORD-TYPE",
		"V3-SENDER",
		"V3-MOTTAKER",
		"V3-LAGER-EAN-LOKASJON",
		"V3-BESTILLINGS-NR",
		"V3-BESTILLINGS-LINJE-NR",
		"V3-LEV-LAGER-EAN-LOK",
		"V3-MOTTATT-DATO",
		"V3-VARE-NR",
		"V3-LEVERANDOR-ART-NR",
		"V3-VARE-NAVN",
		"V3-PALL-STORRELSE",
		"V3-ANT-LAG-PR-PALL",
		"V3-HOYDE-PR-KOLLI",
		"V3-LENGDE-PR-KOLLI",
		"V3-BREDDE-PR-KOLLI",
		"V3-BRUTTO-VEKT",
		"V3-TORR-KJOL-FRYS-KODE",
		"V3-VAREGRUPPE",
		"V3-UNDERGRUPPE",
		"V3-ENVA",
		"V3-EXPRESS-FRAKT",
		"V3-KOLLI-LEVERT",
		"V3-VAREMOTTAKID",
		"V3-BESTILLINGS-NR2"};

	static int[] pos = new int[] { 2, 2, 2, 13, 7, 3, 13, 6, 7, 16, 24, 6, 3, 4, 4, 4, 4, 1, 2, 2, 4, 7, 7, 20, 10};
	
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
