import java.util.Random;

public class main
{
  /**
   * A method to generate a collection of LibraryBook objects to use as
   * test data in your simulation
   * @return  an array of LibraryBook objects
   */
  private static LibraryBook[] generateBookStock()
  {
    String[] authorsList = {
      "Lewis and Loftus",
      "Mitrani",
      "Goodrich",
      "Lippman",
      "Gross", // 5
      "Baase",
      "Maclane",
      "Dahlquist",
      "Stimson",
      "Knuth", // 10
      "Hahn",
      "Cormen and Leiserson",
      "Menzes",
      "Garey and Johnson",
    };
    String[] titlesList = {
      "Java Software Solutions",
      "Simulation",
      "Data Structures",
      "C++ Primer",
      "Graph Theory", // 5
      "Computer Algorithms",
      "Algebra",
      "Numerical Methods",
      "Cryptography",
      "Semi-Numerical Algorithms", // 10
      "Essential MATLAB",
      "Introduction to Algorithms",
      "Handbook of Applied Cryptography",
      "Computers and Intractability"
    };
    int[] pagesList = {
      832,
      185,
      695,
      614,
      586, // 5
      685,
      590,
      573,
      475,
      685, // 10
      301,
      1175,
      820,
      338
    };

    int n = authorsList.length;

    LibraryBook[] bookStock = new LibraryBook[n];
    for (int i = 0; i < n; ++i)
      bookStock[i] = new LibraryBook(authorsList[i], titlesList[i], pagesList[i]);

    // set the library classification for half of the LibraryBooks
    for (int i = 0; i < n; i+=2)
      bookStock[i].setClassification("QA" + (99 - i));

    // set approx. two thirds of LibraryBooks in test data as lending
    for (int i = 0; i < 2*n/3; ++i)
      bookStock[i].setAsForLending();

    // set approx. one third of LibraryBook in test data as reference only
    for (int i = 2*n/3; i < n; ++i)
      bookStock[i].setAsReferenceOnly();

    return bookStock;
  };

  // Part f: simulation
  private static String[] runSimulation(LibraryBook[] bookStock, int numberOfEvents)
  {
    int n = bookStock.length;
    Random random = new Random();
    String[] simulationLog = new String[numberOfEvents];
    for (int i = 0; i < numberOfEvents; ++i)
    {
      LibraryBook book = bookStock[random.nextInt(n)];

      // generate the start of our log
      StringBuilder log = new StringBuilder();
      log.append(i).append(" ").append(book.getLoanCount()).append(" ").append(book.getClassification());

      // now decide what event to run
      if (book.getClassification().equals("----")) // kinda bad form, but then there's LibraryBook::toString to worry about.
      {
        book.setClassification("QA" + (99 - i));
        log.append(" ").append("BOOK IS CLASSIFIED");
      }
      else if (book.isReference())
      {
        log.append(" ").append("REFERENCE ONLY BOOK");
      }
      else if (book.isOnLoan() == false)
      {
        book.borrowBook();
        log.append(" ").append("BOOK IS LOANED OUT");
      }
      else if (book.isOnLoan() == true)
      {
        log.append(" ");
        // decide if we want to return or place a reservation
        if (random.nextBoolean())
        {
          book.returnBook();
          log.append("BOOK IS RETURNED");
        }
        else
        {
          if (book.reserveBook())
            log.append("RESERVATION PLACED FOR ON-LOAN BOOK");
          else
            log.append("BOOK IS ON-LOAN BUT CANNOT BE RESERVED");
        }
      }
      else
      {
        log.append("SOMETHING REALLY BAD HAPPENED WITH THIS BOOK");
      }
      simulationLog[i] = log.toString();
    }
    return simulationLog;
  };

  public static void main(String[] args)
  {
    LibraryBook[] bookStock = generateBookStock();
    String[] log = runSimulation(bookStock, 100);

    for (String entry : log)
      System.out.println(entry);
  };
}
