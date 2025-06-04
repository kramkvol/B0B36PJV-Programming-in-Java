package cz.cvut.fel.pjv;

import java.util.Scanner;

public class Lab01 {

   public void start(String[] args)
   {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Vyber operaci (1-soucet, 2-rozdil, 3-soucin, 4-podil):");
      int operation = scanner.nextInt();
      String a_str, b_str, result_str;
      double a, b, result;
      int count;
      switch (operation) {
         case 1:
            System.out.println("Zadej scitanec: ");
            a_str = (scanner.next()).replace(",", ".");
            a = Double.parseDouble(a_str);
            System.out.println("Zadej scitanec: ");
            b_str = (scanner.next()).replace(",", ".");
            b = Double.parseDouble(b_str);
            System.out.println("Zadej pocet desetinnych mist: ");
            count =  scanner.nextInt();
            if (count < 0)
            {
               System.out.println("Chyba - musi byt zadane kladne cislo!");
               break;
            }
            a = Math.round(a * Math.pow(10, count)) / Math.pow(10, count);
            b = Math.round(b * Math.pow(10, count)) / Math.pow(10, count);
            result = Math.round((a + b) * Math.pow(10, count)) / Math.pow(10, count);
            if (count == 0)
            {
               System.out.println((int)a + " + " + (int)b + " = " + (int)result);
            }
            else
            {
               System.out.println(String.format("%." + count + "f", a) + " + " + String.format("%." + count + "f", b) + " = " + String.format("%." + count + "f", result));
            }
            break;
         case 2:
            System.out.println("Zadej mensenec: ");
            a_str = (scanner.next()).replace(",", ".");
            a = Double.parseDouble(a_str);
            System.out.println("Zadej mensitel: ");
            b_str = (scanner.next()).replace(",", ".");
            b = Double.parseDouble(b_str);
            System.out.println("Zadej pocet desetinnych mist: ");
            count = scanner.nextInt();
            if (count < 0)
            {
               System.out.println("Chyba - musi byt zadane kladne cislo!");
               break;
            }
            a = Math.round(a * Math.pow(10, count)) / Math.pow(10, count);
            b = Math.round(b * Math.pow(10, count)) / Math.pow(10, count);
            result = Math.round((a - b) * Math.pow(10, count)) / Math.pow(10, count);
            if (count == 0)
            {
               System.out.println((int)a + " - " + (int)b + " = " + (int)result);
            }
            else
            {
               System.out.println(String.format("%." + count + "f", a) + " - " + String.format("%." + count + "f", b) + " = " + String.format("%." + count + "f", result));
            }
            break;
         case 3:
            System.out.println("Zadej cinitel: ");
            a_str = (scanner.next()).replace(",", ".");
            a = Double.parseDouble(a_str);
            System.out.println("Zadej cinitel: ");
            b_str = (scanner.next()).replace(",", ".");
            b = Double.parseDouble(b_str);
            System.out.println("Zadej pocet desetinnych mist: ");
            count =  scanner.nextInt();
            if (count < 0)
            {
               System.out.println("Chyba - musi byt zadane kladne cislo!");
               break;
            }
            result = Math.round(a * b * Math.pow(10, count)) / Math.pow(10, count);
            a = Math.round(a * Math.pow(10, count)) / Math.pow(10, count);
            b = Math.round(b * Math.pow(10, count)) / Math.pow(10, count);
            if (count == 0)
            {
               System.out.println((int)a + " * " + (int)b + " = " + (int)result);
            }
            else
            {
               System.out.println(String.format("%." + count + "f", a) + " * " + String.format("%." + count + "f", b) + " = " + String.format("%." + count + "f", result));
            }
            break;
         case 4:
            System.out.println("Zadej delenec: ");
            a_str = (scanner.next()).replace(",", ".");
            a = Double.parseDouble(a_str);
            System.out.println("Zadej delitel: ");
            b_str = (scanner.next()).replace(",", ".");
            b = Double.parseDouble(b_str);
            if (b == 0)
            {
               System.out.println("Pokus o deleni nulou!");
               break;
            }
            System.out.println("Zadej pocet desetinnych mist: ");
            count = scanner.nextInt();
            if (count < 0)
            {
               System.out.println("Chyba - musi byt zadane kladne cislo!");
               break;
            }
            if (count == 0)
            {
               if((a / b) % 1 == 0)
               {
                  System.out.println((int)a + " / " + (int)b + " = " + (int)(a / b));
               }
               else
               {
                  System.out.println((int)a + " / " + (int)b + " = " + (a / b));
               }
            }
            else
            {
               System.out.println(String.format("%." + count + "f", a) + " / " + String.format("%." + count + "f", b) + " = " + String.format("%." + count + "f", (a / b)));
            }
            break;
         default:
            System.out.println("Chybna volba!");
      }
      scanner.close();
   }
}