package rkutils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RegexMatchTest {

	@Test
	public void findMatchedPosisionTest() {
		String regexPattern = "[0-9]+\\_[0-9]+\\_[0-9]+\\s.*";
		String searchText = "2009_08_03 filenamex";

		int returkode = RegexMatch.findMatchedPosision(regexPattern, searchText);

		assertTrue(returkode == 0);

		searchText = "filenamex";

		returkode = RegexMatch.findMatchedPosision(regexPattern, searchText);

		assertTrue(returkode == -1);

		searchText = "2009_08_ filenamex";

		returkode = RegexMatch.findMatchedPosision(regexPattern, searchText);

		assertTrue(returkode == -1);

		String regexPattern2 = "[0-9]+\\.[0-9]+\\.[0-9]+\\s.*";
		String searchText2 = "2009.08.03 filenamex";

		returkode = RegexMatch.findMatchedPosision(regexPattern2, searchText2);

		assertTrue(returkode == 0);

		searchText2 = "filenamex";

		returkode = RegexMatch.findMatchedPosision(regexPattern2, searchText2);

		assertTrue(returkode == -1);

		searchText2 = "2009_08_03 filenamex";

		returkode = RegexMatch.findMatchedPosision(regexPattern2, searchText2);

		assertTrue(returkode == -1);

		regexPattern2 = "[0-9]+\\.[0-9]+\\.[0-9]+\\s.*";
		searchText2 = "2008.08.22 14.41.03 IMG_2265.JPG";

		returkode = RegexMatch.findMatchedPosision(regexPattern2, searchText2);

		assertTrue(returkode == 0);
		
		regexPattern2 = "[0-9]+\\.[0-9]+\\.[0-9]+\\s.*[0-2][0-9].[0-6][0-9].[0-6][0-9]\\s*";
		
		returkode = RegexMatch.findMatchedPosision(regexPattern2, searchText2);

		assertTrue(returkode == 0);
	}

}
