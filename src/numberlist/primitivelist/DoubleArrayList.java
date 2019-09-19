package numberlist.primitivelist;

import numberlist.InvalidIndexException;

/**
 * This class defines an array list for the double data type. It contains an
 * underlying LongArrayList that is used to add, remove and store elements in.
 * As a user adds a double, it gets converted to a long so it can be stored in
 * the LongArrayList. This way, all the methods are already written so all that
 * needs to be done is the conversion.
 *
 * @author Koenn Becker
 * @version 1.0.0 - 4/13/2018
 */
class DoubleArrayList {

    private LongArrayList list;

    /**
     * This is the default constructor that initializes the back end
     * LongArrayList.
     */
    public DoubleArrayList() {
        list = new LongArrayList();
    }

    /**
     * This method converts the double to a long and adds it to the
     * LongArrayList at the specified index.
     *
     * @param index the index to insert object at
     * @param value the double to insert
     * @throws numberlist.InvalidIndexException Thrown on invalid index
     */
    public void add(int index, double value) throws InvalidIndexException {
        try {
            list.add(index, Double.doubleToRawLongBits(value));
        } catch (InvalidIndexException ex) {
            throw ex;
        }
    }

    /**
     * This method sets the value at a particular index to the passed value.
     *
     * @param index the index to remove object from
     * @param value the new value you would like to set index to.
     * @return double - the old value that was replaced.
     * @throws numberlist.InvalidIndexException Thrown on invalid index
     */
    public double set(int index, double value) throws InvalidIndexException {
        if (index >= list.getCount() || index < 0) {
            throw new InvalidIndexException(0, list.getCount() - 1, index);
        }
        double temp = Double.longBitsToDouble(list.get(index));
        list.set(index, Double.doubleToRawLongBits(value));
        return temp;
    }

    /**
     * This method removes the double at the specified index and returns the
     * double that was removed.
     *
     * @param index the index to remove double from
     * @return double - the double that was removed at specified index.
     * @throws numberlist.InvalidIndexException Thrown on invalid index
     */
    public double removeAt(int index) throws InvalidIndexException {
        try {
            return Double.longBitsToDouble(list.removeAt(index));
        } catch (InvalidIndexException iie) {
            throw iie;
        }
    }

    /**
     * This method removes the first appearance of the given double value and
     * removes it from the array.
     *
     * @param value the double to remove
     */
    public void remove(double value) {
        int indexOf = indexOf(value);

        try {
            removeAt(indexOf);
        } catch (InvalidIndexException iie) {
            System.out.println("Object not found, -1 returned.");
        }
    }

    /**
     * This method gets the value at the given index and converts the long into
     * a double.
     *
     * @param index the index to get value from
     * @return double - the value stored at the given index
     * @throws numberlist.InvalidIndexException Thrown on invalid index
     */
    public double get(int index) throws InvalidIndexException {
        try {
            return Double.longBitsToDouble(list.get(index));
        } catch (InvalidIndexException iie) {
            throw iie;
        }
    }

    /**
     * This method looks for the given double value in the array and if it is
     * found, the index is returned. Otherwise, -1 is returned.
     *
     * @param value the double to find index of
     * @return int - the index of found value, -1 if not found.
     */
    public int indexOf(double value) {
        int index = -1;

        for (int i = 0; i < list.getCount() && index < 0; i++) {
            try {
                if (Double.longBitsToDouble(list.get(i)) == value) {
                    index = i;
                    return index;
                }
            } catch (InvalidIndexException iie) {
                System.out.println("Value not found, -1 returned.");
            }
        }
        return index;
    }

    /**
     * This method gets the number of elements in the list.
     *
     * @return int - the total elements in array list
     */
    public int getCount() {
        return list.getCount();
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

        for (int i = 0; i < list.getCount(); i++) {
            if (i == list.getCount() - 1) {
                try {
                    out.append(Double.longBitsToDouble(list.get(i)) + " ");
                } catch (InvalidIndexException ex) {
                }
            } else {
                try {
                    out.append(Double.longBitsToDouble(list.get(i))).append(", ");
                } catch (InvalidIndexException ex) {
                }
            }
        }
        out.append("]");
        return out.toString();
    }

}
