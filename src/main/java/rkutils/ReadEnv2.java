package rkutils;

import java.util.*;

public class ReadEnv2 {
 public static void main(String args[]) {
   // just one
   System.out.println("PATH = " + System.getenv("PATH"));

   // all of them
   Map env = System.getenv();
   for (Iterator it=env.entrySet().iterator(); it.hasNext(); ) {
      Map.Entry entry = (Map.Entry)it.next();
      System.out.println(entry.getKey() + " = " + entry.getValue());
    }
 }
}
