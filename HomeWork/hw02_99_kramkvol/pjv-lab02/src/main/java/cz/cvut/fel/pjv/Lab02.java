/*
 * File name: Lab06.java
 * Date:      2014/08/26 21:39
 * Author:    @author
 */

package cz.cvut.fel.pjv;

import java.util.ArrayList;
import java.util.Scanner;
public class Lab02 {
   public static double calculateAverage(ArrayList<Double> numbers) {
      double sum = 0;
      for (Double number : numbers) {
         sum += number;
      }
      if (numbers.size() != 0) {
         return sum / numbers.size();
      } else {
         return 0;
      }
   }

   public static double calculateStandardDeviation(ArrayList<Double> numbers, double average) {
      double sumSquaredDifferences = 0;
      for (Double number : numbers) {
         double difference = number - average;
         sumSquaredDifferences += Math.pow(difference, 2);
      }

      double variance = sumSquaredDifferences / numbers.size();
      return Math.sqrt(variance);
   }

   public static ArrayList<Double> returnArray() {
      ArrayList<Double> numbers = new ArrayList<>();
      Scanner scanner = new Scanner(System.in);
         while (scanner.hasNextLine()) {
            String currentText = scanner.nextLine();
            if ((TextIO.isInteger(currentText)) || (TextIO.isDouble(currentText)) || (TextIO.isFloat(currentText))) {
               numbers.add(Double.parseDouble(currentText));
            }
            else if (currentText.isEmpty()) {}
            else{
               numbers.add(null);
            }
         }
      return numbers;
   }

   public void start(String[] args) {
      ArrayList<Double> numbersList = returnArray();
      ArrayList<Double> currentNumbersList = new ArrayList<>();
      int line = 0;
      int elementsCouter = 0;
      while (!numbersList.isEmpty()) {
          elementsCouter = Math.min(numbersList.size(), 10);
          for (int i = 0; i < elementsCouter; i++) {
              line++;
              if (numbersList.get(i) == null) {
                  System.err.println("A number has not been parsed from line " + line);
                  elementsCouter++;
              } else {
                  currentNumbersList.add(numbersList.get(i));
              }
          }
          for (int i = 1; i <= elementsCouter; i++) {
              numbersList.remove(numbersList.get(0));
          }
          double average = calculateAverage(currentNumbersList);
          double standardDeviation = calculateStandardDeviation(currentNumbersList, average);
          if ((elementsCouter > 1) && (elementsCouter < 10)) {
              System.out.printf(" %1s %.3f %.3f%n", currentNumbersList.size(), average, standardDeviation);
              System.err.println("End of input detected!");
          }
          else if ((elementsCouter == 0) || (elementsCouter == 1)){
              System.err.println("End of input detected!");
          }
          else {
              System.out.printf("%2s %.3f %.3f%n", currentNumbersList.size(), average, standardDeviation);
              if (numbersList.isEmpty()) {
                  System.err.println("End of input detected!");
              }
          }
          currentNumbersList.clear();
      }
   }
}
