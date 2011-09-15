package rkutils;

import java.util.Scanner;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
public class InvoiceAppBigDecimal 
{
    public static void main(String[] args)
    { 
        // create a Scanner object and start while loop
        Scanner sc = new Scanner(System.in);
        String choice = "y";
        while (choice.equalsIgnoreCase("y"))
        { 
            // get the input from the user
            System.out.print("Enter subtotal: ");
             double subtotal = sc.nextDouble(); 
            
            // calculate the results
             double discountPercent = 0.0;
             if (subtotal >= 100) 
                discountPercent = .1;
             else
                discountPercent = 0.0;           
             
//           convert subtotal and discount percent to BigDecimal
             BigDecimal decimalSubtotal = new BigDecimal(Double.toString(subtotal)); 
             decimalSubtotal = decimalSubtotal.setScale(2, RoundingMode.HALF_UP);
             BigDecimal decimalDiscountPercent =  
                 new BigDecimal(Double.toString(discountPercent)); 
             // calculate discount amount 
             BigDecimal discountAmount = 
                 decimalSubtotal.multiply(decimalDiscountPercent); 
             discountAmount = discountAmount.setScale(2, RoundingMode.HALF_UP); 
             // calculate total before tax, sales tax, and total
             BigDecimal totalBeforeTax = decimalSubtotal.subtract(discountAmount);
             BigDecimal salesTaxPercent = new BigDecimal(".05");
             BigDecimal salesTax = salesTaxPercent.multiply(totalBeforeTax);
             salesTax = salesTax.setScale(2, RoundingMode.HALF_UP);
             BigDecimal total = totalBeforeTax.add(salesTax); 

            
            // format and display the results
            NumberFormat currency = NumberFormat.getCurrencyInstance(); 
            NumberFormat percent = NumberFormat.getPercentInstance(); 
            String message =
                "Discount percent: " + percent.format(decimalDiscountPercent) + "\n" 
              + "Discount amount: " + currency.format(discountAmount) + "\n" 
              + "Total before tax: " + currency.format(totalBeforeTax) + "\n" 
              + "Sales tax: " + currency.format(salesTax) + "\n"
              + "Invoice total: " + currency.format(total) + "\n"; 
            System.out.println(message);

//          debugging statements that display the unformatted fields
            // these are added before displaying the formatted results
            String debugMessage = "\nUNFORMATTED RESULTS\n" 
                                + "Discount percent: " + decimalDiscountPercent + "\n"
                                + "Discount amount:  " + discountAmount + "\n"
                                + "Total before tax:     " + totalBeforeTax + "\n" 
                                + "Sales tax:     " + salesTax + "\n" 
                                + "Invoice total: " + total + "\n" 
                                + "\nFORMATTED RESULTS";
          System.out.println(debugMessage); 
            
            // see if the user wants to continue
            System.out.print("Continue? (y/n): ");
            choice = sc.next();  
            System.out.println();
        }
    }
}

