package rkutils;

import rkutils.javagently.Text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

class FilBehandling4 {

	public static void main(String args[]) throws IOException {

		String version = System.getProperty("java.version");
		System.out.println("java versjon: " + version);

		FilBehandling4 f = new FilBehandling4();

		try {
			if (args.length > 0)
				f.mldInd = args[0].trim();
		} // try
		catch (ArrayIndexOutOfBoundsException e) {
		}

		f.lesogBehParamfil();
		if (args.length > 1 && args[1].trim().equalsIgnoreCase("sort")) {
			f.sortInnfiler();
		}
		f.definerFelt();
		f.mldInd = "N";
		f.lagUtfil();
		f.lagreclofil();

	} // main slutt

	private void sortInnfiler() throws IOException {
		ArrayList list = new ArrayList();
		String s;
		String srtfil1Navn = "c:/javaio/fbsrtfil1.txt";

		PrintWriter fil1ut = null;
		BufferedReader srtfil1 = null;

		try {
			srtfil1 = Text.open(srtfil1Navn);

			for (s = srtfil1.readLine(); !(s == null); s = srtfil1.readLine()) {
				list.add(s);
			}
			Collections.sort(list);

			fil1ut = Text.create(innfil1Navn);

			for (Iterator itList = list.iterator(); itList.hasNext();) {
				s = (String) itList.next();

				fil1ut.println(s);
			}
		} finally {
			fil1ut.close();
			srtfil1.close();
		}

		list = new ArrayList();
		String srtfil2Navn = "c:/javaio/fbsrtfil2.txt";
		PrintWriter fil2ut = null;
		BufferedReader srtfil2 = null;

		try {
			srtfil2 = Text.open(srtfil2Navn);
			for (s = srtfil2.readLine(); !(s == null); s = srtfil2.readLine()) {
				list.add(s);
			}
			Collections.sort(list);

			fil2ut = Text.create(innfil2Navn);

			for (Iterator itList = list.iterator(); itList.hasNext();) {
				s = (String) itList.next();

				fil2ut.println(s);
			}
		} finally {
			fil2ut.close();
			srtfil2.close();
		}
	}

	private class AlphabeticComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			String s1 = (String) o1;
			String s2 = (String) o2;
			return hentFil1Key(s1).compareTo(hentFil1Key(s2));
		}
	} // /:~

	private class JoinByData {

		String fil1Label;

		int fil1Startpos;

		int antallTegn;

		String fil2Label;

		int fil2Startpos;

		JoinByData(String linje) {
			isHarJoinByFelt = true;
			antJoinByFelt++;

			linje = linje
					+ "                                                                                ";

			try {
				fil1Startpos = Integer.parseInt(linje.substring(2, 6));
			} catch (NumberFormatException e) {
				fil1Startpos = 0;
			}
			if (sisteSdvFeltKolonneNummer < fil1Startpos) {
				sisteSdvFeltKolonneNummer = fil1Startpos;
			}

			mld(mldInd, "fil1Sstartpos: " + fil1Startpos);

			try {
				fil2Startpos = Integer.parseInt(linje.substring(12, 16));
			} catch (NumberFormatException e) {
				fil2Startpos = 0;
			}
			if (sisteSdvFeltKolonneNummer < fil2Startpos) {
				sisteSdvFeltKolonneNummer = fil2Startpos;
			}

			mld(mldInd, "startpos: " + fil2Startpos);

			try {
				antallTegn = Integer.parseInt(linje.substring(7, 11));
			} catch (NumberFormatException e) {
				antallTegn = 0;
			}
			if (antallTegn == 0 && !(isInnFmtFlytendeSdv)) {
				System.out.println("Antall tegn er lik null:");
				System.out.println(linje);
			}
			mld(mldInd, "antallTegn: " + antallTegn);

			fil1Label = linje.substring(17, 47).trim();
			fil2Label = linje.substring(48, 78).trim();
		}
	}

	private class UtfilFeltDefinisjon {

		int filnummer;

		int startpos;

		int antallTegn;

		String label;

		String utFormat;

		int startpos2;

		int antallTegn2;

		String renameLabel;

		boolean arvFeltInd;

		String arvFeltVerdi;

		String arvKritTekst;

		String xtrKritTegn;

		String xtrKritTekst;

		UtfilFeltDefinisjon(String linje) {
			antUtfilFelt++;

			linje = linje
					+ "                                                                                ";

			try {
				filnummer = Integer.parseInt(linje.substring(2, 3));
			} catch (NumberFormatException e) {
				filnummer = 0;
			}

			try {
				startpos = Integer.parseInt(linje.substring(4, 8));
			} catch (NumberFormatException e) {
				startpos = 0;
			}
			if (sisteSdvFeltKolonneNummer < startpos) {
				sisteSdvFeltKolonneNummer = startpos;
			}

			mld(mldInd, "startpos: " + startpos);
			try {
				antallTegn = Integer.parseInt(linje.substring(9, 13));
			} catch (NumberFormatException e) {
				antallTegn = 0;
			}
			if (antallTegn == 0 && !(isInnFmtFlytendeSdv)) {
				System.out.println("Antall tegn er lik null:");
				skrivUtfeltBeskrivelse(linje);
			}
			mld(mldInd, "antallTegn: " + antallTegn);

			if (filnummer == 0)
				label = linje.substring(14, (14 + (antallTegn)));
			else
				label = linje.substring(14, 44).trim();

			mld(mldInd, "  " + linje);

			// mld(mldInd, " ");
			utFormat = linje.substring(47, 50);
			mld(mldInd, "utFormat: " + utFormat + "---");

			if (utFormat.equals("NAM"))
				renameLabel = linje.substring(51, 81).trim();

			mld(mldInd, "ufd.renameLabel: " + renameLabel + "---");

			try {
				startpos2 = Integer.parseInt(linje.substring(51, 55));
			} catch (NumberFormatException e) {
				startpos2 = 0;
			}
			// mld(mldInd, "startpos2: " +
			// startpos2);

			try {
				antallTegn2 = Integer.parseInt(linje.substring(56, 60));
			} catch (NumberFormatException e) {
				antallTegn2 = 0;
			}
			// mld(mldInd, "antallTegn2: " +
			// antallTegn2);

			arvKritTekst = linje.substring(61, 71).trim();
			// mld(mldInd, "arvKritTekst: " +
			// arvKritTekst);

			if ((startpos2 != 0) && (antallTegn2 != 0)
					&& (arvKritTekst.trim().length() > 0))
				arvFeltInd = true;
			else
				arvFeltInd = false;
			mld(mldInd, "arvFeltInd: " + arvFeltInd);

			xtrKritTegn = linje.substring(71, 72);
			mld(mldInd, " xtrKritTegn: " + xtrKritTegn);

			if (!xtrKritTegn.equals(" "))
				xtrKritTekst = linje.substring(72, 82).trim();
			mld(mldInd, " xtrKritTekst: " + xtrKritTekst);

		}

		UtfilFeltDefinisjon(int feltnummer, String tekst) {
			filnummer = 1;
			startpos = feltnummer;
			antallTegn = 0;
			label = tekst;
			utFormat = "";
			startpos2 = 0;
			antallTegn2 = 0;
			arvKritTekst = "";
			arvFeltInd = false;
			xtrKritTegn = "";
			xtrKritTekst = "";

			mld("mldInd", "startpos: " + startpos + " label: " + tekst);

			int antPos = tekst.length();

			// if (antPos > sdvFeltLengde)
			// sdvFeltLengde = antPos;
		}

		private void skrivUtfeltBeskrivelse(String linje) {
			System.out.println(linje + '\n');
		}
	}

	// definering av konstanter
	private List utfilFeltDefListe = new ArrayList();

	private List joinByFeltDefListe = new ArrayList();

	PrintWriter tmpfilskriv;

	final String feltfilNavn = "c:/javaio/fbfeltfil.txt";

	final String tmpfilNavn = "c:/javaio/fbtmpfil.txt";

	final String recLayoutfilNavn = "c:/javaio/fbreclofil.txt";

	final String paramfilNavn = "c:/javaio/fbparamfil.txt";

	final String innfil1Navn = "c:/javaio/fbinnfil1.txt";

	final String innfil2Navn = "c:/javaio/fbinnfil2.txt";

	final String utfilNavn = "c:/javaio/fbutfil.txt";

	// definering av klassevariable
	String utlinje, feltSkilleInd = " ", feltSkilleTegn = " ", mldInd = "";

	String utfilHeadingInd;

	boolean isHarJoinByFelt;

	boolean isJoinMatch;

	boolean isInnFmtFlytendeSdv;

	boolean isUtFmtFlytendeSdv;

	int sisteSdvFeltKolonneNummer;

	String blankLinje = "                                                                               ";

	String zeroLinje = "0000";

	int antJoinByFelt = 0;

	int antUtfilFelt = 0;

	int antInnfil1Linjer = 0, antInnfil2Linjer = 0, antUfilLinjer = 0;

	String[] sdvFeltVerdiTab = new String[50];

	int[] sdvFeltLengdeTab = new int[50];

	boolean linjeEkstraktInd;

	String utfilLinje;

	String utFilRecFmt;

	boolean feltFilTomInd = false;

	private int filNr;

	private String fil1RecFmt;

	private String fil2RecFmt;

	private String label;

	private String recFmt;

	BufferedReader inn;

	BufferedReader innfil1;

	BufferedReader innfil2;

	// konstruktør
	FilBehandling4() {
		inn = Text.open(System.in);
	}

	private void lesogBehParamfil() throws IOException {
		mld(mldInd, "--- Behandling av paramfil ---");
		String linje = " ";

		BufferedReader paramfil = Text.open(paramfilNavn);

		try {
			linje = paramfil.readLine();
			linje = linje + "                           ";

			isJoinMatch = linje.substring(3, 10).equalsIgnoreCase("match  ");

			utfilHeadingInd = linje.substring(11, 12);

			if (!utfilHeadingInd.equals("N")) {
				utfilHeadingInd = "J";
			} // if
			mld(mldInd, "utfilHeadingInd: " + utfilHeadingInd);

			feltSkilleInd = linje.substring(15, 16);

			if (!feltSkilleInd.equals("N")) {
				feltSkilleInd = "J";
			} // if
			mld(mldInd, "  feltSkilleInd: " + feltSkilleInd);

			feltSkilleTegn = linje.substring(17, 18);
			mld(mldInd, " feltSkilleTegn: " + feltSkilleTegn);

			String tekst = linje.substring(23, 24);

			if (tekst.equals("I") | tekst.equals("1") | tekst.equals("3"))
				isInnFmtFlytendeSdv = true;
			else
				isInnFmtFlytendeSdv = false;

			if (tekst.equals("U") | tekst.equals("2") | tekst.equals("3"))
				isUtFmtFlytendeSdv = true;
			else
				isUtFmtFlytendeSdv = false;

			mld(mldInd, " isInnFmtFlytendeSdv: " + isInnFmtFlytendeSdv);
			mld(mldInd, " isUtFmtFlytendeSdv: " + isUtFmtFlytendeSdv);

			fil1RecFmt = linje.substring(45, 48);
			mld(mldInd, "      fil1RecFmt: " + fil1RecFmt + "---");

			fil2RecFmt = linje.substring(49, 52);
			mld(mldInd, "      fil2RecFmt: " + fil2RecFmt + "---");

			utFilRecFmt = linje.substring(53, 56);
			mld(mldInd, " utFilRecFmt: " + utFilRecFmt);
		} // try
		catch (NullPointerException e) {
			System.out.println("  NullPointerException!");
			throw e;
		} finally {
			paramfil.close();
		}
	}

	private void definerFelt() throws IOException {
		lesogBehFeltfil();

		justerFeltPosogLengde();

		behRenameFelt();
	}

	private void lesogBehFeltfil() throws IOException {
		int linjeTeller = 0;

		mld(mldInd, "--- Behandling av feltfil ---");

		BufferedReader feltfil = Text.open(feltfilNavn);

		try {
			String linje = feltfil.readLine();

			if (linje == null)
				feltFilTomInd = true;
			mld("mldInd", "feltFilTomInd: " + feltFilTomInd);

			while (linje != null && linje.charAt(0) == 'M') {
				linjeTeller++;

				joinByFeltDefListe.add(new JoinByData(linje));

				linje = feltfil.readLine();
			} // slutt while

			linjeTeller = 0;
			while (linje != null && linje.charAt(0) == 'O') {
				linjeTeller++;

				utfilFeltDefListe.add(new UtfilFeltDefinisjon(linje));

				linje = feltfil.readLine();
			} // slutt while

		} // try
		catch (NullPointerException e) {
			System.out.println("  NullPointerException !");
			throw e;
		} finally {
			feltfil.close();
		}
		antUtfilFelt = linjeTeller;
		mld(mldInd, "  Antutfelt: " + antUtfilFelt + "\n");

		if (isInnFmtFlytendeSdv)
			bestemSdvFeltLengde();
	}

	private void justerFeltPosogLengde() throws IOException {
		tmpfilskriv = Text.create(tmpfilNavn);

		BufferedReader reclofil = Text.open(recLayoutfilNavn);

		int linjeTeller = 0;

		mld(mldInd, "--- Justering av feltfil ---");

		try {
			String linje = reclofil.readLine();

			if (linje == null && feltFilTomInd)
				feltFilTomInd = true;

			mld("mldInd", "feltFilTomInd: " + feltFilTomInd);
			while (linje != null) {
				tmpfilskriv.println(linje);

				linje = linje
						+ "                                                                                ";

				// feltTypeTab[linjeTeller] = linje.substring(0,1);
				// mld(mldInd, " feltTypeTab: " + feltTypeTab [linjeTeller]);
				try {
					filNr = Integer.parseInt(linje.substring(2, 3));
				} catch (NumberFormatException e) {
					filNr = 0;
				}
				int startpos;

				try {
					startpos = Integer.parseInt(linje.substring(4, 8));
				} catch (NumberFormatException e) {
					startpos = 0;
				}

				mld(mldInd, "startpos: " + startpos);
				int antTegn;
				try {
					antTegn = Integer.parseInt(linje.substring(9, 13));
				} catch (NumberFormatException e) {
					antTegn = 0;
				}

				mld(mldInd, " antTegn: " + antTegn);
				recFmt = linje.substring(77, 80);

				mld(mldInd, "recFmt: " + recFmt);
				label = linje.substring(14, 44).trim();
				mld(mldInd, "label: " + label);
				mld(mldInd, "  " + linje);

				int feltTeller = 0;
				// ¤%&
				for (Iterator itFeltliste = utfilFeltDefListe.iterator(); itFeltliste
						.hasNext();) {

					feltTeller++;

					UtfilFeltDefinisjon ufd = (UtfilFeltDefinisjon) itFeltliste
							.next();
					if (((fil1RecFmt.equals(recFmt) && ufd.filnummer == 1) | (fil2RecFmt
							.equals(recFmt) && ufd.filnummer == 2))
							&& ufd.startpos == 0 && ufd.label.equals(label)) {
						mld(mldInd,
								"Overstyrer felt startpos og lengde for feltet: "
										+ label);

						ufd.startpos = startpos;
						ufd.antallTegn = antTegn;
					}
				}

				for (Iterator itFeltliste = joinByFeltDefListe.iterator(); itFeltliste
						.hasNext();) {

					JoinByData jbd = (JoinByData) itFeltliste.next();
					if (fil1RecFmt.equals(recFmt) && jbd.fil1Startpos == 0
							&& jbd.fil1Label.equals(label)) {
						jbd.fil1Startpos = startpos;
						jbd.antallTegn = antTegn;
					}
					if (fil2RecFmt.equals(recFmt) && jbd.fil2Startpos == 0
							&& jbd.fil2Label.equals(label)) {
						jbd.fil2Startpos = startpos;
					}
				}

				linje = reclofil.readLine();
			} // slutt while
		} // try
		catch (NullPointerException e) {
			System.out.println("  NullPointerException !");
			throw e;
		} finally {
			reclofil.close();
		}
	}

	private void behRenameFelt() {
		mld(mldInd, "--- Behandling av rename felt ---");

		int feltTeller = 1;
		for (Iterator itFeltliste = utfilFeltDefListe.iterator(); itFeltliste
				.hasNext(); feltTeller++) {

			UtfilFeltDefinisjon ufd = (UtfilFeltDefinisjon) itFeltliste.next();

			if (ufd.startpos == 0)
				mld("J", "Felt har null i startposisjon: " + ufd.label);

			if (ufd.utFormat.equals("NAM")) {
				String tekst = ufd.label;
				ufd.label = ufd.renameLabel;
				ufd.renameLabel = tekst;
			}
		}
	}

	private void bestemSdvFeltLengde() throws IOException {
		innfil1 = Text.open(innfil1Navn);
		int linjeTeller = 0;

		try {
			String linje = innfil1.readLine();

			while (linje != null) {
				linje = linje
						+ "                                                                                                   ";
				int linjeLengde = linje.length() - 1;

				// mld(mldInd, " linje: " + linje);
				// mld(mldInd, " linjeLengde: " + linjeLengde);

				linjeTeller++;
				int feltTeller = 1;
				int tegnTeller = 0;
				int antPos = 0;

				// ¤%&
				String tekst = "";
				while ((tegnTeller < linjeLengde)
						&& !(linjeTeller > 1 && feltTeller > sisteSdvFeltKolonneNummer)) {
					if ((linje.charAt(tegnTeller) == ';')
							| (tegnTeller + 1 == linjeLengde)) {
						tekst = linje
								.substring(tegnTeller - antPos, tegnTeller)
								.trim();
						int tekstLengde = tekst.length();

						if (feltFilTomInd & linjeTeller == 1) {
							utfilFeltDefListe.add(new UtfilFeltDefinisjon(
									feltTeller, tekst));
						} else {
							if (linjeTeller > 1) {
								if (sdvFeltLengdeTab[feltTeller] < tekstLengde) {
									sdvFeltLengdeTab[feltTeller] = tekstLengde;
								}
							}
						}
						antPos = 0;
						feltTeller++;
					} else {
						antPos++;
					}
					tegnTeller++;
				} // while
				if (feltFilTomInd & linjeTeller == 1) {
					antUtfilFelt = feltTeller;
					antUtfilFelt--;
					if (tekst.equals(""))
						antUtfilFelt--;
					mld("mldInd", "antUtfilFelt: " + antUtfilFelt);
					if (sisteSdvFeltKolonneNummer == 0) {
						sisteSdvFeltKolonneNummer = antUtfilFelt;
					}
				}
				linje = innfil1.readLine();
			} // slutt while
		} // try

		catch (NullPointerException e) {
			System.out.println("  NullPointerException !");
			throw e;
		} finally {
			innfil1.close();
		}

		int feltTeller = 1;

		for (Iterator itFeltliste = utfilFeltDefListe.iterator(); itFeltliste
				.hasNext(); feltTeller++) {

			UtfilFeltDefinisjon ufd = (UtfilFeltDefinisjon) itFeltliste.next();

			if (ufd.filnummer != 0
					& (ufd.antallTegn < sdvFeltLengdeTab[ufd.startpos]))
				ufd.antallTegn = sdvFeltLengdeTab[ufd.startpos];

			mld(mldInd, "feltnr. " + feltTeller + " har maks " + ufd.antallTegn
					+ " tegn.");
		} // for

	}

	private void lagUtfil() throws IOException {
		mld(mldInd, "--- Behandling av utfil ---");
		String linje = " ";
		String linje2 = " ";
		int linjeLengde2 = 0;

		
		PrintWriter utfil = Text.create(utfilNavn);

		innfil1 = Text.open(innfil1Navn);
		innfil2 = Text.open(innfil2Navn);

		// System.out.println("headingInd: " + utfilHeadingInd + "
		// feltfiltomInd: " + feltFilTomInd);

		// if (utfilHeadingInd.equals("J") & !feltFilTomInd) {
		if (utfilHeadingInd.equals("J")) {
			skrivHeading(utfil);
		}

		mld(mldInd, "--- Behandling av utfil: 2.gangs les av innfil1 ---");
		linje = " ";

		int linjeTeller = 0;

		try {
			// les forbi headinglinje hvis sdvformat og feltfil er tom (da angis
			// feltoverskrifter som første linje i innfilen)
			if (feltFilTomInd) {
				linje = innfil1.readLine();
			}
			if (!(linje == null)) {
				linje = innfil1.readLine();
			}
			if (linje == null)
				mld(mldInd,
						"Finner ingen linjer i innfil1 ved 2. gangs lesing!");

			if (isHarJoinByFelt) {
				linje2 = innfil2.readLine();
				linjeLengde2 = linje2.length();

			}

			antInnfil2Linjer = 1;

			String fil1Key = "";
			String fil2Key = "";

			while (linje != null) {
				linje = linje
						+ "                                                                                                   ";
				mld(mldInd, "--- linje: " + linje);

				int linjeLengde = linje.length() - 1;

				mld(mldInd, "  linje: " + linje);
				mld(mldInd, "  linjeLengde: " + linjeLengde);

				linjeTeller++;

				int feltTeller = 1;
				int tegnTeller = 0;
				int antPos = 0;

				linjeEkstraktInd = true;
				utfilLinje = "";

				if (isInnFmtFlytendeSdv) {
					while (tegnTeller < linjeLengde) {
						if ((linje.charAt(tegnTeller) == ';')
								| (tegnTeller + 1 == linjeLengde)) {
							sdvFeltVerdiTab[feltTeller] = linje.substring(
									tegnTeller - antPos, tegnTeller).trim();

							mld(mldInd, "linje: " + linjeTeller
									+ " feltTeller: " + feltTeller
									+ " feltverdi: "
									+ sdvFeltVerdiTab[feltTeller]);
							mld(mldInd, "");
							antPos = 0;
							feltTeller++;
						} else {
							antPos++;
						}
						tegnTeller++;
					} // while
				} // if (isInnFmtFlytendeSdv)

				fil1Key = hentFil1Key(linje);
				fil2Key = hentFil2Key(linje2);

				feltTeller = 0;
				tegnTeller = 1;
				linjeLengde2 = 0;

				int tall = fil2Key.compareTo(fil1Key);

				boolean isFil2KeyMindre = ((fil2Key.compareTo(fil1Key)) < 0);

				int genLinjeLengde = linjeLengde;
				
				while (!(linje2 == null) && isFil2KeyMindre) {
					linje2 = innfil2.readLine();
					
					linjeLengde2 = linje2.length();
					
					antInnfil2Linjer++;

					fil2Key = hentFil2Key(linje2);
					isFil2KeyMindre = ((fil2Key.compareTo(fil1Key)) < 0);
				}

				if (!isHarJoinByFelt
						|| (isJoinMatch == (fil1Key.equals(fil2Key)))) {
					for (Iterator itFeltliste = utfilFeltDefListe.iterator(); itFeltliste
							.hasNext();) {

						feltTeller++;

						UtfilFeltDefinisjon ufd = (UtfilFeltDefinisjon) itFeltliste
								.next();
						int startpos = ufd.startpos - 1;
						int antTegn = ufd.antallTegn;
						int sluttpos = startpos + antTegn;
						
						if (ufd.filnummer == 2) {
							genLinjeLengde  = linjeLengde2;
						}
						
						if (!(sluttpos < genLinjeLengde)) {
							sluttpos = genLinjeLengde;
						}
						
						mld(mldInd, "  startpos: " + ufd.startpos
								+ " antallTegn: " + ufd.antallTegn
								+ " feltTeller: " + feltTeller);
						mld(mldInd, "  filnummer: " + ufd.filnummer);
						mld(mldInd, "  label: " + ufd.label);
						int intTall = ufd.label.length();
						mld(mldInd, "      labelTab lengde : " + intTall);
						mld(mldInd, "ufd.arvFeltInd: " + ufd.arvFeltInd);

						if (feltSkilleInd.equals("J") & feltTeller != 1) {
							utfilLinje = utfilLinje + feltSkilleTegn;
							tegnTeller++;
						}
						String tekst = "";

						if (ufd.filnummer == 0)
							tekst = ufd.label.substring(0, ufd.antallTegn);
						else {
							if (ufd.arvFeltInd) {
								int startpos2tmp = ufd.startpos2 - 1;
								int antTegn2 = ufd.antallTegn2;
								mld(mldInd, "  linjearvkrittekst: "
										+ linje.substring(startpos2tmp,
												startpos2tmp + antTegn2));
								mld(mldInd, "       arvkrittekst: "
										+ ufd.arvKritTekst);

								if (linje.substring(startpos2tmp,
										startpos2tmp + antTegn2).equals(
										ufd.arvKritTekst)) {
									mld(mldInd, "arvkriterium slår til!");

									ufd.arvFeltVerdi = linje.substring(
											startpos, sluttpos);
									mld(mldInd, "arvfeltverdi: "
											+ ufd.arvFeltVerdi);
								}
								if (ufd.arvFeltVerdi != null)
									tekst = ufd.arvFeltVerdi;
							} else {
								if (isInnFmtFlytendeSdv) {
									tekst = sdvFeltVerdiTab[ufd.startpos];
									if (tekst == null) {
										System.out
												.println("!! NULLPOINTEREXCEPTION!! Prøver å lese en sdv inputkolonne som ikke finnes:");
										System.out
												.println("Inputkolonnenr som mangler: "
														+ (ufd.startpos));

										System.out.println("label: " + label);
									}
									mld(mldInd, "sdvFeltVerdiTab[startpos]: "
											+ sdvFeltVerdiTab[ufd.startpos]);
								} else if (!(linjeLengde < sluttpos)) {
									if (ufd.filnummer == 1) {
										tekst = linje.substring(startpos,
												sluttpos);
									} else {
										tekst = linje2.substring(startpos,
												sluttpos);
									}
								} else {
									mld(mldInd,
											"  antTegn større enn linjelengden! ");
								}

								mld(mldInd, "ufd.utFormat: " + ufd.utFormat
										+ "---");
								if (ufd.utFormat.equals("FN "))
									tekst = getFilePath(tekst);
							}
							if (isUtFmtFlytendeSdv)
								tekst = tekst.trim();
							else {
								intTall = tekst.length();
								if (intTall < antTegn)
									tekst = tekst
											+ blankLinje.substring(0, antTegn
													- intTall);
							}
							String tekst2 = ufd.xtrKritTegn;
							if (!tekst2.equals(" ")) {
								intTall = tekst.compareTo(ufd.xtrKritTekst);

								// mld("mldInd", "");
								// mld("mldInd", "tekst: " + tekst);
								// mld("mldInd", "xtrKritTekst: " +
								// xtrKritTekst);
								// mld("mldInd", "intTall: " + intTall);
								// mld("mldInd", "tekst2: " + tekst2);

								if ((tekst2.equals("=") && intTall != 0)
										| (tekst2.equals("-") && intTall == 0)
										| (tekst2.equals(">") && !(intTall > 0))
										| (tekst2.equals("<") && !(intTall < 0)))
									linjeEkstraktInd = false;
							}
						}

						if (linjeTeller == 2)
							skrivreclofilLinje(tegnTeller, antTegn, ufd.label,
									utFilRecFmt, tmpfilskriv);

						utfilLinje = utfilLinje + tekst;
						tegnTeller = tegnTeller + tekst.length();
					} // for alle felt
					if (linjeEkstraktInd == true) {
						utfil.println(utfilLinje);
						antUfilLinjer++;
					}
				} // if match/nomatch

				linje = innfil1.readLine();
			} // slutt while
		} // try

		catch (NullPointerException e) {
			System.out.println("  NullPointerException !");
			throw e;
		} finally {
			innfil1.close();
			innfil2.close();

			utfil.close();
			tmpfilskriv.close();
			antInnfil1Linjer = linjeTeller;

			skrivStatistikk();
		}
	}

	private void skrivHeading(PrintWriter utfil) {
		mld("mldInd", "skriver ut heading linje.");
		int feltTeller = 1;
		String linje = "";

		for (Iterator itFeltliste = utfilFeltDefListe.iterator(); itFeltliste
				.hasNext(); feltTeller++) {

			UtfilFeltDefinisjon ufd = (UtfilFeltDefinisjon) itFeltliste.next();

			if (feltTeller == 1)
				linje = linje + ufd.label.trim();
			else
				linje = linje + feltSkilleTegn + ufd.label.trim();

			// mld(mldInd, "--- linje: " + linje);

		} // for alle felt
		utfil.println(linje);
		antUfilLinjer++;
	}

	private String hentFil1Key(String linje) {
		StringBuffer sb = new StringBuffer();
		for (Iterator itFeltliste = joinByFeltDefListe.iterator(); itFeltliste
				.hasNext();) {
			JoinByData jbf = (JoinByData) itFeltliste.next();

			if (isInnFmtFlytendeSdv) {
				sb.append(sdvFeltVerdiTab[jbf.fil1Startpos].trim());
			} else {
				sb.append(linje.substring(jbf.fil1Startpos - 1,
						jbf.fil1Startpos - 1 + jbf.antallTegn).trim());
			}
		}
		return sb.toString();
	}

	private String hentFil2Key(String linje) {
		if (linje == null) {
			return "zzzzzzzzzzzzzzzz";
		}
		StringBuffer sb = new StringBuffer();
		for (Iterator itFeltliste = joinByFeltDefListe.iterator(); itFeltliste
				.hasNext();) {
			JoinByData jbf = (JoinByData) itFeltliste.next();

			// if (isInnFmtFlytendeSdv) {
			if (false) {
				sb.append(sdvFeltVerdiTab[jbf.fil2Startpos].trim());
			} else {
				sb.append(linje.substring(jbf.fil2Startpos - 1,
						jbf.fil2Startpos - 1 + jbf.antallTegn).trim());
			}
		}
		return sb.toString();
	}

	private void skrivreclofilLinje(int startpos, int antPos, String label,
			String utFilRecFmt, PrintWriter fil) {
		String startposString = String.valueOf(startpos);
		startposString = zeroLinje.substring(0, (4 - startposString.length()))
				+ startposString;

		String antPosString = String.valueOf(antPos);
		antPosString = zeroLinje.substring(0, (4 - antPosString.length()))
				+ antPosString;

		try {
			label = label.toUpperCase()
					+ blankLinje.substring(0, (30 - label.length()));
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println("For lang labeltekst, maks 30 tegn! ");
			throw e;
		}

		String utLinje = "O,1," + startposString + "," + antPosString + ","
				+ label + blankLinje.substring(0, 33) + utFilRecFmt;

		fil.println(utLinje);
	}

	private String getFilePath(String tekst) {
		tekst = tekst.trim();
		if (tekst == null | tekst.equals(""))
			return null;
		int sluttpos = tekst.length();
		int index = sluttpos - 1;
		// mld("mldInd", "tekst: " + tekst + "---");
		while ((tekst.charAt(index) != '/') & (tekst.charAt(index) != ' ')
				& (tekst.charAt(index) != ';') & (index > 0))
			index--;
		if (tekst.charAt(index) == '/' | tekst.charAt(index) == ' '
				| tekst.charAt(index) == ';')
			index++;
		// mld("mldInd", "tekst: " + tekst + "---");
		return tekst.substring(index, sluttpos);
	}

	static void mld(String mldInd, String mld) {
		if (mldInd.equals("J"))
			System.out.println(mld);
	}

	private void skrivStatistikk() {
		System.out.println("Antall linjer paa innfil1: " + antInnfil1Linjer);
		if (isHarJoinByFelt) {
			System.out
					.println("Antall linjer paa innfil2: " + antInnfil2Linjer);
		}
		System.out.println("    Antall felt paa utfil: " + antUtfilFelt);
		System.out.println("  Antall linjer paa utfil: " + antUfilLinjer);
	}

	private void lagreclofil() throws IOException {
		BufferedReader tmpfilles = Text.open(tmpfilNavn);
		PrintWriter reclofil = Text.create(recLayoutfilNavn);
		mld(mldInd, "--- Skriv av recordlayout fil ---");

		try {
			String linje = tmpfilles.readLine();

			while (linje != null) {
				reclofil.println(linje);

				linje = tmpfilles.readLine();
			} // slutt while
		} // try
		catch (NullPointerException e) {
			System.out.println("  NullPointerException recordlayout fil !");
			throw e;
		} finally {
			reclofil.close();
		}
	}
} // class slutt
