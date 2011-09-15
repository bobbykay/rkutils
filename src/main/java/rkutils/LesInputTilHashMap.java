package rkutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LesInputTilHashMap {

	static LesInputTilHashMap instance;

	private Map map;
	private String filnavn;

	public LesInputTilHashMap(String filnavn) {
		map = new HashMap();
		this.filnavn = filnavn;
		init();
	}

	public LesInputTilHashMap() {
	}

	public static LesInputTilHashMap getInstance(String filnavn) {
		if (instance == null) {
			instance = new LesInputTilHashMap(filnavn);
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

			while (sisteLinjeLest == false) {
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

		name = str;
		value = "";

		map.put(name, value);
	}

	public Map hentMap() {
		return map;
	}

	private static void skriv(String s) {
		System.out.println(s);
	}
}
