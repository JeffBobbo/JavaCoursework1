public class LibraryBook
{
  // Part d: Constructor
  /**
   * Constructor with arguments for a LibraryBook’s author(s),
   * title and number of pages
   * @param bookAuthor the names of the author(s) of this LibraryBook
   * @param bookTitle the title of this LibraryBook
   * @param bookPages the number of pages of this LibraryBook
  */
  public LibraryBook(String bookAuthor, String bookTitle, int bookPages)
  {
    author = bookAuthor;
    title = bookTitle;
    classification = null;
    pages = bookPages;
    borrowCount = 0;
    reservationCount = 0;
    status = null;
  };

  // Part e: getter and setter methods
  /**
   * @return author of this LibraryBook
   */
  public String getAuthor() { return author; };
  /**
   * @return title of this LibraryBook
   */
  public String getTitle() { return title; };
  /**
   * @return classification code of this LibraryBook
   */
  public String getClassification()
  {
    if (classification == null)
      return "----"; // 3 hyphens in example, but I love me some monospace goodness
    return classification;
  };
  /**
   * @return page count of this LibraryBook
   */
  public int getPages() { return pages; };
  /**
   * @return borrow count of this LibraryBook
   */
  public int getBorrowCount() { return borrowCount; };

  /**
   * A method to reset the Library classification of this LibraryBook
   * @param bookClass the proposed new classification
   * @return true, if the proposed new classification has at least 3 characters
   * to which the Library classification is reset.
   *         false, otherwise.
   */
  public boolean setClassification(String bookClass)
  {
    if (bookClass.length() < 3)
      return false;

    classification = bookClass;
    return true;
  };

  public void setAsReferenceOnly()
  {
    if (status == null)
      status = BookStatus.REFERENCE_ONLY;
  };

  public void setAsForLending()
  {
    if (status == null)
      status = BookStatus.AVAILABLE_FOR_LENDING;
  };

  /**
   * Checks if this book is available for lending
   * @return true if status == AVAILABLE_FOR_LENDING
   */
  public boolean isAvailable()
  {
    return status == BookStatus.AVAILABLE_FOR_LENDING;
  };

  /**
   * Checks if this book is for reference only (not stipulated in the assignment, but required for Part f).
   * @return  true if status == REFERENCE_ONLY
   */
  public boolean isReference()
  {
    return status == BookStatus.REFERENCE_ONLY;
  };
  /**
   * Check if this book is on loan
   * @return  true if status == ON_LOAN
   */
  public boolean isOnLoan()
  {
    return status == BookStatus.ON_LOAN;
  };

  /**
   * If possible, reverses this LibraryBook.
   * This is only possible if this LibraryBook is currently on loan
   * and less than 3 reservations have been placed since this went on loan.
   * @return  true, if a new reserversation has been made for this.
   *          false, otherwise
   */
  public boolean reserveBook()
  {
    if (reservationCount >= 3)
      return false;

    reservationCount++;
    return true;
  };

  public void borrowBook()
  {
    // if this book was reserved, we should reduce the reservation count
    if (reservationCount > 0)
      reservationCount--;

    onLoanCount++; // increment the amount of books on loan
    borrowCount++; // increment the borrow count

    status = BookStatus.ON_LOAN; // set as on loan
  };

  public void returnBook()
  {
    onLoanCount--; // decrement how many books we're loaning
    // don't decrement the borrow count, because we want to know how often our books are being lent?
    status = BookStatus.AVAILABLE_FOR_LENDING; // set it as available again

    // if someone has reserved it, take it out again automatically
    if (reservationCount > 0)
      borrowBook();
  };

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Title: ").append(getTitle()).append("\n");
    sb.append("Author: ").append(getAuthor()).append("\n");
    sb.append("Pages: ").append(getPages()).append("\n");
    sb.append("Classification: ").append(getClassification()).append("\n");

    return sb.toString();
  };

  //  Part a: Define book status enum
  private enum BookStatus
  {
    REFERENCE_ONLY,
    ON_LOAN,
    AVAILABLE_FOR_LENDING
  };

  // Part b: data members
  private String author;
  private String title;
  private String classification;
  private int pages;
  private int borrowCount;
  private int reservationCount;
  private BookStatus status;
  // Part c: static member for loan count
  private static int onLoanCount = 0;

  // not sure where else to put this, but we like our encapsulation
  public static int getLoanCount() { return onLoanCount; };
}
