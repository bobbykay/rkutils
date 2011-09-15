package rkutils;

import java.io.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.StringTokenizer;

public class Gange2 {

	public static void main(String[] args) {

		boolean ferdig = false;

		boolean riktigSvar = false;
		
		int MAX_TID = new Integer(args[0]);
		
		Long tms = System.currentTimeMillis();
		Long tms2 = new Long(0);

		int forbruktTid = 0;
		int antallRiktige = 0;
		int antallFeil = 0;
		
      	Timestamp dato = new Timestamp(tms);
       	skriv("Starttid: " + dato);

       	skriv(tms.toString());
       	
		while (!ferdig) {

			int tall1 = (int) (Math.random() * 10);
			int tall2 = (int) (Math.random() * 10);

			skriv("Tall1: : " + tall1);
			skriv("Tall2: : " + tall2);

			skriv("Hva er: " + tall1 + " X " + tall2 + " ?");

			// skriv("pos av komma:" + kommapos);

			// skriv("Tall1: : " + tall1);
			// skriv("Tall2: : " + tall2);

			int produkt = tall1 * tall2;

			// open up standard input
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));

			String lestLinje = null;

			int svar = 0;

			riktigSvar = false;

			while (!riktigSvar) {

				try {
					lestLinje = br.readLine();
				} catch (IOException ioe) {
					skriv("Programmet feilet. Start programmet på nytt:-)");
					System.exit(1);
				}

				svar = new Integer(lestLinje);

				//skriv("Tallet: " + lestLinje);

				if (svar == produkt || svar == 999) {
					riktigSvar = true;

					antallRiktige++;
					
					if (svar == produkt) {

						skriv("*************************************");
						skriv("***  Bra!! Det er riktig svar:-)  ***");
						skriv("*************************************");

					}

				} else {
					antallFeil++;
					
					skriv("Godt forsøk, men ikke riktig:-), prøv igjen:");
				}

			}

	       	tms2 = System.currentTimeMillis();
	       	
	       	forbruktTid = (int)((tms2 - tms)/1000);
			
			
			if (svar == 999 || forbruktTid > MAX_TID)
				ferdig = true;

		}

		skriv("");
		skriv("");
		skriv("");
		skriv("*************** Takk for denne gangen, velkommen tilbake!!  **********************");

      	 Timestamp dato2 = new Timestamp(System.currentTimeMillis());
      	 
      	 
       	 skriv("Sluttid: " + dato);
       	
       	 float f = (float)((new Float(antallRiktige)) * 60.0)/ new Float(forbruktTid);
       	
       	 int antallPerMinutt = Math.round(f);
       	 
       	 if (true) {
           	 skriv("**** Nå er det gått " + forbruktTid + " sekunder og tiden er ute ****");
           	 skriv("");
           	 skriv("Du har klart " + antallRiktige + " svar som tilsvarer " + antallPerMinutt + " riktige svar per minutt");
           	 skriv("Antall feil svar er: "+ antallFeil);
       	 }
       	 //skriv("**** Forbrukt tid i sekunder: " + forbruktTid + " ****");

	}

	public static void skriv(String s) {
		System.out.println(s);
	}
} // end of Gange2 class

