package rkutils;

import java.io.IOException;

public class GetProperty {

  
  public static void main (String args []) throws IOException {

 
	  final String HENT_ANVENDTTID = 
		   "  SELECT ANSATTNUMMER, ANVENDTTID_DT, SELSKAP_NR, " +
		   "SUM(ANVENDTTIDTOT) AS ANVENDTTIDTOT, 0 AS ARBEIDSTYPE_LNR, AVDELING_KD " +
		   "FROM (SELECT ANSATTNUMMER, ANVENDTTID_DT, SELSKAP_NR, " +
		   "ANVENDTTIDTOT, ARBEIDSTYPE_LNR, AVDELING_KD " +
		   "FROM PL001T_ANVENDTTID A " +
		   "WHERE SELSKAP_NR = '999' " +
		   "AND ANVENDTTID_DT = TRUNC (sysdate) " +
		   "AND STATUS_FLA IN ('A', 'E') " +
		   "AND ARBEIDSTYPE_LNR IN (" +
		   "      SELECT ARBEIDSTYPE_LNR " +
		   "        FROM PL015T_ARBEIDSTYPER ARB " +
		   "       WHERE A.SELSKAP_NR = ARB.SELSKAP_NR " +
		   "         AND ARB.ARBEIDSGRUPPE_KD = 'PLUKK' " +
		   "         AND ARB.PRESGIVENDE_JN = 'J')) " +
		   "GROUP BY ANSATTNUMMER, ANVENDTTID_DT, SELSKAP_NR, AVDELING_KD";

String linje;

linje = "hi i am robert";

System.out.println(HENT_ANVENDTTID);

System.out.println("pos am: " + (linje.indexOf("am")));
System.out.println("pos i: " + (linje.indexOf("i")));
System.out.println("pos hi: " + (linje.indexOf("hi")));

String version = System.getProperty("java.version");
System.out.println("version: " + version);

String string = System.getProperty("M2_HOME");

System.out.println("M2_HOME: " + string);  


string = System.getProperty("java.class.path");
System.out.println("Classpath: " + string);

string = System.getProperty("java.library.path");
System.out.println("PATH: " + string);
  } // main slutt
  



} // class GetProperty slutt
