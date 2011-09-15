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

public class FilUtil {

  public static File createDirectory(String filename) {
	    // Create a directory; all ancestor directories must exist
	  File file = new File(filename);
	    boolean success = (file).mkdir();
	    if (!success) {
	    	// Create a directory; all non-existent ancestor directories are
	    	// automatically created
	    	boolean success2 = (new File(filename)).mkdirs();
	    
		    if (!success2) {
		    	file = null;
		    	System.out.println("Directory creation failed");
		    	} else {
		    		System.out.println("Directory creation successful");
		    		}
		    }
	    return file;
	    }

  public static boolean deleteDir(File file) {
	    // Deletes all files and subdirectories under dir.
	    // Returns true if all deletions were successful.
	    // If a deletion fails, the method stops attempting to delete and returns false.
	        if (file.isDirectory()) {
	            String[] children = file.list();
	            for (int i=0; i<children.length; i++) {
	                boolean success = deleteDir(new File(file, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
	        }
	    
	        // The directory is now empty so delete it
	        return file.delete();
	    }

  public static boolean existsDir(File file) {
	  boolean exists = (file).exists();
	  return exists;
	}
  
  public static void copyFile(File src, File dst) throws IOException {
//		 Copies src file to dst file.
//	 If the dst file does not exist, it is created
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


  public static File createFile(String filename) {
      File file = new File(filename);
	  try {
        // Create file if it does not exist
        boolean success = file.createNewFile();
        
        if (success) {
            // File did not exist and was created
        } else {
        	// File already exists
        	}
        } catch (IOException e) {
        	}
        return file;
        }

  public static boolean deleteFile(File file) {
	  boolean success = file.delete();
	  if (!success) {
		  // Deletion failed
		  }
	  return success;
  }
} // Klasse slutt
