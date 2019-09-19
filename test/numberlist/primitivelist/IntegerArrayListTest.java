package numberlist.primitivelist;

import numberlist.InvalidIndexException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Koenn Becker
 */
public class IntegerArrayListTest {

    IntegerArrayList testList;

    /**
     *
     */
    @Before
    public void setUp() {

        testList = new IntegerArrayList();

        for (int i = 0; i < 10; i++) {

            testList.add(i);

        }

    }

    /**
     * Test of add method, of class IntegerArrayList.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testAdd() throws InvalidIndexException {

        testList.add(10);
        assertEquals(10, testList.get(10));

    }

    /**
     * Test of removeAll method, of class IntegerArrayList.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testRemoveAll() throws InvalidIndexException {

        testList.add(500);
        testList.add(5, 500);
        testList.add(1, 500);
        testList.removeAll(500);
        assertEquals(5, testList.get(5));
        assertEquals(1, testList.get(1));

    }

    /**
     * Test of lastIndexOf method, of class IntegerArrayList.
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testLastIndexOf() throws InvalidIndexException {

        testList.add(2, 500);
        testList.add(7, 500);

        assertEquals(7, testList.lastIndexOf(500));

    }
    
    /**
     * Test of lastIndexOf method on first index, of class RealArrayList after adding two
     * appearances 500
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testFirst() {

        try {
            testList.add(0, 500);
            assertEquals(0, testList.lastIndexOf(500));
        } catch (InvalidIndexException ex) {
            System.out.println("ERROR");
        }

    }

}
