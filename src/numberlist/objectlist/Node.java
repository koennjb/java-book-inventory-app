package numberlist.objectlist;

/**
 * This class defines a node to be used in a NumericLinkedList. It contains a
 * field for the next node in the list and the Copiable object that is stored in
 * the node.
 *
 * @author Koenn Becker
 * @version 1.0.0 - 4/20/2018
 */
public class Node {

    private Copiable obj;
    private Node nextNode;

    /**
     * This is a constructor for Node, which accepts a Copiable object and
     * assigns it to the value associated with the node.
     *
     * @param obj the Copiable object you want to store in node
     */
    public Node(Copiable obj) {

        this.obj = obj;

    }

    /**
     * This method returns the Copiable object that is contained in this node.
     *
     * @return Copiable - the Copiable object stored in this node.
     */
    public Copiable getValue() {
        return obj;
    }

    /**
     * This method returns the node that follows the current node in a linked
     * list.
     *
     * @return Node - returns the next node in a linked list.
     */
    public Node getNext() {
        return nextNode;
    }

    /**
     * This method sets the Copiable object to the object passed through a
     * parameter.
     *
     * @param obj - the new Copiable you want the node to hold.
     */
    public void setValue(Copiable obj) {
        this.obj = obj.deepCopy();
    }

    /**
     * This method sets the next node in the linked list.
     *
     * @param nextNode - the next node in the linked list.
     */
    public void setNext(Node nextNode) {
        this.nextNode = nextNode;

    }

}
