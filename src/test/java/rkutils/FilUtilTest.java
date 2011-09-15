package rkutils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
//import junit.framework.TestCase;
import rkutils.FilUtil;
import sun.net.www.protocol.file.FileURLConnection;

import org.junit.*;

public class FilUtilTest {

	@Test
	public void testCreateDirectory() {
		String dirname= "c://temptest";

		File file = new File(dirname);
		
		if (FilUtil.existsDir(file)) {
			FilUtil.deleteDir(file);
			}
		
		file = FilUtil.createDirectory(dirname);
		assertTrue(FilUtil.existsDir(new File(dirname)));
	}
	
	@Test
	public void testDeleteDirectory() {
		String dirname= "c://temptest";
		File file = new File(dirname);
		
		if (!FilUtil.existsDir(file)) {
			file = FilUtil.createDirectory(dirname);
			}
		
		assertTrue(FilUtil.existsDir(new File(dirname)));
		assertTrue(FilUtil.deleteDir(new File(dirname)));
		assertFalse(FilUtil.existsDir(new File(dirname)));		
		}

	
	@Test
	public void testCreateFile() {
	String dirname= "c://temptest//";
	
	String filename= "temptestfile.txt";	

	String pathandDocumentname = dirname  + filename;
	
	File file = null;

	if (!FilUtil.existsDir(new File(dirname))) {
		file = FilUtil.createDirectory(dirname);
		
		}
	
	if (!FilUtil.existsDir(new File(pathandDocumentname))) {
		file = FilUtil.createFile(dirname + filename);
		
		}
	
	assertTrue(FilUtil.existsDir(file));
	}
	
	
	@Test
	public void testDeleteFile() {
		String dirname= "c://temptest//";
		
		String filename= "temptestfile.txt";	

		String pathandDocumentname = dirname  + filename;

		File file = null;
		
		if (!FilUtil.existsDir(new File(dirname))) {
			file = FilUtil.createDirectory(dirname);
			
			}
		
		if (!FilUtil.existsDir(new File(pathandDocumentname))) {
			file = FilUtil.createFile(dirname + filename);
			}
		
		file = new File(pathandDocumentname);

		assertTrue(FilUtil.existsDir(file));
		
		FilUtil.deleteFile(file);
		
		assertFalse(FilUtil.existsDir(file));		
	

		}

	@Test
	public void testCopyFile() {
		String dirnameFra= "c://temptest//";
		
		String filenameFra= "temptestfilein.txt";	

		String pathandDocumentnameFra = dirnameFra + filenameFra;

		
		String dirnameTil = "c://temptest//";
		
		String filenameTil = "temptestfileout.txt";	

		String pathandDocumentnameTil = dirnameTil + filenameTil;

		File file = new File(pathandDocumentnameFra);
		
		if (FilUtil.existsDir(file)) {
			FilUtil.deleteFile(file);
			}
		
		assertFalse(FilUtil.existsDir(file));
		

		file = new File(pathandDocumentnameTil);
		
		if (FilUtil.existsDir(file)) {
			FilUtil.deleteFile(file);
			}
		
		assertFalse(FilUtil.existsDir(file));
		
		FilUtil.createDirectory(dirnameTil);
		
		assertTrue(FilUtil.existsDir(new File(dirnameTil)));		

		file = FilUtil.createFile(dirnameFra + filenameFra);		
		
		assertTrue(FilUtil.existsDir(file));
		
		try {
			
		FilUtil.copyFile(new File(pathandDocumentnameFra), new File(pathandDocumentnameTil));
		} catch (IOException e) {
			System.out.println("IOException oppstod i.f.m. testCopyFile");
	}
		assertTrue(FilUtil.existsDir(new File(pathandDocumentnameTil)));		


	String dirnameTil2 = "c://temptest//udir//";
	
	String pathandDocumentnameTil2 = dirnameTil2 + filenameTil;

	file = new File(pathandDocumentnameTil2);
	
	if (FilUtil.existsDir(file)) {
		FilUtil.deleteFile(file);
		}
	
	assertFalse(FilUtil.existsDir(new File(pathandDocumentnameTil2)));
	
	if (!FilUtil.existsDir(new File(dirnameTil2))) {
		FilUtil.createDirectory(dirnameTil2);
	}
	
	assertTrue(FilUtil.existsDir(new File(dirnameTil2)));		

	try {
		
		FilUtil.copyFile(new File(pathandDocumentnameFra), new File(pathandDocumentnameTil2));
		} catch (IOException e) {
			System.out.println("IOException oppstod i.f.m. testCopyFile");
	}
		assertTrue(FilUtil.existsDir(new File(pathandDocumentnameTil2)));		

	
	}	
	
	}
