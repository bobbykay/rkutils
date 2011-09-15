package rkutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import rkutils.LesInputParametreFraFil;

public class BildeUtvalg {

	static boolean isDebugMode = false;
	static boolean isCopy = false;
	static String innparameterFilnavn = "c://a//bildeutvalg.properties";
	static String bildeNavnFilterListeFil = "c://a//bildefilter.txt";
	static String toDirRoot = "C://TMP//TMPDIR";
	static String kilderotmappeInputParameter = "";
	static String startmappeInputParameter = "0";
	static String sluttmappeInputParameter = "zzzzz";
	static String REGEXP_PICTURE_TAKEN_TIMESTAMP = "[12][09][0-9][0-9][\\._][01][0-9][\\._][0123][0-9]\\s.*";
	static String REGEXP_PICTURE_TAKEN_YEAR = "[12][09][0-9][0-9]\\s.*";
	static int antallUtFiler = 0;
	static int utMappeTeller = 0;
	static int maksAntallFilerPerMappe = 100;
	static int antallFileriMappen = 0;
	static final String utfilnavn = "c:\\a\\robert.txt";
	static int antallRapportlinjer = 0;

	static final String FILTYPE_JPG = "JPG";
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static final Exception Exception = null;

	static Map alleBilder = new TreeMap<String, File>();
	static Map filNavnMapping = new TreeMap<String, String>();
	static Map bildeFilterListeMap = new HashMap<String, String>();

	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws Exception {

		skriv("--- Program starts ---", false);

		processInputParameters();

		String curDir = kilderotmappeInputParameter.replaceAll("\\\\", "\\\\\\\\");

		curDir = curDir.replaceAll("/", "//");

		boolean isKildeRotMappeOK = beBrukerOmBekreftelse(curDir);

		if (isKildeRotMappeOK) {
			File kilderotmappe = new File(curDir);

			if (deleteDir(new File(toDirRoot))) {
				skriv("Vellykket sletting av utmappe.", false);
			}
			createDirectory(toDirRoot);

			finnAlleFiler(kilderotmappe, true);

			behandleFilene();

		} else {
			skriv("Programmet avslutter...", false);
		}

	} // main slutt

	private static void processInputParameters() throws Exception {
		skriv("Her følger liste over program-argumenter: ", false);

		LesInputParametreFraFil l = null;

		try {
			l = l.getInstance(innparameterFilnavn);
		} catch (Exception e) {
			skriv("\n Klarer ikke å lese innparameter fil: " + innparameterFilnavn);
			throw new Exception();
		}

		kilderotmappeInputParameter = l.hentParameter("kildemappe");

		startmappeInputParameter = l.hentParameter("startmappe");

		sluttmappeInputParameter = l.hentParameter("sluttmappe");

		String maksAntallFilerPerMappeString = l.hentParameter("antfilerperutmappe");

		try {
			maksAntallFilerPerMappe = new Integer(maksAntallFilerPerMappeString);
		} catch (Exception e) {
			skriv("Feil parameter angitt for maksAntallFilerPerMappe", false);
		}

		isDebugMode = (l.hentParameter("debugmodus")).equalsIgnoreCase("debug");

		isCopy = l.hentParameter("copymodus").equalsIgnoreCase("copy");

		skriv("Startmappe: " + startmappeInputParameter, false);
		skriv("Sluttmappe: " + sluttmappeInputParameter, false);

		skriv("Maks antall filer i hver utmappe er: " + maksAntallFilerPerMappe, false);

		skriv("debugmodus: " + isDebugMode);

		skriv("kopimodus: " + isCopy);

		try {
			bildeFilterListeMap = new LesInputTilHashMap().getInstance(bildeNavnFilterListeFil).hentMap();
			skriv("Fant bildefilterlistfil med navnet: " + bildeNavnFilterListeFil + ". Behandler bare bilder angitt i filterfilen.");

		} catch (Exception e) {
			skriv("Finner ikke bildefilterlistfil med navnet: " + bildeNavnFilterListeFil + ". Utfører ingen filtrering.");
		} finally {
			beBrukerOmAtProgrammetSkalFortsette();
		}
	}

	private static void beBrukerOmAtProgrammetSkalFortsette() {
		System.out.println("Trykk på en tast for å fortsette!\n");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			in.readLine();
		} catch (IOException e) {
		}

	}

	private static boolean beBrukerOmBekreftelse(String curDir) {
		skriv("Er det riktig at kilderotmappen på java-format er: ===>" + curDir + "<=== ?", false);

		String str = "";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			while (str.equalsIgnoreCase("")) {
				str = in.readLine();
				skriv("tekst lest inn fra bruker: " + str, false);
			}
		} catch (IOException e) {
		}

		return str.equalsIgnoreCase("j");
	}

	// Process only files under dir
	public static void finnAlleFiler(File dir, boolean filter) {
		if (dir.isDirectory()) {
			skriv("Leter i mappen: " + dir.getAbsolutePath() + " filter: " + filter);

			String[] children = dir.list();
			Arrays.sort(children);

			// skriv("-- Mappe " + dir.toString()
			// + " inneholder følgende elementer:", true);

			for (int i = 0; i < children.length; i++) {
				// skriv("index " + i + ": " + children[i], true);
			}

			// skriv(" --- slutt på mappe " + dir.toString() + " ---", true);

			for (int i = 0; i < children.length; i++) {
				File newFile = new File(dir, children[i]);

				String newFileString = "";
				boolean inkluderMappe = false;

				newFileString = newFile.getName().toString();
				skriv("Fil som behandles: " + newFileString, false);

				if (filter) {

					boolean stennlavest = (newFileString.compareTo(startmappeInputParameter) < 0);
					boolean mindreennhoyest = (newFileString.compareTo(sluttmappeInputParameter) > 0);

					inkluderMappe = (filter && !(stennlavest) && !(mindreennhoyest));
				}

				if (!filter || inkluderMappe) {

					finnAlleFiler(new File(dir, children[i]), false);
				} else {
					// skriv("Hopper over mappen: " + newFileString + " filter: " + filter + " inkluderMappe: " + inkluderMappe);
				}
			}
		} else {
			String fileNameStripped = dir.getName();

			String fileType = fileNameStripped.substring((fileNameStripped.length() - 3));

			// if (!(fileType.equalsIgnoreCase("db"))) {

			boolean filterTaMed = (bildeFilterListeMap.size() == 0 || bildeFilterListeMap.containsKey(fileNameStripped));

			if (fileType.equalsIgnoreCase(FILTYPE_JPG) && filterTaMed) {
				alleBilder.put(dir.getName(), dir);
			}
		}
	}

	private static void behandleFilene() {

		lagGammelTilNyFilNavnMapping();

		utforKopieringOgRapportering();

		skrivStatistikk();

	}

	private static void skriv(String string) {
		skriv(string, false);
	}

	private static void utforKopieringOgRapportering() {
		String tilMappe = toDirRoot;

		antallFileriMappen = 0;

		BufferedWriter out = null;

		try {
			out = new BufferedWriter(new FileWriter(utfilnavn.replaceAll("\\\\", "\\\\\\\\")));

			for (Iterator<String> i2 = filNavnMapping.keySet().iterator(); i2.hasNext();) {
				if ((antallFileriMappen % maksAntallFilerPerMappe) == 0) {
					antallFileriMappen = 0;

					tilMappe = opprettNyUtmappe();
				}

				String nyttFilNavn = i2.next();

				String gammeltFilNavn = (String) filNavnMapping.get(nyttFilNavn);

				File nyFil = new File(tilMappe + "//" + nyttFilNavn);

				File gammelFil = (File) alleBilder.get(gammeltFilNavn);

				if (isCopy) {

					copyFile(gammelFil, nyFil);

					antallFileriMappen++;
					antallUtFiler++;
				}

				skrivRapportLinje(out, gammelFil, nyFil);
			}
		} catch (Exception e) {
			skriv("Feil oppstått ved skriv til rapportfil , feilmelding: " + e.toString(), false);
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				skriv("Feil ved lukking av utfilen", false);
			}
		}

	}

	private static void lagGammelTilNyFilNavnMapping() {
		for (Iterator<File> i = alleBilder.values().iterator(); i.hasNext();) {
			File fil = (File) i.next();

			String nyttFilNavn = finnNyttFilNavn(fil);

			String gammeltFilNavn = fil.getName();

			filNavnMapping.put(nyttFilNavn, gammeltFilNavn);
		}

	}

	private static void skrivStatistikk() {
		skriv("--- Antall rapportlinjer på filen: " + utfilnavn + " er: " + antallRapportlinjer);
		skriv("--- Antall filer kopiert: " + antallUtFiler, false);
		skriv("--- Program ends ---", false);
	}

	private static void skrivRapportLinje(BufferedWriter out, File gammelFil, File nyFil) {
		try {
			if (antallRapportlinjer == 0) {
				skriv("Oversikt over mapping mellom nytt og gammelt filnavn:");

				out.write("Rapport: FRAFIL STI;FRAFIL NAVN;TILFIL STI;TILFIL NAVN\n");
			}

			skriv("nytt navn: " + nyFil.getName() + " - gammelt navn: " + gammelFil.getName());

			out.write("Rapport: " + finnFilSti(gammelFil) + ";" + finnFilNavnKort(gammelFil) + ";" + finnFilSti(nyFil) + ";" + finnFilNavnKort(nyFil) + "\n");

		} catch (IOException e) {
			System.out.println("Feil ved skriving av rapportlinjer: " + e.toString());
		}

		antallRapportlinjer++;
	}

	private static String finnFilSti(File fil) {
		String fileNameStripped = fil.getName();
		int navnLengde = fileNameStripped.length();

		String filstiOgNavn = fil.getAbsolutePath();

		return filstiOgNavn.substring(0, (filstiOgNavn.length() - navnLengde));
	}

	private static String finnFilNavnKort(File fil) {
		return fil.getName();
	}

	private static void skriv(String string, boolean isDebugMessage) {
		if (!(isDebugMessage) || (isDebugMessage && isDebugMode)) {
			if (isDebugMessage) {
				System.out.print("### debugmelding ### ");
			}
			System.out.println(string);
		}
	}

	private static String finnNyttFilNavn(File fil) {
		String fileNameStripped = fil.getName();

		String fileParent = fil.getParent();

		if (fileNameStripped.startsWith("My")) {
			String s = "";
		}

		// skriv(dir + "%" + fileNameStripped + "?" +
		// fileParent);

		int i = fileParent.toString().lastIndexOf("\\");

		String parentMappe = fileParent.toString().substring(i + 1);

		// File utFil = new File(toDir + "//" + new
		// Integer(++teller).toString() + "@" + parentMappe + " " +
		// fileNameStripped);
		// File utFil = new File(toDir + "//" + parentMappe + " " +
		// fileNameStripped );

		skriv("Prøver å finne picture taken TS for bildefil: -->" + fileNameStripped + "<--", false);

		String utFilNavn = null;

		int regexMatchPosisjonForEvtDato = RegexMatch.findMatchedPosision(REGEXP_PICTURE_TAKEN_TIMESTAMP, fileNameStripped);

		if (regexMatchPosisjonForEvtDato != 0) {
			try {
				String bildeTattTS = getPictureTakenTS(fil);

				skriv(bildeTattTS, false);

				if (bildeTattTS.equals("")) {
					skriv("-- finner ikke picture taken TS, bruker modermappenavn som bildefilnavn-prefiks isteden", false);
					utFilNavn = finnNavnForManglendePictureTakenTimestamp(parentMappe, fileNameStripped);

				} else {
					utFilNavn = bildeTattTS + " " + fileNameStripped;
				}

			} catch (Exception e) {
				skriv("-- finner ikke picture taken TS, bruker modermappenavn som bildefilnavn-prefiks isteden", false);
				utFilNavn = finnNavnForManglendePictureTakenTimestamp(parentMappe, fileNameStripped);
			}
		} else {
			skriv("-- bildefilen har allerede timestamp prefix.", false);
			utFilNavn = fileNameStripped;
		}

		regexMatchPosisjonForEvtDato = RegexMatch.findMatchedPosision(REGEXP_PICTURE_TAKEN_TIMESTAMP, utFilNavn);

		if (regexMatchPosisjonForEvtDato != 0) {
			skriv("Mangler fullstendig timestamp i det nye filnavnet. Hekter på filstørrelse i tillegg.");

			long fileSize = fil.length();

			int pos = utFilNavn.lastIndexOf('.');

			String firstPart = utFilNavn.substring(0, pos);
			String lastPart = utFilNavn.substring(pos);

			utFilNavn = firstPart + " #" + fileSize + lastPart;
		}

		return utFilNavn;
	}

	private static String finnNavnForManglendePictureTakenTimestamp(String parentMappe, String fileNameStripped) {
		String nyttNavn = "";

		int regexMatchPosisjonForEvtAarstall = RegexMatch.findMatchedPosision(REGEXP_PICTURE_TAKEN_YEAR, fileNameStripped);

		boolean filnavnInneholderParentmappeNavn = fileNameStripped.contains(parentMappe);
		boolean filnavnErPrefiksetMedDatoAarstall = (regexMatchPosisjonForEvtAarstall == 0);

		if ((!(filnavnInneholderParentmappeNavn) && !filnavnErPrefiksetMedDatoAarstall)) {
			nyttNavn = parentMappe + " " + fileNameStripped;
		} else {
			if (filnavnInneholderParentmappeNavn) {
				skriv("---Opprinnelige filnavn inneholder allerede modermappenavn: beholder opprinnelige filnavn.", false);
			} else {
				if (filnavnErPrefiksetMedDatoAarstall) {
					skriv("---Opprinnelige filnavn starter allerede med Dato-årstall: beholder opprinnelige filnavn.", false);
				}
			}

			nyttNavn = fileNameStripped;
		}

		return nyttNavn;
	}

	public static void createDirectory(String dirName) {
		// Create a directory; all ancestor directories must exist
		boolean success = (new File(dirName)).mkdir();
		if (!success) {
			// Create a directory; all non-existent ancestor directories are
			// automatically created
			success = (new File(dirName)).mkdirs();

			if (!success) {
				skriv("Directory creation failed", false);
			} else {
				skriv("Directory creation successful", false);
			}
		}
	}

	public static void copyFile(File src, File dst) throws IOException {
		// Copies src file to dst file.
		// If the dst file does not exist, it is created
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	public static boolean deleteDir(File dir) {
		// Deletes all files and subdirectories under dir.
		// Returns true if all deletions were successful.
		// If a deletion fails, the method stops attempting to delete and
		// returns false.
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}

	private static String opprettNyUtmappe() {
		utMappeTeller++;

		String toDir = toDirRoot.concat("//DEL" + new Integer(utMappeTeller).toString());

		skriv("*** Nyopprettet utmappe: " + toDir + " ***", false);

		createDirectory(toDir);

		return toDir;
	}

	public static String getPictureTakenTS(File f) throws Exception {

		// se http://www.hugsan.com/EXIFutils/ for Windows commandline exiflist
		// program
		String toRet = "";

		// form command string
		String cmd[] = new String[2];
		cmd[0] = "exiflist";
		cmd[1] = f.getAbsolutePath();
		// cmd[2] = " > c://a//robert.txt";

		int pos = 0, pos2 = 0;

		try {

			// setup the Process and buffer
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader buffr = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = null;
			while (((line = buffr.readLine()) != null) && line.length() < 1000) {
				toRet = toRet.concat(line);
			}

			// skriv(toRet);

		} catch (IOException ioe) {
			System.err.print("Unknown IO Error in function getOwner(): ");
			System.err.println(ioe.getMessage());
			throw ioe;
		}

		String bildeTatt = "", bildeTatt2 = "";

		if ((pos = toRet.indexOf("Date/Time Modified")) > 0) {
			bildeTatt = (toRet.substring(pos + 27, pos + 27 + 19)).replace(':', '.');

			skriv("Picture Modified TS: " + bildeTatt);

			pos2 = pos;
		}

		if ((pos = toRet.indexOf("Date/Time Digiti")) > 0) {
			bildeTatt2 = (toRet.substring(pos + 27, pos + 27 + 19)).replace(':', '.');

			skriv("Picture Digitized TS: " + bildeTatt2);

			if (bildeTatt2.compareTo(bildeTatt) < 0) {
				bildeTatt = bildeTatt2;
				pos2 = pos;
			}
		}

		if ((pos = toRet.indexOf("Date/Time Taken")) > 0) {
			bildeTatt2 = (toRet.substring(pos + 27, pos + 27 + 19)).replace(':', '.');

			skriv("Picture Taken TS: " + bildeTatt2);

			if (bildeTatt2.compareTo(bildeTatt) < 0) {
				bildeTatt = bildeTatt2;
				pos2 = pos;
			}

			skriv("Picture TS: " + bildeTatt);
		}

		skriv("Posisjon: " + pos2);

		return bildeTatt;
	}

} // class Test4 slutt
