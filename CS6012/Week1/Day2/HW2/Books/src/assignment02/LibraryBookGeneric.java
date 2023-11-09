package assignment02;

import java.util.GregorianCalendar;
public class LibraryBookGeneric<T> extends Book{
    T holder;
    GregorianCalendar dueDate = new GregorianCalendar();
    public LibraryBookGeneric(long isbn, String author, String title) {
        super(isbn, author, title);
        //Initially due date is null and holder is null as well
        dueDate = null;
        holder = null;
    }
    public <T> T getHolder() {
        return (T) holder;
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
        this.dueDate = new GregorianCalendar(year, month - 1, day);
    }
    //helper functions
    public void setHolder(T holder) {
        this.holder = holder;
    }
    public void setDueDate(GregorianCalendar gregorianCalendar) {
        this.dueDate = gregorianCalendar;
    }
}//end of class bracket
