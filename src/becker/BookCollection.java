package becker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import numberlist.InvalidIndexException;
import numberlist.objectlist.Copiable;
import numberlist.objectlist.Money;
import numberlist.objectlist.NumericArrayList;
import numberlist.primitivelist.IntegerArrayList;

/**
 * The business layer class for nozamA. Uses various ArrayLists,
 * IntegerArrayLists, and NumericArrayLists to hold values. Calcultaes and
 * passes data to the CourseProject class for the front end.
 *
 * @author Koenn Becker as Coding Manager.
 */
public class BookCollection {

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> authors = new ArrayList<>();
    private ArrayList<String> isbns = new ArrayList<>();
    private IntegerArrayList years = new IntegerArrayList();
    private NumericArrayList prices = new NumericArrayList();
    private IntegerArrayList quantities = new IntegerArrayList();
    private ArrayList<String> images = new ArrayList<>();

    //Used for statistics
    private ArrayList<String> authorNames;

    private int[] authorBooks;

    /**
     * Method to add books to the program. Storing the data to various
     * ArrayLists
     *
     * @param title Title of the book
     * @param author Author of the book
     * @param isbn ISBN of the book
     * @param year Publication year of the book
     * @param stock Inventory count of the book
     * @param price Price of the book
     * @param imgPath Path to the image of the book
     */
    public void addBook(String title, String author, String isbn, int year, int stock, Money price, String imgPath) {
        titles.add(title);
        authors.add(author);
        isbns.add(isbn);
        years.add(year);
        quantities.add(stock);
        prices.add(price);
        images.add(imgPath);
    }

    /**
     * RemoveAt method that removes the value at a certain index and then slides
     * anything over
     *
     * @param index index in the arrays
     * @return true or false for success or failure
     */
    public boolean removeAt(int index) {
        try {
            years.removeAt(index);
            prices.removeAt(index);
            quantities.removeAt(index);
            titles.remove(titles.get(index));
            authors.remove(authors.get(index));
            isbns.remove(isbns.get(index));
            images.remove(images.get(index));

            //Resave the file
            saveFile();
            //Return true for success
            return true;
        }
        catch (InvalidIndexException e) {
            System.out.println("Failed Removing at: " + index + " Index Out Of Bounds");
            //Return false for failure
            return false;
        }
    }

    /**
     * Getter method for the title
     *
     * @param index Index of title
     * @return The title as a string
     */
    public String getTitle(int index) {
        try {
            return titles.get(index);
        }
        catch (ArrayIndexOutOfBoundsException aioobe) {
            System.out.println("There was an error trying to get the title at index: " + index);
            return null;
        }
    }

    /**
     * Getter method for the author
     *
     * @param index Index of author
     * @return The author as a string
     */
    public String getAuthor(int index) {
        try {
            return authors.get(index);
        }
        catch (ArrayIndexOutOfBoundsException aioobe) {
            System.out.println("There was an error trying to get the author at index: " + index);
            return null;
        }
    }

    /**
     * Getter method for the ISBN number
     *
     * @param index The index of ISBN
     * @return ISBN at given index
     */
    public String getIsbn(int index) {
        try {
            return isbns.get(index);
        }
        catch (ArrayIndexOutOfBoundsException aioobe) {
            System.out.println("There was an error trying to get the ISBN at index: " + index);
            return null;
        }
    }

    /**
     * Getter method for the year
     *
     * @param index The index of the year
     * @return Year at given index
     * @throws InvalidIndexException If the index can't be found
     */
    public long getYear(int index) throws InvalidIndexException {
        return years.get(index);
    }

    /**
     * Getter method for the Quantity
     *
     * @param index The index of the quantity
     * @return The quantity at given index
     * @throws InvalidIndexException If the index can't be found
     */
    public long getQuantity(int index) throws InvalidIndexException {
        return quantities.get(index);
    }

    /**
     * Getter method for the price
     *
     * @param index The index for the price
     * @return The price at given index
     * @throws InvalidIndexException If the index can't be found
     */
    public Money getPrice(int index) throws InvalidIndexException {
        return (Money) prices.get(index);
    }

    /**
     * Getter method for image
     *
     * @param index The index of the image
     * @return The path to the image at given index
     */
    public String getImage(int index) {
        try {
            return images.get(index);
        }
        catch (ArrayIndexOutOfBoundsException aioobe) {
            System.out.println("There was an error trying to get the image at index: " + index);
            return null;
        }
    }

    /**
     * Setter for Title
     *
     * @param index The index for the title
     * @param title The new title
     */
    public void setTitle(int index, String title) {
        titles.set(index, title);
    }

    /**
     * Setter for Author
     *
     * @param index The index for the author
     * @param author The new author
     */
    public void setAuthor(int index, String author) {
        authors.set(index, author);
    }

    /**
     * Setter for ISBN
     *
     * @param index The index for the ISBN
     * @param isbn The new ISBN
     */
    public void setIsbn(int index, String isbn) {
        isbns.set(index, isbn);
    }

    /**
     * Setter for Year
     *
     * @param index The index for the year
     * @param year The new year
     */
    public void setYear(int index, int year) {
        try {
            years.set(index, year);
        }
        catch (InvalidIndexException ex) {
            System.out.println("Invalid Index exception on year");
        }
    }

    /**
     * Setter for Quantity
     *
     * @param index The index for the quantity
     * @param quantity The new quantity
     */
    public void setQuantity(int index, int quantity) {
        try {
            quantities.set(index, quantity);
        }
        catch (InvalidIndexException ex) {
            System.out.println("Invalid Index exception on quantity");
        }
    }

    /**
     * Gets the total number of books in the collection.
     *
     * @return The total number of books in inventory
     */
    public int getCount() {
        return isbns.size();
    }

    /**
     * Setter for the Price
     *
     * @param index The index for the price
     * @param price The new price
     */
    public void setPrice(int index, Money price) {
        try {
            prices.set(index, price);
        }
        catch (InvalidIndexException ex) {
            System.out.println("Invalid Index exception on price");
        }
    }

    /**
     * Setter for Image
     *
     * @param index The index for the image
     * @param imgPath The path to the new image
     */
    public void setImage(int index, String imgPath) {
        images.set(index, imgPath);
    }

    /**
     * Compares two given values of money and returns a positive, negative, or 0
     *
     * @param index1 First index of money
     * @param index2 Second index of money
     * @return Either negative, 0, or positive depending on comparison of money
     */
    public double compareMoney(int index1, int index2) {
        long compare = 0;
        try {
            Money moneyOne = (Money) prices.get(index1);
            Money moneyTwo = (Money) prices.get(index2);
            if (moneyOne.getDollars() == moneyTwo.getDollars()) {
                compare = (moneyOne.getCents() - moneyTwo.getCents());
            }
            else {
                compare = moneyOne.getDollars() - moneyTwo.getDollars();
            }
            return compare;
        }
        catch (InvalidIndexException iie) {
            System.out.println("There was an issue comparing the money objects at"
                    + "position: " + index1 + " and " + index2);
            return 0.0;
        }
    }

    /**
     * Compares Strings
     *
     * @param index1 First index of string
     * @param index2 Second index of string
     * @param arr The array to get the strings from
     * @return Either negative, 0, or positive depending on comparison of
     * strings
     */
    public int compareStrings(int index1, int index2, ArrayList<String> arr) {
        try {
//String of authors
            String string1 = arr.get(index1);
            String string2 = arr.get(index2);
//Compare the strings
            int compare = string1.compareToIgnoreCase(string2);

            return compare;
        }
        catch (ArrayIndexOutOfBoundsException aioobe) {
            System.out.println("There was an issue comparing the strings at"
                    + "position: " + index1 + " and " + index2);
            return 0;
        }
    }

    /**
     * Insertion sorting based upon title in ascending order. Insertion sort is
     * a great way to sort strings and uses minimal memory
     *
     * Time Complexity: O(n) - O(n^2)
     */
    public void sortByTitleAscending() {
//Insertion sort
        int position;
        for (int i = 1; i < titles.size(); i++) {
            position = i;
            while (position > 0 && (compareStrings(position, position - 1, titles) < 0)) {
                swap(position, position - 1);
                position--;
            }
        }
    }

    /**
     * Insertion sorting based upon Author in ascending order. Insertion sort is
     * a great way to sort strings and uses minimal memory
     *
     * Time Complexity: O(n) - O(n^2)
     */
    public void sortByAuthorAscending() {
//Insertion sort
        int position;
        for (int i = 1; i < authors.size(); i++) {
            position = i;
            while (position > 0 && (compareStrings(position, position - 1, authors) < 0)) {
                swap(position, position - 1);
                position--;
            }
        }
    }

    /**
     * Insertion sorting based upon Year in ascending order. Insertion sort is a
     * great way to sort our arrays as they are not that long and insertion sort
     * uses minimal memory
     *
     * Time Complexity: O(n) - O(n^2)
     */
    public void sortByYearAscending() {
        try {
//Insertion sort
            int position;
            for (int i = 1; i < years.getCount(); i++) {
                position = i;
                while (position > 0 && (years.get(position) < years.get(position - 1))) {
                    swap(position, position - 1);
                    position--;
                }
            }
        }
        catch (InvalidIndexException iee) {

        }
    }

    /**
     * Insertion sorting based upon Quantity in ascending order. Insertion sort
     * is a great way to sort our arrays as they are not that long and insertion
     * sort uses minimal memory
     *
     * Time Complexity: O(n) - O(n^2)
     */
    public void sortByQuantityAscending() {
        try {
//Insertion sort
            int position;
            for (int i = 1; i < quantities.getCount(); i++) {
                position = i;
                while (position > 0 && (quantities.get(position) < quantities.get(position - 1))) {
                    swap(position, position - 1);
                    position--;
                }
            }
        }
        catch (InvalidIndexException iee) {

        }
    }

    /**
     * Insertion sorting based upon Price in ascending order. Insertion sort is
     * a great way to sort our arrays as they are not that long and insertion
     * sort uses minimal memory.
     *
     * Time Complexity: O(n) - O(n^2)
     */
    public void sortByPriceAscending() {
//Insertion sort
        int position;
        for (int i = 1; i < prices.getCount(); i++) {
            position = i;
            while (position > 0 && (compareMoney(position, position - 1)) < 0) {
                swap(position, position - 1);
                position--;
            }
        }
    }

    /**
     * Insertion sorting based upon title in Descending order. Insertion sort is
     * a great way to sort strings and uses minimal memory
     *
     * Time Complexity: O(n) - O(n^2)
     */
    public void sortByTitleDescending() {
//Insertion sort
        int position;
        for (int i = 1; i < titles.size(); i++) {
            position = i;
            while (position > 0 && (compareStrings(position, position - 1, titles) > 0)) {
                swap(position, position - 1);
                position--;
            }
        }
    }

    /**
     * Insertion sorting based upon Author in Descending order. Insertion sort is
     * a great way to sort strings and uses minimal memory
     *
     * Time Complexity: O(n) - O(n^2)
     */
    public void sortByAuthorDescending() {
//Insertion sort
        int position;
        for (int i = 1; i < authors.size(); i++) {
            position = i;
            while (position > 0 && (compareStrings(position, position - 1, authors) > 0)) {
                swap(position, position - 1);
                position--;
            }
        }
    }

    /**
     * Insertion sorting based upon Year in Descending order. Insertion sort is a
     * great way to sort our arrays as they are not that long and insertion sort
     * uses minimal memory
     *
     * Time Complexity: O(n) - O(n^2)
     */
    public void sortByYearDescending() {
        try {
//Insertion sort
            int position;
            for (int i = 1; i < years.getCount(); i++) {
                position = i;
                while (position > 0 && (years.get(position) > years.get(position - 1))) {
                    swap(position, position - 1);
                    position--;
                }
            }
        }
        catch (InvalidIndexException iee) {

        }
    }

    /**
     * Insertion sorting based upon Quantity in Descending order. Insertion sort
     * is a great way to sort our arrays as they are not that long and insertion
     * sort uses minimal memory
     *
     * Time Complexity: O(n) - O(n^2)
     */
    public void sortByQuantityDescending() {
        try {
//Insertion sort
            int position;
            for (int i = 1; i < quantities.getCount(); i++) {
                position = i;
                while (position > 0 && (quantities.get(position) > quantities.get(position - 1))) {
                    swap(position, position - 1);
                    position--;
                }
            }
        }
        catch (InvalidIndexException iee) {

        }
    }

    /**
     * Insertion sorting based upon Price in Descending order. Insertion sort is
     * a great way to sort our arrays as they are not that long and insertion
     * sort uses minimal memory.
     *
     * Time Complexity: O(n) - O(n^2)
     */
    public void sortByPriceDescending() {
//Insertion sort
        int position;
        for (int i = 1; i < prices.getCount(); i++) {
            position = i;
            while (position > 0 && (compareMoney(position, position - 1)) > 0) {
                swap(position, position - 1);
                position--;
            }
        }
    }

    /**
     * Swap method which swaps the values of the index parameters
     *
     * @param a The index of the first value
     * @param b The index of the second value
     * @return boolean depending on success or failure
     */
    private boolean swap(int a, int b) {

        boolean success = false;
        try {
            String titTemp = titles.get(a);
            titles.set(a, titles.get(b));
            titles.set(b, titTemp);

            String authTemp = authors.get(a);
            authors.set(a, authors.get(b));
            authors.set(b, authTemp);

            String isbTemp = isbns.get(a);
            isbns.set(a, isbns.get(b));
            isbns.set(b, isbTemp);

            long yrsTemp = years.get(a);
            years.set(a, years.get(b));
            years.set(b, yrsTemp);

            Copiable prcTemp = prices.get(a);
            prices.set(a, prices.get(b));
            prices.set(b, prcTemp);

            long qntTemp = quantities.get(a);
            quantities.set(a, quantities.get(b));
            quantities.set(b, qntTemp);

            String imgTemp = images.get(a);
            images.set(a, images.get(b));
            images.set(b, imgTemp);

            success = true;
        }
        catch (InvalidIndexException iie) {
            success = false;
        }
        return success;
    }

    /**
     * Searching based upon the Title of the books. It returns an
     * IntegerArrayList sorted alphabetically. It searches as part of sub
     * string, so if the user is searching for 'cat' every title with 'cat' in
     * it will come up.
     *
     * @param search The string to search for
     * @return The IntegerArrayList with the titles that match the search
     */
    public IntegerArrayList searchTitle(String search) {
        //The strings to compare
        String strArr = "";
        String strSrch = search;
        //ArrayList of the index of the location of the files 
        IntegerArrayList results = new IntegerArrayList();

        //Linear search through the titles
        for (int i = 0; i < titles.size(); i++) {
            //The string from the array
            strArr = titles.get(i);
            //Check if the title in the array contains any part of the search
            if (strArr.toLowerCase().contains(strSrch.toLowerCase())) {
                results.add(i);
            }
        }
        return results;
    }

    /**
     * LoadFile method that loads the information from the resource file
     */
    public void loadFile() {
        ClassLoader loader = getClass().getClassLoader();
        InputStream books = loader.getResourceAsStream("becker/resources/books.nza");
        try (Scanner fileIn = new Scanner(books)) {
            while (fileIn.hasNextLine()) {

                String[] lineSplit = fileIn.nextLine().split(", ");
                String fileStr = loader.getResource("becker/images/placeholder.jpg").toExternalForm();

                addBook(lineSplit[0],
                        lineSplit[1],
                        lineSplit[2],
                        Integer.parseInt(lineSplit[3]),
                        Integer.parseInt(lineSplit[4]),
                        new Money(Long.parseLong(lineSplit[5]), Byte.parseByte(lineSplit[6])),
                        lineSplit[7]);

            }
        }
    }

    /**
     * SaveFile method that saves any data when save buttons are pressed
     *
     * @return boolean depending on success or failure
     */
    public boolean saveFile() {
//        ClassLoader loader = getClass().getClassLoader();
        File saveFile = new File("src/becker/resources/books.nza");
        try (PrintWriter pw = new PrintWriter(saveFile)) {
            for (int i = 0; i < titles.size(); i++) {
                String line = titles.get(i) + ", " + authors.get(i) + ", "
                        + isbns.get(i) + ", " + years.get(i) + ", "
                        + quantities.get(i) + ", "
                        + ((Money) prices.get(i)).getDollars() + ", "
                        + ((Money) prices.get(i)).getCents() + ", "
                        + images.get(i);
                pw.println(line);

            }
            //Save success
            return true;
        }
        catch (InvalidIndexException ex) {
            System.out.println("Error saving file, index out of bounds exception thrown.");
            //Save failed
            return false;
        }
        catch (FileNotFoundException ex) {
            System.out.println("Error saving file, image not found");
            //Save failed
            return false;
        }
    }

    /**
     * Appends to the resource file without overwriting previous data
     */
    public void appendFile() {
        File saveFile = new File("src/becker/resources/books.nza");

        try (PrintWriter pw = new PrintWriter(new FileOutputStream(saveFile, true))) {
            String line = titles.get(titles.size() - 1) + ", " + authors.get(titles.size() - 1) + ", "
                    + isbns.get(titles.size() - 1) + ", " + years.get(titles.size() - 1) + ", "
                    + quantities.get(titles.size() - 1) + ", "
                    + ((Money) prices.get(titles.size() - 1)).getDollars() + ", "
                    + ((Money) prices.get(titles.size() - 1)).getCents() + ", "
                    + images.get(titles.size() - 1);
            pw.println(line);

        }
        catch (FileNotFoundException e) {
            System.out.println("Error saving file, file not found.");
        }
        catch (InvalidIndexException ex) {
            System.out.println("Error saving file, index out of bounds exception thrown.");
        }
    }

    /**
     * Puts authors into a new array with their names appearing only once, and
     * counts how many books each author has. The information is then used to
     * calculate statistics based on how many books each author has in
     * inventory.
     */
    public void getStats() {
        //Parrelel arrays used for stats
        //New ArrayList to hold author's name's only once (x axis)
        authorNames = new ArrayList<>();

        //Fill the authorStats ArrayList with the author's names only appearing once
        for (int i = 0; i < authors.size(); i++) {
            if (!authorNames.contains(authors.get(i))) {
                authorNames.add(authors.get(i));
            }
        }
        //Testing
        System.out.println("List of authors: " + authorNames);

        //New IntegerArrayList to count how many books a certain author has
        authorBooks = new int[authorNames.size()];
        //Fill the books with 0 values in order to set later
        Arrays.fill(authorBooks, 0);

        //Go through the entire list of authors
        for (int i = 0; i < authorNames.size(); i++) {
            int count = 0;
            //See how many times the name of an author appears in the total list of authors
            for (int j = 0; j < authors.size(); j++) {
                //If the name of the author appears
                if (authorNames.get(i).equals(authors.get(j))) {
                    //Add one to the author's book count
                    authorBooks[i] = ++count;
                }
            }
        }

        //Testing
        System.out.println("Number of books: " + Arrays.toString(authorBooks));
    }

    /**
     * Getter for Number of Authors
     *
     * @return Number of Authors
     */
    public int getNumberOfAuthors() {
        return authorNames.size();
    }

    /**
     * Getter for Author Index
     *
     * @param index The index for the author
     * @return The author at the given index
     */
    public String getAuthorIndex(int index) {
        return authorNames.get(index);
    }

    /**
     * Getter for Author Book Count
     *
     * @param index The index of the number of books
     * @return The number of books at the given index
     */
    public int getAuthorBookCount(int index) {
        return authorBooks[index];
    }

    /**
     * Getter for the Highest Book Count
     *
     * @return The book with the most occurences
     */
    public int getHighestBookCount() {
        int max = 0;
        for (int i = 0; i < authorBooks.length; i++) {
            if (max < authorBooks[i]) {
                max = authorBooks[i];
            }
        }
        return max;
    }

    /**
     * Calculates the lowest book in stock, book with highest stock, and the
     * average amount of books in stock. Also says which books have the least
     * stock to help the user know which books to order more of as well as which
     * books are not selling.
     *
     * @return double array with the lowest, highest, and average (in that
     * order)
     */
    public double[] inventoryStats() {
        double[] bookStats = new double[3];
        //Formatter for average
        DecimalFormat df = new DecimalFormat("#.##");
        //Gets the indexes of the lowest/highest quantities
        int low = 0;
        int high = 0;
        //Gets the average stock of a book
        double avg = 0;

        try {
            for (int i = 0; i < quantities.getCount(); i++) {
                if (getQuantity(i) < getQuantity(low)) {
                    low = i;
                }
                if (getQuantity(i) > getQuantity(high)) {
                    high = i;
                }
                avg += getQuantity(i);
            }
            avg /= quantities.getCount();

            bookStats[0] = low;
            bookStats[1] = high;
            bookStats[2] = Double.parseDouble(df.format(avg));
            return bookStats;

        }
        catch (InvalidIndexException iie) {
            System.out.println("Failed trying to calculate inventory stats");
            return null;
        }

    }

}
