package rkutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class WindowsAttribute {
	
	public static void main(String[] args) {
		System.out.println("Programmet starter");
		  File fil = new File("c://a//bilde.JPG");
		  System.out.println(getOwner(fil));
		  
		  
			System.out.println("Programmet avsluttet.");		  
	}
	public static String getOwner( File f ) {
		
		String toRet = null;
			
		//form command string
		String cmd[] = new String[2];
		cmd[0] = "getOwner ";
		cmd[1] = f.getAbsolutePath();
			
		try {
		
			//setup the Process and buffer
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader buffr = new BufferedReader( new InputStreamReader( p.getInputStream() ) );
				
			String line = null;
			while( (line = buffr.readLine()) != null ) {
				toRet = line;
			}
				
		}catch( IOException ioe ) {
			System.err.print( "Unknown IO Error in function getOwner(): ");
	                  System.err.println(ioe.getMessage() );	
		}
			
		return toRet;
	}
}
