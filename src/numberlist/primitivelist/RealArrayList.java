package numberlist.primitivelist;

import java.util.logging.Level;
import java.util.logging.Logger;
import numberlist.InvalidIndexException;

/**
 * This class extends DoubleArrayList to add a few additional methods. One of
 * those methods is to add a double with no parameters, which will add element
 * to end of array. The second is to remove all appearances of a particular
 * value, and the third is to find the last index of a given value.
 *
 * @author Koenn Becker
 * @version 1.0.0 - 4/13/2018
 */
public class RealArrayList extends DoubleArrayList {

    /**
     * This method adds a double to the end of the array list and returns the
     * index it was added at.
     *
     * @param value the double to insert
     * @return int - the index at which element was added.
     */
    public int add(double value) {
        try {
            add(getCount(), value);
        } catch (InvalidIndexException ex) {
        }
        return getCount() - 1;
    }

    /**
     * This method removes all instances of the given value.
     *
     * @param value the double to remove all appearances of
     */
    public void removeAll(double value) {
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
     * @param value the double to search for
     * @return int - the last index of given double.
     */
    public int lastIndexOf(double value) {
        int index = -1;

        for (int i = getCount() - 1; i >= 0; i--) {
            try {
                if (get(i) == value) {
                    index = i;
                    return index;
                }
            } catch (InvalidIndexException ex) {
            }
        }
        return index;
    }

}
