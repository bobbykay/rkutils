package rkutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import org.junit.*;

import static org.junit.Assert.*;

public class LesInputParametreFraFilTest {
	static LesInputParametreFraFil l;

	@Test
	public void populerMapMedInnfilParametreTest() {
		String parameterfilNavn = "c://a//parameterfil.txt";
		String parameternavn1 = "parameter1";
		String parameternavn2 = "parameter2";
		String parameterVerdi1 = "hei";
		String parameterVerdi2 = "GOOGLE";

		String[] filInnhold = new String[] {
				lagParameterPar(parameternavn1, parameterVerdi1),
				lagParameterPar(parameternavn2, parameterVerdi2) };

		lagParameterFil(parameterfilNavn, filInnhold);

		l = l.getInstance(parameterfilNavn);

		assertNotNull(l);

		assertNotNull((l.hentParameter(parameternavn1)));

		assertEquals(parameterVerdi1, l.hentParameter(parameternavn1));

		assertNotNull((l.hentParameter("parameter2")));

		assertEquals(parameterVerdi2, l.hentParameter(parameternavn2));

	}

	private void lagParameterFil(String filnavn, String[] filInnhold) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(filnavn));
			
			for (int i = 0; i < filInnhold.length; i++) {

				out.write(filInnhold[i]);
			}
			
			out.write("EOF");
			
		} catch (IOException e) {
		} finally {
			try {

				out.close();
			} catch (IOException e) {
				System.out.println("Exception ved forsøk av close av utfil: "
						+ e);
			}
		}
	}

	private String lagParameterPar(String navn, String verdi) {
		String par = navn + "=" + verdi + "\n";
		return par;
	}
}
