package rkutils;

import rkutils.javagently.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

class FilBehandling {

	public static void main(String args[]) throws IOException {

		String version = System.getProperty("java.version");
		System.out.println("java versjon: " + version);

		FilBehandling F = new FilBehandling();

		try {
			if (args.length > 0)
				F.mldInd = args[0].trim();
		} // try
		catch (ArrayIndexOutOfBoundsException e) {
		}

		F.lesogBehParamfil();
		F.definerFelt();
		F.mldInd = "N";
		F.lagUtfil();
		F.lagreclofil();

	} // main slutt

	// definering av konstanter
	PrintWriter tmpfilskriv;

	final String feltfilNavn = "c:/javaio/fbfeltfil.txt";

	final String tmpfilNavn = "c:/javaio/fbtmpfil.txt";

	final String recLayoutfilNavn = "c:/javaio/fbreclofil.txt";

	final String paramfilNavn = "c:/javaio/fbparamfil.txt";

	final String innfil1Navn = "c:/javaio/fbinnfil1.txt";

	final String utfilNavn = "c:/javaio/fbutfil.txt";

	// definering av klassevariable
	String linje = " ", feltlinje, utlinje, member = " ", hjelpString,
			feltSkilleInd = " ", feltSkilleTegn = " ", mldInd = "";

	String utfilHeadingInd;

	String inFmtFlytendeSdvInd;

	String utFmtFlytendeSdvInd;

	String tekst;

	String blankLinje = "                                                                               ";

	String zeroLinje = "0000";

	int filtype = 0, linjeTeller = 0, antUtFelt = 0, feltTeller = 0;

	int startPos, sluttPos, antTegn, linjeLengde, labelSluttPos,
			antInnfilLinjer = 0, antUfilLinjer = 0;

	int tegnTeller;

	int antPos;

	int intTall;

	String[] feltTypeTab = new String[50];

	int[] filNrTab = new int[50];

	int[] startPosTab = new int[50];

	int[] antTegnTab = new int[50];

	String[] labelTab = new String[50];

	String[] labelRenameTab = new String[50];

	String[] utFmtTab = new String[50];

	int[] startPos2Tab = new int[50];

	int[] antTegn2Tab = new int[50];

	String[] arvKritTekst = new String[50];

	String[] arvFeltVerdi = new String[50];

	boolean[] arvFeltInd = new boolean[50];

	int[] arvType = new int[50];

	int[] sdvFeltLengdeTab = new int[50];

	String[] sdvFeltVerdiTab = new String[50];

	String[] xtrKritTekstTab = new String[50];

	String[] xtrKritTegnTab = new String[50];

	boolean linjeEkstraktInd;

	String utfilLinje;

	String tekst2;

	String utFilRecFmt;

	boolean feltFilTomInd = false;

	private int filNr;

	private String fil1RecFmt;

	private String fil2RecFmt;

	private String recFmt;

	private String label;

	BufferedReader inn;

	BufferedReader innfil1;

	// konstruktør
	FilBehandling() {
		inn = Text.open(System.in);
	}

	private void lesogBehParamfil() throws IOException {
		mld(mldInd, "--- Behandling av paramfil ---");
		linje = " ";

		BufferedReader paramfil = Text.open(paramfilNavn);

		try {
			linje = paramfil.readLine();
			linje = linje + "                           ";

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

			tekst = linje.substring(23, 24);

			if (tekst.equals("I") | tekst.equals("1") | tekst.equals("3"))
				inFmtFlytendeSdvInd = "J";
			else
				inFmtFlytendeSdvInd = "N";

			if (tekst.equals("U") | tekst.equals("2") | tekst.equals("3"))
				utFmtFlytendeSdvInd = "J";
			else
				utFmtFlytendeSdvInd = "N";

			mld(mldInd, " inFmtFlytendeSdvInd: " + inFmtFlytendeSdvInd);
			mld(mldInd, " utFmtFlytendeSdvInd: " + utFmtFlytendeSdvInd);

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
		linjeTeller = 0;

		mld(mldInd, "--- Behandling av feltfil ---");

		BufferedReader feltfil = Text.open(feltfilNavn);

		try {
			linje = feltfil.readLine();

			if (linje == null)
				feltFilTomInd = true;
			mld("mldInd", "feltFilTomInd: " + feltFilTomInd);
			while (linje != null) {
				linje = linje
						+ "                                                                                ";
				linjeTeller++;

				feltTypeTab[linjeTeller] = linje.substring(0, 1);
				mld(mldInd, "  feltTypeTab: " + feltTypeTab[linjeTeller]);
				try {
					filNrTab[linjeTeller] = Integer.parseInt(linje.substring(2,
							3));
				} catch (NumberFormatException e) {
					filNrTab[linjeTeller] = 0;
				}

				try {
					startPosTab[linjeTeller] = Integer.parseInt(linje
							.substring(4, 8));
				} catch (NumberFormatException e) {
					startPosTab[linjeTeller] = 0;
				}

				mld(mldInd, "startPosTab[linjeTeller]: "
						+ startPosTab[linjeTeller]);
				try {
					antTegnTab[linjeTeller] = Integer.parseInt(linje.substring(
							9, 13));
				} catch (NumberFormatException e) {
					antTegnTab[linjeTeller] = 0;
				}

				mld(mldInd, "antTegnTab[linjeTeller]: "
						+ antTegnTab[linjeTeller]);
				if (filNrTab[linjeTeller] == 0)
					labelTab[linjeTeller] = linje.substring(14,
							(14 + (antTegnTab[linjeTeller])));
				else
					labelTab[linjeTeller] = linje.substring(14, 44).trim();

				mld(mldInd, "  " + linje);

				// mld(mldInd, " ");
				utFmtTab[linjeTeller] = linje.substring(47, 50);
				mld(mldInd, "utFmtTab[linjeTeller]: " + utFmtTab[linjeTeller]
						+ "---");

				if (utFmtTab[linjeTeller].equals("NAM"))
					labelRenameTab[linjeTeller] = linje.substring(51, 81)
							.trim();

				mld(mldInd, "labelRenameTab[linjeTeller]: "
						+ labelRenameTab[linjeTeller] + "---");

				try {
					startPos2Tab[linjeTeller] = Integer.parseInt(linje
							.substring(51, 55));
				} catch (NumberFormatException e) {
					startPos2Tab[linjeTeller] = 0;
				}
				// mld(mldInd, "startPos2Tab[linjeTeller]: " +
				// startPos2Tab[linjeTeller]);

				try {
					antTegn2Tab[linjeTeller] = Integer.parseInt(linje
							.substring(56, 60));
				} catch (NumberFormatException e) {
					antTegn2Tab[linjeTeller] = 0;
				}
				// mld(mldInd, "antTegn2Tab[linjeTeller]: " +
				// antTegn2Tab[linjeTeller]);

				arvKritTekst[linjeTeller] = linje.substring(61, 71).trim();
				// mld(mldInd, "arvKritTekst[linjeTeller]: " +
				// arvKritTekst[linjeTeller]);

				if ((startPos2Tab[linjeTeller] != 0)
						&& (antTegn2Tab[linjeTeller] != 0)
						&& (arvKritTekst[linjeTeller].trim().length() > 0))
					arvFeltInd[linjeTeller] = true;
				else
					arvFeltInd[linjeTeller] = false;
				mld(mldInd, "arvFeltInd[linjeTeller]: "
						+ arvFeltInd[linjeTeller]);

				xtrKritTegnTab[linjeTeller] = linje.substring(71, 72);
				mld(mldInd, " xtrKritTegnTab[linjeTeller]: "
						+ xtrKritTegnTab[linjeTeller]);

				if (!xtrKritTegnTab[linjeTeller].equals(" "))
					xtrKritTekstTab[linjeTeller] = linje.substring(72, 82)
							.trim();
				mld(mldInd, " xtrKritTekstTab[linjeTeller]: "
						+ xtrKritTekstTab[linjeTeller]);

				// utfil.println(linje);
				linje = feltfil.readLine();
			} // slutt while
		} // try
		catch (NullPointerException e) {
			System.out.println("  NullPointerException !");
			throw e;
		} finally {
			feltfil.close();
		}
		antUtFelt = linjeTeller;
		mld(mldInd, "  Antutfelt: " + antUtFelt + "\n");

		if (inFmtFlytendeSdvInd.equals("J"))
			bestemFeltStartPos();
	}

	private void justerFeltPosogLengde() throws IOException {
		tmpfilskriv = Text.create(tmpfilNavn);

		BufferedReader reclofil = Text.open(recLayoutfilNavn);

		linjeTeller = 0;

		mld(mldInd, "--- Justering av feltfil ---");

		try {
			linje = reclofil.readLine();

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

				try {
					startPos = Integer.parseInt(linje.substring(4, 8));
				} catch (NumberFormatException e) {
					startPos = 0;
				}

				mld(mldInd, "startPos: " + startPos);
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

				feltTeller = 0;

				while (feltTeller < antUtFelt) {
					feltTeller++;

					if (((fil1RecFmt.equals(recFmt) && filNr == 1) | (fil2RecFmt
							.equals(recFmt) && filNr == 2))
							&& startPosTab[feltTeller] == 0
							&& labelTab[feltTeller].equals(label)) {
						mld(mldInd,
								"Overstyrer felt startpos og lengde for feltet: "
										+ label);

						startPosTab[feltTeller] = startPos;
						antTegnTab[feltTeller] = antTegn;
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

		feltTeller = 0;

		while (feltTeller < antUtFelt) {
			feltTeller++;

			if (startPosTab[feltTeller] == 0)
				mld("J", "Felt har null i startposisjon: "
						+ labelTab[feltTeller]);

			if (utFmtTab[feltTeller].equals("NAM")) {
				tekst = labelTab[feltTeller];
				labelTab[feltTeller] = labelRenameTab[feltTeller];
				labelRenameTab[feltTeller] = tekst;
			}
		}
	}

	private void bestemFeltStartPos() throws IOException {
		innfil1 = Text.open(innfil1Navn);
		linjeTeller = 0;

		try {
			linje = innfil1.readLine();

			while (linje != null) {
				linje = linje
						+ "                                                                                                   ";
				linjeLengde = linje.length() - 1;

				// mld(mldInd, " linje: " + linje);
				// mld(mldInd, " linjeLengde: " + linjeLengde);

				linjeTeller++;
				feltTeller = 1;
				tegnTeller = 0;
				antPos = 0;

				while (tegnTeller < linjeLengde) {
					if ((linje.charAt(tegnTeller) == ';')
							| (tegnTeller + 1 == linjeLengde)) {
						tekst = linje
								.substring(tegnTeller - antPos, tegnTeller)
								.trim();

						if (feltFilTomInd & linjeTeller == 1) {
							feltTypeTab[feltTeller] = "O";
							filNrTab[feltTeller] = 1;
							startPosTab[feltTeller] = feltTeller;
							antTegnTab[feltTeller] = 0;
							labelTab[feltTeller] = tekst;
							utFmtTab[feltTeller] = "";
							startPos2Tab[feltTeller] = 0;
							antTegn2Tab[feltTeller] = 0;
							arvKritTekst[feltTeller] = "";
							arvFeltInd[feltTeller] = false;
							xtrKritTegnTab[feltTeller] = "";
							xtrKritTekstTab[feltTeller] = "";

							mld("mldInd", "startpos: "
									+ startPosTab[feltTeller] + " label: "
									+ tekst);
						}
						antPos = tekst.length();

						if (!(feltFilTomInd & utfilHeadingInd == "J" & linjeTeller == 1)) {
							if (antPos > sdvFeltLengdeTab[feltTeller])
								sdvFeltLengdeTab[feltTeller] = antPos;
						}

						antPos = 0;
						feltTeller++;
					} else {
						antPos++;
					}
					tegnTeller++;
				} // while
				if (feltFilTomInd & linjeTeller == 1) {
					antUtFelt = feltTeller;
					antUtFelt--;
					if (tekst.equals(""))
						antUtFelt--;
					mld("mldInd", "antUtFelt: " + antUtFelt);
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

		feltTeller = 0;

		while (feltTeller < antUtFelt) {
			feltTeller++;

			if (filNrTab[feltTeller] != 0
					& (antTegnTab[feltTeller] < sdvFeltLengdeTab[startPosTab[feltTeller]]))
				antTegnTab[feltTeller] = sdvFeltLengdeTab[startPosTab[feltTeller]];

			mld(mldInd, "feltnr. " + feltTeller + " har maks "
					+ antTegnTab[feltTeller] + " tegn.");
		} // while
	}

	private void lagUtfil() throws IOException {
		mld(mldInd, "--- Behandling av utfil ---");
		linje = " ";
		PrintWriter utfil = Text.create(utfilNavn);
		innfil1 = Text.open(innfil1Navn);
		//System.out.println("headingInd: " + utfilHeadingInd + " feltfiltomInd: " + feltFilTomInd);

		if (utfilHeadingInd.equals("J") & !feltFilTomInd) {
			mld("mldInd", "skriver ut heading linje.");
			feltTeller = 0;
			linje = "";

			while (feltTeller < antUtFelt) {
				feltTeller++;

				if (feltTeller == 1)
					linje = linje + labelTab[feltTeller].trim();
				else
					linje = linje + feltSkilleTegn
							+ labelTab[feltTeller].trim();

				// mld(mldInd, "--- linje: " + linje);

			} // while feltteller
			utfil.println(linje);
			antUfilLinjer++;
		}

		mld(mldInd, "--- Behandling av utfil: 2.gangs les av innfil1 ---");
		linje = " ";

		linjeTeller = 0;

		try {
			linje = innfil1.readLine();
			if (linje == null)
				mld(mldInd,
						"Finner ingen linjer i innfil1 ved 2. gangs lesing!");
			while (linje != null) {
				linje = linje
						+ "                                                                                                   ";
				mld(mldInd, "--- linje: " + linje);

				linjeLengde = linje.length() - 1;

				mld(mldInd, "  linje: " + linje);
				mld(mldInd, "  linjeLengde: " + linjeLengde);

				linjeTeller++;

				feltTeller = 1;
				tegnTeller = 0;
				antPos = 0;

				linjeEkstraktInd = true;
				utfilLinje = "";

				if (inFmtFlytendeSdvInd.equals("J")) {
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
				}
				feltTeller = 0;
				tegnTeller = 1;

				while (feltTeller < antUtFelt) {
					feltTeller++;
					startPos = startPosTab[feltTeller] - 1;
					antTegn = antTegnTab[feltTeller];
					sluttPos = startPos + antTegn;
					mld(mldInd, "  startpos: " + startPos + " antTegn: "
							+ antTegn + " feltTeller: " + feltTeller);
					mld(mldInd, "  filNrTab[feltTeller]: "
							+ filNrTab[feltTeller]);
					mld(mldInd, "  labelTab[feltTeller]: "
							+ labelTab[feltTeller]);
					intTall = labelTab[feltTeller].length();
					mld(mldInd, "      labelTab lengde : " + intTall);
					mld(mldInd, "arvFeltInd[feltTeller]: "
							+ arvFeltInd[feltTeller]);

					if (feltSkilleInd.equals("J") & feltTeller != 1) {
						utfilLinje = utfilLinje + feltSkilleTegn;
						tegnTeller++;
					}

					if (filNrTab[feltTeller] == 0)
						tekst = (labelTab[feltTeller]).substring(0, antTegn);
					else {
						if (arvFeltInd[feltTeller]) {
							int startPos2 = startPos2Tab[feltTeller] - 1;
							int antTegn2 = antTegn2Tab[feltTeller];
							mld(mldInd, "  linjearvkrittekst: "
									+ linje.substring(startPos2, startPos2
											+ antTegn2));
							mld(mldInd, "       arvkrittekst: "
									+ arvKritTekst[feltTeller]);

							if (linje
									.substring(startPos2, startPos2 + antTegn2)
									.equals(arvKritTekst[feltTeller])) {
								mld(mldInd, "arvkriterium slår til!");

								arvFeltVerdi[feltTeller] = linje.substring(
										startPos, sluttPos);
								mld(mldInd, "arvfeltverdi: "
										+ arvFeltVerdi[feltTeller]);
							}
							if (arvFeltVerdi[feltTeller] != null)
								tekst = arvFeltVerdi[feltTeller];
						} else {
							if (inFmtFlytendeSdvInd.equals("J")) {
								tekst = sdvFeltVerdiTab[startPos + 1];
								if (tekst == null) {
									System.out
											.println("!! NULLPOINTEREXCEPTION!! Prøver å lese en sdv inputkolonne som ikke finnes:");
									System.out
											.println("Inputkolonnenr som mangler: "
													+ (startPos + 1));
									System.out.println("labelTab[feltTeller]: "
											+ labelTab[feltTeller]);
								}
								mld(mldInd, "sdvFeltVerdiTab[startPos + 1]: "
										+ sdvFeltVerdiTab[startPos + 1]);
							} else if (!(linjeLengde < sluttPos))
								tekst = linje.substring(startPos, sluttPos);
							else
								mld(mldInd,
										"  antTegn større enn linjelengden! ");

							mld(mldInd, "utFmtTab[feltTeller]: "
									+ utFmtTab[feltTeller] + "---");
							if (utFmtTab[feltTeller].equals("FN "))
								tekst = getFilePath(tekst);
						}
						if (utFmtFlytendeSdvInd.equals("J"))
							tekst = tekst.trim();
						else {
							intTall = tekst.length();
							if (intTall < antTegn)
								tekst = tekst
										+ blankLinje.substring(0, antTegn
												- intTall);
						}
						tekst2 = xtrKritTegnTab[feltTeller];
						if (!tekst2.equals(" ")) {
							intTall = tekst
									.compareTo(xtrKritTekstTab[feltTeller]);

							// mld("mldInd", "");
							// mld("mldInd", "tekst: " + tekst);
							// mld("mldInd", "xtrKritTekstTab[feltTeller]: " +
							// xtrKritTekstTab[feltTeller]);
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
						skrivreclofilLinje(tegnTeller, antTegn,
								labelTab[feltTeller], utFilRecFmt, tmpfilskriv);

					utfilLinje = utfilLinje + tekst;
					tegnTeller = tegnTeller + tekst.length();
				} // while feltteller
				if (linjeEkstraktInd == true) {
					utfil.println(utfilLinje);
					antUfilLinjer++;
				}
				linje = innfil1.readLine();
			} // slutt while
		} // try

		catch (NullPointerException e) {
			System.out.println("  NullPointerException !");
			throw e;
		} finally {
			innfil1.close();
			utfil.close();
			tmpfilskriv.close();
			antInnfilLinjer = linjeTeller;

			skrivStatistikk(antInnfilLinjer, antUtFelt, antUfilLinjer);
		}
	}

	private void skrivreclofilLinje(int startPos, int antPos, String label,
			String utFilRecFmt, PrintWriter fil) {
		String startPosString = String.valueOf(startPos);
		startPosString = zeroLinje.substring(0, (4 - startPosString.length()))
				+ startPosString;

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

		String utLinje = "O,1," + startPosString + "," + antPosString + ","
				+ label + blankLinje.substring(0, 33) + utFilRecFmt;

		fil.println(utLinje);
	}

	private String getFilePath(String tekst) {
		tekst = tekst.trim();
		if (tekst == null | tekst.equals(""))
			return null;
		int sluttPos = tekst.length();
		int index = sluttPos - 1;
		// mld("mldInd", "tekst: " + tekst + "---");
		while ((tekst.charAt(index) != '/') & (tekst.charAt(index) != ' ')
				& (tekst.charAt(index) != ';') & (index > 0))
			index--;
		if (tekst.charAt(index) == '/' | tekst.charAt(index) == ' '
				| tekst.charAt(index) == ';')
			index++;
		// mld("mldInd", "tekst: " + tekst + "---");
		return tekst.substring(index, sluttPos);
	}

	static void mld(String mldInd, String mld) {
		if (mldInd.equals("J"))
			System.out.println(mld);
	}

	static void skrivStatistikk(int antInnfilLinjer, int antUtFelt,
			int antUfilLinjer) {
		System.out.println("Antall linjer paa innfil1: " + antInnfilLinjer);
		System.out.println("    Antall felt paa utfil: " + antUtFelt);
		System.out.println("  Antall linjer paa utfil: " + antUfilLinjer);
	}

	private void lagreclofil() throws IOException {
		BufferedReader tmpfilles = Text.open(tmpfilNavn);
		PrintWriter reclofil = Text.create(recLayoutfilNavn);
		mld(mldInd, "--- Skriv av recordlayout fil ---");

		try {
			linje = tmpfilles.readLine();

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
