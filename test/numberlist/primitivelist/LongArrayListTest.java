package numberlist.primitivelist;

import java.util.logging.Level;
import java.util.logging.Logger;
import numberlist.InvalidIndexException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class is to test the LongArrayList methods to make sure they all work as
 * expected
 *
 * @author Koenn Becker
 */
public class LongArrayListTest {

    LongArrayList testList;

    /**
     *
     */
    @Before
    public void setUp() throws InvalidIndexException {

        testList = new LongArrayList();
        for (int i = 0; i < 10; i++) {

            testList.add(i, i);
        }

    }

    /**
     * Test of add method, of class LongArrayList to add 100 to end of array
     * list.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testAddOne() throws InvalidIndexException {

        testList.add(10, 100);
        assertEquals(100, testList.get(10));
        assertEquals(11, testList.getCount());

    }

    /**
     * Test of add method, of class LongArrayList to add 500 in the middle of
     * array list.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testAddMiddle() throws InvalidIndexException {

        testList.add(5, 500);
        assertEquals(500, testList.get(5));
    }

    /**
     * Test of add method, of class LongArrayList to add two numbers to same
     * index.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testAddTwo() throws InvalidIndexException {

        testList.add(10, 100);

        testList.add(10, 200);

        assertEquals(200, testList.get(10));
        assertEquals(100, testList.get(11));

    }

    /**
     * Test of set method.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testSet() throws InvalidIndexException {
        long temp = testList.set(0, 100);
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
        long temp = testList.set(testList.getCount() - 1, 100);
        assertFalse(testList.get(testList.getCount() - 1) != 100);
        assertFalse(temp != 9);

    }

    /**
     * Test of set method at invalid index.
     */
    @Test
    public void testSetInvalid(){
        boolean error = false;
        try {
            testList.set(-1, 100);
        } catch (InvalidIndexException ex) {
            error = true;
        }
        assertEquals(true, error);

    }

    /**
     * Test of removeAt method, of class LongArrayList to remove the first
     * index.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testRemoveAtFirst() throws InvalidIndexException {

        testList.removeAt(0);
        assertEquals(1, testList.get(0));

    }

    /**
     * Test of removeAt method, of class LongArrayList to remove the last index.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testRemoveAtLast() throws InvalidIndexException {

        testList.removeAt(testList.getCount() - 1);
        assertEquals(8, testList.get(testList.getCount() - 1));

    }

    /**
     * Test of remove method, of class LongArrayList to 500 from array list
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testRemove() throws InvalidIndexException {
        testList.add(5, 500);
        testList.remove(500);
        assertEquals(5, testList.get(5));

    }

    /**
     * Test of get method, of class LongArrayList to get the value of first
     * index.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testGet() throws InvalidIndexException {

        assertEquals(0, testList.get(0));

    }

    /**
     * Test of get method for invalid index below 0
     */
    @Test
    public void testGetInvalidIndex() {
        boolean error = false;
        try {
            testList.get(-1);
            error = false;
        } catch (InvalidIndexException iie) {
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
            testList.removeAt(15);
        } catch (InvalidIndexException ex) {
            error = true;
        }
        assertEquals(true, error);

    }

    /**
     * Test of getCount method after adding numbers 0-9 to array
     */
    @Test
    public void testGetCount() {

        assertEquals(10, testList.getCount());

    }

    /**
     * Test of getCount method after removing 3 numbers
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testGetCountRemoved() throws InvalidIndexException {

        testList.removeAt(0);
        testList.removeAt(0);
        testList.removeAt(0);
        assertEquals(7, testList.getCount());

    }

    /**
     * Test of getCount method after adding 4 numbers to a blank array
     */
    @Test
    public void testGetCountAdded() throws InvalidIndexException {

        LongArrayList myList = new LongArrayList();
        myList.add(0, 1);
        myList.add(0, 2);
        myList.add(0, 3);
        myList.add(0, 4);
        assertEquals(4, myList.getCount());

    }

    /**
     * Test of indexOf method to find 0
     */
    @Test
    public void testIndexOf() {

        assertEquals(0, testList.indexOf(0));

    }

    /**
     * Test of indexOf method to find number not in array
     */
    @Test
    public void testIndexOfInvalid() {

        assertEquals(-1, testList.indexOf(20));

    }

    /**
     * Test of toString method to output entire array
     */
    @Test
    public void testToString() {

        assertEquals("[ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]", testList.toString());

    }

    /**
     * Test of toString method to output empty array
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testToStringEmpty() throws InvalidIndexException {

        for (int i = 0; i < 10; i++) {
            testList.removeAt(0);
        }

        assertEquals("[ ]", testList.toString());

    }

}
