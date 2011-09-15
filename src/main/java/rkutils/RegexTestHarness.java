package rkutils;

//import java.io.Console;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexTestHarness {

    public static void main(String[] args){
        if (true) {

            Pattern pattern = null; 
//            Pattern.compile(console.readLine("%nEnter your regex: "));

    		try {
    			pattern = Pattern.compile(args[0]);
    		} catch (Exception e) {
    			System.out.println("Regexp parameter mangler");
    		}

    		skriv("Regexp til programmet er: " + args[0]);
            
            Matcher matcher = null;
//            pattern.matcher(console.readLine("Enter input string to search: "));

  		try {
  			matcher = pattern.matcher(args[1]);
  		} catch (Exception e) {
  			System.out.println("Inputtekst parameter mangler");
  		}

		skriv("Inputtekst til programmet er: " + args[1]);
		
            boolean found = false;
            while (matcher.find()) {
                skriv("I found the text " 
                		+ matcher.group() 
                		+ " starting at index:" 
                		+ matcher.start()
                		+ " and ending at index "
                		+ matcher.end());
                found = true;
            }
            if(!found){
                skriv("No match found.");
            }
        }
    }
	private static void skriv(String s) {
		System.out.println(s);
	}    
}
