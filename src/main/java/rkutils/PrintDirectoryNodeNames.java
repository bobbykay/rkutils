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
import java.math.BigDecimal;
import java.util.*;

public class PrintDirectoryNodeNames {

        static String toDir = "C://TMP//TMPDIR";
        static int teller = 0;
        static String utfilnavn = "c:\\a\\data.txt";
        static int antall = 0;
        static final String RADTYPE_MAPPE = "Mappe";
        static final String RADTYPE_FIL = "Fil";
        static int filstiStartPos = 0;
        static boolean RelativFilStiInd = true;

        public static void main(String args[]) throws IOException {

                konsollut("Program starts");
                // String curDir = System.getProperty("user.dir");

                // String curDir = new String("C:\\Documents and Settings\\rokind\\Mine
                // dokumenter\\Mine bilder");

                String curDir = "";

                try {
                        curDir = args[0].replaceAll("\\\\", "\\\\\\\\")
                                        .replaceAll("\"", "").trim();
                        // curDir = (new String("\"" + curDir + "\""));
                } catch (Exception e) {
                        System.out.println("Rotmappe parameter mangler");
                }

                String absRelParameter = "";

                try {
                        absRelParameter = args[1];
                } catch (Exception e) {
                        absRelParameter = "abs";
                }

                if (absRelParameter.length() > 2
                                && absRelParameter.substring(0, 3).equalsIgnoreCase("abs")) {
                        RelativFilStiInd = false;
                        System.out.println("Oversikten viser absolutte filstier" + "\n");
                } else {
                        System.out.println("Oversikten viser relative filstier" + "\n");
                }

                String utfilnavn = "";

                try {
                        utfilnavn = args[2].replaceAll("\\\\", "\\\\\\\\");
                } catch (Exception e) {
                        utfilnavn = "c://a//data3.txt";
                }

                for (int i = 0; i < args.length; i++) {
                        skriv("arg" + i + " = " + args[i].toString());
                }
                File fil = new File(curDir);

                String rotMappeSti = fil.toString();

                if (RelativFilStiInd) {
                        filstiStartPos = rotMappeSti.lastIndexOf("\\");
                } else {
                        filstiStartPos = 0;
                }

                konsollut("Lager filoversikt for mappe: " + rotMappeSti
                                + " til utfilen: " + utfilnavn);

                try {
                        BufferedWriter out = new BufferedWriter(new FileWriter(utfilnavn
                                        .replaceAll("\\\\", "\\\\\\\\")));

                        int level = -1;
                        out
                                        .write("radtype;nivå;filstørrelse(byte);filsti lengde;filnavn lengde;filsti;filnavn"
                                                        + "\n");

                        visitAllFiles(fil, out, level);

                        // out.write("aString");
                        out.close();
                } catch (IOException e) {
                }

                konsollut("Antall filer i mappen: " + antall + ".");

                konsollut("Program ends.");

        } // main slutt

        private static void skriv(String string) {
                System.out.println(string);

        }

        private static void konsollut(String melding) throws IOException {
                System.out.println("--- " + melding);
        }

        private static long process(File dir, BufferedWriter out, int level)
                        throws IOException {
                String fileNameStripped = dir.getName();
                String fileParent = dir.getParent();
                int filnavnLengde = fileNameStripped.length();
                int filstiLengde = fileParent.length() - filstiStartPos;

                long length = dir.length();
                long filesizeMB = 0;

                // System.out.println(dir + "%" + fileNameStripped + "?" + fileParent);
                try {
                        antall++;

                        filesizeMB = Math.round(length / (1024 * 1024));

                        String outFileParent;
                        if (level > 0) {
                                outFileParent = fileParent.substring(filstiStartPos);
                        } else {
                                outFileParent = "";
                        }

                        out.write(RADTYPE_FIL + ";" + level + ";" + filesizeMB + ";" + length + ";"
                                        + filstiLengde + ";" + filnavnLengde + ";" + outFileParent
                                        + "\\;" + fileNameStripped + "\n");
                } catch (IOException e) {
                        throw e;
                }

                int i = fileParent.toString().lastIndexOf("\\");

                // System.out.println("Kopierer fra mappe: " +
                // fileParent.toString().substring(i+1));

                String parentMappe = fileParent.toString().substring(i + 1);

                // File utFil = new File(toDir + "//" + new Integer(++teller).toString()
                // + "@" + parentMappe + " " + fileNameStripped);

                return length;
        }

        // Process all files and directories under dir
        public static void visitAllDirsAndFiles(File dir, BufferedWriter out,
                        int level) throws Exception {
                process(dir, out, level);

                if (dir.isDirectory()) {
                        String[] children = dir.list();

                        for (int i = 0; i < children.length; i++) {
                                visitAllDirsAndFiles(new File(dir, children[i]), out, level);
                        }
                }
        }

        // Process only directories under dir
        public static void visitAllDirs(File dir, BufferedWriter out, int level)
                        throws IOException {
                if (dir.isDirectory()) {
                        process(dir, out, level);

                        String[] children = dir.list();
                        for (int i = 0; i < children.length; i++) {
                                visitAllDirs(new File(dir, children[i]), out, level);
                        }
                }
        }

        // Process only files under dir
        public static long visitAllFiles(File dir, BufferedWriter out, int level)
                        throws IOException {
                level++;
                long catsize = 0;
                long catsizeMB;

                if (dir.isDirectory()) {
                        String[] children = dir.list();
                        if (!(children == null)) {
                                Arrays.sort(children);

                                for (int i = 0; i < children.length; i++) {

                                        catsize += visitAllFiles(new File(dir, children[i]), out,
                                                        level);
                                }
                        }

                        String fileNameStripped = dir.getName();
                        int filnavnLengde = fileNameStripped.length();

                        String fileParent = dir.getParent();

                        if (fileParent == null) {
                                fileParent = "";
                        }

                        int filstiLengde = fileParent.length() - filstiStartPos;

                        //catsizeMB = Math.round(catsize / (1024 * 1024));
                        catsizeMB = Math.round(catsize / (1024 * 1024));

                        String filstiNavn = fileParent.toString();

                        String outFileParent;

                        if (level > 0) {
                                outFileParent = fileParent.substring(filstiStartPos);
                        } else {
                                outFileParent = "";
                        }

                        out.write(RADTYPE_MAPPE + ";" + level + ";" + catsize + ";"
                                        + filstiLengde + ";" + filnavnLengde + ";" + outFileParent
                                        + "\\;" + fileNameStripped + "\n");

                } else {
                        catsize += process(dir, out, level);
                }

                return catsize;
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

} // class Test4 slutt
