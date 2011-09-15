package rkutils;

import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import rkutils.javagently.Text;

import rkutils.repository.GeneriskQueryDao;

/**
 * Created by IntelliJ IDEA.
 * Date: 05.feb.2004
 * Time: 14:21:04
 * @author Robert Kindingstad
 */
public class JdbcSqlPoi {
	GeneriskQueryDao generiskQueryDao;
	public void setGeneriskQueryDao(GeneriskQueryDao generiskQueryDao) {
		this.generiskQueryDao = generiskQueryDao;
	}	
    String sqlfilNavn = "c:/javaio/sqlin.txt";
    static String connectfilNavn = "c:/javaio/sqlconn.txt";
    String utfilNavn  = "c:/a/data.txt";
    String xlutfilNavn  = "c:/a/data.xls";


	public void setSqlfilNavn(String sqlfilNavn) {
		this.sqlfilNavn = sqlfilNavn;
	}

	public void setUtfilNavn(String utfilNavn) {
		this.utfilNavn = utfilNavn;
	}
    
	public void setXlutfilNavn(String xlutfilNavn) {
		this.xlutfilNavn = xlutfilNavn;
	}
	
	static final char VENSTREJUSTERT = 'V';
    static final char HOYREJUSTERT = 'H';
    static final char TEXTFORMAT = 'T';
    
    static final char NUMERICFORMAT = 'N';
    static final char DATEFORMAT = 'D';       
    static final char GENERELLFORMAT = 'G';    

    static char[]feltJusteringTegn = new char[100];
    static HSSFCellStyle[] cellStyleNumericTab = new HSSFCellStyle[10];
    
    static HSSFWorkbook wb = new HSSFWorkbook();
    static HSSFSheet sheet = wb.createSheet("new sheet");
    static HSSFCellStyle cellStyleText = wb.createCellStyle();

    static HSSFCellStyle cellStyleNumeric = wb.createCellStyle();
    
    static HSSFCellStyle cellStyleDate= wb.createCellStyle();
    static HSSFCellStyle cellStyleGenerell= wb.createCellStyle();
    
//  cellStyleDate.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd.mm.åååå"));
    
    int[]feltLengde = new int[100];
    String[] feltTab = new String[100];    
    String[] filKjennetegn = new String[3];
//    static String linje = " ", feltlinje, utlinje, member =  " ", hjelpString, feltSkilleInd = " ", feltSkilleTegn = " ", mldInd = " ";

    static String mldInd = " ";    
//    int filtype = 0, linjeTeller = 0, antUtFelt = 0, feltTeller = 0;
//    int startPos, sluttPos, antTegn, linjeLengde, labelSluttPos, antInnfilLinjer = 0, antUfilLinjer = 0, fromPos = 0, fromPos2 = 0, kommaPos = 0,
//    	asPos = 0, asPos2 = 0;

//    String databaseUrl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(COMMUNITY=tcp)(PROTOCOL=TCP)(HOST = 192.168.9.100)(Port=1521))(ADDRESS=(COMMUNITY=tcp)(PROTOCOL=TCP)(HOST = 192.168.9.100)(Port=1521)))(CONNECT_DATA=(SID=NB2B)))";
//    String user = "an20";
    String password = "askonett";

    private char tegn;
    static private int antFelt = 0, index = 0;
    private int sqlStmLength = 0;
    private String feltVerdi;
    static private String sqlStatement;
    private String blankeTegn = "                                 ";

    /*
    public static void main (String[]args) throws IOException {
//        Resource resource = new ClassPathResource("beans.xml");
//        BeanFactory factory = new XmlBeanFactory(resource);

        XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("beans.xml"));    	
        PropertyPlaceholderConfigurer cfg = new PropertyPlaceholderConfigurer();
        cfg.setLocation(new ClassPathResource("db.properties"));
        cfg.postProcessBeanFactory(factory);

        
        


        try {
          mldInd = args[0].trim();
        } // try
        catch (ArrayIndexOutOfBoundsException e) {}

      JdbcSqlPoi exs = new JdbcSqlPoi();
//      exs.generiskQueryDao = (GeneriskQueryDao) factory.getBean("generiskQueryDao");      
      try {
         exs.kjorSql();

      } catch (SQLException e) {
         e.printStackTrace();  //To change body of catch statement use Options | File Templates.
      }
              }
*/
   public void kjorSql(String mldInd) throws SQLException, IOException {
	   // hent konfigurasjonsparametre
	   
	   this.mldInd = mldInd;
	   
//	   hentKonfig();

	   PrintWriter utfil         = Text.create(utfilNavn);

	    cellStyleText.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));

	    cellStyleNumeric.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
	     
	 //  cellStyleDate.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd.mm.åååå"));
	   cellStyleDate.setDataFormat(HSSFDataFormat.getBuiltinFormat("d-mmm-yy"));       
	   
	   cellStyleGenerell.setDataFormat(HSSFDataFormat.getBuiltinFormat("General"));	   
       
       cellStyleNumericTab[0] = wb.createCellStyle();
       cellStyleNumericTab[0].setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
       cellStyleNumericTab[1] = wb.createCellStyle();
       cellStyleNumericTab[1].setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.0"));       
       cellStyleNumericTab[2] = wb.createCellStyle();
       cellStyleNumericTab[2].setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));       
       cellStyleNumericTab[3] = wb.createCellStyle();
       cellStyleNumericTab[3].setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.000"));       
       cellStyleNumericTab[4] = wb.createCellStyle();
       cellStyleNumericTab[4].setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.0000"));       
       cellStyleNumericTab[5] = wb.createCellStyle();
       cellStyleNumericTab[5].setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00000"));       
       cellStyleNumericTab[6] = wb.createCellStyle();
       cellStyleNumericTab[6].setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.000000"));       
       cellStyleNumericTab[7] = wb.createCellStyle();
       cellStyleNumericTab[7].setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.0000000"));       
       cellStyleNumericTab[8] = wb.createCellStyle();
       cellStyleNumericTab[8].setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00000000"));       
       cellStyleNumericTab[9] = wb.createCellStyle();
       cellStyleNumericTab[9].setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.000000000"));       

       int diff = 0, lengde = 0;


       // les inn sql-statement og bygg opp kolonneformat-tabell
       parseSql();

       HSSFRow xlRow = sheet.createRow((short)0);

       // lag og skriv kolonneoverskrifter
       for (int i = 0; i < antFelt; i++) {
        if (i != 0)  utfil.print(";");
           utfil.print(feltTab[i]);
           
           HSSFCell cell = xlRow.createCell((short)i);
           cell.setCellValue(feltTab[i]);


           if (mldInd.equals("J")) {
        	   if (i != 0)  System.out.print(";");
        	   System.out.print(feltTab[i]);
        	   }
           }
       
       utfil.println("");

       if (mldInd.equals("J"))
         System.out.println("");       

//      eksekver SQL
		List<Object> queryList = generiskQueryDao.getAlleRader(sqlStatement, antFelt);
         
         try {         
// skriv resultatsett til utfil         
         int antUtFilLinjer = 0;

         Object[] rad = null;
         
         for (Object rs: queryList) {
            rad = (Object[]) rs;        	 
            antUtFilLinjer ++;
    	    xlRow = sheet.createRow((short)antUtFilLinjer);

            for (int i = 0; i < antFelt; i++) behandleUtLinje(i, utfil, xlRow, rad);
              
            utfil.println("");

            if (mldInd.equals("J"))
               System.out.println("");
         }
 	    // Write the output to a file
 	    FileOutputStream fileOut = new FileOutputStream(xlutfilNavn);
 	    wb.write(fileOut);
 	    fileOut.close();

         System.out.println("");
         System.out.println("Antall rader i svarsettet: " + antUtFilLinjer);

      } catch (Exception e) {
         e.printStackTrace();  //To change body of catch statement use Options | File Templates.
      } finally {
//         ps.close();
//         con.close();
         utfil.close();
      }
      
//      lagPoi();
   }

   private void behandleUtLinje(int i, PrintWriter utfil, HSSFRow row, Object[] rad) throws IOException {

       if (i != 0)  utfil.print(";");
       
       HSSFCell cell = row.createCell((short)i);
       
       if (!(rad[i] == null)) {
     	  feltVerdi = rad[i].toString();
       } else {
     	  feltVerdi = null;
     	  }

       if (feltVerdi == null) feltVerdi = "";

       int lengde = feltVerdi.length();

//       mld("J", "feltnr. " + i + ": " + feltVerdi + "/" + lengde);

       int diff = feltLengde[i] - lengde;

       if (feltJusteringTegn[i] == NUMERICFORMAT) {
      	 feltVerdi =  feltVerdi.replace('.', ',');
       } else {
//     	  if (feltJusteringTegn[i] == TEXTFORMAT && feltVerdi. length() > 10 && !(feltVerdi.indexOf('.') > 0) && !(feltVerdi.indexOf('-') > 0 )) {
//     		  feltVerdi = "\'" + feltVerdi;
//     	  }
       }
       
//       if (diff > 0){
       if (false){              
          if (feltJusteringTegn[i] == NUMERICFORMAT) {
             utfil.print(blankeTegn.substring(0, diff));
             utfil.print(feltVerdi);
          }
          else {
             utfil.print(feltVerdi);
             utfil.print(blankeTegn.substring(0, diff));
               }
       }
       else utfil.print(feltVerdi);

       if (mldInd.equals("J")) {
          if (i != 0)  System.out.print(";");
         System.out.print(feltVerdi);
       }

       Double celleVerdi = null;
		  double faktor = 1.0;              
       
       if (feltJusteringTegn[i] == NUMERICFORMAT) {
//     	  if (feltLengde[i] > 0) {
     		  try {
     			  double d = new Double((feltVerdi.trim().replace(',', '.'))).doubleValue();
     			  
//     			  celleVerdi = new Double((feltVerdi.trim().replace(',', '.')));
     			  for (int k = 0; k < feltLengde[i]; k++){
     				  faktor = faktor * 10;

     			  }
     			  
     			  celleVerdi = new Double((Math.round(d * faktor))/faktor);
     			  
     			  cell.setCellValue(celleVerdi);
     			  } catch(Exception e) {
     				  cell.setCellValue(feltVerdi.trim());
     				  }
     			  } else {
     				  if (feltJusteringTegn[i] == DATEFORMAT) {
     					  try {
     						  System.out.println(feltVerdi);
			            		  System.out.println(Timestamp.valueOf((feltVerdi).replace('.', '-').trim().concat(" 00:00:00.0")));
			            		  
			            		  cell.setCellValue(Timestamp.valueOf((feltVerdi).replace('.', '-').trim().concat(" 00:00:00.0")  ));
			            		  } catch (Exception e) {
			            			  cell.setCellValue(feltVerdi.trim());
			            			  }
			            		  } else {
			            			  cell.setCellValue(feltVerdi.trim());
			            			  }
     				  }
//       java.sql.Timestamp ts2 = java.sql.Timestamp.valueOf("2005-04-06 09:01:10");
       char c = feltJusteringTegn[i];
       
       if (c == NUMERICFORMAT) {
     	  int j = feltLengde[i];
     	  if (j == 2 || j == 0) {
     		  cell.setCellStyle(cellStyleNumericTab[feltLengde[i]]);
     		  }
     	  } else {
               if (c == DATEFORMAT) {
             	  cell.setCellStyle(cellStyleDate);
               } else {
            	   if (c == TEXTFORMAT) {
                  	  cell.setCellStyle(cellStyleText);
                    } else {
                    	cell.setCellStyle(cellStyleGenerell);
                    }
             	  }
               }
     
	   
   }
   
   private void parseSql() throws IOException {
       // Initier feltjustering til venstrejustert for alle felt som default
       for (int i = 0;i < 100;i++) {
           feltJusteringTegn[i] = TEXTFORMAT;
           feltLengde[i] = 0;
       }

       sqlStatement = "";

       mld("J", "\n SQL statement som kjøres:");

       mld("J", "_________________________________________________");

       BufferedReader sqlfil     = Text.open(sqlfilNavn);
       
       try {
          String linje = sqlfil.readLine();

          String tekst = "";
          int startPos = 0, sluttPos = 0;

          if (linje.length() > 16) tekst = linje.substring(0, 16);

// sett feltformat ut fra definisjoner fra inputfil
          if (tekst.equals("-- fieldlengths=")) {
               startPos = 16;
               index = 0;
               tegn = linje.charAt(startPos);
               sqlStmLength = linje.length() + 1;

               while (startPos < sqlStmLength && (linje.charAt(startPos) != ' ')){
                   sluttPos = startPos + 3;

//                   mld("J","lengde feltnr. " + index + ": " + linje.substring(startPos, sluttPos));

                   feltJusteringTegn[index] = linje.charAt(startPos);
                   
                   char c = feltJusteringTegn[index]; 
                   
                   if (feltJusteringTegn[index] == VENSTREJUSTERT || feltJusteringTegn[index] == TEXTFORMAT) {
                	   feltJusteringTegn[index] = TEXTFORMAT;
                	   } else {
                		   if (feltJusteringTegn[index] == HOYREJUSTERT || feltJusteringTegn[index] == NUMERICFORMAT) {
                			   feltJusteringTegn[index] = NUMERICFORMAT;
                		   } else {
                			   feltJusteringTegn[index] = GENERELLFORMAT;
                			   }
                		   }

                   feltLengde[index] = Integer.parseInt(linje.substring(startPos + 1, sluttPos));

//                   mld("J","lengde feltnr. " + index + ": " + feltJusteringTegn[index] + "/" + feltLengde[index]);

                   startPos = startPos + 4;

                   index ++;
               } // end while

//          maksAntFeltLengder = index - 1;

            linje = sqlfil.readLine();
          } // end if

// les inn SQL linje for linje og bygg opp felttabell
           while (linje != null) {
               mld("J", linje);
               
               startPos = linje.indexOf("--");
               
               if (startPos > 0) {
                   linje = linje.substring(0, startPos);      	   
               }

               if (!(startPos==0)) {
                   sqlStatement = sqlStatement + linje.trim() + " " ;
                   }

               linje        = sqlfil.readLine();
           }

          mld("J", "_________________________________________________");

//          Text.prompt("Trykk enter for å fortsette!");
//          inn.readLine();

           if (mldInd.equals("J")) System.out.println("Startpos for select er : " + startPos );

           startPos = 6;
           sluttPos = 0;
           
           sqlStmLength = sqlStatement.length() + 1;

           int fromPos = sqlStatement.indexOf(" from ");
           
           int fromPos2 = sqlStatement.indexOf(" FROM ");
           
           if (fromPos < 0) fromPos = sqlStmLength;
           if (fromPos2 < 0) fromPos2 = sqlStmLength;
           if (fromPos2 < fromPos) fromPos = fromPos2;

           while ((startPos < fromPos) & antFelt < 99){
        	   boolean isAsFelt = false;
        	   // finn først kommaposisjonen som skiller mot neste felt
        	   
             tegn = sqlStatement.charAt(startPos);
             int pos = startPos;
             int antParenteser = 0;
			while ((!(tegn == ',') || antParenteser > 0) && pos < fromPos) {
            	if (tegn == '(') {
            		antParenteser++;
            	    isAsFelt = true;
			}
            	else
            		if (tegn == ')') {antParenteser--;}
				pos++;
				tegn = sqlStatement.charAt(pos);
             }
			
			int kommaPos = Math.min(pos, fromPos); 
             
             int asPos = sqlStatement.substring(startPos, fromPos).indexOf(" as ");
             if (asPos < 0) asPos = fromPos; else asPos = asPos + startPos;
             
             
             int asPos2 = sqlStatement.substring(startPos, fromPos).indexOf(" AS ");
             if (asPos2 < 0) asPos2 = fromPos; else asPos2 = asPos2 + startPos;
             
             if (asPos2 < asPos) asPos = asPos2;
             
             if (isAsFelt) {
            	 kommaPos = sqlStatement.substring(asPos, fromPos).indexOf(",");
            	 if (kommaPos < 0) kommaPos = fromPos; else kommaPos = kommaPos + asPos;
             }

             if ((asPos < kommaPos)) 
            	 startPos = asPos + 3;

             
             while (tegn == ' ' & startPos < sqlStmLength){
                 startPos ++;

                 if (mldInd.equals("J")) System.out.println(tegn);

                 tegn = sqlStatement.charAt(startPos);
             }

             if (kommaPos < fromPos) 
            	 sluttPos = kommaPos;
             else 
            	 sluttPos = fromPos;

             String feltNavn = sqlStatement.substring((startPos), sluttPos).trim();

             feltTab [antFelt] = feltNavn;

             antFelt ++;

             startPos = sluttPos + 1;
           } //  end while


        } // try
        catch (NullPointerException e) {
          mld(mldInd, "  NullPointerException!");
         } finally {
              sqlfil.close();
          }
	
}
private void hentKonfig() throws SQLException, IOException {
	    Properties properties = new Properties();
	    try {
//	        properties.load(new FileInputStream("app.properties"));
	        properties.load(JdbcSqlPoi.class.getClassLoader().getResourceAsStream("app.properties"));	        
//	        properties.load(new FileInputStream("C:/cvswork/rkutils/target/classes/app.properties"));
	    } catch (IOException e) {
	    	mld("J", "\n Klarer ikke å lese app.properties fil!");
	    }
	   
	    String string = properties.getProperty("sqlfilNavn");
	    if (string == null) {
	        mld("J", "sqlfilNavn parameter mangler!.");
	    } else {
	    	sqlfilNavn = string;
	    }
	    
	    string = properties.getProperty("utfilNavn");
		   if(string == null) {
			   mld("J", "utfilNavn parameter mangler!.");
		    } else {			   
			   utfilNavn = string;
			   }
		   
		    string = properties.getProperty("xlutfilNavn");
			   if(string == null) {
				   mld("J", "xlUtfilNavn parameter mangler!.");
			    } else {			   
				   xlutfilNavn = string;
				   }
		   
/*		mld("J", "\n sqlfilNavn: " + sqlfilNavn);
		mld("J", " utfilNavn: " + utfilNavn);
		mld("J", " xlUtfilNavn: " + xlUtfilNavn);		
		mld("J", " databaseURL: " + databaseUrl);
		*/
	   
   }
    static void mld(String mldInd, String mld) throws IOException {
      if (mldInd.equals("J"))
        System.out.println(mld);
    }
    static void mld(String mldInd, char mld) throws IOException {
       if (mldInd.equals("J"))
         System.out.println(mld);
     }




}
