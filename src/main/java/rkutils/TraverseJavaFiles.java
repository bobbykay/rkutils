package rkutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

public class TraverseJavaFiles{

	  static String toDir = "C://TMP//TMPDIR";
	  static int teller = 0;
	  static HashSet<String > stringTab = new HashSet<String>();
	  
  public static void main (String args []) throws IOException {
	  
	  System.out.println("--- Program starts ---");
	  //	  String curDir = System.getProperty("user.dir");

//	  String curDir = new String("C:\\Documents and Settings\\rokind\\Mine dokumenter\\Mine bilder");
	  
	  String arg0 = args[0];
	  
	  String curDir = arg0.replaceAll("\\\\", "\\\\\\\\");
	  curDir = curDir.replaceAll("/", "//");
	  
//	  System.out.print("Ut-mappen er: " + arg0 + " Er det riktig (j/n)? > ");
	  
        String str = "";
        
        /*
	  try {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (str.equalsIgnoreCase("")) {
            str = in.readLine();
            System.out.println("tekst lest inn fra bruker: " + str);
        }
    } catch (IOException e) {
    }
    */
        
    str = new String("j");
    
    if (str.equalsIgnoreCase("j")) {
	  File fil = new File(curDir);
	  
	  if (deleteDir(new File(toDir))) {
//		  System.out.println("Vellykket sletting av utmappe.");
	  }
	  createDirectory(toDir);

	  visitAllFiles(fil);
	  
    	
    } else {
    	System.out.println("Utmappen ikke godkjent");
    }

    
    try {
        BufferedWriter out = new BufferedWriter(new FileWriter("C://a//data.txt"));
        
        out.write("fil;tabell" + "\n");
        for (String s : stringTab) {
            out.write(s + "\n");
        }
        out.close();
    } catch (IOException e) {
    	}
    

    System.out.println("Mappen:" + "\n" + arg0 + "\n" + "inneholder " + stringTab.size() + " kombinasjoner av .java-filer og insert-tabeller.");
    
    System.out.println("--- Program ends ---");	  
	  
  } // main slutt

  private static void process(File dir)  {
	  String fileNameStripped = dir.getName();
	  String fileParent = dir.getParent();	  
	  
//	  System.out.println(dir + "%" + fileNameStripped + "?" + fileParent);
	  
	  int i = fileParent.toString().lastIndexOf("\\");
	  
//	  System.out.println("Kopierer fra mappe: " + fileParent.toString().substring(i+1));
	  
	  String parentMappe = fileParent.toString().substring(i+1);

//	  File utFil = new File(toDir + "//"  + new Integer(++teller).toString() + "@" + parentMappe + " " + fileNameStripped);	  
	  File utFil = new File(toDir + "//"  + parentMappe + " " + fileNameStripped );	  

	   String fileType = fileNameStripped.substring((fileNameStripped.length() - 4));
	   
//	  System.out.println("Kopierer til " + utFil);

	  if (fileType.toUpperCase().equals("JAVA"))  {
	  
	  try {
		  traverseFile(dir, fileNameStripped);
	  	  
//	  System.out.println("in: " + dir + "% ut: " + utFil);
//	  System.out.println(dir.getAbsolutePath());
//	  System.out.println(dir.getCanonicalPath());
//	  System.out.println(dir.getPath());
//	  System.out.println(dir.getCanonicalFile());	  
//	  System.out.println(dir.getParentFile());
		  
	  } catch (Exception e) {
		  System.out.println("Feil oppstått ved copy, innfil: " + dir); 
		  }
	  }
  }
  
		  
	
		  private static void process(String string, String filnavn)  {
			  int j = string.toUpperCase().indexOf("INSERT");
			  int jj = string.length();			  

/*
			  if (filnavn.equals("AktivitetsDAO.java") && jj == 76 ) {

				  System.out.println("linjelengde: "  + jj);
			  }
*/			  
			  
			  if (j > 0) {
				  String string2 = string.substring(j + 5);
				  int k = string2.toUpperCase().indexOf("INTO");
				  if ((k > 0) && string2.length() > 10) {
					  int m = k + 4;
					  int stringSize = string2.length() - 1;
					  while ((m < stringSize) && string2.charAt(m)!= ' ' ) m++;

					  if (m < stringSize) {
						  int n = m + 1;
						  
						  Character c = string2.charAt(n);
						  
						  while (n < stringSize && (!(c.compareTo(' ') == 0  || c.compareTo('(') == 0))) {
		//						  System.out.println(string2.charAt(n));
							  n++;
							  
							  c = string2.charAt(n);
							  }
						  if (n > m) {
							  String element = string2.substring(m,n);
							  if ((element.indexOf('"') <= 0) && (element.indexOf('(') <= 0) && (element.indexOf('/') <= 0))
							  leggTilElement(string2.substring(m,n), filnavn);
							  }
						  }
					  }
				  }
			  }
		  

  private static void leggTilElement(String tabellnavn, String filnavn) {
	  stringTab.add(filnavn + ';' + tabellnavn);
	
}

private static void traverseFile(File dir, String filnavn) {
	    try {
	        BufferedReader in = new BufferedReader(new FileReader(dir));
	        String str;
	        while ((str = in.readLine()) != null) {
	            process(str, filnavn);
	        }
	        in.close();
	    } catch (IOException e) {
	    }
	  
	
}

// Process all files and directories under dir
  public static void visitAllDirsAndFiles(File dir) throws Exception {
      process(dir);
  
      if (dir.isDirectory()) {
          String[] children = dir.list();

          for (int i=0; i<children.length; i++) {
              visitAllDirsAndFiles(new File(dir, children[i]));
          }
      }
  }
  

// Process only directories under dir
  public static void visitAllDirs(File dir) {
      if (dir.isDirectory()) {
          process(dir);
  
          String[] children = dir.list();
          for (int i=0; i<children.length; i++) {
              visitAllDirs(new File(dir, children[i]));
          }
      }
  }
  
  // Process only files under dir
  public static void visitAllFiles(File dir) {
      if (dir.isDirectory()) {
          String[] children = dir.list();
          Arrays.sort(children);

//          System.out.println("-- Mappe " + dir.toString() + " inneholder følgende elementer:");
/*
          for (int i=0; i<children.length; i++) {
              System.out.println("index " + i + ": " + children[i]);
          }
*/
//          System.out.println(" --- slutt på mappe " + dir.toString() + " ---" );

          for (int i=0; i<children.length; i++) {

              visitAllFiles(new File(dir, children[i]));
          }
      } else {
          process(dir);
      }
  }


  public static void createDirectory(String dirName) {
	    // Create a directory; all ancestor directories must exist
	    boolean success = (new File(dirName)).mkdir();
	    if (!success) {
	    	// Create a directory; all non-existent ancestor directories are
	    	// automatically created
	    	success = (new File(dirName)).mkdirs();
	    
		    if (!success) {
		    	System.out.println("Directory creation failed");
		    	} else {
		    		System.out.println("Directory creation successful");
		    		}
		    }
	    }

  public static void copyFile(File src, File dst) throws IOException {
//	 Copies src file to dst file.
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
	    // If a deletion fails, the method stops attempting to delete and returns false.
	        if (dir.isDirectory()) {
	            String[] children = dir.list();
	            for (int i=0; i<children.length; i++) {
	                boolean success = deleteDir(new File(dir, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
	        }
	    
	        // The directory is now empty so delete it
	        return dir.delete();
	    }
	  
} // class Test4 slutt
