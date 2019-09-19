package numberlist.primitivelist;

import numberlist.InvalidIndexException;

/**
 * This class extends LongArrayList to add a few additional methods. One of
 * those methods is to add a long with no parameters, which will add element to
 * end of array. The second is to remove all appearances of a particular value,
 * and the third is to find the last index of a given value.
 *
 * @author Koenn Becker
 * @version 1.0.0 - 4/13/2018
 */
public class IntegerArrayList extends LongArrayList {

    /**
     * This method adds a long to the end of the array list and returns the
     * index it was added at.
     *
     * @param value the long to insert
     * @return int - the index at which element was added.
     */
    public int add(long value) {
        try {
            add(getCount(), value);
        } catch (InvalidIndexException ex) {
        }
        return getCount() - 1;
    }

    /**
     * This method removes all instances of the given value.
     *
     * @param value the long to remove all appearances of
     */
    public void removeAll(long value) {
        for (int i = 0; i < getCount(); i++) {
            try {
                if (get(i) == value) {
                    removeAt(i);
                    i--;
                }
            } catch (InvalidIndexException ex) {
            }
        }
    }

    /**
     * This method finds the last index of a given value by starting from the
     * end of the array.
     *
     * @param value the long to search for
     * @return int - the last index of given long.
     */
    public int lastIndexOf(long value) {
        int index = -1;

        for (int i = getCount() - 1; i >= 0; i--) {
            try {
                if (get(i) == value) {
                    index = i;
                    return index;
                }
            } catch (InvalidIndexException ex) {
                System.out.println("Value not found, -1 returned.");
            }
        }
        return index;
    }
}
