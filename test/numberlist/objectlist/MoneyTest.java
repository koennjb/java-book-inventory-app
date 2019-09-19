package numberlist.objectlist;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class is to test the Money class and ensure all of its methods are
 * working properly
 *
 * @author Koenn
 */
public class MoneyTest {

    Money testMoney;
    Money negativeTest;

    /**
     *
     */
    @Before
    public void setUp() {

        testMoney = new Money(5, (byte) 0);
        negativeTest = new Money(-1, (byte) -5);

    }

    /**
     * Test of getDollars method, of class Money positive dollars and cents with
     * cents between 0-100
     */
    @Test
    public void testGetDollarsPositive() {

        // Test positive dollars and cents with cents between 0-100
        Money myMoney = new Money(5, (byte) 50);
        assertEquals(5, myMoney.getDollars());

    }

    /**
     * Test of getDollars method, of class Money positive dollars and cents with
     * cents above 100
     */
    @Test
    public void testGetDollarsCentsTest() {

        // Test positive dollars and cents with cents above 100
        Money myMoney = new Money(5, (byte) 123);
        assertEquals(6, myMoney.getDollars());

    }

    /**
     * Test of getCents method, of class Money positive dollars and cents with
     * cents between 0-100
     */
    @Test
    public void testGetCents() {

        // Test positive dollars and cents with cents between 0-100
        Money myMoney = new Money(5, (byte) 50);
        assertEquals(50, myMoney.getCents());

    }

    /**
     * Test of getCents method, of class Money positive dollars and cents with
     * cents more than one hundred
     */
    @Test
    public void testGetCentsMoreThanHundred() {

        // Test positive dollars and cents with cents above 100
        Money myMoney = new Money(5, (byte) 123);
        assertEquals(23, myMoney.getCents());

    }

    /**
     * Test of add method, of class Money to add positive dollars and cents
     */
    @Test
    public void testAddPositive() {

        testMoney = testMoney.add(new Money(1, (byte) 50));
        assertEquals(6, testMoney.getDollars());
        assertEquals(50, testMoney.getCents());

    }

    /**
     * Test of add method, of class Money to add positive dollars and negative
     * cents
     */
    @Test
    public void testAddPosDollar() {

        testMoney = testMoney.add(new Money(1, (byte) -50));
        assertEquals(5, testMoney.getDollars());
        assertEquals(50, testMoney.getCents());

    }

    /**
     * Test of add method, of class Money to add negative dollars and positive
     * cents
     */
    @Test
    public void testAddNegDollar() {

        testMoney = testMoney.add(new Money(-1, (byte) 50));
        assertEquals(4, testMoney.getDollars());
        assertEquals(50, testMoney.getCents());

    }

    /**
     * Test of add method, of class Money to add negative dollars and negative
     * cents
     */
    @Test
    public void testAddBothNegative() {

        testMoney = testMoney.add(new Money(-1, (byte) -50));
        assertEquals(3, testMoney.getDollars());
        assertEquals(50, testMoney.getCents());

    }

    /**
     * Test of subtract method, of class Money to subtract positive dollars and
     * cents
     */
    @Test
    public void testSubtract() {

        testMoney = testMoney.subtract(new Money(1, (byte) 50));
        assertEquals(3, testMoney.getDollars());
        assertEquals(50, testMoney.getCents());

    }

    /**
     * Test of subtract method, of class Money to subtract positive dollars and
     * negative cents
     */
    @Test
    public void testSubtractNegativeCents() {

        testMoney = testMoney.subtract(new Money(1, (byte) -50));
        assertEquals(4, testMoney.getDollars());
        assertEquals(50, testMoney.getCents());

    }

    /**
     * Test of subtract method, of class Money to subtract negative dollars and
     * negative cents
     */
    @Test
    public void testSubtractBothNegative() {

        testMoney = testMoney.subtract(new Money(-1, (byte) -50));
        assertEquals(6, testMoney.getDollars());
        assertEquals(50, testMoney.getCents());

    }

    /**
     * Test of toString method, of class Money for dollar amounts above or equal
     * to 0
     */
    @Test
    public void testToStringPositive() {

        Money myMoney = new Money(0, (byte) 0);
        assertEquals("$0.00", myMoney.toString());

    }

    /**
     * Test of toString method, of class Money for dollar amounts below 0
     */
    @Test
    public void testToStringNegative() {

        Money myMoney = new Money(1, (byte) -125);
        assertEquals("-$0.25", myMoney.toString());

    }

    /**
     * Test of toString method, of class Money for dollar amounts equal to 0
     */
    @Test
    public void testToStringZero() {

        Money myMoney = new Money(0, (byte) 0);
        assertEquals("$0.00", myMoney.toString());

    }

    /**
     * Test of getDollars method, of class Money.
     */
    @Test
    public void testGetDollars() {
    }

    /**
     * Test of add method, of class Money.
     */
    @Test
    public void testAdd() {
    }

    /**
     * Test of toString method, of class Money.
     */
    @Test
    public void testToString() {
    }

    /**
     * Test of deepCopy method, of class Money.
     */
    @Test
    public void testDeepCopy() {

        Money copy = testMoney.deepCopy();
        assertFalse(testMoney == copy);
        assertFalse(Double.compare(testMoney.getDollars(), copy.getDollars()) != 0);
        assertFalse(Double.compare(testMoney.getCents(), copy.getCents()) != 0);
    }

}
