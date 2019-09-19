package numberlist.objectlist;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class is to test the Complex number class.
 *
 * @author Koenn Becker
 */
public class ComplexTest {

    Complex bothPositive;
    Complex realNegative;
    Complex bothNegative;
    Complex imaginaryOnly;
    Complex realOnly;
    Complex bothZero;
    Complex roundTest;

    /**
     *
     */
    @Before
    public void setUp() {

        bothPositive = new Complex(1.2, 2.3);
        realNegative = new Complex(-5.6, 6.7);
        bothNegative = new Complex(-5.6, -6.7);
        imaginaryOnly = new Complex(0, 1.2);
        realOnly = new Complex(2.3, 0);
        bothZero = new Complex(0, 0);
        roundTest = new Complex(111.2345, 111.2999);

    }

    /**
     * Test of getReal method, of class Complex with a positive real
     */
    @Test
    public void testGetReal() {

        Complex testComplex = new Complex(1, 10);
        assertEquals(1, testComplex.getReal(), 3);

    }

    /**
     * Test of getReal method, of class Complex with a negative real
     */
    @Test
    public void testGetRealNegative() {

        Complex testComplex = new Complex(-1, 10);
        assertEquals(-1, testComplex.getReal(), 3);

    }

    /**
     * Test of getImaginary method, of class Complex on a positive imaginary
     * num.
     */
    @Test
    public void testGetImaginary() {

        Complex testComplex = new Complex(1, 10);
        assertEquals(10, testComplex.getImaginary(), 3);

    }

    /**
     * Test of getImaginary method, of class Complex on a negative imaginary
     * num.
     */
    @Test
    public void testGetImaginaryNegative() {

        Complex testComplex = new Complex(1, -10);
        assertEquals(-10, testComplex.getImaginary(), 4);

    }

    /**
     * Test of add method, of class Complex to add positive numbers
     */
    @Test
    public void testAdd() {

        Complex complexOne = new Complex(5, 1);
        Complex complexTwo = new Complex(10, 5);
        Complex sum = complexOne.add(complexTwo);

        assertEquals(15, sum.getReal(), 4);
        assertEquals(6, sum.getImaginary(), 4);

    }

    /**
     * Test of add method, of class Complex to add positive and negative numbers
     */
    @Test
    public void testAddNegPos() {

        Complex complexOne = new Complex(-5, -1);
        Complex complexTwo = new Complex(10, 5);
        Complex sum = complexOne.add(complexTwo);

        assertEquals(5, sum.getReal(), 4);
        assertEquals(4, sum.getImaginary(), 4);

    }

    /**
     * Test of add method, of class Complex to add negative numbers
     */
    @Test
    public void testAddNeg() {

        Complex complexOne = new Complex(-5, -1);
        Complex complexTwo = new Complex(-10, -5);
        Complex sum = complexOne.add(complexTwo);

        assertEquals(-15, sum.getReal(), 4);
        assertEquals(-6, sum.getImaginary(), 4);

    }

    /**
     * Test of subtract method, of class Complex to subtract two positive
     * numbers.
     */
    @Test
    public void testSubtract() {

        Complex complexOne = new Complex(10, 0);
        Complex complexTwo = new Complex(10, 5);
        Complex sum = complexOne.subtract(complexTwo);

        assertEquals(0, sum.getReal(), 4);
        assertEquals(-5, sum.getImaginary(), 4);

    }

    /**
     * Test of subtract method, of class Complex to subtract two negative
     * numbers.
     */
    @Test
    public void testSubtractNegative() {

        Complex complexOne = new Complex(10, 0);
        Complex complexTwo = new Complex(-10, 5);
        Complex sum = complexOne.subtract(complexTwo);

        assertEquals(20, sum.getReal(), 4);
        assertEquals(-5, sum.getImaginary(), 4);

    }

    /**
     * Test of toString method, of class Complex.
     */
    @Test
    public void testToString() {

        assertEquals("1.2 + 2.3i", bothPositive.toString());
        assertEquals("-5.6 + 6.7i", realNegative.toString());
        assertEquals("-5.6 - 6.7i", bothNegative.toString());
        assertEquals("1.2i", imaginaryOnly.toString());
        assertEquals("2.3", realOnly.toString());
        assertEquals("0.0", bothZero.toString());
        assertEquals("111.2 + 111.3i", roundTest.toString());

    }

    /**
     * Test of deepCopy method, of class Complex.
     */
    @Test
    public void testDeepCopy() {

        Complex copy = bothPositive.deepCopy();
        assertFalse(bothPositive == copy);
        assertFalse(Double.compare(bothPositive.getReal(), copy.getReal()) != 0);
        assertFalse(Double.compare(bothPositive.getImaginary(), copy.getImaginary()) != 0);

    }

}
