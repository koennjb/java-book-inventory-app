package numberlist.objectlist;

import numberlist.InvalidIndexException;

/**
 * This class defines a NumericLinkedList that stores Copiable data types. It is
 * made up of Nodes which contain a next node and a object to hold a value.
 *
 * @author Koenn Becker
 * @version 1.0 - 4/24/2018
 */
public class NumericLinkedList extends NumericList implements Copiable {

    private Node firstNode;

    /**
     * This method adds a new Node to the given index of the LinkedList. If the
     * specified index is already filled, it will insert the new object in its
     * spot and shift everything, including the object that previously occupied
     * that index, up one spot.
     *
     * @param index the index to insert object at
     * @param obj the Copiable to insert
     */
    @Override
    void add(int index, Copiable obj) throws InvalidIndexException {

        if (index > count || index < 0) {
            throw new InvalidIndexException(0, count, index);
        }

        Node newNode = new Node(obj);

        if (index == 0) {
            newNode.setNext(firstNode);
            firstNode = newNode;
        } else {
            Node currentNode = firstNode;

            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.getNext();
            }
            newNode.setNext(currentNode.getNext());
            currentNode.setNext(newNode);
        }

        count++;

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

        Node currentNode = firstNode;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }
        Copiable temp = currentNode.getValue();
        currentNode.setValue(obj);
        return temp;
    }

    /**
     * This method removes the object at the specified index and shifts all
     * other elements after it down to fill the removed elements spot. If an
     * invalid index is entered, the Long.MIN_VALUE will be returned.
     *
     * @param index the index to remove object from
     */
    @Override
    Copiable removeAt(int index) throws InvalidIndexException {

        Node currentNode;
        Copiable tempReturn;

        if (index >= count || index < 0) {
            throw new InvalidIndexException(0, count - 1, index);
        }
        if (index == 0) {

            tempReturn = firstNode.getValue();
            firstNode = firstNode.getNext();

        } else {
            currentNode = firstNode;

            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.getNext();
            }

            tempReturn = currentNode.getNext().getValue();
            currentNode.setNext(currentNode.getNext().getNext());
        }
        count--;
        return tempReturn;
    }

    /**
     * This method removes the first appearance of the object passed as a
     * parameter from the linked list.
     *
     * @param obj the object to remove.
     */
    @Override
    void remove(Copiable obj) {
        try {
            removeAt(indexOf(obj));
        } catch (InvalidIndexException iie) {
            System.out.println("Object not found, -1 returned.");
        }
    }

    /**
     * This method returns the object at the requested index. If a index is
     * requested that is out of range, it will return null.
     *
     * @param index the index to get Copiable from
     * @return Object - the object at specified index
     */
    @Override
    Copiable get(int index) throws InvalidIndexException {
        if (index >= count || index < 0) {
            throw new InvalidIndexException(0, count - 1, index);
        }

        Node currentNode = firstNode;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }
        return currentNode.getValue();
    }

    /**
     * This method finds the index of a given object and returns it. If no
     * object is found, -1 is returned.
     *
     * @param obj The object to find
     * @return int - the index of specified object
     */
    @Override
    int indexOf(Copiable obj) {

        Node currentNode = firstNode;
        int indexCounter = 0;

        if (currentNode.getValue().toString().equals(obj.toString())) {
            return indexCounter;
        } else {
            for (int i = 1; i < count; i++) {
                currentNode = currentNode.getNext();
                indexCounter++;

                if (currentNode.getValue().toString().equals(obj.toString())) {
                    return indexCounter;
                }
            }
        }
        return -1;  //If no match found
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
        Node currentNode = firstNode;

        for (int i = 0; i < count; i++) {
            if (i == count - 1) {
                out.append(currentNode.getValue().toString()).append(" ");
            } else {
                out.append(currentNode.getValue().toString()).append(", ");
            }
            currentNode = currentNode.getNext();
        }
        out.append("]");
        return out.toString();
    }

    /**
     * This method makes a deep copy of a NumericLinkedList and returns it.
     *
     * @return NumericLinkedList - a deep copy of the NumericLinkedList object.
     */
    @Override
    public NumericLinkedList deepCopy() {
        NumericLinkedList temp = new NumericLinkedList();

        for (int i = 0; i < count; i++) {
            try {
                temp.add(temp.getCount(), get(i).deepCopy());
            } catch (InvalidIndexException iie) {
            }
        }
        return temp;
    }

}
