package rkutils;

import rkutils.javagently.Text;

import java.io.*;

class RecordSammenslaaing {

	public static void main(String args[]) throws IOException {

		final String innFilNavn = "c:/javaio/tmpinnfil.txt";
		final String utFilNavn = "c:/javaio/tmputfil.txt";
		String startord = " ";

		String linje = ""; 
		String utlinje = "";

		BufferedReader innfil = Text.open(innFilNavn);
		BufferedReader inn = Text.open(System.in);
		PrintWriter utfil = Text.create(utFilNavn);
		try {
			startord = args[0].trim();
			System.out.println("startord:" + startord);
		} // try
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("startord mangler. Angi startord!");
		}
		try {
			int pos = 0 ,startpos = 0, antlest = 0, antskrevet = 0;


			linje = innfil.readLine();
			while (linje != null) {
				antlest++;
				int lengde = linje.length();
				startpos = 0;
				while ((startpos < (lengde -1)) && (linje.charAt(startpos) == ' ')) startpos++;
				if (startpos > 0) {linje = linje.substring(startpos);}
				pos = linje.indexOf(startord);
				if (!(pos < 0) && (pos < 5)) {
					pos = utlinje.lastIndexOf(");");
					if (!(pos < 0)) {
					utfil.println(utlinje.substring(0, pos) + ")" + "\"" + ");");
					}
					utlinje = "";
					linje = "setupSQL.add(" + "\"" + linje;
					antskrevet++;
				}
				utlinje += (linje + " ") ;
				linje = innfil.readLine();
			} // slutt while
			System.out.println(utlinje);

			System.out.println("Antall linjer lest: " + antlest);
			System.out.println("Antall linjer skrevet: " + antskrevet);
		} // slutt try
		catch (IOException e) {
			System.out.println("IOException!");
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println("StringIndexOutOfBoundsException!");
		} catch (NullPointerException e) {
			System.out.println("NullPointerException!");
		} finally {
			innfil.close();
			utfil.close();
		}
	}
}
