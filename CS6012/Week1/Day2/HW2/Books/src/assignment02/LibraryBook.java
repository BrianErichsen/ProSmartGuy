package assignment02;

import java.util.GregorianCalendar;
public class LibraryBook<T> extends Book{
    T holder;
    GregorianCalendar dueDate = new GregorianCalendar();
    public LibraryBook(long isbn, String author, String title) {
        super(isbn, author, title);
    }
    public T getHolder() {
        return holder;
    }
    public long getIsbn() {
        return this.isbn;
    }
    public GregorianCalendar getDueDate() {
        return this.dueDate;
    }
    public void checkIn() {
        //Initially due date is null and holder is null as well
        this.dueDate = null;
        this.holder = null;
    }
    public void checkOut(T holder, int year, int month, int day) {
        //checking out a book sets a dueDate and a book holder
        this.holder = holder;
        this.dueDate = new GregorianCalendar(year, month, day);
    }
    //helper functions
//    public void setHolder(T holder) {
//        this.holder = holder;
//    }
//    public void setDueDate(GregorianCalendar gregorianCalendar) {
//        this.dueDate = gregorianCalendar;
//    }
}//end of class bracket
