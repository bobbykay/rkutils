package rkutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

public class TraverseDirectoryJavaFiles {

	  static String toDir = "C://TMP//TMPDIR";
	  static int teller = 0;
	  
  public static void main (String args []) throws IOException {
	  
	  System.out.println("--- Program starts ---");
	  //	  String curDir = System.getProperty("user.dir");

//	  String curDir = new String("C:\\Documents and Settings\\rokind\\Mine dokumenter\\Mine bilder");
	  
	  String arg0 = args[0];
	  
	  String curDir = arg0.replaceAll("\\\\", "\\\\\\\\");
	  curDir = curDir.replaceAll("/", "//");
	  
	  System.out.print("Ut-mappen er: " + arg0 + " Er det riktig (j/n)? > ");
	  
        String str = "";
	  try {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (str.equalsIgnoreCase("")) {
            str = in.readLine();
            System.out.println("tekst lest inn fra bruker: " + str);
        }
    } catch (IOException e) {
    }
    
    if (str.equalsIgnoreCase("j")) {
	  File fil = new File(curDir);
	  
	  if (deleteDir(new File(toDir))) {
		  System.out.println("Vellykket sletting av utmappe.");
	  }
	  createDirectory(toDir);

	  visitAllFiles(fil);
	  
    	
    } else {
    	System.out.println("Utmappen ikke godkjent");
    }

	  System.out.println("--- Program ends ---");	  
	  
  } // main slutt

  private static void process(File dir)  {
	  String fileNameStripped = dir.getName();
	  String fileParent = dir.getParent();	  
	  
//	  System.out.println(dir + "%" + fileNameStripped + "?" + fileParent);
	  
	  int i = fileParent.toString().lastIndexOf("\\");
	  
	  System.out.println("Kopierer fra mappe: " + fileParent.toString().substring(i+1));
	  
	  String parentMappe = fileParent.toString().substring(i+1);

//	  File utFil = new File(toDir + "//"  + new Integer(++teller).toString() + "@" + parentMappe + " " + fileNameStripped);	  
	  File utFil = new File(toDir + "//"  + parentMappe + " " + fileNameStripped );	  

	   String fileType = fileNameStripped.substring((fileNameStripped.length() - 4));
	   
	  System.out.println("Kopierer til " + utFil);

	  if (fileType.toUpperCase().equals("JAVA"))  {		  
	  
	  try {
		  copyFile(dir, utFil);
	  	  
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

          System.out.println("-- Mappe " + dir.toString() + " inneholder følgende elementer:");

          for (int i=0; i<children.length; i++) {
              System.out.println("index " + i + ": " + children[i]);
          }

          System.out.println(" --- slutt på mappe " + dir.toString() + " ---" );

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
