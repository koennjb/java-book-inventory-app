package numberlist.primitivelist;

import numberlist.InvalidIndexException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class is to test the DoubleArrayList class to make sure all methods are
 * working as expected.
 *
 * @author Koenn Becker
 */
public class DoubleArrayListTest {

    DoubleArrayList testList;

    /**
     * Sets up array with 10 elements.
     *
     * @throws InvalidIndexException
     */
    @Before
    public void setUp() throws InvalidIndexException {

        testList = new DoubleArrayList();
        for (int i = 0; i < 10; i++) {

            testList.add(i, i + ((double) i / 10));
        }

    }

    /**
     * Test of add method, of class DoubleArrayList to add one double to end of
     * array.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testAddOne() throws InvalidIndexException {

        testList.add(9, 123.8);
        assertEquals(123.8, testList.get(9), 0);

    }

    /**
     * Test of add method, of class DoubleArrayList to add two doubles to end of
     * array.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testAddTwo() throws InvalidIndexException {

        testList.add(9, 123.8);
        testList.add(9, 500.3);
        assertEquals(500.3, testList.get(9), 0);
        assertEquals(123.8, testList.get(10), 0);

    }

    /**
     * Test of set method.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testSet() throws InvalidIndexException {
        double temp = testList.set(0, 100);
        assertFalse(testList.get(0) != 100);
        assertFalse(temp != 0);

    }

    /**
     * Test of set method at last index.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testSetLast() throws InvalidIndexException {
        double temp = testList.set(testList.getCount() - 1, 100);
        assertFalse(testList.get(testList.getCount() - 1) != 100);
        assertFalse(temp != 9.9);

    }

    /**
     * Test of set method at invalid index.
     */
    @Test
    public void testSetInvalid() {
        boolean error = false;
        try {
            testList.set(-1, 100);
        } catch (InvalidIndexException ex) {
            error = true;
        }
        assertEquals(true, error);

    }

    /**
     * Test of get method for invalid index below 0
     *
     */
    @Test
    public void testGetInvalidIndex() {

        boolean error = false;
        try {
            testList.get(-1);
        } catch (InvalidIndexException ex) {
            error = true;
        }
        assertEquals(true, error);

    }

    /**
     * Test of get method for invalid index above count
     */
    @Test
    public void testGetInvalidIndexAbove() {

        boolean error = false;
        try {
            testList.get(15);
        } catch (InvalidIndexException ex) {
            error = true;
        }
        assertEquals(true, error);

    }

    /**
     * Test of remove method for invalid index below 0
     */
    @Test
    public void testRemoveAtInvalid() {

        boolean error = false;
        try {
            testList.removeAt(-1);
        } catch (InvalidIndexException ex) {
            error = true;
        }
        assertEquals(true, error);

    }

    /**
     * Test of removeAt method, of class DoubleArrayList.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testRemoveAt() throws InvalidIndexException {

        testList.removeAt(0);
        assertEquals(1.1, testList.get(0), 0);

    }

    /**
     * Test of remove method, of class DoubleArrayList.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testRemove() throws InvalidIndexException {

        testList.remove(5.5);
        assertEquals(6.6, testList.get(5), 0);

    }

    /**
     * Test of get method, of class DoubleArrayList.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testGet() throws InvalidIndexException {

        assertEquals(9.9, testList.get(9), 0);

    }

    /**
     * Test of indexOf method, of class DoubleArrayList.
     */
    @Test
    public void testIndexOf() {

        assertEquals(5, testList.indexOf(5.5));

    }

    /**
     * Test of getCount method, of class DoubleArrayList.
     */
    @Test
    public void testGetCount() {

        assertEquals(10, testList.getCount());

    }

    /**
     * Test of getCount method, of class DoubleArrayList after removing 3
     * doubles
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testGetCountRemoved() throws InvalidIndexException {
        testList.removeAt(3);
        testList.removeAt(3);
        testList.removeAt(3);
        assertEquals(7, testList.getCount());

    }

    /**
     * Test of toString method, of class DoubleArrayList to output entire array.
     */
    @Test
    public void testToString() {

        assertEquals("[ 0.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8.8, 9.9 ]",
                testList.toString());

    }

    /**
     * Test of toString method, of class DoubleArrayList to output empty array.
     */
    @Test
    public void testToStringEmpty() {

        DoubleArrayList myList = new DoubleArrayList();
        assertEquals("[ ]", myList.toString());

    }

}
