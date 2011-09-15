package rkutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LesInputParametreFraFil {

	static LesInputParametreFraFil instance;

	private Map map;
	private String filnavn;

	public LesInputParametreFraFil(String filnavn) {
		map = new HashMap();
		this.filnavn = filnavn;
		init();
	}

	public LesInputParametreFraFil() {
	}

	public static LesInputParametreFraFil getInstance(String filnavn) {
		if (instance == null) {
			instance = new LesInputParametreFraFil(filnavn);
		}

		return instance;
	}

	public void init() {
		BufferedReader in = null;
		int linjeNr = 0;
		try {
			in = new BufferedReader(new FileReader(filnavn));
			String str;
			String utlinje = "";

			linjeNr = 0;

			boolean sisteLinjeLest = false;
			
			while (sisteLinjeLest == false ) {
				str = in.readLine();
				linjeNr++;

				if (str.equals("EOF")) {
					sisteLinjeLest = true;
				} else {
					if (str != null && str.length() > 0 && str.charAt(0) != '#')
						populerMapMedInnfilParametre(str);
				}
			}
			in.close();
		} catch (IOException e) {
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				skriv("IOException ifm. les av parameterfil:" + e);
			}
		}
	}

	private void populerMapMedInnfilParametre(String str) {

		String name = "", value = "";

		int posOfLikhetsTegn = str.indexOf('=');

		if (posOfLikhetsTegn > 0) {

			name = str.substring(0, posOfLikhetsTegn);
			value = str.substring(posOfLikhetsTegn + 1);
		}

		map.put(name, value);
	}

	public String hentParameter(String parameterNavn) {
		String parameter = "";

		if (map.containsKey(parameterNavn)) {
			parameter = (String) map.get(parameterNavn);
			return parameter;
		} else {
			return "";
		}
	}

	private static void skriv(String s) {
		System.out.println(s);
	}
}
