package rkutils;

import java.io.*; 
import java.util.StringTokenizer;

public class Gange { 

   public static void main (String[] args) { 

	  boolean ferdig = false;
	  
	  while (!ferdig) {
	  
		  
      //  prompt the user to enter their name 
      System.out.print("legg inn to tall med komm imellom: "); 

      //  open up standard input 
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 

      String userName = null; 

      //  read the username from the command-line; need to use try/catch with the 
      //  readLine() method 
      try { 
         userName = br.readLine(); 
      } catch (IOException ioe) { 
         System.out.println("Programmet feilet. Start programmet på nytt:-)"); 
         System.exit(1); 
      } 
      
      int kommapos = userName.indexOf(',');
      
      //System.out.println("pos av komma:" + kommapos);
      
      int tall1 = new Integer(userName.substring(0, kommapos));
      int tall2 = new Integer(userName.substring(kommapos + 1));
      
//      System.out.println("Tall1: : " + tall1);
//      System.out.println("Tall2: : " + tall2);

      int produkt = tall1 * tall2;
      
      System.out.println("Produktet av tallene er: " +  produkt);  

      if (tall1 == 0) ferdig = true;

	  }
      
   } 

}  // end of Gange class 

