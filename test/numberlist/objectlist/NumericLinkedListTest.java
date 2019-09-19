package numberlist.objectlist;

import numberlist.InvalidIndexException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class tests the NumericLinkedList class to make sure all of its methods
 * are working as expected
 *
 * @author Koenn Becker
 */
public class NumericLinkedListTest {

    NumericLinkedList testList;

    /**
     *
     * @throws InvalidIndexException
     */
    @Before
    public void setUp() throws InvalidIndexException {

        testList = new NumericLinkedList();

        for (int i = 0; i < 10; i++) {

            testList.add(i, new Money(i, (byte) i));

        }

    }

    /**
     * Test of add method, of class NumericLinkedList to add $100 to end of
     * array list.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testAddOne() throws InvalidIndexException {

        testList.add(9, new Money(100, (byte) 0));
        Money testMoney = (Money) testList.get(9);
        assertEquals(100, testMoney.getDollars());

    }

    /**
     * Test of add method with no parameters, of class NumericLinkedList to add
     * $200 to end of linked list.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testAddNoParam() throws InvalidIndexException {

        testList.add(new Money(200, (byte) 0));
        Money testMoney = (Money) testList.get(testList.getCount() - 1);
        assertEquals(200, testMoney.getDollars());

    }

    /**
     * Test of add method, of class NumericLinkedList to add $500 in the middle
     * of linked list.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testAddMiddle() throws InvalidIndexException {

        testList.add(5, new Money(500, (byte) 0));
        Money testMoney = (Money) testList.get(5);
        assertEquals(500, testMoney.getDollars());
    }

    /**
     * Test of add method, of class NumericLinkedList to add two numbers to same
     * index.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testAddTwo() throws InvalidIndexException {

        testList.add(10, new Money(100, (byte) 0));

        testList.add(10, new Money(200, (byte) 0));

        Money moneyOne = (Money) testList.get(10);
        Money moneyTwo = (Money) testList.get(11);
        assertEquals(200, moneyOne.getDollars());
        assertEquals(100, moneyTwo.getDollars());

    }
    
    /**
     * Test of set method.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testSet() throws InvalidIndexException {
        Money temp = (Money) testList.set(0, new Money(100, (byte) 1));
        Money cast = (Money) testList.get(0);
        assertFalse(cast.getDollars() != 100);
        assertFalse(cast.getCents() != 1);
        assertFalse(temp.getDollars() != 0);
        assertFalse(temp.getCents() != 0);

    }

    /**
     * Test of set method at last index.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testSetLast() throws InvalidIndexException {
        Money temp = (Money) testList.set(testList.getCount() - 1, new Money(100, (byte) 1));
        Money cast = (Money) testList.get(testList.getCount() - 1);
        assertFalse(cast.getDollars() != 100);
        assertFalse(cast.getCents() != 1);
        assertFalse(temp.getDollars() != 9);
        assertFalse(temp.getCents() != 9);

    }

    /**
     * Test of set method at invalid index.
     */
    @Test
    public void testSetInvalid(){
        boolean error = false;
        try {
            testList.set(-1, new Money(3, (byte)9));
        } catch (InvalidIndexException ex) {
            error = true;
        }
        assertEquals(true, error);

    }

    /**
     * Test of removeAt method, of class NumericLinkedList to remove the first
     * index.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testRemoveAtFirst() throws InvalidIndexException {

        Money testMoney = (Money) testList.removeAt(5);
        assertEquals(5, testMoney.getDollars());

    }

    /**
     * Test of removeAt method, of class NumericLinkedList to remove the middle
     * index and make sure it returns the removed object
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testRemoveAtMiddle() throws InvalidIndexException {

        testList.removeAt(0);
        Money testMoney = (Money) testList.get(0);
        assertEquals(1, testMoney.getDollars());

    }

    /**
     * Test of removeAt method, of class NumericLinkedList to remove the last
     * index.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testRemoveAtLast() throws InvalidIndexException {

        testList.removeAt(testList.getCount() - 1);
        Money testMoney = (Money) testList.get(testList.getCount() - 1);
        assertEquals(8, testMoney.getCents());

    }

    /**
     * Test of remove method, of class NumericLinkedList to remove $500 from
     * linked list
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testRemove() throws InvalidIndexException {
        testList.add(5, new Money(500, (byte) 0));
        testList.remove(new Money(500, (byte) 0));
        Money testMoney = (Money) testList.get(5);
        assertEquals(5, testMoney.getDollars());

    }

    /**
     * Test of get method, of class NumericLinkedList to get the value of first
     * index.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testGet() throws InvalidIndexException {

        Money testMoney = (Money) testList.get(0);
        assertEquals(0, testMoney.getDollars());

    }

    /**
     * Test of get method for invalid index below 0
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
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testGetCountAdded() throws InvalidIndexException {

        NumericLinkedList myList = new NumericLinkedList();
        myList.add(0, new Money(1, (byte) 0));
        myList.add(0, new Money(2, (byte) 0));
        myList.add(0, new Money(3, (byte) 0));
        myList.add(0, new Money(4, (byte) 0));
        assertEquals(4, myList.getCount());

    }

    /**
     * Test of indexOf method to find 0
     */
    @Test
    public void testIndexOf() {

        assertEquals(0, testList.indexOf(new Money(0, (byte) 0)));

    }

    /**
     * Test of indexOf method to find number not in array
     */
    @Test
    public void testIndexOfInvalid() {

        assertEquals(-1, testList.indexOf(new Money(20, (byte) 0)));

    }

    /**
     * Test of toString method to output entire array
     */
    @Test
    public void testToString() {
        assertEquals("[ $0.00, $1.01, $2.02, $3.03, $4.04, $5.05, $6.06, $7.07,"
                + " $8.08, $9.09 ]", testList.toString());

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

    /**
     * Test of deepCopy method.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testDeepCopy() throws InvalidIndexException {

        NumericLinkedList copy = testList.deepCopy();
        assertFalse(testList == copy);
        assertFalse(testList.getCount() != copy.getCount());
        for (int i = 0; i < copy.getCount(); i++) {
            assertFalse(testList.get(i) == copy.get(i));
            assertFalse(((Money) testList.get(i)).getDollars() != ((Money) copy.get(i)).getDollars());
            assertFalse(((Money) testList.get(i)).getCents() != ((Money) copy.get(i)).getCents());
        }

    }

}
