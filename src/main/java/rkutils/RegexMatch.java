package rkutils;

//import java.io.Console;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexMatch {

	static final boolean isSkrivPaa = false;

	public static int findMatchedPosision(String regexPattern, String searchText) {
		Pattern pattern = null;

		try {
			pattern = Pattern.compile(regexPattern);
		} catch (Exception e) {
			System.out.println("Regexp pattern parameter feiler");
		}

		skriv("Regexp til programmet er: " + regexPattern, isSkrivPaa);

		Matcher matcher = null;

		try {
			matcher = pattern.matcher(searchText);
		} catch (Exception e) {
			System.out.println("Inputtekst parameter mangler");
		}

		skriv("Inputtekst til programmet er: " + searchText, isSkrivPaa);

		boolean found = false;
		while (matcher.find() && !found) {

			skriv("I found the text " + matcher.group()
					+ " starting at index: " + matcher.start()
					+ " and ending at index:  " + matcher.end(), isSkrivPaa);

			found = true;
		}
		if (!found) {
			skriv("No match found.", isSkrivPaa);
			return -1;
		}
		return matcher.groupCount();
	}

	private static void skriv(String s, boolean isSkrivPaa) {
		if (isSkrivPaa) {
			System.out.println(s);
			}
		}
}
