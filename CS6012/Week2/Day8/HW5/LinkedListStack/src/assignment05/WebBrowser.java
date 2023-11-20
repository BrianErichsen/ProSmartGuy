package assignment05;


import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Iterator;


public class WebBrowser {
    private Stack<URL> backButtonStack;  // Stack to store URLs for backward navigation
    private Stack<URL> forwardButtonStack;  // Stack to store URLs for forward navigation
    private URL currentWebpage;  // Current webpage URL


    //===============================================================================================================//


    /**
     * Constructs a WebBrowser object with empty stacks and null currentWebpage.
     *
     * This constructor initializes a WebBrowser with empty backButtonStack and forwardButtonStack,
     * and sets the currentWebpage to null, indicating that the browser has no current webpage.
     */
    public WebBrowser() {
        this.backButtonStack = new LinkedListStack<>();
        this.forwardButtonStack = new LinkedListStack<>();
        this.currentWebpage = null;
    }


    //===============================================================================================================//


    /**
     * Constructs a WebBrowser object with a given history.
     *
     * This constructor initializes a WebBrowser with the provided history. It sets the currentWebpage to
     * the last URL in the history and populates the backButtonStack with all URLs in the history except
     * the current one.
     *
     * @param history The history of URLs to initialize the WebBrowser with.
     * @throws IllegalArgumentException if the provided history is null or empty.
     */
    public WebBrowser(SinglyLinkedList<URL> history) {
        if (history == null || history.isEmpty()) {
            throw new IllegalArgumentException("History cannot be null or empty");
        }


        this.backButtonStack = new LinkedListStack<>();
        this.forwardButtonStack = new LinkedListStack<>();


        // Initialize with the last URL as the current webpage
        this.currentWebpage = history.getFirst();


        // Push all URLs EXCEPT the current one to the backButtonStack
        for (int i = 1; i < history.size(); i++) {
            this.backButtonStack.push(history.get(i));
        }
    }


    //===============================================================================================================//


    /**
     * Simulates visiting a webpage and clears the forward button stack.
     *
     * @param webpage - the URL of the webpage to visit
     */
    public void visit(URL webpage) {
        backButtonStack.push(currentWebpage);  // Push the current webpage to the backButtonStack
        forwardButtonStack.clear();  // Clear the forward button stack
        currentWebpage = webpage;  // Set the current webpage to the specified URL
    }


    //===============================================================================================================//


    /**
     * Simulates using the back button, returning the URL visited.
     *
     * @return the URL of the previously visited webpage
     * @throws NoSuchElementException if there is no previously visited URL
     */
    public URL back() {
        if (backButtonStack.isEmpty()) {
            throw new NoSuchElementException("No previously visited URL");
        }


        // Pop the previous URL from the backStack and set it as the currentWebpage
        currentWebpage = backButtonStack.pop();


        // Move the currentWebpage to the forwardStack
        if (currentWebpage != null) {
            forwardButtonStack.push(currentWebpage);
        }


        return currentWebpage;
    }


    //===============================================================================================================//


    /**
     * Simulates using the forward button, returning the URL visited.
     *
     * @return the URL of the next visited webpage
     * @throws NoSuchElementException if there is no URL to visit next
     */
    public URL forward() throws NoSuchElementException {
        if (forwardButtonStack.isEmpty()) {
            throw new NoSuchElementException("No URL to visit next");
        }


        // Pop the next URL from the forwardButtonStack and set it as the currentWebpage
        currentWebpage = forwardButtonStack.pop();
        return currentWebpage;
    }


    //===============================================================================================================//


    /**
     * Generates a history of URLs visited, including the current webpage.
     *
     * @return a list of URLs ordered from most recently visited to least recently visited
     */
    public SinglyLinkedList<URL> history() {
        SinglyLinkedList<URL> historyList = new SinglyLinkedList<>();


        // Add the currentWebpage to the historyList if it is not null
        if (currentWebpage != null) {
            historyList.insertLast(currentWebpage);
        }


        // Copy elements from backButtonStack to historyList without modifying backButtonStack
        Stack<URL> tempStack = new LinkedListStack<>();
        while (!backButtonStack.isEmpty()) {
            URL url = backButtonStack.pop();
            tempStack.push(url);


            // Skip adding the currentWebpage again in the loop
            if (url != null && !url.toString().equals(currentWebpage.toString())) {
                historyList.insertLast(url); // Use insertLast to maintain the order
            }
        }


        // Restore the backButtonStack state
        while (!tempStack.isEmpty()) {
            backButtonStack.push(tempStack.pop());
        }


        return historyList;
    }
}


