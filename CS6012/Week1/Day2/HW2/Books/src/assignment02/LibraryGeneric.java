package assignment02;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Class representation of a library (a collection of library books).
 * 
 */
public class LibraryGeneric<T> {

  private ArrayList<LibraryBookGeneric<T>> library;

  public LibraryGeneric() {
    library = new ArrayList<LibraryBookGeneric<T>>();
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
    library.add(new LibraryBookGeneric(isbn, author, title));
  }

  /**
   * Add the list of library books to the library, assume no duplicates.
   * 
   * @param list
   *          -- list of library books to be added
   */
  public void addAll(ArrayList<LibraryBookGeneric<T>> list) {
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
    ArrayList<LibraryBookGeneric<T>> toBeAdded = new ArrayList<LibraryBookGeneric<T>>();

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
          toBeAdded.add(new LibraryBookGeneric(isbn, author, title));
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
  public <T> T lookup(long isbn) {
    //Loops through each library-book arraylist
    for (LibraryBookGeneric book : library) {
      //if book at a index has same isbn then return its holder
      if (book.getIsbn() == isbn) {
        return (T)  book.getHolder();
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
  public ArrayList<LibraryBookGeneric> lookup(T holder) {
    //Creates a new arraylist where it will contain the list of books checked by a holder
    ArrayList<LibraryBookGeneric> holdersBooks = new ArrayList<>();
    //Iterates through library list and if a book's holder == holder then adds the specific
    //book into the new created array list
    for (LibraryBookGeneric book : library) {
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
    for (LibraryBookGeneric book : library) {
      if (book.getIsbn() == isbn) {
        //If a specific book is found; then check to see if it already has a holder; if it does
        //then returns false because book is already checked out
        if (book.getHolder() != null) {
          return false;
        } else { //still inside if when match for isbn was found
          //sets the holder and new due date and returns true
          book.setHolder(holder);
          book.setDueDate(new GregorianCalendar(year, month, day));
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
    for (LibraryBookGeneric book : library) {
      //if book is checked in, then it's holder and dueDate are null so if book has a holder
      //and there is a match for the isbn then sets the books holder and dueDate to null and returns true
      if (book.holder != null && book.isbn == isbn) {
        book.holder = null;
        book.dueDate = null;
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
  public boolean checkin(T holder) {
    //Loops through whole library array and if a book has a holder and it equals to the specified
    //holder then sets to null the book's holder, due date along with returning true
    for (LibraryBookGeneric book : library) {
      if (book.holder != null && book.holder.equals(holder)) {
        book.holder = null;
        book.dueDate = null;
        return true;
      }
    }
    //returns false if no books with the specified holder are in the library
    return false;
  }
}
