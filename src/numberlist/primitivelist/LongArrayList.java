package numberlist.primitivelist;

import numberlist.InvalidIndexException;

/**
 * This class defines an array list for the long data type. It contains an
 * underlying long array that can become partially filled as the front end
 * programmer adds elements. If the maximum size for the array is filled, it
 * gets doubled in size. The front end programmer only has access to the
 * elements 0-(count-1).
 *
 * @author Koenn Becker
 * @version 1.0.0 - 4/6/2018
 */
public class LongArrayList {

    private long[] list;
    private int count;

    /**
     * This is the default constructor that initializes the back end array and
     * count variable.
     */
    public LongArrayList() {
        list = new long[10];
        count = 0;
    }

    /**
     * This method adds a long to the given index of the array. If the specified
     * index is already filled, it will insert the new long in its spot and
     * shift everything, including the long that previously occupied that index,
     * up one spot.
     *
     * @param index the index to insert object at
     * @param value the long to insert
     * @throws numberlist.InvalidIndexException Thrown on invalid index
     */
    public void add(int index, long value) throws InvalidIndexException {
        if (index > count || index < 0) {
            throw new InvalidIndexException(0, count, index);
        } else {
            if (count == list.length) {
                long[] temp = new long[list.length * 2];

                for (int i = 0; i < count; i++) {
                    temp[i] = list[i];
                }
                list = temp;
            }
        }
        if (index == count) {
            list[count++] = value;
        } else {
            for (int i = count; i >= index; i--) {
                list[i + 1] = list[i];
            }
            list[index] = value;
            count++;
        }
    }

    /**
     * This method sets the value at a particular index to the passed value.
     *
     * @param index the index to remove object from
     * @param value the new value you would like to set index to.
     * @return long - the old value that was replaced.
     * @throws numberlist.InvalidIndexException Thrown on invalid index
     */
    public long set(int index, long value) throws InvalidIndexException {
        if (index >= count || index < 0) {
            throw new InvalidIndexException(0, count - 1, index);
        }
        long temp = list[index];
        list[index] = value;
        return temp;
    }

    /**
     * This method removes the long at the specified index and shifts all other
     * elements after it down to fill the removed elements spot. If an invalid
     * index is entered, the Long.MIN_VALUE will be returned.
     *
     * @param index the index to remove long from
     * @return long - returns the long that was removed
     * @throws numberlist.InvalidIndexException Thrown on invalid index
     */
    public long removeAt(int index) throws InvalidIndexException {

        if (index >= 0 && index < count) {
            long temp = list[index];

            for (int i = index; i <= count - 2; i++) {
                //long temp = list[i + 1];
                list[i] = list[i + 1];
            }
            count--;
            return temp;
        } else {
            throw new InvalidIndexException(0, count - 1, index);
        }
    }

    /**
     * This method removes the first appearance of the long passed as a
     * parameter from the array.
     *
     * @param value the long to remove.
     */
    public void remove(int value) {
        int indexOf = indexOf(value);

        try {
            removeAt(indexOf);
        } catch (InvalidIndexException iie) {
            System.out.println("Object not found, -1 returned.");
        }
    }

    /**
     * This method returns the long at the requested index. If a index is
     * requested that is out of range, it will return the Long.MIN_VALUE.
     *
     * @param index the index to get long from
     * @return long - the long at specified index
     * @throws numberlist.InvalidIndexException Thrown on invalid index
     */
    public long get(int index) throws InvalidIndexException {
        if (index >= 0 && index < count) {
            return list[index];
        } else {
            throw new InvalidIndexException(0, count - 1, index);
        }

    }

    /**
     * This method finds the first index of a given long and returns it. If no
     * long is found, -1 is returned.
     *
     * @param value The long to find
     * @return int - the index of specified long
     */
    public int indexOf(long value) {
        int index = -1;

        for (int i = 0; i < count && index < 0; i++) {
            if (list[i] == value) {
                index = i;
                return index;
            }
        }
        return index;
    }

    /**
     * Returns how many elements there are in the partially filled array. These
     * are the only elements the user has access to.
     *
     * @return int - the count of how many elements the user has added to array.
     */
    public int getCount() {
        return count;
    }

    /**
     * This method outputs the entire array as a string surrounded by brackets
     * and separated by commas.
     *
     * @return String - a string containing all elements in the array.
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("[ ");

        for (int i = 0; i < count; i++) {
            if (i == count - 1) {
                out.append(list[i] + " ");
            } else {
                out.append(list[i]).append(", ");
            }
        }

        out.append("]");
        return out.toString();
    }
}
