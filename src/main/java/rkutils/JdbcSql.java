package rkutils;

import rkutils.javagently.Text;


//package no.norgesgruppen.exempel ;

import java.sql.*;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Date: 05.feb.2004
 * Time: 14:21:04
 * @author Robert Kindingstad
 */
public class JdbcSql {
    static String sqlfilNavn = "c:/javaio/sqlin.txt";
//    static String connectfilNavn = "c:/javaio/sqlconn.txt";
    static String utfilNavn  = "c:/a/data.txt";
    static final char VENSTREJUSTERT = 'V';
    static final char HOYREJUSTERT = 'H';
    static final char TEXTFORMAT = 'T';
    static final char NUMERICFORMAT= 'N';

    String[] filKjennetegn = new String[3];
    static String linje = " ", feltlinje, utlinje, member =  " ", hjelpString, feltSkilleInd = " ", feltSkilleTegn = " ", mldInd = " ";
    int filtype = 0, linjeTeller = 0, antUtFelt = 0, feltTeller = 0;
    int startPos, sluttPos, antTegn, linjeLengde, labelSluttPos, antInnfilLinjer = 0, antUfilLinjer = 0, fromPos = 0, fromPos2 = 0, kommaPos = 0,
    	asPos = 0, asPos2 = 0;

    String databaseUrl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(COMMUNITY=tcp)(PROTOCOL=TCP)(HOST = 192.168.9.100)(Port=1521))(ADDRESS=(COMMUNITY=tcp)(PROTOCOL=TCP)(HOST = 192.168.9.100)(Port=1521)))(CONNECT_DATA=(SID=NB2B)))";
    String user = "an20";
    String password = "askonett";

    private char tegn;
    private int antFelt = 0;
    private int sqlStmLength = 0;
    private String feltVerdi;
    private String sqlStatement;
    private String blankeTegn = "                                 ";

    private Connection getConnection() throws SQLException{
          DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
          return DriverManager.getConnection(databaseUrl, user, password);

      }
    public static void main(String[] args) throws IOException {
        try {
          mldInd = args[0].trim();
        } // try
        catch (ArrayIndexOutOfBoundsException e) {}

      JdbcSql exs = new JdbcSql();
      try {
         exs.kjorSql();

      } catch (SQLException e) {
         e.printStackTrace();  //To change body of catch statement use Options | File Templates.
      }
              }

   private void kjorSql() throws SQLException, IOException {
	   // hent konfigurasjonsparametre
	   hentKonfig();
	   
        BufferedReader sqlfil     = Text.open(sqlfilNavn);
//        BufferedReader connectfil = Text.open(connectfilNavn);
        BufferedReader inn        = Text.open(System.in);
        PrintWriter utfil         = Text.create(utfilNavn);

       String feltNavn = "", tekst = "";
       int index = 0, diff = 0, lengde = 0, maksAntFeltLengder = 0;
       char[]feltJusteringTegn = new char[100];
       int[]feltLengde = new int[100];

       String[] feltTab = new String[100];

       // Initier feltjustering til venstrejustert for alle felt som default
       for (int i = 0;i < 100;i++) {
           feltJusteringTegn[i] = TEXTFORMAT;
           feltLengde[i] = 0;
       }

       sqlStatement = "";

       mld("J", "\n SQL statement som kjøres:");

       mld("J", "_________________________________________________");

       try {
          linje = sqlfil.readLine();

          tekst = "";

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
                   
                   if (feltJusteringTegn[index] == VENSTREJUSTERT || feltJusteringTegn[index] == TEXTFORMAT) {
                	   feltJusteringTegn[index] = TEXTFORMAT;
                	   } else {
                		   feltJusteringTegn[index] = NUMERICFORMAT;
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

           fromPos = sqlStatement.indexOf(" from ");
           
           fromPos2 = sqlStatement.indexOf(" FROM ");
           
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
			
			kommaPos = Math.min(pos, fromPos); 
             
             asPos = sqlStatement.substring(startPos, fromPos).indexOf(" as ");
             if (asPos < 0) asPos = fromPos; else asPos = asPos + startPos;
             
             
             asPos2 = sqlStatement.substring(startPos, fromPos).indexOf(" AS ");
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

             feltNavn = sqlStatement.substring((startPos), sluttPos).trim();

             feltTab [antFelt] = feltNavn;

             antFelt ++;

             startPos = sluttPos + 1;
           } //  end while

           for (int i = 0; i < antFelt; i++) {
            if (i != 0)  utfil.print(";");
               utfil.print(feltTab[i]);

            if (mldInd.equals("J")) {
             if (i != 0)  System.out.print(";");
                System.out.print(feltTab[i]);
            }
           }
           utfil.println("");

           if (mldInd.equals("J"))
             System.out.println("");
        } // try
        catch (NullPointerException e) {
          mld(mldInd, "  NullPointerException!");
         }

//      eksekver SQL
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;

         con = getConnection();
         ps = con.prepareStatement(sqlStatement);
         //Setter parameter for alle det som er markert som ? i sqlStatement string
//         ps.setLong(1, 33);
         rs = ps.executeQuery();
         
         try {         
// skriv resultatsett til utfil         
         int antUtFilLinjer = 0;

         while (rs.next()) {
            antUtFilLinjer ++;

            for (int i = 0; i < antFelt; i++) {
              if (i != 0)  utfil.print(";");

              feltVerdi = rs.getString(i + 1);

              if (feltVerdi == null) feltVerdi = "null";

              lengde = feltVerdi.length();

//              mld("J", "feltnr. " + i + ": " + feltVerdi + "/" + lengde);

              diff = feltLengde[i] - lengde;

              if (feltJusteringTegn[i] == NUMERICFORMAT) {
             	 feltVerdi =  feltVerdi.replace('.', ',');
              } else {
            	  if (feltJusteringTegn[i] == TEXTFORMAT && feltVerdi. length() > 10 && !(feltVerdi.indexOf('.') > 0) && !(feltVerdi.indexOf('-') > 0 )) {
            		  feltVerdi = "\'" + feltVerdi;
            	  }
              }

              
              if (diff > 0){
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
             }
            utfil.println("");

            if (mldInd.equals("J"))
               System.out.println("");
         }
         System.out.println("");
         System.out.println("Antall rader i svarsettet: " + antUtFilLinjer);

      } catch (SQLException e) {
         e.printStackTrace();  //To change body of catch statement use Options | File Templates.
      } finally {
         rs.close();
         ps.close();
         con.close();

          sqlfil.close();
          utfil.close();
      }
   }
   
   private void hentKonfig() throws SQLException, IOException {
	    Properties properties = new Properties();
	    try {
//	        properties.load(new FileInputStream("app.properties"));
	        properties.load(JdbcSql.class.getClassLoader().getResourceAsStream("app.properties"));	        
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
//		 hent connection-parametre og connection  
		      PreparedStatement ps = null;
		      ResultSet rs = null;

			    try {
			    	properties.load(JdbcSql.class.getClassLoader().getResourceAsStream("db.properties"));	        
			    } catch (IOException e) {
			    	mld("J", "\n Klarer ikke å lese db.properties fil!");
			    	}			    

		         string = properties.getProperty("dburl");
		 		   if(string == null) {
		 			   mld("J", "dburl parameter mangler!.");
		 		    } else {		 			   
		 			   databaseUrl = string;
		 			   }        
		         
		         string = properties.getProperty("dbuserid");
		 		   if(string == null) {
		 			   mld("J", "dbuserid parameter mangler!.");
		 		  } else {
		 			   user = string;
		 			   }        
		         
		         string = properties.getProperty("dbpassword");
		 		   if(string == null) {
		 			   mld("J", "dbpassword parameter mangler!.");
		 		  } else {
		 			   password = string;
		 			   }        
		         
		mld("J", "\n sqlfilNavn: " + sqlfilNavn);
		mld("J", " utfilNavn: " + utfilNavn);
		mld("J", " databaseURL: " + databaseUrl);
		
	   
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
