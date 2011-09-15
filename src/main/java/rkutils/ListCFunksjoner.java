package rkutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ListCFunksjoner {

	static String[] labels = new String[] { 
		"funksjon", 
		"filnavn", 
		"linjenr"};
	static int[] pos = new int[] { 2, 6, 6, 4, 2, 6, 2, 2, 5, 8, 8, 4 };
	static int linjeNr = 0;
	static String filnavn = "", utlinje = "", sokeUttrykk = "";
	static String innfilNavn = "c://a//innfil.txt";

	public static void main(String args[]) throws Exception {

		  try {
			  innfilNavn = args[0].replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "").trim();
			  //curDir = (new String("\"" + curDir + "\""));
			  } catch (Exception e) {
				  System.out.println("Rotmappe parameter mangler");
				  }
		

			  skriv("Innfil til programmet er: " + innfilNavn);
			  
			  try {
				  sokeUttrykk = args[1];
				  } catch (Exception e) {
					  System.out.println("Rotmappe parameter mangler");
					  }
			

				  skriv("Søkeuttrykket (regulært uttrykk) til programmet er: " + sokeUttrykk);

			  BufferedWriter out = null;
		BufferedReader in = null;
		try {
			out = new BufferedWriter(new FileWriter("c://a//data.txt"));

			in = new BufferedReader(new FileReader(innfilNavn));
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
			if (str.matches("filename:.*")) {
				linjeNr = 0;
				filnavn = str.substring(11);
				filnavn = filnavn.replace("\"", "").trim();
			}
			//if (str.matches("[a-zA-Z]+[\\s]+[a-zA-Z]+[\\s]+\\(.+\\)")) {
			if (str.matches(sokeUttrykk)) {

				utlinje = str + ";" + filnavn + ";" + linjeNr;
				
				skriv(utlinje);
				out.write(utlinje + "\n");
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
