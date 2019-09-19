package numberlist.objectlist;

import numberlist.InvalidIndexException;

/**
 * This class defines an array list for numeric object data types(Money and
 * Complex.) It contains an underlying Object array that can become partially
 * filled as the front end programmer adds elements. If the maximum size for the
 * array is filled, it gets doubled in size. The front end programmer only has
 * access to the elements 0-(count-1).
 *
 * @author Koenn Becker
 * @version 1.0.0 - 4/6/2018
 */
public class NumericArrayList extends NumericList implements Copiable {

    private Copiable[] list;

    /**
     * This is the default constructor that initializes the back end array and
     * count variable.
     */
    public NumericArrayList() {
        list = new Copiable[10];
        count = 0;
    }

    /**
     * This method adds an object to the given index of the array. If the
     * specified index is already filled, it will insert the new object in its
     * spot and shift everything, including the object that previously occupied
     * that index, up one spot.
     *
     * @param index the index to insert object at
     * @param obj the object to insert
     * @throws numberlist.InvalidIndexException Thrown on invalid index
     */
    @Override
    public void add(int index, Copiable obj) throws InvalidIndexException {

        if (index > count || index < 0) {
            throw new InvalidIndexException(0, count, index);
        } else if (count == list.length) {
            Copiable[] temp = new Copiable[list.length * 2];
            for (int i = 0; i < count; i++) {
                temp[i] = list[i];
            }
            list = temp;
        }
        if (index == count) {
            list[count++] = obj;
        } else {
            for (int i = count; i >= index; i--) {
                list[i + 1] = list[i];
            }
            list[index] = obj;
            count++;
        }
    }

    /**
     * This method sets the value at a particular index to the passed value.
     *
     * @param index the index to remove object from
     * @param obj the new value you would like to set index to.
     * @return Copiable - the old value that was replaced.
     * @throws numberlist.InvalidIndexException Thrown on invalid index
     */
    @Override
    public Copiable set(int index, Copiable obj) throws InvalidIndexException {
        if (index >= count || index < 0) {
            throw new InvalidIndexException(0, count - 1, index);
        }
        Copiable temp = list[index];
        list[index] = obj;
        return temp;
    }

    /**
     * This method removes the object at the specified index and shifts all
     * other elements after it down to fill the removed elements spot. If an
     * invalid index is entered, null will be returned.
     *
     * @param index the index to remove object from
     * @return Copiable - the object that was removed at given index.
     * @throws numberlist.InvalidIndexException Thrown on invalid index
     */
    @Override
    public Copiable removeAt(int index) throws InvalidIndexException {
        if (index >= 0 && index < count) {
            Copiable temp = list[index];

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
     * This method removes the first appearance of the object passed as a
     * parameter from the array.
     *
     * @param obj the object to remove.
     */
    @Override
    public void remove(Copiable obj) {
        int indexOf = indexOf(obj);

        try {
            removeAt(indexOf);
        } catch (InvalidIndexException iie) {
            System.out.println("Object not found, -1 returned.");
        }
    }

    /**
     * This method returns the object at the requested index. If a index is
     * requested that is out of range, it will return the Long.MIN_VALUE.
     *
     * @param index the index to get Copiable from
     * @return Object - the object at specified index
     * @throws numberlist.InvalidIndexException Thrown on invalid index
     */
    @Override
    public Copiable get(int index) throws InvalidIndexException {

        if (index >= 0 && index < count) {
            return list[index];
        } else {
            throw new InvalidIndexException(0, count - 1, index);
        }
    }

    /**
     * This method finds the index of a given object and returns it. If no
     * object is found, -1 is returned.
     *
     * @param obj The object to find
     * @return int - the index of specified object
     */
    @Override
    public int indexOf(Copiable obj) {
        int index = -1;

        for (int i = 0; i < count && index < 0; i++) {
            if (list[i].toString().equals(obj.toString())) {
                index = i;
                return index;
            }
        }
        return index;
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
                out.append(list[i]).append(" ");
            } else {
                out.append(list[i]).append(", ");
            }
        }
        out.append("]");
        return out.toString();
    }

    /**
     * This method makes a deep copy of a NumericArrayList and returns it.
     *
     * @return NumericArrayList - a deep copy of the NumericArrayList object.
     */
    @Override
    public NumericArrayList deepCopy() {

        NumericArrayList temp = new NumericArrayList();
        for (int i = 0; i < count; i++) {
            try {
                temp.add(temp.getCount(), list[i].deepCopy());
            } catch (InvalidIndexException iie) {
            }
        }
        return temp;
    }
}
