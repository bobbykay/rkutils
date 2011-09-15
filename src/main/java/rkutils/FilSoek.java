package rkutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilSoek {

	static int linjeNr = 0;
	static String filnavn = "", utlinje = "", sokeUttrykk = "";
	static String innfilNavn = "c://a//innfil.txt";
	private static Pattern pattern;
	private static Matcher matcher;

	public static void main(String args[]) throws Exception {

		try {
			innfilNavn = args[0].replaceAll("\\\\", "\\\\\\\\").replaceAll(
					"\"", "").trim();
			// curDir = (new String("\"" + curDir + "\""));
		} catch (Exception e) {
			System.out.println("Rotmappe parameter mangler");
		}

		skriv("Innfil til programmet er: " + innfilNavn);

		try {
			sokeUttrykk = args[1];
		} catch (Exception e) {
			System.out.println("Søkeuttrykk parameter mangler");
		}

		skriv("Søkeuttrykket (regulært uttrykk) til programmet er: "
				+ sokeUttrykk);

		pattern = Pattern.compile(sokeUttrykk);

		BufferedWriter out = null;
		BufferedReader in = null;
		try {
			out = new BufferedWriter(new FileWriter("c://a//data.txt"));

			in = new BufferedReader(new FileReader(innfilNavn));
			String str;
			String utlinje = "";

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
	        matcher = pattern.matcher(str);

	        boolean forsteGang = true;
	        
			while (matcher.find()) {

				utlinje = linjeNr + ";" + str;

				skriv(utlinje);

				if (forsteGang) {
					out.write(utlinje + "\n");
				}
				
//				String repl = matcher.replaceAll(matcher.group(1));

				utlinje = "Gruppen \\1 består av teksten: " + matcher.group(1);
				
                skriv("Fant teksten " 
                		+ matcher.group() 
                		+ " som startet ved indeks " 
                		+ matcher.start()
                		+ " og sluttet ved indeks "
                		+ matcher.end());
				skriv(utlinje);

					if (forsteGang) {
						forsteGang = false;
				}
			}

		} catch (Exception e) {
			skriv("Feil oppstod på linje " + linjeNr + ", feiltype: " + e);
			skriv("Linjen med feil: \n" + str);
		}
	}

	private static void skriv(String s) {
		System.out.println(s);
	}
}
