package assignment02;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;

/**
 * Class representation of a library (a collection of library books).
 * 
 */
public class Library<T> {

  private ArrayList<LibraryBook<T>> library;

  public Library() {
    library = new ArrayList<LibraryBook<T>>();
  }

  /**
   * Add the specified book to the library, assume no duplicates.
   * 
   * @param isbn
   *          -- ISBN of the book to be added
   * @param author
   *          -- author of the book to be added
   * @param title
   *          -- title of the book to be added
   */
  public void add(long isbn, String author, String title) {
    library.add(new LibraryBook(isbn, author, title));
  }

  /**
   * Add the list of library books to the library, assume no duplicates.
   * 
   * @param list
   *          -- list of library books to be added
   */
  public void addAll(ArrayList<LibraryBook<T>> list) {
    library.addAll(list);
  }

  /**
   * Add books specified by the input file. One book per line with ISBN, author,
   * and title separated by tabs.
   * 
   * If file does not exist or format is violated, do nothing.
   * 
   * @param filename
   */
  public void addAll(String filename) {
    ArrayList<LibraryBook<T>> toBeAdded = new ArrayList<LibraryBook<T>>();

    try (Scanner fileIn = new Scanner(new File(filename))) {

      int lineNum = 1;

      while (fileIn.hasNextLine()) {
        String line = fileIn.nextLine();

        try (Scanner lineIn = new Scanner(line)) {
          lineIn.useDelimiter("\\t");

          if (!lineIn.hasNextLong()) {
            throw new ParseException("ISBN", lineNum);
          }
          long isbn = lineIn.nextLong();

          if (!lineIn.hasNext()) {
            throw new ParseException("Author", lineNum);
          }
          String author = lineIn.next();

          if (!lineIn.hasNext()) {
            throw new ParseException("Title", lineNum);
          }
          String title = lineIn.next();
          toBeAdded.add(new LibraryBook(isbn, author, title));
        }
        lineNum++;
      }
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage() + " Nothing added to the library.");
      return;
    } catch (ParseException e) {
      System.err.println(e.getLocalizedMessage() + " formatted incorrectly at line " + e.getErrorOffset()
          + ". Nothing added to the library.");
      return;
    }

    library.addAll(toBeAdded);
  }

  /**
   * Returns the holder of the library book with the specified ISBN.
   * 
   * If no book with the specified ISBN is in the library, returns null.
   * 
   * @param isbn
   *          -- ISBN of the book to be looked up
   */
  public T lookup(long isbn) {
    //Loops through each library-book arraylist
    for (LibraryBook<T> book : library) {
      //if book at a index has same isbn then return its holder
      if (book.getIsbn() == isbn) {
        return book.getHolder();
      }
    }
    //else if no matches then it returns null
    return null;
  }

  /**
   * Returns the list of library books checked out to the specified holder.
   * 
   * If the specified holder has no books checked out, returns an empty list.
   * 
   * @param holder
   *          -- holder whose checked out books are returned
   */
  public ArrayList<LibraryBook> lookup(T holder) {
    //Creates a new arraylist where it will contain the list of books checked by a holder
    ArrayList<LibraryBook> holdersBooks = new ArrayList<>();
    //Iterates through library list and if a book's holder == holder then adds the specific
    //book into the new created array list
    for (LibraryBook book : library) {
      if (book.getHolder() != null && book.getHolder().equals(holder)) {
        holdersBooks.add(book);
      }
    }
    //returns new created array list
    return holdersBooks;
  }

  /**
   * Sets the holder and due date of the library book with the specified ISBN.
   * 
   * If no book with the specified ISBN is in the library, returns false.
   * 
   * If the book with the specified ISBN is already checked out, returns false.
   * 
   * Otherwise, returns true.
   * 
   * @param isbn
   *          -- ISBN of the library book to be checked out
   * @param holder
   *          -- new holder of the library book
   * @param month
   *          -- month of the new due date of the library book
   * @param day
   *          -- day of the new due date of the library book
   * @param year
   *          -- year of the new due date of the library book
   * 
   */
  public boolean checkout(long isbn, T holder, int month, int day, int year) {
    //loops through library books; and tries to match the isbn with a specific book in the library
    for (LibraryBook book : library) {
      if (book.getIsbn() == isbn) {
        //If a specific book is found; then check to see if it already has a holder; if it does
        //then returns false because book is already checked out
        if (book.getHolder() != null) {
          return false;
        } else { //still inside if when match for isbn was found
          //sets the holder and new due date and returns true
//          book.setHolder(holder);
//          book.setDueDate(new GregorianCalendar(year, month, day));
          book.checkOut(holder, year, month, day);
          return true;
        }
      }
    }
    //if no match for isbn is found also returns false
    return false;
  }

  /**
   * Unsets the holder and due date of the library book.
   * 
   * If no book with the specified ISBN is in the library, returns false.
   * 
   * If the book with the specified ISBN is already checked in, returns false.
   * 
   * Otherwise, returns true.
   * 
   * @param isbn
   *          -- ISBN of the library book to be checked in
   */
  public boolean checkin(long isbn) {
    //Loops through the whole library
    for (LibraryBook book : library) {
      //if book is checked in, then it's holder and dueDate are null so if book has a holder
      //and there is a match for the isbn then sets the books holder and dueDate to null and returns true
      if (book.holder != null && book.isbn == isbn) {
//        book.holder = null;
//        book.dueDate = null;
        book.checkIn();
        return true;
      }
    }
    //returns false if book was already checked in (having null holder) and book was not found in the
    //library
    return false;
  }

  /**
   * Unsets the holder and due date for all library books checked out be the
   * specified holder.
   * 
   * If no books with the specified holder are in the library, returns false;
   * 
   * Otherwise, returns true.
   * 
   * @param holder
   *          -- holder of the library books to be checked in
   */
  //change logic, dont return true too early
  public boolean checkin(T holder) {
    //Loops through whole library array and if a book has a holder and it equals to the specified
    //holder then sets to null the book's holder, due date along with returning true
    for (LibraryBook book : library) {
      if (book.holder != null && book.holder.equals(holder)) {
        book.holder = null;
        book.dueDate = null;
        return true;
      }
    }
    //returns false if no books with the specified holder are in the library
    return false;
  }
  /**
   * Returns the list of library books, sorted by ISBN (smallest ISBN
   first).
   */
  public ArrayList<LibraryBook<T>> getInventoryList() {
    ArrayList<LibraryBook<T>> libraryCopy = new
            ArrayList<>();
    libraryCopy.addAll(library);
    OrderByIsbn comparator = new OrderByIsbn();
    sort(libraryCopy, comparator);
    return libraryCopy;
  }

  /**
   * Returns the list of library books, sorted by author
   */
  public ArrayList<LibraryBook<T>> getOrderedByAuthor() {
    ArrayList<LibraryBook<T>> libraryCopy = new
            ArrayList<LibraryBook<T>>();
    libraryCopy.addAll(library);
    OrderByAuthor comparator = new OrderByAuthor();
    sort(libraryCopy, comparator);
    return libraryCopy;
  }

  /**
   * Returns the list of library books whose due date is older than the
   input
   * date. The list is sorted by date (oldest first).
   *
   * If no library books are overdue, returns an empty list.
   */
  public ArrayList<LibraryBook<T>> getOverdueList(int month, int
          day, int year) {
//    //creates object dueDate that has given specific date
//    GregorianCalendar dueDate = new GregorianCalendar(year, month, day);
//    //creates empty list to store the overdue books
//    ArrayList<LibraryBook<T>> overDueL = new ArrayList<LibraryBook<T>>();
//    //Iterates through whole books list in Library
//    for (LibraryBook book : library) {
//      //returns the due date of each book and compares to specified dueDate
//      int overdue = book.getDueDate().compareTo(dueDate);
//      //if residual is negative; it means that book is overdue then added to list of all books
//      if (overdue < 0) {
//        overDueL.add(book);
//      }
//    }
//    //creates custom comparator to make sure that overdue books are sorted by the dueDate
//    OrderByDueDate comparator = new OrderByDueDate();
//    sort(overDueL, comparator);
//    return overDueL;
      ArrayList<LibraryBook<T>> overdueBooks = new ArrayList<>();

      // Adjust the month to be zero-based
      Calendar inputDate = new GregorianCalendar(year, month - 1, day);

      for (LibraryBook<T> book : library) {
        if (book.getDueDate() != null && book.getDueDate().before(inputDate)) {
          overdueBooks.add(book);
        }
      }

      // Sort the overdue books by due date (oldest first)
      overdueBooks.sort(new OrderByDueDate());

      return overdueBooks;
    }
  /**
   * Performs a SELECTION SORT on the input ArrayList.
   * 1. Find the smallest item in the list.
   * 2. Swap the smallest item with the first item in the list.
   * 3. Now let the list be the remaining unsorted portion
   * (second item to Nth item) and repeat steps 1, 2, and 3.
   */
  private static <ListType> void sort(ArrayList<ListType> list,
                                      Comparator<ListType> c) {
    for (int i = 0; i < list.size() - 1; i++) {
      int j, minIndex;
      for (j = i + 1, minIndex = i; j < list.size(); j++)
        if (c.compare(list.get(j), list.get(minIndex)) < 0)
          minIndex = j;
      ListType temp = list.get(i);
      list.set(i, list.get(minIndex));
      list.set(minIndex, temp);
    }
  }

  /**
   * Comparator that defines an ordering among library books using the
   ISBN.
   */
  protected class OrderByIsbn implements
          Comparator<LibraryBook<T>> {
    /**
     * Returns a negative value if lhs is smaller than rhs. Returns a positive
     * value if lhs is larger than rhs. Returns 0 if lhs 	and rhs are equal.
     */
    public int compare(LibraryBook<T> lhs,
                       LibraryBook<T> rhs) {
      return (int) (lhs.getIsbn() - rhs.getIsbn());
    }
  }

  /**
   * Comparator that defines an ordering among library books using the
   author, and book title as a tie-breaker.
   */
  //Compares objects based on authors, and if authors are tied then uses title
  protected class OrderByAuthor implements
          Comparator<LibraryBook<T>> {
    //int compare compares two LibraryBooks and first compares the authors and if tied
    //then compares the title
    public int compare(LibraryBook<T> o1, LibraryBook<T> o2) {
      int author = (o1.getAuthor().compareTo(o2.getAuthor()));
      //if tied, title compares the 2 String objects based on their titles and returns it
      if (author == 0) {
        int title = (o1.getTitle().compareTo(o2.getTitle()));
        return title;
      }
      return author;
    }
  }

  /**
   * Comparator that defines an ordering among library books using the
   due date.
   */
  protected class OrderByDueDate implements
          Comparator<LibraryBook<T>> {
    public int compare(LibraryBook<T> o1, LibraryBook<T> o2) {
      GregorianCalendar dueDate1 =  o1.getDueDate();
      GregorianCalendar dueDate2 =  o2.getDueDate();

      int dueDate = (dueDate1.compareTo(dueDate2));
      return dueDate;
      //I could also do
      //return (int) (dueDate1.compareTo(dueDate2));
    }
  }
}//end of class bracket
