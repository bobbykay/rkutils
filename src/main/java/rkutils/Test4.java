package rkutils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Test4 {

	static NumberFormat currencyFormat = null;

	public static void main(String args[]) throws IOException {

		final String HENT_ANVENDTTID = "  SELECT ANSATTNUMMER, ANVENDTTID_DT, SELSKAP_NR, "
				+ "SUM(ANVENDTTIDTOT) AS ANVENDTTIDTOT, 0 AS ARBEIDSTYPE_LNR, AVDELING_KD "
				+ "FROM (SELECT ANSATTNUMMER, ANVENDTTID_DT, SELSKAP_NR, "
				+ "ANVENDTTIDTOT, ARBEIDSTYPE_LNR, AVDELING_KD "
				+ "FROM PL001T_ANVENDTTID A "
				+ "WHERE SELSKAP_NR = '999' "
				+ "AND ANVENDTTID_DT = TRUNC (sysdate) "
				+ "AND STATUS_FLA IN ('A', 'E') "
				+ "AND ARBEIDSTYPE_LNR IN ("
				+ "      SELECT ARBEIDSTYPE_LNR "
				+ "        FROM PL015T_ARBEIDSTYPER ARB "
				+ "       WHERE A.SELSKAP_NR = ARB.SELSKAP_NR "
				+ "         AND ARB.ARBEIDSGRUPPE_KD = 'PLUKK' "
				+ "         AND ARB.PRESGIVENDE_JN = 'J')) "
				+ "GROUP BY ANSATTNUMMER, ANVENDTTID_DT, SELSKAP_NR, AVDELING_KD";

		String linje;

		linje = "hi i am robert";

		System.out.println(HENT_ANVENDTTID);

		System.out.println("pos am: " + (linje.indexOf("am")));
		System.out.println("pos i: " + (linje.indexOf("i")));
		System.out.println("pos hi: " + (linje.indexOf("hi")));

		String version = System.getProperty("java.version");
		System.out.println("version" + version);

		BigDecimal bd = new BigDecimal(-5);

		System.out.println("BigDecimal compareto zero: "
				+ bd.compareTo(new BigDecimal(0)));

		if (bd.compareTo(new BigDecimal(0)) == 0) {
			System.out.println("BigDecimal likhet");
		} else {
			System.out.println("BigDecimal ulikhet");

			Timestamp ts = Timestamp.valueOf("2005-01-17 00:00:00");
			System.out.println("timestamp: " + ts);

			
			String filnavn = "c://a//data.txt";
			
			File f = new File(filnavn);

			double tillegg = ((1024.0 * 1024.0) * new Double("0.5"));

			long filLengde = f.length() + (long) tillegg;

			skriv("Fillengden til filen " + filnavn + " er: " + filLengde);

			long filLengdekB = Math.round(filLengde / 1024);

			long filLengdeMB = Math.round((filLengde / (1024 * 1024)));

			skriv("Fillengden i kB: " + filLengdekB);
			skriv("Fillengden i MB: " + filLengdeMB);
			
			Long sistEndret = f.lastModified();
			
			Date sistEndretDato = new Date(sistEndret);
			
			skriv("Filen sist endret: " + sistEndretDato);
			
			String string1 = "a";
			String string2 = "0";
			
			int i = string1.compareTo(string2);
			
			System.out.println(string1 + " compareto " + string2 + " = " + i);
			
			

		}
	}

	private static void skriv(String string) {
		System.out.println(string);

	} // main slutt

} // class Test4 slutt
