package numberlist.objectlist;

/**
 * This interface defines an object that can be copied.
 *
 * @author Koenn Becker
 * @version 1.0 - 4/22/2018
 */
public interface Copiable {

    /**
     * This method should make a deep copy of a Copiable object and return it.
     * It should make all fields of the copied object identical to the object
     * you're copying.
     *
     * @return the new Copiable
     */
    Copiable deepCopy();

}
