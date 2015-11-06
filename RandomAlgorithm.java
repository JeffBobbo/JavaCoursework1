/**
 * A rather curious algorithm to generate two random four digit numbers which
 * aren't a multiple of 1111, then subtract the smaller from the larger.
 * Do this ten times, if two successive numbers are the same then stop.
 * @author  James Delph
 */

import java.util.Random;

public class RandomAlgorithm
{
  /**
   * Generates numbers in the range 0001..9998, excluding % 1111 ones
   * @return random number
   */
  private static int generateDigits()
  {
    Random random = new Random();
    int digits = 0;
    while (digits % 1111 == 0)
    {
      digits = 0;
      for (int i = 0; i < 4; ++i)
      {
        final int digit = random.nextInt(10);
        digits += digit * Math.pow(10, i);
      }
    }
    return digits;
  }

  /**
   * main
   * @param args cli args
   */
  public static void main(String[] args)
  {

    int lastNumber = -1;
    for (int i = 0; i < 10; ++i)
    {
      int d1 = generateDigits();
      int d2 = generateDigits();

      final int bigger = d1 > d2 ? d1 : d2;
      final int smaller = d1 > d2 ? d2 : d1;

      final int number = bigger - smaller;
      System.out.printf("%04d-%04d=%04d%n", bigger, smaller, number);
      if (number == lastNumber)
        break;
      lastNumber = number;
    }
  }
}
