/**
 * A rather curious algorithm to generate two random four digit numbers which
 * aren't a multiple of 1111, then subtract the smaller from the larger.
 * Do this ten times, if two successive numbers are the same then stop.
 * @author  James Delph
 */

import java.util.Random;
import java.util.Arrays; // Arrays.sort()

public class RandomAlgorithm
{
  /**
   * How many digits to make
   */
  private static final int CODE_LENGTH = 4;

  /**
   * Generates numbers in the range 1..10^@{@link RandomAlgorithm.CODE_LENGTH CODE_LENGTH} - 2,
   * excluding those with all the same digits
   * @return random number
   */
  private static int generateDigits()
  {
    final int goTo = (int)Math.pow(10, CODE_LENGTH) - 2; // calculate how far to go, then take two
    int mod = 0; // calculate what isn't allowed
    for (int i = 0; i < CODE_LENGTH; ++i)
      mod += Math.pow(10, i);

    // make our digits
    Random random = new Random();
    int digits = 0;
    while (digits % mod == 0)
      digits = random.nextInt(goTo) + 1; // add one to get us in a range ignoring 0 and 10^CODE_LENGTH-1

    return digits;
  }

  /**
   * Create an array version of our digits for easier sorting
   * @param  digits to convert
   * @return        the converted array
   */
  private static int[] makeArray(int digits)
  {
    int[] ret = new int[CODE_LENGTH];
    for (int i = 0; i < CODE_LENGTH; ++i)
    {
      final int mult = (int)Math.pow(10, (CODE_LENGTH - 1) - i); // do it in reverse, otherwise we make our array backwards
      ret[i] = (digits / mult) % 10;
    }
    return ret;
  }

  /**
   * Sort the digits using Arrays.sort() and then reconstruct our int to do maths
   * @param  digits  array of digits to sort
   * @param  reverse if true, reverse sort them
   * @return         sorted digits as an integer
   */
  private static int sortDigits(int[] digits, boolean reverse)
  {
    Arrays.sort(digits);
    // now 'join' them
    int ret = 0;
    for (int i = 0; i < CODE_LENGTH; ++i)
    {
      final int pow = reverse ? i : (CODE_LENGTH - 1) - i; // little voodoo ish,
      // this makes it so we go in the right direction
      final int mult = (int)Math.pow(10, pow);
      ret += digits[i] * mult;
    }
    return ret;
  }

  /**
   * Runs our algorithm
   * @param  digits Initial set of digits to use
   * @return        The final number we come to
   */
  public static int runAlgorithm(int[] digits)
  {
    int lastNumber = -1;
    for (int i = 0; i < 10; ++i)
    {
      final int bigger = sortDigits(digits, true);
      final int smaller = sortDigits(digits, false);

      final int number = bigger - smaller;

      System.out.printf("%0" + CODE_LENGTH + "d-%0" + CODE_LENGTH + "d=%0" + CODE_LENGTH + "d%n", bigger, smaller, number);
      if (number == lastNumber)
        break;
      lastNumber = number;
      digits = makeArray(number); // setup for next iteration
    }
    return lastNumber;
  }

  /**
   * Runs a few simple tests to make sure everything is working as intended
   * @return true if everything is good, false if stuff is broken
   */
  private static boolean runTests()
  {
    int[] ret = makeArray(6539);
    // really lazy testing, but lazy testing better than no testing
    if (ret[0] != 6 || ret[1] != 5 || ret[2] != 3 || ret[3] != 9)
    {
      System.out.print("TEST: makeArray(6539) failed\nGot: {" + ret[0]);
      for (int i = 1; i < ret.length; ++i)
        System.out.print(", " + ret[i]);
      System.out.print("}, Expected: {6, 5, 3, 9}\n");
      return false;
    }

    int asc = sortDigits(ret, false);
    if (asc != 3569)
    {
      System.out.println("TEST: sortDigits({6, 5, 3, 9}, false) failed\nGot: " + asc + ", Expected: " + 3569);
      return false;
    }
    int dsc = sortDigits(ret, true);
    if (dsc != 9653)
    {
      System.out.println("TEST: sortDigits({6, 5, 3, 9}, true) failed\nGot: " + dsc + ", Expected: " + 9653);
      return false;
    }

    return true;
  }

  /**
   * main
   * @param cli args, use "tests" without quotes to run tests
   */
  public static void main(String[] args)
  {
    // parse args
    boolean doTests = false;
    for (String arg : args)
    {
      if (arg.equals("tests"))
        doTests = true;
    }

    // do tests?
    if (doTests)
    {
      if (runTests())
        System.out.println("All tests passed successfully\n");
      else
        return; // runTests() prints why they failed
    }

    int[] digits = makeArray(generateDigits()); // generate initial set of digits
    runAlgorithm(digits); // run the algorithm
  }
}
