package numberlist.objectlist;

import numberlist.InvalidIndexException;

/**
 * This abstract class defines an ADT for Numeric data types. Most of its
 * methods are abstract, "add" and "getCount" are the only two concrete methods.
 * The add method adds a Copiable object to the end of the array, and the
 * getCount method returns how many added elements are there in any given list.
 *
 * @author Koenn Becker
 */
abstract class NumericList {

    int count;

    /**
     * This method adds a Copiable object to the given index of the list. If the
     * specified index is already filled, it will insert the new object in its
     * spot and shift everything, including the object that previously occupied
     * that index, up one spot.
     *
     * @param index the index to insert object at
     * @param obj the object to insert
     * @throws numberlist.InvalidIndexException Thrown on invalid index
     */
    abstract void add(int index, Copiable obj) throws InvalidIndexException;

    /**
     * This method adds a Copiable object to the end of the list.
     *
     * @param obj the object to insert
     * @return int the index at which the object was added.
     */
    public int add(Copiable obj) {
        try {
            add(count, obj);
            return count - 1;
        } catch (InvalidIndexException iie) {
            return 0;
        }
    }

    abstract Copiable set(int index, Copiable obj) throws InvalidIndexException;

    /**
     * This method removes a Copiable object at a specified index and returns
     * the removed object if it is in range. If it is out of range, null is
     * returned.
     *
     * @param index the index to remove element from.
     * @return Copiable the object that was removed.
     * @throws numberlist.InvalidIndexException Thrown on invalid index
     */
    abstract Copiable removeAt(int index) throws InvalidIndexException;

    /**
     * This method removes the first instance of a given Copiable object.
     *
     * @param obj the object to remove
     */
    abstract void remove(Copiable obj);

    /**
     * This method returns the Copiable object at the specified index.
     *
     * @param index the index to return Copiable from
     * @return Copiable the Copiable object at specified index.
     * @throws numberlist.InvalidIndexException Thrown on invalid index
     */
    abstract Copiable get(int index) throws InvalidIndexException;

    /**
     * This method finds the first instance of a a Copiable object and returns
     * its index. If no instance is found, -1 should be returned.
     *
     * @param obj the Copiable object to find
     * @return int the index at which the object was found.
     */
    abstract int indexOf(Copiable obj);

    /**
     * Returns how many elements there are in the linked list. These are the
     * only elements the user has access to.
     *
     * @return int - the count of how many elements the user has added to linked
     * list.
     */
    public int getCount() {

        return count;

    }
}
