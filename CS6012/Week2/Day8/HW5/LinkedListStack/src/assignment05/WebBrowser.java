package assignment05;


import java.net.URL;
import java.util.NoSuchElementException;


public class WebBrowser {
    private Stack<URL> backButtonStack;
    private Stack<URL> forwardButtonStack;
    private URL currentWebpage;


    public WebBrowser() {
        this.backButtonStack = new LinkedListStack<>();
        this.forwardButtonStack = new LinkedListStack<>();
        this.currentWebpage = null;
    }


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




    /**
     * Simulates visiting a webpage and clears the forward button stack.
     *
     * @param webpage - the URL of the webpage to visit
     */
    public void visit(URL webpage) {
        backButtonStack.push(currentWebpage);
        forwardButtonStack.clear();
        currentWebpage = webpage;
    }
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
        //backButtonStack.push(currentWebpage);
        currentWebpage = forwardButtonStack.pop();
        return currentWebpage;
    }
    /**
     * Generates a history of URLs visited, including the current webpage.
     *
     * @return a list of URLs ordered from most recently visited to least recently visited
     */
//    public SinglyLinkedList<URL> history() {
//        // Create a copy of the backStack to avoid modifying its state
//        Stack<URL> tempStack = new LinkedListStack<>();
//        SinglyLinkedList<URL> historyList = new SinglyLinkedList<>();
//
//        // Push elements from backStack to tempStack and historyList
//        while (!backButtonStack.isEmpty()) {
//            URL url = backButtonStack.pop();
//            if (url != null) {
//                tempStack.push(url);
//                historyList.insertFirst(url);
//            }
//        }
//
//        // Restore the backStack state
//        while (!tempStack.isEmpty()) {
//            backButtonStack.push(tempStack.pop());
//            URL url = tempStack.pop();
//            historyList.insertFirst(url);
//        }
//
//        // Add the currentWebpage to the historyList if it is not null
//        if (currentWebpage != null) {
//            historyList.insertFirst(currentWebpage);
//        }
//
//        return historyList;
//    }


    /**
     * Generates a history of URLs visited, including the current webpage.
     *
     * @return a list of URLs ordered from most recently visited to least recently visited
     */
    /**
     * Generates a history of URLs visited, including the current webpage.
     *
     * @return a list of URLs ordered from most recently visited to least recently visited
     */
    public SinglyLinkedList<URL> history() {
        SinglyLinkedList<URL> historyList = new SinglyLinkedList<>();


        // Add the currentWebpage to the historyList if it is not null
        if (currentWebpage != null) {
            historyList.insertLast(currentWebpage); // Use insertFirst to add at the beginning of the list
        }


        // Copy elements from backButtonStack to historyList without modifying backButtonStack
        Stack<URL> tempStack = new LinkedListStack<>();
        while (!backButtonStack.isEmpty()) {
            URL url = backButtonStack.pop();
            tempStack.push(url);


            // Skip adding the currentWebpage again in the loop
            if (url != null && !url.toString().equals(currentWebpage.toString())) {
                historyList.insertFirst(url);
            }


        }


        // Restore the backButtonStack state
        while (!tempStack.isEmpty()) {
            backButtonStack.push(tempStack.pop());
        }


        return historyList;
    }
}

