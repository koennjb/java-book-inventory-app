package numberlist.primitivelist;

import java.util.logging.Level;
import java.util.logging.Logger;
import numberlist.InvalidIndexException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Koenn Becker
 */
public class RealArrayListTest {

    RealArrayList testList;

    /**
     *
     */
    @Before
    public void setUp() {

        testList = new RealArrayList();

        for (int i = 0; i < 10; i++) {

            testList.add(i + (double) i / 10);

        }

    }

    /**
     * Test of add method, of class RealArrayList.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testAdd() throws InvalidIndexException {

        testList.add(10.123);
        assertEquals(10.123, testList.get(10), 0);

    }

    /**
     * Test of removeAll method, of class RealArrayList.
     *
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testRemoveAll() throws InvalidIndexException {

        testList.add(500);
        testList.add(5, 500);
        testList.add(1, 500);
        testList.removeAll(500);
        assertEquals(5.5, testList.get(5), 0);
        assertEquals(1.1, testList.get(1), 0);

    }

    /**
     * Test of lastIndexOf method, of class RealArrayList after adding two
     * appearances 500
     */
    @Test
    public void testLastIndexOf() throws InvalidIndexException {

        testList.add(5, 500);
        testList.add(8, 500);
        assertEquals(8, testList.lastIndexOf(500));

    }
    
    /**
     * Test of lastIndexOf method on first index, of class RealArrayList after adding two
     * appearances 500
     * @throws numberlist.InvalidIndexException
     */
    @Test
    public void testFirst() {

        try {
            testList.add(0, 500.0);
            assertEquals(0, testList.lastIndexOf(500.0));
        } catch (InvalidIndexException ex) {
            System.out.println("ERROR");
        }

    }

}
